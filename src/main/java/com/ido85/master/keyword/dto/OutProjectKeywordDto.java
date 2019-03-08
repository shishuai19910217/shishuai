package com.ido85.master.keyword.dto;

import java.io.Serializable;
import java.util.List;

public class OutProjectKeywordDto implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<FuzzyKeywordDto> keywords;

	public List<FuzzyKeywordDto> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<FuzzyKeywordDto> keywords) {
		this.keywords = keywords;
	}
	
}
