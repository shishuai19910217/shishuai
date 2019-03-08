package com.ido85.master.project.dto;

import java.util.Date;
import java.util.List;

import com.ido85.frame.common.utils.DateUtils;

/**
 * 获取项目基本信息接口 返回数据
 * @author fire
 *
 */
public class OutProjectBaseInfoDto {
	private List<CompetitorDto> competitors;
	private String createDate;
	private List<String> groups;
	private int keywordNum;
	private String preMonLastUpdate;
	private String projectId;
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
	public List<CompetitorDto> getCompetitors() {
		return competitors;
	}
	public void setCompetitors(List<CompetitorDto> competitors) {
		this.competitors = competitors;
	}
	public OutProjectBaseInfoDto(Date createDate, Integer projectId,
			String projectName, String projectUrl, String receiveEmail,
			String subdomain) {
		super();
		this.createDate = DateUtils.formatDate(createDate, "yyyy-MM-dd");
		this.projectId = projectId.toString();
		this.projectName = projectName;
		this.projectUrl = projectUrl;
		this.receiveEmail = receiveEmail;
		this.subdomain = subdomain;
	}
	public OutProjectBaseInfoDto(Date createDate, Integer projectId,
			String projectName, String projectUrl, String receiveEmail,
			String subdomain, Integer tenantId) {
		super();
		this.createDate = DateUtils.formatDate(createDate, "yyyy-MM-dd");
		this.projectId = projectId.toString();
		this.projectName = projectName;
		this.projectUrl = projectUrl;
		this.receiveEmail = receiveEmail;
		this.subdomain = subdomain;
		this.tenantId = tenantId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public List<String> getGroups() {
		return groups;
	}
	public void setGroups(List<String> groups) {
		this.groups = groups;
	}
	public int getKeywordNum() {
		return keywordNum;
	}
	public void setKeywordNum(int keywordNum) {
		this.keywordNum = keywordNum;
	}
	public String getPreMonLastUpdate() {
		return preMonLastUpdate;
	}
	public void setPreMonLastUpdate(String preMonLastUpdate) {
		this.preMonLastUpdate = preMonLastUpdate;
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
