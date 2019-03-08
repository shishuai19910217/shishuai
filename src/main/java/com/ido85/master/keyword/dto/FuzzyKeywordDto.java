package com.ido85.master.keyword.dto;

import java.util.List;

import com.ido85.master.keyword.domain.Group;


public class FuzzyKeywordDto {
	private String branded;
	private List<Group> groups;
	private String keywordId;
	private String keywordName;
	
	
	public FuzzyKeywordDto() {
		super();
	}
	public FuzzyKeywordDto(String branded, Integer keywordId, String keywordName) {
		super();
		this.branded = branded;
		this.keywordId = keywordId.toString();
		this.keywordName = keywordName;
	}
	public String getBranded() {
		return branded;
	}
	public void setBranded(String branded) {
		this.branded = branded;
	}
	public List<Group> getGroups() {
		return groups;
	}
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	public String getKeywordId() {
		return keywordId;
	}
	public void setKeywordId(String keywordId) {
		this.keywordId = keywordId;
	}
	public String getKeywordName() {
		return keywordName;
	}
	public void setKeywordName(String keywordName) {
		this.keywordName = keywordName;
	}
	
}
