package com.ido85.master.user.dto;

public class UserInfo {
	private Integer tenantID;
	private Integer userId;
	private String accessKeyID;
	private String accessSecurityKey; 
	public String getAccessSecurityKey() {
		return accessSecurityKey;
	}
	public void setAccessSecurityKey(String accessSecurityKey) {
		this.accessSecurityKey = accessSecurityKey;
	}
	public Integer getTenantID() {
		return tenantID;
	}
	public void setTenantID(Integer tenantID) {
		this.tenantID = tenantID;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getAccessKeyID() {
		return accessKeyID;
	}
	public void setAccessKeyID(String accessKeyID) {
		this.accessKeyID = accessKeyID;
	}
}
