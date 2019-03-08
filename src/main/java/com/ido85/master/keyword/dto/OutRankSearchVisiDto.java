package com.ido85.master.keyword.dto;


/**
 * 关键词排名页面排名tab中搜索可见性下拉列表接口出参
 * @author fire
 *
 */
public class OutRankSearchVisiDto {
	private String crawlDate;
	private String id;
	private String isCom;
	private String name;
	private double visibility;
	
	public String getCrawlDate() {
		return crawlDate;
	}
	public void setCrawlDate(String crawlDate) {
		this.crawlDate = crawlDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIsCom() {
		return isCom;
	}
	public void setIsCom(String isCom) {
		this.isCom = isCom;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getVisibility() {
		return visibility;
	}
	public void setVisibility(double visibility) {
		this.visibility = visibility;
	}

}
