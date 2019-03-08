package com.ido85.master.keyword.dto;

import java.io.Serializable;
import java.util.List;

public class InProjectKeywordsDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String branded;
	private String projectId;
	private List<String> groups;
	public String getBranded() {
		return branded;
	}
	public void setBranded(String branded) {
		this.branded = branded;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public List<String> getGroups() {
		return groups;
	}
	public void setGroups(List<String> groups) {
		this.groups = groups;
	}
	
}
