package com.ido85.seo.extlinks.dto;


public class OutTrendItemDto {
	private String crawlDate;
	private String id;
	private String name;
	private long extBackLinks;
	private long refDomains;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
}
