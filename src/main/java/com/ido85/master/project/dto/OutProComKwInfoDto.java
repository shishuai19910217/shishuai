package com.ido85.master.project.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ido85.frame.common.utils.DateUtils;
import com.ido85.master.keyword.dto.OutProKeywordDto;

public class OutProComKwInfoDto implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<OutProCompetitorDto> competitors;
	private List<OutProKeywordDto> keywords;
	private String createDate;
	private List<Integer> groups;
	private Integer keywordNum;
	private String preMonLastUpdate;
	private Integer projectId;
	private String projectName;
	private String projectUrl;
	private String receiveEmail;
	private String subdomain;
	private String updateDate;
	private Integer tenantId;
	
	public Integer getTenantId() {
		return tenantId;
	}


	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}


	public OutProComKwInfoDto(Date createDate, Integer projectId,
			String projectName, String projectUrl, String receiveEmail,
			String subdomain, Integer tenantId) {
		super();
		this.createDate = DateUtils.formatDate(createDate, "yyyy-MM-dd");
		this.projectId = projectId;
		this.projectName = projectName;
		this.projectUrl = projectUrl;
		this.receiveEmail = receiveEmail;
		this.subdomain = subdomain;
		this.tenantId = tenantId;
	}
	
	
	public List<OutProCompetitorDto> getCompetitors() {
		return competitors;
	}
	public void setCompetitors(List<OutProCompetitorDto> competitors) {
		this.competitors = competitors;
	}
	public List<OutProKeywordDto> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<OutProKeywordDto> keywords) {
		this.keywords = keywords;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public List<Integer> getGroups() {
		return groups;
	}
	public void setGroups(List<Integer> groups) {
		this.groups = groups;
	}
	public Integer getKeywordNum() {
		return keywordNum;
	}
	public void setKeywordNum(Integer keywordNum) {
		this.keywordNum = keywordNum;
	}
	public String getPreMonLastUpdate() {
		return preMonLastUpdate;
	}
	public void setPreMonLastUpdate(String preMonLastUpdate) {
		this.preMonLastUpdate = preMonLastUpdate;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectUrl() {
		return projectUrl;
	}
	public void setProjectUrl(String projectUrl) {
		this.projectUrl = projectUrl;
	}
	public String getReceiveEmail() {
		return receiveEmail;
	}
	public void setReceiveEmail(String receiveEmail) {
		this.receiveEmail = receiveEmail;
	}
	public String getSubdomain() {
		return subdomain;
	}
	public void setSubdomain(String subdomain) {
		this.subdomain = subdomain;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
}
