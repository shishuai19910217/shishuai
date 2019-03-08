package com.ido85.seo.dashboard.dto;

import javax.validation.constraints.NotNull;

public class InSearchRankDto {
	@NotNull(message = "{projectId.not.null}")
	private String projectId;
	private String isWeek;
	private String engineType;
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
	public String getEngineType() {
		return engineType;
	}
	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}
}
