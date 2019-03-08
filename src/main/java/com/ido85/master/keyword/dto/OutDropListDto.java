package com.ido85.master.keyword.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.ido85.master.keyword.domain.Group;
import com.ido85.master.keyword.domain.Keyword;

public class OutDropListDto {
	@NotNull
	private List<Group> groups;
	@NotNull
	private List<Keyword> keywords;
	
	
	public List<Group> getGroups() {
		return groups;
	}
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	public List<Keyword> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<Keyword> keywords) {
		this.keywords = keywords;
	}
	
	
}
