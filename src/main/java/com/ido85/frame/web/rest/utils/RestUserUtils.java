package com.ido85.frame.web.rest.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.ThreadContext;

import com.ido85.frame.web.UserInfo;
import com.ido85.frame.web.rest.security.Constants;
import com.ido85.frame.web.rest.security.StatelessPrincipal;
import com.ido85.frame.web.security.domain.UserOrm;

public class RestUserUtils {
	public static final String USER_CACHE = "userCache";
	/**
	 * 获取user
	 * @param accessKeyID
	 * @return
	 */
	public static final UserInfo getUserInfo() {
		StatelessPrincipal principal = (StatelessPrincipal) SecurityUtils.getSubject().getPrincipal();
		if (null == principal) {
			principal = (StatelessPrincipal) ThreadContext.get("principal");
		}
		return getUserInfo(principal.getAccessKeyID());
	}
	
	public static Integer getTencentId() {
		UserInfo userInfo = getUserInfo();
		return userInfo == null ? null : userInfo.getTenantID();
	}
	
	
	/**
	 * 获取user
	 * @param accessKeyID
	 * @return
	 */
	public static UserInfo getUserInfo(String accessKeyID) {
		UserOrm user = (UserOrm)CacheUtils.get(USER_CACHE, accessKeyID);
		if (user ==  null){
			user = UserClient.postForGetUserInfo(accessKeyID, Constants.USER_MANAGE_URL, Constants.USER_INFO_URI);
			if(user == null || "".equals(user.getAccessKeyID())){
				return null;
			}
			CacheUtils.put(USER_CACHE, user.getAccessKeyID(), user);
		}
		return user;
	}
}
