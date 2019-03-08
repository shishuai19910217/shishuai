/**
 * 
 */
package com.ido85.frame.web.shiro.tools;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * shiro 当前用户工具
 * @author rongxj
 *
 */
public class CurrentUser {

	/**
	 * 获取当前用户凭证
	 * @return
	 */
	public static PrincipalCollection getPrincipals(){
		return SecurityUtils.getSubject().getPrincipals();
	}
}
