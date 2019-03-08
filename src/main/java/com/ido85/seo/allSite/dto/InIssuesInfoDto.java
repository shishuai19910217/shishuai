package com.ido85.seo.allSite.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class InIssuesInfoDto {
	private String issueCode;
	@NotNull
	@Length(min=1, max=2)
	private String issueType;
	@NotNull
	private int projectId;
	public String getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(String issueCode) {
		this.issueCode = issueCode;
	}
	public String getIssueType() {
		return issueType;
	}
	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	
}
