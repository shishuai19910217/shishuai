package com.ido85.frame.common.utils;

import java.util.Date;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ido85.frame.web.rest.security.TrustAccessKeyEnum;

public class HttpClient {
	private static RestTemplate restTemplate = new RestTemplate();
	
	/**
	 * 根据用户的accesskeyid获取用户信息
	 * @param accessKeyId
	 * @param url
	 * @param uri
	 * @return
	 */
    public static String postForObject(String accessKeyId, String url, String uri) {
    	String res = null;
    	MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        String datetime = DateUtils.formatISO8601Date(new Date());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Datetime", datetime);
       
        headers.add("Authorization",  TrustAccessKeyEnum.ORM_ACCESS_KEY_ID.getCode() + ":" + HmacSHA256Utils.digest(
				TrustAccessKeyEnum.ORM_SECURITY_KEY.getCode(),
				"POST" + uri + datetime));
    	HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
    	res = restTemplate.postForObject(url+ uri + accessKeyId, request, String.class);
//    	if(ResourceUtils.checkResult(StringUtils.toString(res))){
//		}
		return res;
	}
    
}
