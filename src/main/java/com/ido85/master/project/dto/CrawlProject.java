package com.ido85.master.project.dto;

public class CrawlProject {
	private Integer projectId;
	private Integer competitorId;
	private String projectName;
	private Integer tenantId; 
	private String projectUrl;
	private String isSubdomain;
	private boolean isCompetitor;
	
	public Integer getCompetitorId() {
		return competitorId;
	}
	public void setCompetitorId(Integer competitorId) {
		this.competitorId = competitorId;
	}
	public void setCompetitor(boolean isCompetitor) {
		this.isCompetitor = isCompetitor;
	}
	public boolean getIsCompetitor() {
		return isCompetitor;
	}
	public void setIsCompetitor(boolean isCompetitor) {
		this.isCompetitor = isCompetitor;
	}
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
}
