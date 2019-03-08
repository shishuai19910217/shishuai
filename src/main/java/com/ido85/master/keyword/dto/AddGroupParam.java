package com.ido85.master.keyword.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class AddGroupParam {
	
	@NotNull
	@Length(min=1,max=64)
	private String groupName;
	
	@NotNull
	private String projectId;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	
}
