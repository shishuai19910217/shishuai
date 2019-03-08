package com.ido85.seo.dashboard.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/***
 * 概览 搜索可见性
 * @author shishuai
 *
 */
public class OutVisibilityDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Map<String, Object>> visibilityList = new ArrayList<Map<String, Object>>();

	public List<Map<String, Object>> getVisibilityList() {
		return visibilityList;
	}

	public void setVisibilityList(List<Map<String, Object>> visibilityList) {
		this.visibilityList = visibilityList;
	}
}
