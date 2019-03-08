package com.ido85.master.keyword.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class InAddKeywordDto {
	private String branded;
	@Size(min=1)
	private List<String> groups;
	private Map<String, ArrayList<String>> keywords;
	@NotNull
	private String projectId;
	
	@NotNull
	public Map<String, ArrayList<String>> getKeywords() {
		return keywords;
	}
	public void setKeywords(Map<String, ArrayList<String>> keywords) {
		this.keywords = keywords;
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
	@NotNull
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
}
