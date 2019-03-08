package com.ido85.seo.keywordrank.vo;

import java.util.Date;

public class ProRankVo {
	private Integer competitorId;
	private Date crawlDate;
	private Integer rank;
	private Integer keywordId;
	private Integer engineType;
	private Long rankNum;
	
	public ProRankVo(Integer competitorId, Date crawlDate, Long rankNum) {
		super();
		this.competitorId = competitorId;
		this.crawlDate = crawlDate;
		this.rankNum = rankNum;
	}
	public ProRankVo() {
		super();
	}
	public ProRankVo(Integer competitorId, Date crawlDate, Integer rank, Integer keywordId, Integer engineType) {
		super();
		this.competitorId = competitorId;
		this.crawlDate = crawlDate;
		this.rank = rank;
		this.keywordId = keywordId;
		this.engineType = engineType;
	}
	public Long getRankNum() {
		return rankNum;
	}
	public void setRankNum(Long rankNum) {
		this.rankNum = rankNum;
	}
	public Integer getEngineType() {
		return engineType;
	}
	public void setEngineType(Integer engineType) {
		this.engineType = engineType;
	}
	public Integer getKeywordId() {
		return keywordId;
	}
	public void setKeywordId(Integer keywordId) {
		this.keywordId = keywordId;
	}
	public Integer getCompetitorId() {
		return competitorId;
	}
	public void setCompetitorId(Integer competitorId) {
		this.competitorId = competitorId;
	}
	public Date getCrawlDate() {
		return crawlDate;
	}
	public void setCrawlDate(Date crawlDate) {
		this.crawlDate = crawlDate;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
}
