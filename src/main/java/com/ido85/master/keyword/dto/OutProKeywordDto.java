package com.ido85.master.keyword.dto;

import java.io.Serializable;
import java.util.List;

import com.ido85.master.keyword.domain.Group;

public class OutProKeywordDto implements Serializable{
	private static final long serialVersionUID = 1L;
	private String branded;
	private List<Group> groups;
	private Integer keywordId;
	private String keywordName;
	
	public OutProKeywordDto(String branded, Integer keywordId, String keywordName) {
		super();
		this.branded = branded;
		this.keywordId = keywordId;
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

	public Integer getKeywordId() {
		return keywordId;
	}

	public void setKeywordId(Integer keywordId) {
		this.keywordId = keywordId;
	}

	public String getKeywordName() {
		return keywordName;
	}

	public void setKeywordName(String keywordName) {
		this.keywordName = keywordName;
	}
	
}
