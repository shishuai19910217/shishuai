package com.ido85.frame.web.rest.security.exceptions;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 验证码错误
 * @author rongxj
 *
 */
public class CaptchaErrorException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1843924556642057946L;

	private Object token;
	
	public CaptchaErrorException(Object token){
		this(token, "验证码错误");
	}
	
	public CaptchaErrorException(Object token, String msg) {
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
