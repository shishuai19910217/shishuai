package com.ido85.seo.extlinks.dto;

import java.util.Date;

public class OutLinksViewDto {
	private long domainVariety;
	private long extBackLinks;
	private long linksVariety;
	private String projectId;
	private String projectName;
	private long refDomains;
	private String url;
	private Date crawlDate;;
	private String lastUpdate;;
	
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public Date getCrawlDate() {
		return crawlDate;
	}
	public void setCrawlDate(Date crawlDate) {
		this.crawlDate = crawlDate;
	}
	public long getDomainVariety() {
		return domainVariety;
	}
	public void setDomainVariety(long domainVariety) {
		this.domainVariety = domainVariety;
	}
	public long getExtBackLinks() {
		return extBackLinks;
	}
	public void setExtBackLinks(long extBackLinks) {
		this.extBackLinks = extBackLinks;
	}
	public long getLinksVariety() {
		return linksVariety;
	}
	public void setLinksVariety(long linksVariety) {
		this.linksVariety = linksVariety;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public long getRefDomains() {
		return refDomains;
	}
	public void setRefDomains(long refDomains) {
		this.refDomains = refDomains;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
