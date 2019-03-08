package com.ido85.seo.extlinks.dto;


public class OutProLinkTrendDto {
	private String crawlDate;
	private long extBackLinks;
	private long refDomains;
	
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
