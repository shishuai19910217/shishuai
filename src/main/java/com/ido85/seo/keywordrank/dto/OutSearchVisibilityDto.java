package com.ido85.seo.keywordrank.dto;

import java.util.List;

public class OutSearchVisibilityDto {
	private String[] engineType;
	private String id;
	private List<VisibilityDto> visibilityList;
	
	
	public String[] getEngineType() {
		return engineType;
	}
	public void setEngineType(String[] engineType) {
		this.engineType = engineType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<VisibilityDto> getVisibilityList() {
		return visibilityList;
	}
	public void setVisibilityList(List<VisibilityDto> visibilityList) {
		this.visibilityList = visibilityList;
	}
	
}
