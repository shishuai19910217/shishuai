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
 * The persistent class for the tf_b_serp_detail database table.
 * 
 */
@Entity
@Table(name="tf_b_serp_detail")
@NamedQuery(name="SerpDetail.findAll", query="SELECT s FROM SerpDetail s")
public class SerpDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="detail_id")
	private String detailId;

	@Column(name="engine_type")
	private Integer engineType;

	@Temporal(TemporalType.DATE)
	@Column(name="crawl_date")
	private Date crawlDate;

	@Column(name="del_flag")
	private String delFlag;

	@Column(name="keyword_id")
	private Integer keywordId;

	@Column(name="rank")
	private Integer rank;

	@Column(name="title")
	private String title;

	@Column(name="url")
	private String url;
	
	@Column(name="link_url")
	private String linkUrl;

	public SerpDetail() {
	}

	public Integer getEngineType() {
		return engineType;
	}

	public void setEngineType(Integer engineType) {
		this.engineType = engineType;
	}

	public String getDetailId() {
		return this.detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
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

	public Integer getRank() {
		return this.rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
}