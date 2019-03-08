package com.ido85.seo.dashboard.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
/***
 * 品牌 某个搜索引擎的可见性
 * @author shishuai
 *
 */
public class projectEngineTypeVisibilityDto implements Serializable {
	private Long dashboardVisibilityId;
	private Date crawlDate;
	private Integer brandId;//品牌id
	private String isCom;//是否竞品
	private Integer engineType;
	private Double visibility;
	private String name;//品牌名称
	private Double upOrDown;
	public Long getDashboardVisibilityId() {
		return dashboardVisibilityId;
	}
	public void setDashboardVisibilityId(Long dashboardVisibilityId) {
		this.dashboardVisibilityId = dashboardVisibilityId;
	}
	public Date getCrawlDate() {
		return crawlDate;
	}
	public void setCrawlDate(Date crawlDate) {
		this.crawlDate = crawlDate;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public String getIsCom() {
		return isCom;
	}
	public void setIsCom(String isCom) {
		this.isCom = isCom;
	}
	public Double getVisibility() {
		return visibility;
	}
	public void setVisibility(Double visibility) {
		this.visibility = visibility;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getUpOrDown() {
		return upOrDown;
	}
	public void setUpOrDown(Double upOrDown) {
		this.upOrDown = upOrDown;
	}
	public Integer getEngineType() {
		return engineType;
	}
	public void setEngineType(Integer engineType) {
		this.engineType = engineType;
	}
	
}
