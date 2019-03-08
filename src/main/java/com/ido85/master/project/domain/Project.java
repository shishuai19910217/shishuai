package com.ido85.master.project.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ido85.seo.common.BaseEntity;

/**
 * 项目基本信息 
 */
@Entity
@Table(name = "tf_f_seo_project")
public class Project extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="project_id")
	private Integer projectId;
	
	@Column(name="project_name")
	private String projectName;
	
	//2.0新增
	@Column(name="tenant_id")
	private Integer tenantId; 
	
	@Column(name="business_name")
	private String businessName;
	
	@Column(name="project_url")
	private String projectUrl;
	
	@Column(name="is_subdomain")
	private String isSubdomain;
	
	@Column(name="project_state")
	private String projectState;
	
	@Column(name="archive_date")
	private Date archiveDate;
	
	@Column(name="del_flag")
	private String delFlag;
	
	@Column(name="end_date")
	private String endDate;
	
	@Column(name="receive_email")
	private String receiveEmail;
	
	//2.0新增
	@Column(name="next_crawl_date")
	private Date nextCrawlDate;
	
	//2.0新增
	@Column(name="crawl_date")
	private Date crawlDate;
	
	
	public Integer getTenantId() {
		return tenantId;
	}
	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}
	

	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getReceiveEmail() {
		return receiveEmail;
	}
	public void setReceiveEmail(String receiveEmail) {
		this.receiveEmail = receiveEmail;
	}
	public Project(){
		
	}
	public Project(Integer tenantId, Integer projectId, String projectName ,String businessName,String projectUrl,String isSubdomain,
			String projectState, Date archiveDate, String delFlag ,String endDate ,String receiveEmail,
			Integer createBy, Date createDate,Integer updateBy,Date updateDate,Date crawlDate, Date nextCrawlDate){
		super(createBy, createDate, updateBy, updateDate);
		this.tenantId = tenantId;
		this.projectId=projectId;
		this.projectName=projectName;
		this.businessName=businessName;
		this.projectUrl=projectUrl;
		this.isSubdomain=isSubdomain;
		this.projectState=projectState;
		this.archiveDate=archiveDate;
		this.delFlag=delFlag;
		this.endDate=endDate;
		this.receiveEmail = receiveEmail;
		this.crawlDate = crawlDate;
		this.nextCrawlDate = nextCrawlDate;
	}

	public String getProjectName() {
		return projectName;
	}
	public String getBusinessName() {
		return businessName;
	}
	public String getProjectUrl() {
		return projectUrl;
	}
	public String getIsSubdomain() {
		return isSubdomain;
	}
	public String getProjectState() {
		return projectState;
	}
	public Date getArchiveDate() {
		return archiveDate;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public String getEndDate() {
		return endDate;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public void setProjectUrl(String projectUrl) {
		this.projectUrl = projectUrl;
	}
	public void setIsSubdomain(String isSubdomain) {
		this.isSubdomain = isSubdomain;
	}
	public void setProjectState(String projectState) {
		this.projectState = projectState;
	}
	public void setArchiveDate(Date archiveDate) {
		this.archiveDate = archiveDate;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Date getNextCrawlDate() {
		return nextCrawlDate;
	}
	public void setNextCrawlDate(Date nextCrawlDate) {
		this.nextCrawlDate = nextCrawlDate;
	}
	public Date getCrawlDate() {
		return crawlDate;
	}
	public void setCrawlDate(Date crawlDate) {
		this.crawlDate = crawlDate;
	}

}
