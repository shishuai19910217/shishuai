package com.ido85.seo.extlinks.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;


public class InCommonDto {
	@NotNull
	private int pageNo;
	@NotNull
	private int pageSize;
	@NotNull
	@Length(min=1, max=64)
	private String projectId;
	private String anchorText;
	
	public String getAnchorText() {
		return anchorText;
	}
	public void setAnchorText(String anchorText) {
		this.anchorText = anchorText;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	
}
