package com.ido85.master.project.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

/***
 * 创建项目入参
 * @author SHISHUAI
 *
 */
public class InProjectDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotNull
	private String businessName;
	private List<Map<String,Object>> competitors;
	private List<String> keywords;
	@NotNull
	private String projectName;
	@NotNull
	private String projectUrl;
	private String subdomain;
	public List<Map<String, Object>> getCompetitors() {
		return competitors;
	}
	public void setCompetitors(List<Map<String, Object>> competitors) {
		this.competitors = competitors;
	}
	public List<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}
	
	
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectUrl() {
		return projectUrl;
	}
	public void setProjectUrl(String projectUrl) {
		this.projectUrl = projectUrl;
	}
	public String getSubdomain() {
		return subdomain;
	}
	public void setSubdomain(String subdomain) {
		this.subdomain = subdomain;
	}
}
