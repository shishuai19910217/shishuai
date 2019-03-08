package com.ido85.services.user;

import java.util.Map;

/**
 * 用户服务对本应用内公共接口类
 * @author fire
 *
 */
public interface UserApi {
	/***
	 * 判断是否是主用户
	 * @param userId
	 * @param appType
	 * @return
	 */
	public String isPrimary(String userId,String appType);
	/***
	 * 生成租户信息
	 * @param userId
	 * @param appType
	 * @return 返回租户id
	 */
	public String saveTenant(String userId ,String appType);
	/**
	 * 订购套餐并且声称租户库
	 * @param map
	 * @return 标识
	 * @throws Exception 
	 */
	public String orderPackageAndCreateTenantDB(Map<String, Object> map) throws Exception;
}
