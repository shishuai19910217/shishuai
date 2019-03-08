/**
 * 
 */
package com.ido85.frame.web.rest.security.exceptions;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 设备ID未定义
 * @author rongxj
 *
 */
public class DeviceIDNoDefinetionException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2283959472058526231L;
	
	private Object token;
	
	public DeviceIDNoDefinetionException(Object token){
		this(token, "设备ID未定义");
	}
	
	public DeviceIDNoDefinetionException(Object token, String msg) {
		super(msg);
		this.token = token;
	}
	
	public Object getToken() {
		return token;
	}
	
	public void setToken(Object token) {
		this.token = token;
	}

}
