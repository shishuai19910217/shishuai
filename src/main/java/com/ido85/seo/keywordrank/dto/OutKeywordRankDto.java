package com.ido85.seo.keywordrank.dto;

import java.util.List;

public class OutKeywordRankDto {
	private String difficulty;
	private List<String> groups;
	private String keyword;
	private String keywordRank;
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	public List<String> getGroups() {
		return groups;
	}
	public void setGroups(List<String> groups) {
		this.groups = groups;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getKeywordRank() {
		return keywordRank;
	}
	public void setKeywordRank(String keywordRank) {
		this.keywordRank = keywordRank;
	}
	
	
}
