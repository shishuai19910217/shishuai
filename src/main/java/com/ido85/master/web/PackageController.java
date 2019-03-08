package com.ido85.master.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ido85.config.properties.BussinessMsgCodeProperties;
import com.ido85.config.properties.WiConstantsProperties;
import com.ido85.frame.common.exceptions.BusinessException;
import com.ido85.frame.common.restful.Resource;
import com.ido85.frame.common.restful.Resources;
import com.ido85.frame.common.utils.DateUtils;
import com.ido85.frame.common.utils.JsonUtil;
import com.ido85.frame.common.utils.StringUtils;
import com.ido85.frame.web.rest.utils.RestUserUtils;
import com.ido85.master.packages.application.TenantPackageApplication;
import com.ido85.master.packages.domain.Packages;
import com.ido85.master.packages.domain.TenantPackage;
import com.ido85.master.packages.dto.ManageTenantPackageDto;
import com.ido85.master.packages.dto.PackageDTO;
import com.ido85.master.packages.dto.PackagePersonalDto;
import com.ido85.master.packages.dto.PackagesDto;
import com.ido85.master.user.application.UserApplication;
import com.ido85.master.user.util.UserUtils;
import com.ido85.services.packages.PackageApi;
import com.ido85.services.user.UserApi;

@RestController
public class PackageController {
	@Inject
	private TenantPackageApplication tenantPackageApp;
	@Inject
	private BussinessMsgCodeProperties msg;
	@Inject
	private WiConstantsProperties wiConstantsProperties;
	@PersistenceContext(unitName="system")
	private EntityManager entity;
	@Inject
	private PackageApi packageservice;
	@Inject
	private UserApi userService;
	@Inject
	private UserApplication userApp;
	/***
	 * 订购套餐
	 * userId
	 * packageId   
	 * startDate 生效开始时间   endDate 结束时间
	 * tenantPackageId  实例id  续费时使用
	 * @param param
	 * @return
	 */
	@RequestMapping("/trust/orderedPackage")
	public Resource<String> orderedPackage(@RequestBody String param) throws Exception{
		
		if(StringUtils.isNull(param)){
			return new Resource<String>("", msg.getProcessStatus("PARAM_IS_NULL"));
		}
		Map<String,Object> map = (Map<String, Object>) JsonUtil.jsonToMap(param);
		
		if(null==map || map.isEmpty()){
			return new Resource<String>("", msg.getProcessStatus("PARAM_IS_NULL"));
		}
		String s = userService.orderPackageAndCreateTenantDB(map);
		if("1".equals(s)){
			return new Resource<String>("1", msg.getProcessStatus("PACKAGE_NOT_EXIST"));
		}else if("2".equals(s)){
			return new Resource<String>("2", msg.getProcessStatus("PACKAGE_NOT_ORDER"));
		}
		if("0".equals(s)){
			return new Resource<String>(msg.getProcessStatus("COMMON_SUCCESS"));
		}
		return new Resource<String>(msg.getProcessStatus("ERROR"));
	}
	/***
	 * 套餐订购页面    所有的套餐列表
	 * @param param isShow
	 * @return
	 */
	@RequestMapping("/seo/getPackageList")
	public Resources<PackagesDto> getPackageList(@RequestBody Map<String, Object> param) throws Exception{
		if(null==param&&param.isEmpty()){
			return new Resources<PackagesDto>(null, msg.getProcessStatus("PARAM_IS_NULL"));
		}
		List<PackagesDto> list = tenantPackageApp.getPackageList(param.get("isShow").toString());
		return new Resources<PackagesDto>((Iterable)list);
	}
	/***
	 * 查询某个套餐规格  订购后页面  
	 * @param param
	 * packageId  套餐id
	 * no
	 * @return
	 */
	@RequestMapping("/test/getpackageByPackageId")
	public Resource<PackagesDto> getpackageByPackageId(@RequestBody Map<String, Object> param) throws Exception{
		if(null==param || param.isEmpty()){
			return new Resource<PackagesDto>(msg.getProcessStatus("PARAM_IS_NULL"));
		}
		PackagesDto  dto = tenantPackageApp.getPackageInfoByPackageId(param.get("packageId").toString());
		return  new Resource<PackagesDto>(dto,msg.getProcessStatus("COMMON_SUCCESS"));
	}
	
	/***
	 * 得到某个应用的套餐实例no
	 * @param param appType 0 SEO  1 orm  个人中心中的
	 * @return
	 */
	@RequestMapping("/seo/getTenantPackage")
	public Resource<PackagePersonalDto> getTenantPackage(@RequestBody Map<String, Object> param) throws Exception{
		if(null==param || param.isEmpty()){
			return new Resource<PackagePersonalDto>(msg.getProcessStatus("PARAM_IS_NULL"));
		}
			String appType = param.get("apptype")==null?"0":param.get("appType").toString();
			param.put("appType", appType);
			String tenantId = RestUserUtils.getTencentId()+"";
			
			List<TenantPackage> tenantPackageList = tenantPackageApp.getPackageExampleAllListByTenantId(tenantId, appType);
			TenantPackage tenantPackage = null;
			if(null!=tenantPackageList&&tenantPackageList.size()>0){
				for(TenantPackage t : tenantPackageList){
					if("0".equals(t.getDelFlag())){//第一个有效的
						tenantPackage = t;
					}
				}
				
			}
			if(null==tenantPackage){//未找到套餐实例
				return new Resource<PackagePersonalDto>(msg.getProcessStatus("RETURN_IS_NULL"));
			}
			//找到所归属的套餐
			Packages packages = tenantPackageApp.getPackageInfoByPackageId(tenantPackage.getPackageId()+"", appType);
			
			String packageStartDate = DateUtils.formatDate(tenantPackage.getPackageStartDate(), "yyyyMMddHHmmss");
			String packageEndDate = DateUtils.formatDate(tenantPackage.getPackageEndDate(), "yyyyMMddHHmmss");
			
			int remainingDay = (int)DateUtils.getDistanceOfTwoDate(new Date(),tenantPackage.getPackageEndDate()) +1;
			PackagePersonalDto packagePersonalDto =  new PackagePersonalDto(packages.getPackageName(), packageStartDate, packageEndDate,
					remainingDay, tenantPackage.getTenantPackageId()+"", packages.getPackageId()+"",tenantPackage.getPackageLevel());
			if(tenantPackage.getPayType().equals("0")){//月费
				packagePersonalDto.setPrice(packages.getPackagePriceMontn());
			}else{
				packagePersonalDto.setPrice(packages.getPackagePriceYear());
			}
			
			if(remainingDay<=0){//过期了
				packagePersonalDto.setIsExpire("1");
			}else{
				packagePersonalDto.setIsExpire("0");
			}
			return new Resource<PackagePersonalDto>(packagePersonalDto,msg.getProcessStatus("COMMON_SUCCESS"));
	}
	/***
	 * startDate 生效开始时间   endDate 结束时间
	 * apptype
	 * packageId
	 * isProbation
	 * userId
	 * 修改wi套餐
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/trust/modifyPackageWi")
    public Resource<String> modifyPackageWi(@RequestBody Map<String, Object> param) throws Exception{
		
    	String s = tenantPackageApp.modifyPackageWi(param);
    	if("1".equals(s)){
			return new Resource<String>("1", msg.getProcessStatus("PACKAGE_NOT_EXIST"));
		}else if("2".equals(s)){
			return new Resource<String>("2", msg.getProcessStatus("PACKAGE_NOT_ORDER"));
		}else if ("3".equals(s)) {
			return new Resource<String>("3", msg.getProcessStatus("OLDPACKAGE_NOT_EXIST"));
		}else if ("4".equals(s)) {
			return new Resource<String>("4", msg.getProcessStatus("PACKAGE_UP"));
		}
    	return new Resource<String>(msg.getProcessStatus("COMMON_SUCCESS"));
    }

	/***
	 * 所有的套餐列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPackageList")
	public Resources<PackageDTO> getPackageList() throws Exception{
		List<PackageDTO> packageDTOList = tenantPackageApp.getPackageList();
		if(null==packageDTOList || packageDTOList.size() == 0){
			return new Resources<PackageDTO>(msg.getProcessStatus("RETURN_IS_NULL"));
		}
		return new Resources<PackageDTO>(packageDTOList);
	} 

	/****
	 * wi 所有的套餐实例
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getTenantPackageList")
	public Resources<ManageTenantPackageDto> getTenantPackageList(@RequestBody List<String> tenantIds) throws Exception{
		List<ManageTenantPackageDto> TenantPackageDtoList = tenantPackageApp.getTenantPackageList(tenantIds);
		if(null==TenantPackageDtoList || TenantPackageDtoList.size() == 0){
//			return new Resources<ManageTenantPackageDto>(msg.getProcessStatus("RETURN_IS_NULL"));
			TenantPackageDtoList = new ArrayList<ManageTenantPackageDto>();
		}
		return new Resources<ManageTenantPackageDto>(TenantPackageDtoList);
	}
	@RequestMapping("/test")
	public void test() throws Exception{
		userApp.createTenantDB(100);
	}
}
