package com.ido85.master.keyword.dto;

import java.util.List;


public class OutKeywordDto {
	private int count;
	private List<FuzzyKeywordDto> keywords;
	private int pageNo;
	private int pageSize;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<FuzzyKeywordDto> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<FuzzyKeywordDto> keywords) {
		this.keywords = keywords;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}	
	
}
