package com.ido85.seo.dashboard.dto;

import javax.validation.constraints.NotNull;

public class InSearchQuestionDto {
	@NotNull(message = "{projectId.not.null}")
	private String projectId;
	private String isWeek;
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getIsWeek() {
		return isWeek;
	}
	public void setIsWeek(String isWeek) {
		this.isWeek = isWeek;
	}
}
