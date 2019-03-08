package com.ido85.seo.keywordrank.dto;

import java.io.Serializable;

public class OutSERPReportDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer linkDomainNum;
	private Integer linkPageNum;
	private String title;
	private String linkPageUrl;
	private Integer rank;
	private String url;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getLinkDomainNum() {
		return linkDomainNum;
	}
	public void setLinkDomainNum(Integer linkDomainNum) {
		this.linkDomainNum = linkDomainNum;
	}
	public Integer getLinkPageNum() {
		return linkPageNum;
	}
	public void setLinkPageNum(Integer linkPageNum) {
		this.linkPageNum = linkPageNum;
	}
	public String getLinkPageUrl() {
		return linkPageUrl;
	}
	public void setLinkPageUrl(String linkPageUrl) {
		this.linkPageUrl = linkPageUrl;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
