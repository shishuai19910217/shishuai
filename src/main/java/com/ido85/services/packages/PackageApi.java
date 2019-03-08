package com.ido85.services.packages;

import java.util.List;
import java.util.Map;

import com.ido85.master.packages.domain.TenantPackage;
import com.ido85.master.packages.domain.TenantPackageElementsRel;
import com.ido85.master.packages.dto.PackagePersonalDto;
import com.ido85.master.packages.dto.PackagesDto;
import com.ido85.master.packages.dto.TenantPackageDto;

/**
 * 套餐相关
 * 
 * @author shishuai
 *
 */
public interface PackageApi {
	/***
	 * 公共方法 根据租户查找所有 的套餐规格 有效的
	 * 
	 * @param tenantId
	 *            租户id
	 * @param appType
	 *            应用类型
	 * @return
	 */
	public List<TenantPackage> getPackageExampleListByTenantId(String tenantId, String appType);
	/***
	 * 公共方法 根据租户批量查找所有的套餐规格 有效的
	 * 
	 * @param tenantId
	 *            租户id
	 * @param appType
	 *            应用类型
	 * @return
	 */
	public List<TenantPackage> getBatchPackageExampleListByTenantId(List<Integer> tenantId);

	/****
	 * 公共方法 查询租户下 某个应用下的所有套餐 包含到期的
	 * 
	 * @param tenantId
	 * @param appType
	 * @return
	 */
	public List<TenantPackage> getPackageExampleAllListByTenantId(String tenantId, String appType);

	/***
	 * 公共方法 根据租户查找所有 的套餐规格 实例
	 * 
	 * @param tenantId
	 *            租户id
	 * @param appType
	 * @return
	 */
	public List<TenantPackageDto> getPackageExampleListByTenantId(String tenantId);

	/**
	 * 根据套餐id获取套餐信息
	 * 
	 * @param packageId
	 * @return
	 */
	public PackagePersonalDto getPackagePersonalInfoByPackageId(String packageId);

	/**
	 * 公共方法 根据套餐id查询套餐的所有信息
	 * 
	 * @param packageId
	 * @return
	 */
	public PackagesDto getPackegeInfoById(String packageId);

	/**
	 * 用户订购套餐，保存套餐实例信息、套餐实例和元素关系
	 * 
	 * @param tenantId
	 * @param packageId
	 *            调用此函数之前请先校验套餐id是否正确
	 * @return
	 * @throws Exception
	 */
	public boolean savePackageInstance(String tenantId, String packageId) throws Exception;
	public List<Map<String,Object>> getScope(List<TenantPackageElementsRel> list);
	public String getEngine(List<Map<String,Object>> temp);
}
