package com.ido85.master.project.dto;

import java.util.List;
import java.util.Map;

public class CheckUrlResultDto{
	private String urlFlag;//0：既没有重定向也不存在已经创建的项目网址，1：网址有重定向;2:超时或无效
	private String redirectUrl;
	public String getUrlFlag() {
		return urlFlag;
	}
	public void setUrlFlag(String urlFlag) {
		this.urlFlag = urlFlag;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	
	

}
