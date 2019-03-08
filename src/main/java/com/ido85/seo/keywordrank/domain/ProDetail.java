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
 * The persistent class for the tf_b_pro_detail database table.
 * 
 */
@Entity
@Table(name="tf_b_pro_detail")
@NamedQuery(name="ProDetail.findAll", query="SELECT t FROM ProDetail t")
public class ProDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="pro_detail_id")
	private Long proDetailId;

	@Column(name="engine_type")
	private Integer engineType;

	@Column(name="competitor_id")
	private Integer competitorId;

	@Temporal(TemporalType.DATE)
	@Column(name="crawl_date")
	private Date crawlDate;

	@Column(name="del_flag")
	private String delFlag;

	@Column(name="keyword_id")
	private Integer keywordId;

	@Column(name="project_id")
	private Integer projectId;

	@Column(name="rank")
	private Integer rank;

	@Column(name="visibility")
	private Double visibility;

	@Column(name="detail_id")
	private Long detailId;

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public ProDetail() {
	}

	public Long getProDetailId() {
		return proDetailId;
	}

	public void setProDetailId(Long proDetailId) {
		this.proDetailId = proDetailId;
	}

	public Integer getEngineType() {
		return engineType;
	}

	public void setEngineType(Integer engineType) {
		this.engineType = engineType;
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

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Double getVisibility() {
		return visibility;
	}

	public void setVisibility(Double visibility) {
		this.visibility = visibility;
	}


}