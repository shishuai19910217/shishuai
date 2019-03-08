package com.ido85.master.project.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UrlDto {
	private List<Map<String, Object>> urList = new ArrayList<Map<String, Object>>();

	public List<Map<String, Object>> getUrList() {
		return urList;
	}

	public void setUrList(List<Map<String, Object>> urList) {
		this.urList = urList;
	}
}
