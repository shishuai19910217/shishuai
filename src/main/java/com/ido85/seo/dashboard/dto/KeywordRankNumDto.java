package com.ido85.seo.dashboard.dto;

import java.util.Date;

public class KeywordRankNumDto {
	private Date crawlDate;
	private Integer projectId;
	private Integer competitorId;
	private Long num;

	public KeywordRankNumDto() {
	}

	public KeywordRankNumDto(Date crawlDate, Integer projectId, Integer competitorId, Long num) {
		super();
		this.crawlDate = crawlDate;
		this.projectId = projectId;
		this.competitorId = competitorId;
		this.num = num;
	}

	public Date getCrawlDate() {
		return crawlDate;
	}

	public void setCrawlDate(Date crawlDate) {
		this.crawlDate = crawlDate;
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

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}
}
