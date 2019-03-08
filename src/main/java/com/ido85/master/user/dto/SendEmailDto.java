package com.ido85.master.user.dto;

import javax.validation.constraints.NotNull;


public class SendEmailDto {
	@NotNull
	private String flag;
	@NotNull
	private String email;
	@NotNull
	private String serverPath;
	public String getServerPath() {
		return serverPath;
	}
	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
