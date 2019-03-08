package com.ido85.master.user.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.ThreadContext;

import com.ido85.frame.common.utils.CopyUtil;
import com.ido85.frame.common.utils.KeysUtils;
import com.ido85.frame.web.rest.security.Constants;
import com.ido85.frame.web.rest.security.StatelessPrincipal;
import com.ido85.frame.web.rest.utils.CacheUtils;
import com.ido85.frame.web.rest.utils.UserClient;
import com.ido85.frame.web.security.domain.UserOrm;
import com.ido85.master.user.domain.User;
import com.ido85.master.user.dto.UserDto;

public class UserUtils {
//	private static UserResources userResources = SpringContextHolder.getBean(UserResources.class);
	public static final String USER_CACHE = "userCache";
	
	/**
	 * 根据用户id来获取用户信息
	 * @param userId
	 * @return
	 */
	public static User getUser(String userId){
		User user = (User)CacheUtils.get(USER_CACHE, userId);
		if (user ==  null){
			UserOrm userOrm = UserClient.postForGetUserInfoByUserId(userId, Constants.USER_MANAGE_URL, Constants.USER_URI);
			try {
				user = new User();
				CopyUtil.copyProperties(user, userOrm);
				user.setTenantId(userOrm.getTenantId().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(user == null || "".equals(user.getAccessKeyID())){
				return null;
			}
			CacheUtils.put(USER_CACHE, user.getUserId(), user);
		}
		return user;
	}
	
	/**
	 * 用户登陆获取用户信息
	 * @param accessKeyId
	 * @return
	 */
	public static User getUserByAccessKeyId(String accessKeyId){
//		User user = userResources.getUserByUserAccessID(accessKeyId);
		User user = (User)CacheUtils.get(USER_CACHE, accessKeyId);
		if (user ==  null){
			UserOrm userOrm = UserClient.postForGetUserInfo(accessKeyId, Constants.USER_MANAGE_URL, Constants.USER_INFO_URI);
			try {
				user = new User();
				CopyUtil.copyProperties(user, userOrm);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(user == null || "".equals(user.getAccessKeyID())){
				return null;
			}
			CacheUtils.put(USER_CACHE, user.getAccessKeyID(), user);
		}
		return user;
	}
	
	/**
	 * 获取当前用户
	 * @return 取不到返回 null
	 * @throws Exception 
	 */
	public static User getUser() throws Exception{
		StatelessPrincipal principal = (StatelessPrincipal) SecurityUtils.getSubject().getPrincipal();
		if (null == principal) {
			principal = (StatelessPrincipal) ThreadContext.get("principal");
		}
		if (principal != null){
			User user = (User)CacheUtils.get(USER_CACHE, principal.getAccessKeyID());
			if (user ==  null){
				UserOrm userOrm = UserClient.postForGetUserInfo(principal.getAccessKeyID(), Constants.USER_MANAGE_URL, Constants.USER_INFO_URI);
				user = new User();
				CopyUtil.copyProperties(user, userOrm);
				
				if (user == null || "".equals(user.getAccessKeyID())){
					return null;
				}
				CacheUtils.put(USER_CACHE, user.getAccessKeyID(), user);
			}
//			User user = getUserByAccessKeyId(principal.getAccessKeyID());
			
			return user;
		}
		// 如果没有登录，则返回实例化空的User对象。
		return null;
	}
	/***
	 * 得到租户id
	 * @return
	 */
	public static String getTenantID(){
		StatelessPrincipal principal = (StatelessPrincipal) SecurityUtils.getSubject().getPrincipal();
		if (null == principal) {
			principal = (StatelessPrincipal) ThreadContext.get("principal");
		}
		String tenantId = "";
		tenantId = principal==null?"":principal.getTenantID();
		return tenantId;
	}
	
	/**
	 * 清除当前用户缓存
	 * @throws Exception 
	 */
	public static void clearCache() throws Exception{
		UserUtils.clearCache(getUser());
	}
	
	/**
	 * 清除指定用户缓存
	 * @param user
	 */
	public static void clearCache(User user){
		CacheUtils.remove(USER_CACHE,  user.getUserId());
	}
	
	/**
	 * 将传入的userdto 转化为user对象
	 * @param userDto
	 * @return
	 */
	public static User dtoToUser(UserDto userDto){
		User user = new User();
		user.setEmail(userDto.getEmail().trim());
		user.setMobile(userDto.getMobile().trim());
		user.setUsername(userDto.getUsername().trim());
		user.setEmailType("0");
		user.setPassword(userDto.getPassword());
		user.setAccessKeyID(KeysUtils.generateAcceptKeyID());
		user.setSecurityKey(KeysUtils.generateSecurityKeyID());
		user.setSalt(KeysUtils.uuid());
		return user;
	}
}
