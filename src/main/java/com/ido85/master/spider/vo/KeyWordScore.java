package com.ido85.master.spider.vo;

import java.util.HashMap;
import java.util.Map;

public class KeyWordScore {
	
	double original = 0;
	Map<String,Integer> originalList = new HashMap<>();
	
	
	
	public double getOriginal() {
		return original;
	}
	public void setOriginal(double original) {
		this.original = original;
	}
	public Map<String, Integer> getOriginalList() {
		return originalList;
	}
	public void setOriginalList(Map<String, Integer> originalList) {
		this.originalList = originalList;
	}
	
	
	
}
