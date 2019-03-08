package com.ido85.seo.dashboard.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OutExtLinkDomainsDto {
	private long domainsNum;//域名个数
	private String upDate;
	private long upOrDown ;
	public long getDomainsNum() {
		return domainsNum;
	}
	public void setDomainsNum(long domainsNum) {
		this.domainsNum = domainsNum;
	}
	public List<Map<String, Object>> getComDomainsList() {
		return comDomainsList;
	}
	public void setComDomainsList(List<Map<String, Object>> comDomainsList) {
		this.comDomainsList = comDomainsList;
	}
	public String getUpDate() {
		return upDate;
	}
	public void setUpDate(String upDate) {
		this.upDate = upDate;
	}
	public long getUpOrDown() {
		return upOrDown;
	}
	public void setUpOrDown(long l) {
		this.upOrDown = l;
	}
	private List<Map<String, Object>> comDomainsList = new ArrayList<Map<String,Object>>();
}
