package com.ido85.seo.extlinks.dto;

public class OutDomainDto {
	private String domain;
	private long extLinks;
	private long refDomains;
	private long totalLinks;
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public long getExtLinks() {
		return extLinks;
	}
	public void setExtLinks(long extLinks) {
		this.extLinks = extLinks;
	}
	public long getRefDomains() {
		return refDomains;
	}
	public void setRefDomains(long refDomains) {
		this.refDomains = refDomains;
	}
	public long getTotalLinks() {
		return totalLinks;
	}
	public void setTotalLinks(long totalLinks) {
		this.totalLinks = totalLinks;
	}
	
}
