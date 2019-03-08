package com.ido85.seo.extlinks.dto;

public class OutDownUrlDto {
	private String crawlResult;
	private long extLinks;
	private String lastDate;
	private String redirectUrl;
	private long refDomains;
	private String title;
	private String url;
	public String getCrawlResult() {
		return crawlResult;
	}
	public void setCrawlResult(String crawlResult) {
		this.crawlResult = crawlResult;
	}
	public long getExtLinks() {
		return extLinks;
	}
	public void setExtLinks(long extLinks) {
		this.extLinks = extLinks;
	}
	public String getLastDate() {
		return lastDate;
	}
	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public long getRefDomains() {
		return refDomains;
	}
	public void setRefDomains(long refDomains) {
		this.refDomains = refDomains;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}


}
