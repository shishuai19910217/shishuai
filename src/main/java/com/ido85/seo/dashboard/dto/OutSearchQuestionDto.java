package com.ido85.seo.dashboard.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OutSearchQuestionDto {
	private List<Map<String, Object>> commonQuestionList = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> questionList  = new ArrayList<Map<String, Object>>();
	private int highQuestionNum;
	private int pageNum;
	public List<Map<String, Object>> getCommonQuestionList() {
		return commonQuestionList;
	}
	public void setCommonQuestionList(List<Map<String, Object>> commonQuestionList) {
		this.commonQuestionList = commonQuestionList;
	}
	public List<Map<String, Object>> getQuestionList() {
		return questionList;
	}
	public void setQuestionList(List<Map<String, Object>> questionList) {
		this.questionList = questionList;
	}
	public int getHighQuestionNum() {
		return highQuestionNum;
	}
	public void setHighQuestionNum(int highQuestionNum) {
		this.highQuestionNum = highQuestionNum;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
}
