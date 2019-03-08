package com.ido85.master.keyword.dto;


public class GroKeyRelDto {
	private String groupKeyId;
	private String groupName;
	private String groupId;
	private String proKeyRelId;
	private String delFlag;
	private String keywordId;
	
	
	public GroKeyRelDto(Integer groupKeyId, String groupName, Integer groupId,
			Integer proKeyRelId, Integer keywordId) {
		super();
		this.groupKeyId = groupKeyId.toString();
		this.groupName = groupName;
		this.groupId = groupId.toString();
		this.proKeyRelId = proKeyRelId.toString();
		this.keywordId = keywordId.toString();
	}
	public String getGroupKeyId() {
		return groupKeyId;
	}
	public void setGroupKeyId(String groupKeyId) {
		this.groupKeyId = groupKeyId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getProKeyRelId() {
		return proKeyRelId;
	}
	public void setProKeyRelId(String proKeyRelId) {
		this.proKeyRelId = proKeyRelId;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getKeywordId() {
		return keywordId;
	}
	public void setKeywordId(String keywordId) {
		this.keywordId = keywordId;
	}
	
	
}
