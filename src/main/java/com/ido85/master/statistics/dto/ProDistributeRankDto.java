package com.ido85.master.statistics.dto;

import java.util.Date;

public class ProDistributeRankDto {
	private String statisticsId ;
	 private String projectId;
	 private String url ;
	 private String engineType ;
	 private String topThree ;
	 private String fourToTen ;
	 private String elevenToTwenty;
	 private String twentyoneTothirty ;
	 private String isCom;
	 private String delFlag;
	 private String projectName;
	 public ProDistributeRankDto(){
		 
	 }
	 public ProDistributeRankDto(String statisticsId, String projectId, String url,
			String engineType, String topThree, String fourToTen, String elevenToTwenty, String twentyoneTothirty,
			String isCom, String delFlag, String projectName, Date crawlDate) {
		super();
		this.statisticsId = statisticsId;
		this.projectId = projectId;
		this.url = url;
		this.engineType = engineType;
		this.topThree = topThree;
		this.fourToTen = fourToTen;
		this.elevenToTwenty = elevenToTwenty;
		this.twentyoneTothirty = twentyoneTothirty;
		this.isCom = isCom;
		this.delFlag = delFlag;
		this.projectName = projectName;
		this.crawlDate = crawlDate;
	}
	private Date crawlDate;
	public String getStatisticsId() {
		return statisticsId;
	}
	public void setStatisticsId(String statisticsId) {
		this.statisticsId = statisticsId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
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
	public String getElevenToTwenty() {
		return elevenToTwenty;
	}
	public void setElevenToTwenty(String elevenToTwenty) {
		this.elevenToTwenty = elevenToTwenty;
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
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Date getCrawlDate() {
		return crawlDate;
	}
	public void setCrawlDate(Date crawlDate) {
		this.crawlDate = crawlDate;
	}
}
