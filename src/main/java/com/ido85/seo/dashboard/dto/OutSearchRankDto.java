package com.ido85.seo.dashboard.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OutSearchRankDto {
	private int downNum;
	private int brand;
	private int noBrand;
	private List<Map<String, Object>> rangeList = new ArrayList<Map<String, Object>>();
	private int upNum;
	private String updateDate;
	public int getDownNum() {
		return downNum;
	}
	public void setDownNum(int downNum) {
		this.downNum = downNum;
	}
	public int getBrand() {
		return brand;
	}
	public void setBrand(int brand) {
		this.brand = brand;
	}
	public int getNoBrand() {
		return noBrand;
	}
	public void setNoBrand(int noBrand) {
		this.noBrand = noBrand;
	}
	public List<Map<String, Object>> getRangeList() {
		return rangeList;
	}
	public void setRangeList(List<Map<String, Object>> rangeList) {
		this.rangeList = rangeList;
	}
	public int getUpNum() {
		return upNum;
	}
	public void setUpNum(int upNum) {
		this.upNum = upNum;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
}
