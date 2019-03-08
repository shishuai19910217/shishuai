package com.ido85.seo.keywordrank.dto;

import java.util.List;

public class OutRankAnalysisDto {
	private String brand;
	private List<String> group;
	private String keywordId;
	private List<OutRankAnalysisItemDto> list;
	private String name;
	public String getBrand() {
		return brand;
	}
	public List<String> getGroup() {
		return group;
	}
	public void setGroup(List<String> group) {
		this.group = group;
	}
	public void setBrand(String brand) {
		if("0".equals(brand)){
			brand = "1";
		}else if ("1".equals(brand)) {
			brand = "0";
		}
		this.brand = brand;
	}
	public String getKeywordId() {
		return keywordId;
	}
	public void setKeywordId(String keywordId) {
		this.keywordId = keywordId;
	}
	public List<OutRankAnalysisItemDto> getList() {
		return list;
	}
	public void setList(List<OutRankAnalysisItemDto> list) {
		this.list = list;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
