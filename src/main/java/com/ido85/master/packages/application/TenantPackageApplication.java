package com.ido85.master.packages.application;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.ido85.master.packages.domain.PackageElementsRel;
import com.ido85.master.packages.domain.Packages;
import com.ido85.master.packages.domain.TenantPackage;
import com.ido85.master.packages.dto.ManageTenantPackageDto;
import com.ido85.master.packages.dto.PackageDTO;
import com.ido85.master.packages.dto.PackagePersonalDto;
import com.ido85.master.packages.dto.PackagesDto;


/**
 * 套餐实例相关
 * 
 * @author shishuai
 *
 */

public interface TenantPackageApplication {
	/***
	 * 根据租户查找所有 的套餐规格
	 * 
	 * @param tenantId
	 * @return
	 */
	public List<TenantPackage> getPackageExampleListByTenantId(String tenantId, String appType);
	/***
	 * 根据租户查找所有 的套餐规格
	 * 
	 * @param tenantId
	 * @return
	 */
	public List<TenantPackage> getBatchPackageExampleListByTenantId(List<Integer> tenantIds);

	/***
	 * 订购套餐
	 * 
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public String orderedPackage(Map<String, Object> param) throws Exception;
	
	
	/***
	 * 订购套餐
	 * 
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public String orderedPackageWi(Map<String, Object> param) throws Exception;
	/***
	 * 修改套餐
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public String modifyPackageWi(Map<String, Object> param) throws Exception;
	
	

	/***
	 * 
	 * @param p
	 * @param isProbation
	 *            是否试用
	 * @param payType
	 *            付费 按月 还是按年
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @param tenantId
	 *            租户id
	 * @return
	 * @throws Exception 
	 */

	public TenantPackage makeTenantPackage(Packages p,String isProbation,String payType,Date startDate,Date endDate,String tenantId,String userId) throws Exception;

	/***
	 * 查询所有套餐
	 * 
	 * @param appType
	 * @return
	 */
	public List<PackagesDto> getPackageList(String isShow) throws Exception;

	/*
	 * **********上方是应用服务私有方法不对别的模块和别的应用直接提供提供***********
	 */

	/*
	 * ********************此处之下是service服务层调用的公共方法，若要修改，请慎重操作********************
	 * *
	 */
	/**
	 * 根据套餐实例id获取套餐实例信息
	 * 
	 * @param tenantId
	 * @return
	 */
	public TenantPackage getTenantPackageInfo(String tenantId);

	/***
	 * 查询租户下所有的套餐实例 包含到期的 删除的
	 * 
	 * @param tenantId
	 * @param appType
	 * @return
	 */
	public List<TenantPackage> getPackageExampleAllListByTenantId(String tenantId, String appType);

	/**
	 * 根据套餐id获取套餐信息
	 * 
	 * @param packageId
	 * @return
	 */
	public Packages getPackageInfoByPackageId(String packageId, String appType);

	/**
	 * 根据套餐id获取套餐信息
	 * 
	 * @param packageId
	 * @return
	 */
	public PackagesDto getPackageInfoByPackageId(String packageId);

	/**
	 * 根据套餐id获取套餐信息
	 */
	public PackagePersonalDto getPackagePersonalInfoByPackageId(String packageId);

	/**
	 * 查询租户下的所有套餐
	 * 
	 * @param tenantId
	 * @return
	 */
	public List<TenantPackage> getPackageExampleListByTenantId(@Param("tenantId") String tenantId);

	/**
	 * 保存套餐实例和套餐实例与元素关系
	 * 
	 * @param tenantPackage
	 * @return
	 */
	void savePackageInstance(Packages pack, String tenantId) throws Exception;
	
	
	public List<PackageDTO> getPackageList() throws Exception;

	public List<Map<String, Object>> getPackageSpecifications(List<PackageElementsRel> rels) throws Exception ;
	/****
	 * wi 套餐实例列表
	 * @param tenantIds
	 * @return
	 */
	public List<ManageTenantPackageDto> getTenantPackageList(List<String> tenantIds) throws Exception ;
	
	/***
	 * 修改套餐
	 * @param tenantPackageId  原套餐id
	 * @param tenantId
	 * @param packageEndDate
	 * @throws Exception
	 */
	void updateTenantPackagePackageEndDate(String tenantPackageId, String tenantId, Date packageEndDate,String userId)
			throws Exception;
}
