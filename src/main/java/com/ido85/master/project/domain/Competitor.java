package com.ido85.master.project.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ido85.seo.common.BaseEntity;
/***
 * 竞品
 */
@Entity
@Table(name="tf_f_seo_competitor")


public class Competitor extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="competitor_id")
	private Integer  competitorId  ;
	
	@Column(name="tenant_id")
	private Integer tenantId;
	
	@Column(name="competitor_name")
	private String  competitorName;
	
	@Column(name="project_id")
	private Integer  projectId  ;
	
	@Column(name="competitor_url")
	private String  competitorUrl;
	
	@Column(name="del_flag")
	private String  delFlag ;
	
	@Column(name="end_date")
	private Date  endDate ;
	public Integer getCompetitorId() {
		return competitorId;
	}
	public void setCompetitorId(Integer competitorId) {
		this.competitorId = competitorId;
	}
	
	public Integer getTenantId() {
		return tenantId;
	}
	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}
	public String getCompetitorName() {
		return competitorName;
	}
	public void setCompetitorName(String competitorName) {
		this.competitorName = competitorName;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getCompetitorUrl() {
		return competitorUrl;
	}
	public void setCompetitorUrl(String competitorUrl) {
		this.competitorUrl = competitorUrl;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
