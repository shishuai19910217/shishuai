package com.ido85.master.project.dto;

import java.io.Serializable;
import java.util.Date;

public class ProjectDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7778023599972337426L;
	
	private String projectId;
	private String projectName;
	private String businessName;
	private String projectUrl;
	private String isSubdomain;
	private String projectState;
	private Date archiveDate;
	private String delFlag;
	private String  endDate;
	public ProjectDto(){
		
	}
	public ProjectDto(Integer projectId, String projectName ,String businessName,String projectUrl,String isSubdomain,
			String projectState, Date archiveDate, String delFlag ,String endDate ){
		this.projectId=projectId.toString();
		this.projectName=projectName;
		this.businessName=businessName;
		this.projectUrl=projectUrl;
		this.isSubdomain=isSubdomain;
		this.projectState=projectState;
		this.archiveDate=archiveDate;
		this.delFlag=delFlag;
		this.endDate=endDate;
		
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getProjectUrl() {
		return projectUrl;
	}
	public void setProjectUrl(String projectUrl) {
		this.projectUrl = projectUrl;
	}
	public String getIsSubdomain() {
		return isSubdomain;
	}
	public void setIsSubdomain(String isSubdomain) {
		this.isSubdomain = isSubdomain;
	}
	public String getProjectState() {
		return projectState;
	}
	public void setProjectState(String projectState) {
		this.projectState = projectState;
	}
	public Date getArchiveDate() {
		return archiveDate;
	}
	public void setArchiveDate(Date archiveDate) {
		this.archiveDate = archiveDate;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
