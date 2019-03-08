package com.ido85.seo.time.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

@Entity
@Table(name = "tf_f_pro_crawl")
@NamedQueries(value = {})
public class ProjectCrawl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="pro_crawl_id")
	private Long proCrawlId;
	@Column(name="crawl_date")
	private Date crawlDate;
	@Column(name="project_id")
	private Integer projectId;
	@Column(name="del_flag")
	private String delFlag;
	
	
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Long getProCrawlId() {
		return proCrawlId;
	}
	public void setProCrawlId(Long proCrawlId) {
		this.proCrawlId = proCrawlId;
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
	
	
}
