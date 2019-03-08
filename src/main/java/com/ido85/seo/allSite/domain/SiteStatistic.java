package com.ido85.seo.allSite.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tf_b_site_statistic database table.
 * 
 */
@Entity
@Table(name="tf_b_site_statistic")
@NamedQuery(name="SiteStatistic.findAll", query="SELECT s FROM SiteStatistic s")
public class SiteStatistic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="site_id")
	private Integer siteId;

	@Temporal(TemporalType.DATE)
	@Column(name="create_date")
	private Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="issues_code")
	private String issuesCode;

	@Column(name="issues_level")
	private String issuesLevel;
	
	@Column(name="num")
	private Integer num;

	@Column(name="project_id")
	private Integer projectId;

	public SiteStatistic() {
	}

	public Integer getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public String getIssuesLevel() {
		return this.issuesLevel;
	}

	public void setIssuesLevel(String issuesLevel) {
		this.issuesLevel = issuesLevel;
	}

	public int getNum() {
		return this.num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getProjectId() {
		return this.projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

}