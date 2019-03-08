package com.ido85.master.project.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * 修改项目信息接口
 * @author fire
 *
 */
public class InUpdateProjectDto {
	private List<InCompetitorDto> competitors;
	@NotNull
	private String projectId;
	private String projectName;
	private String receiveEmail;
	
	public List<InCompetitorDto> getCompetitors() {
		return competitors;
	}
	public void setCompetitors(List<InCompetitorDto> competitors) {
		this.competitors = competitors;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getReceiveEmail() {
		return receiveEmail;
	}
	public void setReceiveEmail(String receiveEmail) {
		this.receiveEmail = receiveEmail;
	}
	
}