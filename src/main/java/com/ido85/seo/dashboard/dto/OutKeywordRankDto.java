package com.ido85.seo.dashboard.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutKeywordRankDto {
	private List<Map<String,Object>> competitorList = new ArrayList<Map<String,Object>>();
	private Map<String, Object> project = new HashMap<String,Object>();
	private String updateDate;
	public List<Map<String, Object>> getCompetitorList() {
		return competitorList;
	}
	public void setCompetitorList(List<Map<String, Object>> competitorList) {
		this.competitorList = competitorList;
	}
	public Map<String, Object> getProject() {
		return project;
	}
	public void setProject(Map<String, Object> project) {
		this.project = project;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
}
