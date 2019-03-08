package com.ido85.seo.dashboard.domain;

import java.io.Serializable;
import javax.persistence.*;

import com.ido85.seo.common.BaseEntity;

import java.util.Date;


/**
 * The persistent class for the tf_b_dashboard_keyword database table.
 * 
 */
@Entity
@Table(name="tf_b_dashboard_keyword")
@NamedQuery(name="DashKeyword.findAll", query="SELECT d FROM DashKeyword d")
public class DashKeyword  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="dashboard_keyword_id")
	private Long dashboardKeywordId;

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
	
	@Column(name="rank")
	private int rank;
	@Column(name="engine_type")
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
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public Integer getKeywordId() {
		return keywordId;
	}
	public void setKeywordId(Integer keywordId) {
		this.keywordId = keywordId;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
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
	public Integer getCompetitorId() {
		return competitorId;
	}
	public void setCompetitorId(Integer competitorId) {
		this.competitorId = competitorId;
	}
	

}