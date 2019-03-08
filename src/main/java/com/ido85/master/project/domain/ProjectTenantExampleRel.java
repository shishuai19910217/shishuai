package com.ido85.master.project.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ido85.seo.common.BaseEntity;
/***
 * 项目和租户以及实例关系表 
 */
@Entity
@Table(name="tf_b_project_tenant_example_rel")


public class ProjectTenantExampleRel extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="pcp_rel_id")
	private Integer pcpRelId;
	
	@Column(name="tenant_id")
	private Integer tenantId;
	
	@Column(name="tenant_package_id")
	private Integer tenantPackageId;
	
	@Column(name="del_flag")
	private String delFlag;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	private Project project;
	
	@Column(name="end_date")
	private Date endDate;
	
	public Integer getPcpRelId() {
		return pcpRelId;
	}
	public void setPcpRelId(Integer pcpRelId) {
		this.pcpRelId = pcpRelId;
	}
	public Integer getTenantId() {
		return tenantId;
	}
	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}
	public Integer getTenantPackageId() {
		return tenantPackageId;
	}
	public void setTenantPackageId(Integer tenantPackageId) {
		this.tenantPackageId = tenantPackageId;
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


	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	

}
