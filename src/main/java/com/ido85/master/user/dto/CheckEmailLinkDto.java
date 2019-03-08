package com.ido85.master.user.dto;

import javax.validation.constraints.NotNull;

public class CheckEmailLinkDto {
	@NotNull
	private String email;
	@NotNull
	private String uuid;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	
	
}
