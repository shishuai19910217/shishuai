/**
 * jeesite-master
 * laula
 * 2015-9-19
 */
package com.ido85.frame.web.shiro.session;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;



import com.ido85.frame.common.utils.StringUtils;
import com.ido85.frame.web.rest.utils.CacheUtils;

/**
 * 获取SESSION ID
 */
public class RestSessionUtil {
	
	public static final String getRestRequestSessionId(ServletRequest request){
		HttpServletRequest httpRequest = (HttpServletRequest)request;
//		String sid = request.getParameter(Global.getConfig("stateless.security.authorization.param.accesskey.name"));
//		if (StringUtils.isNotBlank(sid)) {
//        	return sid;
//		}else{
//			
//			
//		}
//		String header = httpRequest.getHeader("DeviceID");
//		if(StringUtils.isNotBlank(header)){
//			return header;
//		}
		String header = httpRequest.getHeader("Authorization");
		if(StringUtils.isNotBlank(header)){
			String[] s = header.split(":");
			if(s.length > 0 && StringUtils.isNotBlank(s[0])){
				String accessKeyId = s[0];
				return getRestSessionId(accessKeyId);
			}
		}
		return null;
	}
	
	/**
	 * 更新当前session
	 * @param accessKeyID
	 * @param deviceID
	 */
	public static synchronized final void updateRestSessionId(String accessKeyID, String sessionId, String securityKey){
		StatelessSessionKey sessionKey = getRestSessionKey(accessKeyID);
		if(sessionKey != null){
			sessionKey.setSecurityKey(securityKey);
			sessionKey.setSessionId(sessionId);
		}else{
			sessionKey = new StatelessSessionKey();
			sessionKey.setSecurityKey(securityKey);
			sessionKey.setSessionId(sessionId);
		}
		CacheUtils.put(CacheUtils.REST_SESSION_KEY, accessKeyID, sessionKey);
	}
	
	/**
	 * 更新当前session
	 * @param accessKeyID
	 * @param deviceID
	 */
	public static synchronized final void updateRestSessionId(String accessKeyID, String sessionId){
		StatelessSessionKey sessionKey = getRestSessionKey(accessKeyID);
		if(sessionKey != null){
			sessionKey.setSessionId(sessionId);
		}else{
			sessionKey = new StatelessSessionKey();
			sessionKey.setSessionId(sessionId);
		}
		CacheUtils.put(CacheUtils.REST_SESSION_KEY, accessKeyID, sessionKey);
	}

	
	/**
	 * 根据accessId 获取sessionId
	 * @param accessKeyID
	 * @return
	 */
	public static synchronized final String getRestSessionId(String accessKeyID){
		StatelessSessionKey sessionKey = getRestSessionKey(accessKeyID);
		if(sessionKey != null){
			return sessionKey.getSessionId();
		}
		return null;
	}
	
	/**
	 * 根据认证ID
	 * @param accessKeyID
	 * @return
	 */
	public static final StatelessSessionKey getRestSessionKey(String accessKeyID){
		return (StatelessSessionKey)CacheUtils.get(CacheUtils.REST_SESSION_KEY, accessKeyID);
	}
	
	/**
	 * 根据accessKeyID获取加密key
	 * @param accessKeyID
	 * @return
	 */
	public static final String getSecurityKey(String accessKeyID){
		StatelessSessionKey sessionKey = getRestSessionKey(accessKeyID);
		if(sessionKey != null){
			return sessionKey.getSecurityKey();
		}
		return null;
	}
	
	public static class StatelessSessionKey implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = -2971795602547481017L;
		private String sessionId;
		private String deviceId;
		private String securityKey;
		
		public StatelessSessionKey(){
			
		}
		
		
		public StatelessSessionKey(String sessionId, String deviceId){
			this.sessionId = sessionId;
			this.deviceId = deviceId;
		}
		
		public StatelessSessionKey(String sessionId, String deviceId, String securityKey){
			this.sessionId = sessionId;
			this.deviceId = deviceId;
			this.securityKey = securityKey;
		}


		public String getSessionId() {
			return sessionId;
		}

		public void setSessionId(String sessionId) {
			this.sessionId = sessionId;
		}

		public String getDeviceId() {
			return deviceId;
		}

		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}

		public String getSecurityKey() {
			return securityKey;
		}

		public void setSecurityKey(String securityKey) {
			this.securityKey = securityKey;
		}
	}
}
