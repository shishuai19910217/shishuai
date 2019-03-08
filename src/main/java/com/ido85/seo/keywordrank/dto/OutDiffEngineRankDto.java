package com.ido85.seo.keywordrank.dto;

import java.util.List;

public class OutDiffEngineRankDto {
	private String brand;
	private List<String> group;
	private String isPc;
	private String keywordId;
	private String name;
	private String ranking1;
	private String ranking2;
	private String rankingpromotion1;
	private String rankingpromotion2;
	
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
	public List<String> getGroup() {
		return group;
	}
	public void setGroup(List<String> group) {
		this.group = group;
	}
	public String getIsPc() {
		return isPc;
	}
	public void setIsPc(String isPc) {
		this.isPc = isPc;
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
	public String getRanking1() {
		return ranking1;
	}
	public void setRanking1(String ranking1) {
		this.ranking1 = ranking1;
	}
	public String getRanking2() {
		return ranking2;
	}
	public void setRanking2(String ranking2) {
		this.ranking2 = ranking2;
	}
	public String getRankingpromotion1() {
		return rankingpromotion1;
	}
	public void setRankingpromotion1(String rankingpromotion1) {
		this.rankingpromotion1 = rankingpromotion1;
	}
	public String getRankingpromotion2() {
		return rankingpromotion2;
	}
	public void setRankingpromotion2(String rankingpromotion2) {
		this.rankingpromotion2 = rankingpromotion2;
	}
	
}
