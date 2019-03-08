package com.ido85.seo.keywordrank.vo;

import java.util.Date;

public class RankSearchVisibilityVo {
	private Date crawlDate;
	private Integer competitorId;
	private Integer engineType;
	private Double visibility;
	
	public Integer getEngineType() {
		return engineType;
	}
	public void setEngineType(Integer engineType) {
		this.engineType = engineType;
	}
	public Date getCrawlDate() {
		return crawlDate;
	}
	public void setCrawlDate(Date crawlDate) {
		this.crawlDate = crawlDate;
	}
	public Integer getCompetitorId() {
		return competitorId;
	}
	public void setCompetitorId(Integer competitorId) {
		this.competitorId = competitorId;
	}
	public Double getVisibility() {
		return visibility;
	}
	public void setVisibility(Double visibility) {
		this.visibility = visibility;
	}
	public RankSearchVisibilityVo() {
		super();
	}
	public RankSearchVisibilityVo(Date crawlDate, Integer competitorId, Double visibility, Integer engineType) {
		super();
		this.crawlDate = crawlDate;
		this.competitorId = competitorId;
		this.visibility = visibility;
		this.engineType = engineType;
	}
}
