package com.ido85.services.user.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.shiro.SecurityUtils;

import com.ido85.frame.common.utils.DateUtils;
import com.ido85.frame.common.utils.StringUtils;
import com.ido85.frame.web.rest.security.StatelessPrincipal;
import com.ido85.master.packages.application.TenantPackageApplication;
import com.ido85.master.packages.domain.Packages;
import com.ido85.master.packages.domain.TenantPackage;
import com.ido85.master.user.application.UserApplication;
import com.ido85.master.user.domain.Tenant;
import com.ido85.master.user.domain.TenantApp;
import com.ido85.master.user.domain.TenantRelation;
import com.ido85.master.user.domain.User;
import com.ido85.master.user.util.UserUtils;
import com.ido85.services.user.UserApi;

@Named
public class UserApiImpl implements UserApi{

	@Inject
	private UserApplication userApp;
	@Inject
	private TenantPackageApplication tenantPackageApp;
	@PersistenceContext(unitName="system")
	private EntityManager em;
	/***
	 * 判断是否是主用户
	 * @param userId
	 * @param appType
	 * @return
	 */
	@Override
	public String isPrimary(String userId,String appType){
//		List<TenantRelation> relations = userApp.getTenantRelationInfosByUserId(userId);
//		if(null != relations && !"".equals(relations) && relations.size() >  0){
//			for (int i = 0; i < relations.size(); i++) {
//				TenantRelation tenRelation = relations.get(i);
//				if(appType.equals(tenRelation.getAppType()) && "1".equals(tenRelation.getRelationFlag())){
//					return tenRelation.getTenantId();
//				}
//			}
//			
//		}
		User user = UserUtils.getUser(userId);
		return user.getTenantId();
		
	} 
	/***
	 * 生成租户信息
	 * @param userId
	 * @param appType
	 * @return 返回租户id
	 */
	@Override
	@Transactional
	public String saveTenant(String userId ,String appType){
		User user = userApp.getUserInfoById(userId);
		Tenant tenant = null;
		int flag = 0;
		String tenantId = "";
		
		if(null != user && !"".equals(user) && !StringUtils.isNull(appType)){
			List<TenantRelation> relations = userApp.getTenantRelationInfosByUserId(userId);
			if(null != relations){
				TenantRelation tempRelation = null;
				for (int i = 0, len = relations.size(); i < len; i++) {
					tempRelation = relations.get(i);
					if(appType.equals(tempRelation.getAppType()) && "1".equals(tempRelation.getRelationFlag())){
						return null;
					}else if(!appType.equals(tempRelation.getAppType()) && "1".equals(tempRelation.getRelationFlag())){
						flag = 1;
						tenantId = tempRelation.getTenantId();
					}
				}
			}
			
			if(flag == 0){
				tenant = new Tenant();
				tenant.setDelFalg("0");
				tenant.setTenantName(user.getUsername());
				tenant = userApp.saveTenant(tenant);
				
				tenantId = tenant.getTenantId();
				tenant = null;
			}
			
			TenantRelation tenantRelation = new TenantRelation();
			tenantRelation.setDelFlag("0");
			tenantRelation.setTenantId(tenantId);
			tenantRelation.setRelationFlag("1");
			tenantRelation.setUserId(user.getUserId());
			tenantRelation.setAppType(appType);
			
			userApp.saveTenantRel(tenantRelation);
			
			TenantApp tenantApp = new TenantApp();
			tenantApp.setAppType(appType);
			tenantApp.setDelFlag("0");
			tenantApp.setTenantId(tenantId);
			
			userApp.saveTenantApp(tenantApp);
			
			StatelessPrincipal principal = (StatelessPrincipal)SecurityUtils.getSubject().getPrincipal();
			if (null!=principal) {
				principal.setTenantID(tenantId);
			}
		}
		
		return tenantId;
	}
	@Transactional
	public String orderPackageAndCreateTenantDB(Map<String, Object> param) throws Exception {
		param.put("isProbation", "0");//是不是试用
		param.put("appType", "0");
		param.put("oldTenantPackageId", null);
		// 查找套餐
		Packages p = tenantPackageApp.getPackageInfoByPackageId(param.get("packageId").toString(), param.get("appType").toString());
		if (null == p) {
			return "1";// 未找到套餐
		}
		String userId = param.get("userId") == null ? "05C686A9-14CC-4B90-9244-B46921999CBC"
				: param.get("userId").toString();
		System.out.println(isPrimary(userId, param.get("appType").toString()));
		String tenantIdOld = isPrimary(userId, param.get("appType").toString()) == null
				? param.get("tenantId").toString() : isPrimary(userId, param.get("appType").toString());// 租户id
		Date startDate = null;
		Date endDate = null;
		String endDatestr = "";
		startDate = DateUtils.parse(param.get("startDate").toString(), "yyyy-MM-dd");
		endDate = DateUtils.parse(param.get("endDate").toString(), "yyyy-MM-dd");
		endDatestr = DateUtils.formatDate(endDate, "yyyy-MM-dd");
		endDatestr = endDatestr + " 23:59:59";
		endDate = DateUtils.parse(endDatestr, "yyyy-MM-dd HH:mm:ss");
		/***
		 * 判断此用户是否可以订购: 此应用是主用户不能订购（购买过此应用下的套餐）
		 * 
		 */
		TenantPackage tenantPackageold = null;
		TenantPackage tenantPackage = null;
		// 判断当前租户下是否存在有效的套餐实例
		List<TenantPackage> list = tenantPackageApp.getPackageExampleListByTenantId(tenantIdOld, param.get("appType").toString());
		if (null != list && list.size() > 0) {
			tenantPackageold = list.get(0);
			if (tenantPackageold.getDelFlag().equals("0")) {
				return "2";// 此用户不能订购套餐
			}
		}
		// 生成实体信息
		tenantPackage = tenantPackageApp.makeTenantPackage(p, param.get("isProbation").toString(), "0", startDate, endDate, tenantIdOld,
				userId);
		// 实例信息入库
		em.persist(tenantPackage);
		if(tenantPackage!=null){
			userApp.createTenantDB(tenantPackage.getTenantId());
		}
		return "0";// 成功
	}

}
