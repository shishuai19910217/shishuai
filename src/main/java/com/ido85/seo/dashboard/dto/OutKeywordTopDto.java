package com.ido85.seo.dashboard.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OutKeywordTopDto {
	private List<Map<String, Object>> keywordList = new ArrayList<Map<String,Object>>();
	private String updateDate;

	public List<Map<String, Object>> getKeywordList() {
		return keywordList;
	}

	public void setKeywordList(List<Map<String, Object>> keywordList) {
		this.keywordList = keywordList;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
}
