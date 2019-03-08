package com.ido85.seo.dashboard.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

/***
 * 
 * @author shishuai
 *
 */
@Entity
@Table(name = "tf_b_dashboard_visibility")
@NamedQueries(value = {})
public class DashboardVisibility implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "dashboard_visibility_id")
	private Long dashboardVisibilityId;
	@Column(name = "crawl_date")
	private Date crawlDate;
	@Column(name = "project_id")
	private Integer projectId;
	@Column(name = "competitor_id")
	private Integer competitorId;
	@Column(name = "engine_type")
	private Integer engineType;
	@Column(name = "visibility")
	private Double visibility;
	@Column(name = "del_flag")
	private String delFlag;
	
	public Long getDashboardVisibilityId() {
		return dashboardVisibilityId;
	}

	public void setDashboardVisibilityId(Long dashboardVisibilityId) {
		this.dashboardVisibilityId = dashboardVisibilityId;
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


	public Double getVisibility() {
		return visibility;
	}

	public void setVisibility(Double visibility) {
		this.visibility = visibility;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}


	public Integer getEngineType() {
		return engineType;
	}

	public void setEngineType(Integer engineType) {
		this.engineType = engineType;
	}


	

}
