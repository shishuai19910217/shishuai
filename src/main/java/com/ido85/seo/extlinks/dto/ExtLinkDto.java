package com.ido85.seo.extlinks.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.sql.Update;

public class ExtLinkDto {
	private long linkNum;
	private long upOrDown;
	private List<Map<String, Object>> comLinkList = new ArrayList<Map<String,Object>>();
	private String upDate;
	public long getLinkNum() {
		return linkNum;
	}
	public void setLinkNum(long linkNum) {
		this.linkNum = linkNum;
	}
	public long getUpOrDown() {
		return upOrDown;
	}
	public void setUpOrDown(long upOrDown) {
		this.upOrDown = upOrDown;
	}
	public List<Map<String, Object>> getComLinkList() {
		return comLinkList;
	}
	public void setComLinkList(List<Map<String, Object>> comLinkList) {
		this.comLinkList = comLinkList;
	}
	public String getUpDate() {
		return upDate;
	}
	public void setUpDate(String upDate) {
		this.upDate = upDate;
	}
}
