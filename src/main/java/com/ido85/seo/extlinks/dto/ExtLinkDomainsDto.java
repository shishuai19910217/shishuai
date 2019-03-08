package com.ido85.seo.extlinks.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtLinkDomainsDto {
	private long domainsNum;//域名个数
	private String upDate;
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
	private List<Map<String, Object>> comDomainsList = new ArrayList<Map<String,Object>>();
}
