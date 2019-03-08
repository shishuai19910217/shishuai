package com.ido85.master.keyword.dto;

import javax.validation.constraints.NotNull;

public class InKeywordRankDto {
	@NotNull
	private String engine;
	@NotNull
	private String keyword;
	@NotNull
	private String projectId;
	@NotNull
	public String getEngine() {
		return engine;
	}
	public void setEngine(String engine) {
		this.engine = engine;
	}
	@NotNull
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
