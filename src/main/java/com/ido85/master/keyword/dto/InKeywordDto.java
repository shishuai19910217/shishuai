package com.ido85.master.keyword.dto;

import java.util.List;

import javax.validation.constraints.NotNull;



public class InKeywordDto {
	private String branded;
	private List<String> groups;
	private List<String> keyword;
	@NotNull
	private String projectId;
	@NotNull
	private int pageSize;
	@NotNull
	private int pageNo;
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public String getBranded() {
		return branded;
	}
	public void setBranded(String branded) {
		this.branded = branded;
	}
	public List<String> getGroups() {
		return groups;
	}
	public void setGroups(List<String> groups) {
		this.groups = groups;
	}
	public List<String> getKeyword() {
		return keyword;
	}
	public void setKeyword(List<String> keyword) {
		this.keyword = keyword;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
}
