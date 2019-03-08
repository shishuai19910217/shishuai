package com.ido85.seo.allSite.domain;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the tf_b_page_realate database table.
 * 
 */
@Entity
@Table(name="tf_b_page_realate")
@NamedQuery(name="PageRealate.findAll", query="SELECT p FROM PageRealate p")
public class PageRealate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="page_realate_id")
	private String pageRealateId;

	@Column(name="issues_code")
	private String issuesCode;

	@Column(name="issues_lable")
	private String issuesLable;

	@Column(name="issues_level")
	private String issuesLevel;


	@Column(name="detail_id")
	private String detailId;

	
	@Temporal(TemporalType.DATE)
	@Column(name="crawl_date")
	private Date crawlDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;
	
	public PageRealate() {
	}

	public String getPageRealateId() {
		return this.pageRealateId;
	}

	public void setPageRealateId(String pageRealateId) {
		this.pageRealateId = pageRealateId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIssuesCode() {
		return this.issuesCode;
	}

	public void setIssuesCode(String issuesCode) {
		this.issuesCode = issuesCode;
	}

	public String getIssuesLable() {
		return this.issuesLable;
	}

	public void setIssuesLable(String issuesLable) {
		this.issuesLable = issuesLable;
	}

	public String getIssuesLevel() {
		return this.issuesLevel;
	}

	public void setIssuesLevel(String issuesLevel) {
		this.issuesLevel = issuesLevel;
	}

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	public Date getCrawlDate() {
		return crawlDate;
	}

	public void setCrawlDate(Date crawlDate) {
		this.crawlDate = crawlDate;
	}



}