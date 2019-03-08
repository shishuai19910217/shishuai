package com.ido85.seo.dashboard.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class KeywordRankRangeNumDto {
	private Long dashboardKeywordId;

	private Date crawlDate;

	private Integer keywordId;

	private String keywordName;
	
	private String isBrand;
	
	private Integer projectId;

	private Integer competitorId;

	private int rank;
	
	private int engineType;

	public Long getDashboardKeywordId() {
		return dashboardKeywordId;
	}

	public void setDashboardKeywordId(Long dashboardKeywordId) {
		this.dashboardKeywordId = dashboardKeywordId;
	}

	public Date getCrawlDate() {
		return crawlDate;
	}

	public void setCrawlDate(Date crawlDate) {
		this.crawlDate = crawlDate;
	}

	public Integer getKeywordId() {
		return keywordId;
	}

	public void setKeywordId(Integer keywordId) {
		this.keywordId = keywordId;
	}

	public String getKeywordName() {
		return keywordName;
	}

	public void setKeywordName(String keywordName) {
		this.keywordName = keywordName;
	}

	public String getIsBrand() {
		return isBrand;
	}

	public void setIsBrand(String isBrand) {
		this.isBrand = isBrand;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getCompetitorId() {
		return competitorId;
	}

	public void setCompetitorId(Integer competitorId) {
		this.competitorId = competitorId;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getEngineType() {
		return engineType;
	}

	public void setEngineType(int engineType) {
		this.engineType = engineType;
	}
}
