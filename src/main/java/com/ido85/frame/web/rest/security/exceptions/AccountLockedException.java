/**
 * 
 */
package com.ido85.frame.web.rest.security.exceptions;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author IBM
 *
 */
public class AccountLockedException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2916833005472280930L;
	
	private Object token;
	
	public AccountLockedException(Object token){
		this(token, "账号被禁用");
	}
	
	public AccountLockedException(Object token, String msg) {
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
