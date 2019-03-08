package com.ido85.seo.keywordrank.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class InKeywordRankDto {
	@NotBlank(message="field.is.not.null")
	private String engine;
	@NotBlank(message="field.is.not.null")
	private String keyword;
	@NotBlank(message="field.is.not.null")
	private String projectId;
	
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
