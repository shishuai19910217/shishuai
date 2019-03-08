package com.ido85.seo.extlinks.dto;

import javax.validation.constraints.NotNull;



public class InComcontrastDto {
	private String endDate; //格式：yyyy-MM-dd
	@NotNull
	private String flag;
	@NotNull
	private String projectId;
	private String queryFlag;
	private String startDate; // 格式：yyyy-MM-dd
	
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getQueryFlag() {
		return queryFlag;
	}
	public void setQueryFlag(String queryFlag) {
		this.queryFlag = queryFlag;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
}
