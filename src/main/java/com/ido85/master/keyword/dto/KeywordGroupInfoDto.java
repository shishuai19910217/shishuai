package com.ido85.master.keyword.dto;

public class KeywordGroupInfoDto {
	private String keywordId;
	private String projectId;
	private String groupId;
	private String groupKeyId;
	private String proKeyRelId;
	private String groupName;
	private String keywordName;
	
	public String getKeywordName() {
		return keywordName;
	}
	public void setKeywordName(String keywordName) {
		this.keywordName = keywordName;
	}
	public KeywordGroupInfoDto() {
		super();
	}
	public KeywordGroupInfoDto(Integer keywordId, Integer projectId,
			Integer groupId, Integer groupKeyId, Integer proKeyRelId,
			String groupName, String keywordName) {
		super();
		this.keywordId = keywordId.toString();
		this.projectId = projectId.toString();
		this.groupId = groupId.toString();
		this.groupKeyId = groupKeyId.toString();
		this.proKeyRelId = proKeyRelId.toString();
		this.groupName = groupName;
		this.keywordName = keywordName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getKeywordId() {
		return keywordId;
	}
	public void setKeywordId(String keywordId) {
		this.keywordId = keywordId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupKeyId() {
		return groupKeyId;
	}
	public void setGroupKeyId(String groupKeyId) {
		this.groupKeyId = groupKeyId;
	}
	public String getProKeyRelId() {
		return proKeyRelId;
	}
	public void setProKeyRelId(String proKeyRelId) {
		this.proKeyRelId = proKeyRelId;
	}
	
}
