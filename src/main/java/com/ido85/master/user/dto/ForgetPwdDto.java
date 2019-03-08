package com.ido85.master.user.dto;

import javax.validation.constraints.NotNull;

public class ForgetPwdDto {
	@NotNull
	private String newPassword;
	@NotNull
	private String retrieveType;
	@NotNull
	private String retrieveValue;
	private String uuid;
	
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getRetrieveType() {
		return retrieveType;
	}
	public void setRetrieveType(String retrieveType) {
		this.retrieveType = retrieveType;
	}
	public String getRetrieveValue() {
		return retrieveValue;
	}
	public void setRetrieveValue(String retrieveValue) {
		this.retrieveValue = retrieveValue;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	
	
}
