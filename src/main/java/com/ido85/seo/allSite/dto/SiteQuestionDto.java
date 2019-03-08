package com.ido85.seo.allSite.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SiteQuestionDto {
	private List<Map<String, Object>> highIssuesInfo  =  new ArrayList<>();//严重问题
	private List<Map<String, Object>> issuesShow=  new ArrayList<>();//
	private List<Map<String, Object>> lowIssuesInfo=  new ArrayList<>();//轻微问题
	private List<Map<String, Object>> mediumIssuesInfo=  new ArrayList<>();//一般问题
	public List<Map<String, Object>> getHighIssuesInfo() {
		return highIssuesInfo;
	}
	public void setHighIssuesInfo(List<Map<String, Object>> highIssuesInfo) {
		this.highIssuesInfo = highIssuesInfo;
	}
	public List<Map<String, Object>> getIssuesShow() {
		return issuesShow;
	}
	public void setIssuesShow(List<Map<String, Object>> issuesShow) {
		this.issuesShow = issuesShow;
	}
	public List<Map<String, Object>> getLowIssuesInfo() {
		return lowIssuesInfo;
	}
	public void setLowIssuesInfo(List<Map<String, Object>> lowIssuesInfo) {
		this.lowIssuesInfo = lowIssuesInfo;
	}
	public List<Map<String, Object>> getMediumIssuesInfo() {
		return mediumIssuesInfo;
	}
	public void setMediumIssuesInfo(List<Map<String, Object>> mediumIssuesInfo) {
		this.mediumIssuesInfo = mediumIssuesInfo;
	}
}
