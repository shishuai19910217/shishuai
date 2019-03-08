package com.ido85.services.project.dto;

import java.util.Date;

import javax.persistence.Column;

public class ProjectCrawlDto {
	private Integer projectId;

	private String projectName;

	// 2.0新增
	private Integer tenantId;

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Integer getTenantId() {
		return tenantId;
	}

	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

	public String getProjectUrl() {
		return projectUrl;
	}

	public void setProjectUrl(String projectUrl) {
		this.projectUrl = projectUrl;
	}

	public String getIsSubdomain() {
		return isSubdomain;
	}

	public void setIsSubdomain(String isSubdomain) {
		this.isSubdomain = isSubdomain;
	}

	private String projectUrl;

	private String isSubdomain;

}
