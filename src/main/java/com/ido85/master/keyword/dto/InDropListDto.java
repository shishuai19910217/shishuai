package com.ido85.master.keyword.dto;

import javax.validation.constraints.NotNull;

public class InDropListDto {
	private String groups;
	private String keyword;
	@NotNull
	private String projectId;
	
	public String getGroups() {
		return groups;
	}
	public void setGroups(String groups) {
		this.groups = groups;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	@NotNull
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
}
