package com.ido85.seo.dashboard.dto;

import javax.validation.constraints.NotNull;

public class InKeywordTopDto {
	@NotNull(message = "{projectId.not.null}")
	private String projectId;
	private String engineType;
	private String isHave;
	private String isWeek;
	private int num = 5;
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getEngineType() {
		return engineType;
	}

	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}

	public String getIsHave() {
		return isHave;
	}

	public void setIsHave(String isHave) {
		this.isHave = isHave;
	}

	public String getIsWeek() {
		return isWeek;
	}

	public void setIsWeek(String isWeek) {
		this.isWeek = isWeek;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	
	
}
