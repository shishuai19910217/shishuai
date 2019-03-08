package com.ido85.seo.extlinks.dto;

import java.util.Date;

import com.ido85.frame.common.utils.DateUtils;

public class ProjectExtDto {
	private String tenantId;
	private String projectId;
	private String competitorId;
	private String  crawlDate;
	private long extBackLinks;
	private long refDomains;
	private String projectName;
	private String competitorName;
	//项目的
	public ProjectExtDto(Integer projectId, 
			 Date crawlDate, long extBackLinks,
			long refDomains, String projectName) {
		super();
		this.projectId = projectId.toString();
		if(null!=crawlDate){
			this.crawlDate = DateUtils.formatDate(crawlDate, "yyyyMMddHHmmss");
		}
		this.extBackLinks = extBackLinks;
		this.refDomains = refDomains;
		this.projectName = projectName;
	}
	//竞品
	public ProjectExtDto( Date crawlDate, long extBackLinks,
			long refDomains, String competitorName,Integer competitorId) {
		super();
		if(null!=crawlDate){
			this.crawlDate = DateUtils.formatDate(crawlDate, "yyyyMMddHHmmss");
		}
		this.extBackLinks = extBackLinks;
		this.refDomains = refDomains;
		this.competitorName = competitorName;
		this.competitorId = competitorId.toString();
	}

	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getCompetitorId() {
		return competitorId;
	}
	public void setCompetitorId(String competitorId) {
		this.competitorId = competitorId;
	}
	public String getCrawlDate() {
		return crawlDate;
	}
	public void setCrawlDate(String crawlDate) {
		this.crawlDate = crawlDate;
	}
	public long getExtBackLinks() {
		return extBackLinks;
	}
	public void setExtBackLinks(long extBackLinks) {
		this.extBackLinks = extBackLinks;
	}
	public long getRefDomains() {
		return refDomains;
	}
	public void setRefDomains(long refDomains) {
		this.refDomains = refDomains;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getCompetitorName() {
		return competitorName;
	}
	public void setCompetitorName(String competitorName) {
		this.competitorName = competitorName;
	}
}
