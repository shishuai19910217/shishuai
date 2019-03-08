package com.ido85.seo.keywordrank.dto;

import java.io.Serializable;
import java.util.List;

public class OutKeywordRankInfoDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private String baiduIndex;
	private String brand;
	private List<String> group;
	private String keywordId;
	private String name;
	private String ranking;
	private String upOrDown;
	private String url;
	private String linkUrl;
	
	
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public List<String> getGroup() {
		return group;
	}
	public void setGroup(List<String> group) {
		this.group = group;
	}
	public String getBaiduIndex() {
		return baiduIndex;
	}
	public void setBaiduIndex(String baiduIndex) {
		this.baiduIndex = baiduIndex;
	}
	public String getBrand() {
		return brand;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRanking() {
		return ranking;
	}
	public void setRanking(String ranking) {
		this.ranking = ranking;
	}
	public String getUpOrDown() {
		return upOrDown;
	}
	public void setUpOrDown(String upOrDown) {
		this.upOrDown = upOrDown;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}