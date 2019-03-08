package com.ido85.master.statistics.dto;

import java.util.Date;

public class ComDistributeRankDto {
	private String statisticsId ;
	 private String competitorId ;
	 private String url ;
	 private String engineType ;
	 private String topThree ;
	 private String fourToTen ;
	 private String eleventToTwenty;
	 private String twentyoneTothirty ;
	 private String isCom;
	 private String delFlag;
	 private String competitorName;
	 private Date crawlDate;
	 public ComDistributeRankDto(){
		 
	 }
	public ComDistributeRankDto(String statisticsId, String competitorId, String url, String engineType,
			String topThree, String fourToTen, String eleventToTwenty, String twentyoneTothirty, String isCom,
			String delFlag, String competitorName,Date crawlDate) {
		this.statisticsId = statisticsId;
		this.competitorId = competitorId;
		this.url = url;
		this.engineType = engineType;
		this.topThree = topThree;
		this.fourToTen = fourToTen;
		this.setEleventToTwenty(eleventToTwenty);
		this.twentyoneTothirty = twentyoneTothirty;
		this.isCom = isCom;
		this.delFlag = delFlag;
		this.competitorName = competitorName;
		this.crawlDate =   crawlDate;
	}
	public String getStatisticsId() {
		return statisticsId;
	}
	public void setStatisticsId(String statisticsId) {
		this.statisticsId = statisticsId;
	}
	public String getCompetitorId() {
		return competitorId;
	}
	public void setCompetitorId(String competitorId) {
		this.competitorId = competitorId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getEngineType() {
		return engineType;
	}
	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}
	public String getTopThree() {
		return topThree;
	}
	public void setTopThree(String topThree) {
		this.topThree = topThree;
	}
	public String getFourToTen() {
		return fourToTen;
	}
	public void setFourToTen(String fourToTen) {
		this.fourToTen = fourToTen;
	}
	public String getTwentyoneTothirty() {
		return twentyoneTothirty;
	}
	public void setTwentyoneTothirty(String twentyoneTothirty) {
		this.twentyoneTothirty = twentyoneTothirty;
	}
	public String getIsCom() {
		return isCom;
	}
	public void setIsCom(String isCom) {
		this.isCom = isCom;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getCompetitorName() {
		return competitorName;
	}
	public void setCompetitorName(String competitorName) {
		this.competitorName = competitorName;
	}
	public Date getCrawlDate() {
		return crawlDate;
	}
	public void setCrawlDate(Date crawlDate) {
		this.crawlDate = crawlDate;
	}
	public String getEleventToTwenty() {
		return eleventToTwenty;
	}
	public void setEleventToTwenty(String eleventToTwenty) {
		this.eleventToTwenty = eleventToTwenty;
	}
}
