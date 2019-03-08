package com.ido85.frame.web.rest.security;

import javax.persistence.Transient;


/**
 * rest身份信息
 */
public class StatelessPrincipal extends Principal{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 默认当前时间十年之后失效
	 */
	@Transient
	public static final long DEFAULT_EXPIRES = System.currentTimeMillis() + 10*365*24*60*60*1000;

	/**
	 * accessID
	 */
	private String accessKeyID;
	/**
	 * 设备ID
	 */
	private String deviceID;
	
	/**
	 * 租户ID
	 */
	private String tenantID;
	
	/**
	 * 失效日期
	 */
	private long expirationDate;
	
	public StatelessPrincipal(String accessKeyID){
		this.accessKeyID = accessKeyID;
		this.expirationDate = DEFAULT_EXPIRES;
	}
	
	public StatelessPrincipal(String accessKeyID, String tenantID){
		this.accessKeyID = accessKeyID;
		this.expirationDate = DEFAULT_EXPIRES;
		this.tenantID = tenantID;
	}
	
	public StatelessPrincipal(String accessKeyID, long expirationDate){
		this.accessKeyID = accessKeyID;
		this.expirationDate = expirationDate;
	}
	
	public String getAccessKeyID() {
		return accessKeyID;
	}
	
	public void setAccessKeyID(String accessKeyID) {
		this.accessKeyID = accessKeyID;
	}
	
	@Override
	public String toString() {
		return accessKeyID;
	}

	@Override
	public String getId() {
		return accessKeyID;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public long getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(long expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	public String getTenantID() {
		return tenantID;
	}
	
	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}
}