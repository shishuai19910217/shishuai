package com.ido85.seo.allSite.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the tf_b_page_main database table.
 * 
 */
@Entity
@Table(name="tf_b_page_main")
@NamedQuery(name="PageMain.findAll", query="SELECT p FROM PageMain p")
public class PageMain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="page_main_id")
	private String pageMainId;

	@Column(name="project_id")
	private int projectId;

	@Column(name="is_entering_baidu")
	private int isEnteringBaidu;

	@Column(name="is_entering_haoso")
	private int isEnteringHaoso;

	@Column(name="is_entering_sogou")
	private int isEnteringSogou;

	@Column(name="is_robots")
	private int isRobots;

	@Column(name="is_safe_baidu")
	private int isSafeBaidu;

	@Column(name="is_safe_haosou")
	private int isSafeHaosou;

	@Column(name="is_safe_sogou")
	private int isSafeSogou;
	
	@Column(name="gzip")
	private int gzip;
	
	@Temporal(TemporalType.DATE)
	@Column(name="crawl_date")
	private Date crawlDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;
	public PageMain() {
	}

	public String getPageMainId() {
		return this.pageMainId;
	}

	public void setPageMainId(String pageMainId) {
		this.pageMainId = pageMainId;
	}



	public int getIsEnteringBaidu() {
		return this.isEnteringBaidu;
	}

	public void setIsEnteringBaidu(int isEnteringBaidu) {
		this.isEnteringBaidu = isEnteringBaidu;
	}

	public int getIsEnteringHaoso() {
		return this.isEnteringHaoso;
	}

	public void setIsEnteringHaoso(int isEnteringHaoso) {
		this.isEnteringHaoso = isEnteringHaoso;
	}

	public int getIsEnteringSogou() {
		return this.isEnteringSogou;
	}

	public void setIsEnteringSogou(int isEnteringSogou) {
		this.isEnteringSogou = isEnteringSogou;
	}

	public int getIsRobots() {
		return this.isRobots;
	}

	public void setIsRobots(int isRobots) {
		this.isRobots = isRobots;
	}

	public int getIsSafeBaidu() {
		return this.isSafeBaidu;
	}

	public void setIsSafeBaidu(int isSafeBaidu) {
		this.isSafeBaidu = isSafeBaidu;
	}

	public int getIsSafeHaosou() {
		return this.isSafeHaosou;
	}

	public void setIsSafeHaosou(int isSafeHaosou) {
		this.isSafeHaosou = isSafeHaosou;
	}

	public int getIsSafeSogou() {
		return this.isSafeSogou;
	}

	public void setIsSafeSogou(int isSafeSogou) {
		this.isSafeSogou = isSafeSogou;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getGzip() {
		return gzip;
	}

	public void setGzip(int gzip) {
		this.gzip = gzip;
	}

	public Date getCrawlDate() {
		return crawlDate;
	}

	public void setCrawlDate(Date crawlDate) {
		this.crawlDate = crawlDate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}