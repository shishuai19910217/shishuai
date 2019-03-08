package com.ido85.frame.web.rest.utils;

import java.util.Date;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ido85.frame.common.restful.ResourceUtils;
import com.ido85.frame.common.utils.DateUtils;
import com.ido85.frame.common.utils.HmacSHA256Utils;
import com.ido85.frame.common.utils.JsonUtil;
import com.ido85.frame.common.utils.StringUtils;
import com.ido85.frame.web.rest.security.TrustAccessKeyEnum;
import com.ido85.frame.web.security.domain.UserOrm;

public class UserClient {
//	private static String ORM_USER_CACHE = "ormUserCache";
	public static final String USER_CACHE = "userCache";
	
	private static RestTemplate restTemplate = new RestTemplate();
	
	
	/**
	 * 根据用户的accesskeyid获取用户信息
	 * @param accessKeyId
	 * @param url
	 * @param uri
	 * @return
	 */
    public static UserOrm postForGetUserInfo(String accessKeyId, String url, String uri) {
    	UserOrm user = (UserOrm)CacheUtils.get(USER_CACHE, accessKeyId);
		if (user ==  null){
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
	        String datetime = DateUtils.formatISO8601Date(new Date());

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.set("Datetime", datetime);
	       
	        headers.add("Authorization",  TrustAccessKeyEnum.ORM_ACCESS_KEY_ID.getCode() + ":" + HmacSHA256Utils.digest(
					TrustAccessKeyEnum.ORM_SECURITY_KEY.getCode(),
					"POST" + uri + accessKeyId + datetime));
	    	HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
	    	String object = restTemplate.postForObject(url + uri + accessKeyId, request, String.class);
	    	if(ResourceUtils.checkResult(StringUtils.toString(object))){
				user = (UserOrm) JsonUtil.jsonToBean(JsonUtil.objectToJson(JsonUtil.getJsonValue(StringUtils.toString(object), "data")), UserOrm.class);
			}
			if(user == null || "".equals(user.getAccessKeyID())){
				return null;
			}
			CacheUtils.put(USER_CACHE, user.getAccessKeyID(), user);
		}
    	
		return user;
	}
    
	/**
	 * 根据用户的userId获取用户信息
	 * @param userId
	 * @param url
	 * @param uri
	 * @return
	 */
    public static UserOrm postForGetUserInfoByUserId(String userId, String url, String uri) {
    	UserOrm user = (UserOrm)CacheUtils.get(USER_CACHE, userId);
		if (user ==  null){
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
	        String datetime = DateUtils.formatISO8601Date(new Date());

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.set("Datetime", datetime);
	       
	        headers.add("Authorization",  TrustAccessKeyEnum.ORM_ACCESS_KEY_ID.getCode() + ":" + HmacSHA256Utils.digest(
					TrustAccessKeyEnum.ORM_SECURITY_KEY.getCode(),
					"POST" + uri + userId + datetime));
	    	HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
	    	String object = restTemplate.postForObject(url + uri + userId, request, String.class);
	    	if(ResourceUtils.checkResult(StringUtils.toString(object))){
				user = (UserOrm) JsonUtil.jsonToBean(JsonUtil.objectToJson(JsonUtil.getJsonValue(StringUtils.toString(object), "data")), UserOrm.class);
			}
			if(user == null || "".equals(user.getAccessKeyID())){
				return null;
			}
			CacheUtils.put(USER_CACHE, user.getAccessKeyID(), user);
		}
    	
		return user;
	}
}
