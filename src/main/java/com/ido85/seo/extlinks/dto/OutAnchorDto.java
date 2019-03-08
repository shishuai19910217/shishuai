package com.ido85.seo.extlinks.dto;

public class OutAnchorDto {
	private String anchor;
	private long deletedLinks;
	private long nofollowLinks;
	private long refDomains;
	private long totalLinks;
	public String getAnchor() {
		return anchor;
	}
	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}
	public long getDeletedLinks() {
		return deletedLinks;
	}
	public void setDeletedLinks(long deletedLinks) {
		this.deletedLinks = deletedLinks;
	}
	public long getNofollowLinks() {
		return nofollowLinks;
	}
	public void setNofollowLinks(long nofollowLinks) {
		this.nofollowLinks = nofollowLinks;
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
