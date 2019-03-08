/**
 * 
 */
package com.ido85.frame.web.rest.dto;

import java.util.List;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ido85.frame.web.UserInfo;

/**
 * @author rongxj
 *
 */
public class RestUserDTO implements UserInfo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3334752006472712340L;

	private boolean isLogon = false;
	private String AccessKeyID = "";
	private String AccessSecurityKey = "";
	private String Token="";
	@Transient
	private String deviceID = "";
	@Transient
	private String delFlag = "0";//用户状态 0、正常 1 、冻结
	@Transient
	private String username = "";
	@Transient
	private String salt = "";
	@Transient
	private String password = "";
	private Integer userId;
	private Integer tenantID;
	
	
	public Integer getTenantID() {
		return tenantID;
	}


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public RestUserDTO(){
		
	}
	
	public RestUserDTO(boolean isLogon, String accessKeyID, String accessSecurityKey) {
		super();
		this.isLogon = isLogon;
		AccessKeyID = accessKeyID;
		AccessSecurityKey = accessSecurityKey;
	}

	public boolean isLogon() {
		return isLogon;
	}

	public void setLogon(boolean isLogon) {
		this.isLogon = isLogon;
	}

	public String getAccessKeyID() {
		return AccessKeyID;
	}
	
	public void setAccessKeyID(String accessKeyID) {
		AccessKeyID = accessKeyID;
	}
	
	public String getToken() {
		return Token;
	}
	
	public void setToken(String token) {
		Token = token;
	}
	
	@JsonIgnore
	@Transient
	public String getDeviceID() {
		return deviceID;
	}
	
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	@JsonIgnore
	@Transient
	public String getUserState() {
		return delFlag;
	}

	public void setUserState(String userState) {
		this.delFlag = userState;
	}


	@JsonIgnore
	@Transient
	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}

	@JsonIgnore
	@Transient
	public String getSalt() {
		return salt;
	}


	public void setSalt(String salt) {
		this.salt = salt;
	}

	@JsonIgnore
	@Transient
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	public String getAccessSecurityKey() {
		return AccessSecurityKey;
	}

	public void setAccessSecurityKey(String accessSecurityKey) {
		AccessSecurityKey = accessSecurityKey;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	@Override
	public List<String> getUserTenants() {
		return null;
	}


	public void setTenantID(Integer tenantID) {
		this.tenantID = tenantID;
	}

	
}
