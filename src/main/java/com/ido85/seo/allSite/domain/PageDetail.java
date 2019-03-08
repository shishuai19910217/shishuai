package com.ido85.seo.allSite.domain;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tf_b_page_detail database table.
 * 
 */
@Entity
@Table(name="tf_b_page_detail")
@NamedQuery(name="PageDetail.findAll", query="SELECT p FROM PageDetail p")
public class PageDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="detail_id")
	private String detailId;

	private String des;

	private Integer domains;

	@Column(name="h1_sum")
	private Integer h1Sum;

	@Column(name="h2_sum")
	private Integer h2Sum;

	@Column(name="h3_sum")
	private Integer h3Sum;

	@Column(name="img_alt")
	private Integer imgAlt;

	@Column(name="is_flash")
	private Integer isFlash;

	@Column(name="link_num")
	private Integer linkNum;

	@Column(name="page_url")
	private String pageUrl;

	@Column(name="project_id")
	private Integer projectId;

	@Column(name="response_time")
	private String responseTime;

	@Column(name="status_code")
	private String statusCode;

	private String title;
	
	@Column(name="title_num")
	private Integer titleNum;
	
	@Temporal(TemporalType.DATE)
	@Column(name="crawl_date")
	private Date crawlDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;
	
	public PageDetail() {
	}
	
	public PageDetail(String detailId, Date createTime, String des,
			Integer domains, Integer h1Sum, Integer h2Sum, Integer h3Sum,
			Integer imgAlt, Integer isFlash, Integer linkNum, String pageUrl,
			Integer projectId, String responseTime, String statusCode,
			String title, Integer titleNum) {
		super();
		this.detailId = detailId;
		this.createTime = createTime;
		this.des = des;
		this.domains = domains;
		this.h1Sum = h1Sum;
		this.h2Sum = h2Sum;
		this.h3Sum = h3Sum;
		this.imgAlt = imgAlt;
		this.isFlash = isFlash;
		this.linkNum = linkNum;
		this.pageUrl = pageUrl;
		this.projectId = projectId;
		this.responseTime = responseTime;
		this.statusCode = statusCode;
		this.title = title;
		this.titleNum = titleNum;
	}

	public String getDetailId() {
		return detailId;
	}
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public Integer getDomains() {
		return domains;
	}
	public void setDomains(Integer domains) {
		this.domains = domains;
	}
	public Integer getH1Sum() {
		return h1Sum;
	}
	public void setH1Sum(Integer h1Sum) {
		this.h1Sum = h1Sum;
	}
	public Integer getH2Sum() {
		return h2Sum;
	}
	public void setH2Sum(Integer h2Sum) {
		this.h2Sum = h2Sum;
	}
	public Integer getH3Sum() {
		return h3Sum;
	}
	public void setH3Sum(Integer h3Sum) {
		this.h3Sum = h3Sum;
	}
	public Integer getImgAlt() {
		return imgAlt;
	}
	public void setImgAlt(Integer imgAlt) {
		this.imgAlt = imgAlt;
	}
	public Integer getIsFlash() {
		return isFlash;
	}
	public void setIsFlash(Integer isFlash) {
		this.isFlash = isFlash;
	}
	public Integer getLinkNum() {
		return linkNum;
	}
	public void setLinkNum(Integer linkNum) {
		this.linkNum = linkNum;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getTitleNum() {
		return titleNum;
	}
	public void setTitleNum(Integer titleNum) {
		this.titleNum = titleNum;
	}

	
}