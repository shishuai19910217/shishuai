package com.ido85.config.properties;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Named(value = "smsProperties")
@ConfigurationProperties(prefix = "sms", locations = "application.properties", merge = true)
public class SmsProperties {
	@Value("${sms.apiKey}")
	private String apiKey;
	@Value("${sms.registerTemplate}")
	private String registerTemplate;
	@Value("${sms.resetTemplate}")
	private String resetTemplate;
	@Value("${sms.updateMobile}")
	private String updateMobile;
	@Value("${sms.commonTemplate}")
	private String commonTemplate;
	
	public String getCommonTemplate() {
		return commonTemplate;
	}
	public void setCommonTemplate(String commonTemplate) {
		this.commonTemplate = commonTemplate;
	}
	public String getUpdateMobile() {
		return updateMobile;
	}
	public void setUpdateMobile(String updateMobile) {
		this.updateMobile = updateMobile;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getRegisterTemplate() {
		return registerTemplate;
	}
	public void setRegisterTemplate(String registerTemplate) {
		this.registerTemplate = registerTemplate;
	}
	public String getResetTemplate() {
		return resetTemplate;
	}
	public void setResetTemplate(String resetTemplate) {
		this.resetTemplate = resetTemplate;
	}

}
