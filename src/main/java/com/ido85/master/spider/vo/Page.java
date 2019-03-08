package com.ido85.master.spider.vo;

import java.util.List;
import java.util.Map;

public class Page {
	
	Map<String, Integer> keywords;
	List<String> errorlist;
	
	
	public Map<String, Integer> getKeywords() {
		return keywords;
	}
	public void setKeywords(Map<String, Integer> keywords) {
		this.keywords = keywords;
	}
	public List<String> getErrorlist() {
		return errorlist;
	}
	public void setErrorlist(List<String> errorlist) {
		this.errorlist = errorlist;
	}
	
	
	
	
}
