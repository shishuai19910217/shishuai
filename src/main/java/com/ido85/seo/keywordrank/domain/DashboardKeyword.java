package com.ido85.seo.keywordrank.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the tf_b_dashboard_keyword database table.
 * 
 */
@Entity
@Table(name="tf_b_dashboard_keyword")
@NamedQuery(name="DashboardKeyword.findAll", query="SELECT d FROM DashboardKeyword d")
public class DashboardKeyword implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="dashboard_keyword_id")
	private String dashboardKeywordId;

	@Temporal(TemporalType.DATE)
	@Column(name="crawl_date")
	private Date crawlDate;

	@Column(name="del_flag")
	private String delFlag;

	@Column(name="keyword_id")
	private Integer keywordId;

	@Column(name="project_id")
	private Integer projectId;
	
	@Column(name="competitor_id")
	private Integer competitorId;

	@Column(name="engine_type")
	private Integer engineType;
	
	@Column(name="rank")
	private Integer rank;
	
	@Column(name="baidu_record")
	private Integer baiduRecord;
	
	@Column(name="baidu_index")
	private Integer baiduIndex;

	public Integer getBaiduRecord() {
		return baiduRecord;
	}

	public void setBaiduRecord(Integer baiduRecord) {
		this.baiduRecord = baiduRecord;
	}

	public Integer getBaiduIndex() {
		return baiduIndex;
	}

	public void setBaiduIndex(Integer baiduIndex) {
		this.baiduIndex = baiduIndex;
	}

	public DashboardKeyword() {
	}

	public String getDashboardKeywordId() {
		return this.dashboardKeywordId;
	}

	public void setDashboardKeywordId(String dashboardKeywordId) {
		this.dashboardKeywordId = dashboardKeywordId;
	}

	public Date getCrawlDate() {
		return this.crawlDate;
	}

	public void setCrawlDate(Date crawlDate) {
		this.crawlDate = crawlDate;
	}

	public String getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Integer getKeywordId() {
		return this.keywordId;
	}

	public void setKeywordId(Integer keywordId) {
		this.keywordId = keywordId;
	}

	public Integer getProjectId() {
		return this.projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getRank() {
		return this.rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
	public Integer getCompetitorId() {
		return competitorId;
	}

	public void setCompetitorId(Integer competitorId) {
		this.competitorId = competitorId;
	}

	public Integer getEngineType() {
		return engineType;
	}

	public void setEngineType(Integer engineType) {
		this.engineType = engineType;
	}

}