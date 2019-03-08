/**
 * 
 */
package com.ido85.frame.web;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息
 * @author rongxj
 *
 */
public interface UserInfo extends Serializable{

	String getAccessKeyID();
	String getAccessSecurityKey();
	Integer getTenantID();
	List<String> getUserTenants();
	Integer getUserId();
}
