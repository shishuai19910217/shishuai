package com.ido85.seo.keywordrank.vo;

import java.util.Date;

public class KeywordRankCompareVo {
	private String url;
	private Integer rank;
	private Integer keywordId;
	private Date crawlDate;
	private String upOrDown;
	private String linkUrl;
	private Integer engineType;
	
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public Integer getEngineType() {
		return engineType;
	}
	public void setEngineType(Integer engineType) {
		this.engineType = engineType;
	}
	public String getUpOrDown() {
		return upOrDown;
	}
	public void setUpOrDown(String upOrDown) {
		this.upOrDown = upOrDown;
	}
	public Date getCrawlDate() {
		return crawlDate;
	}
	public void setCrawlDate(Date crawlDate) {
		this.crawlDate = crawlDate;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public Integer getKeywordId() {
		return keywordId;
	}
	public void setKeywordId(Integer keywordId) {
		this.keywordId = keywordId;
	}
	public KeywordRankCompareVo(String url, Integer rank, Integer keywordId, Date crawlDate) {
		super();
		this.url = url;
		this.rank = rank;
		this.keywordId = keywordId;
		this.crawlDate = crawlDate;
	}
	public KeywordRankCompareVo() {
		super();
	}
	public KeywordRankCompareVo(String url, Integer rank, Integer keywordId, Date crawlDate, String linkUrl) {
		super();
		this.url = url;
		this.rank = rank;
		this.keywordId = keywordId;
		this.crawlDate = crawlDate;
		this.linkUrl = linkUrl;
	}
	
}
