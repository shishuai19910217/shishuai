package com.ido85.master.packages.dto;

import java.io.Serializable;
/**
 * 套餐实例的规格
 * @author shishuai
 *
 */

public class TenantPackageDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String appType;
	private int competitorNum;
	private String endDate;
	private int keywordNum;
	private String  name;
	private int priceMonth;
	private int priceYear;
	private int projectNum;
	private String startDate;
	private String trialFlag;
	private String yearOrMonth;
	private String packageId;
	private String tenantPackageId;
	private String packageLevel;
	
	public String getPackageLevel() {
		return packageLevel;
	}
	public void setPackageLevel(String packageLevel) {
		this.packageLevel = packageLevel;
	}
	public String getTenantPackageId() {
		return tenantPackageId;
	}
	public void setTenantPackageId(String tenantPackageId) {
		this.tenantPackageId = tenantPackageId;
	}
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public int getCompetitorNum() {
		return competitorNum;
	}
	public void setCompetitorNum(int competitorNum) {
		this.competitorNum = competitorNum;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getKeywordNum() {
		return keywordNum;
	}
	public void setKeywordNum(int keywordNum) {
		this.keywordNum = keywordNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPriceMonth() {
		return priceMonth;
	}
	public void setPriceMonth(int priceMonth) {
		this.priceMonth = priceMonth;
	}
	public int getPriceYear() {
		return priceYear;
	}
	public void setPriceYear(int priceYear) {
		this.priceYear = priceYear;
	}
	public int getProjectNum() {
		return projectNum;
	}
	public void setProjectNum(int projectNum) {
		this.projectNum = projectNum;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String  startDate) {
		this.startDate = startDate;
	}
	public String getTrialFlag() {
		return trialFlag;
	}
	public void setTrialFlag(String trialFlag) {
		this.trialFlag = trialFlag;
	}
	public String getYearOrMonth() {
		return yearOrMonth;
	}
	public void setYearOrMonth(String yearOrMonth) {
		this.yearOrMonth = yearOrMonth;
	}
	
}
