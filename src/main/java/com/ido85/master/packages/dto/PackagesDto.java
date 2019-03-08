package com.ido85.master.packages.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/****
 * 
 * @author shishuai
 *
 */
public class PackagesDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String packageId;
	private String packageName;
	private String packageType;
	private int packagePriceMontn;
	private int packagePriceYear;
	private int packageOldPriceMonth;
	private int packageOldPriceYear;
	private String appType;
	private String delFlag;
	private Date endDate;
	private String orderType;//订购类型   0未订购 1 订购  2 购买  
	private String montnType;//月付费  显示按钮  0 购买  1 续费  2 升级 -1不显示
	private String yearType;//年付费  显示按钮  0 购买  1 续费  2 升级 -1 不显示
	private String isProbation;//套餐是否可以试用
	//private String packageStartDate;//
	//private String packageEndDate;
	private List<Map<String,Object>> scope = new ArrayList<Map<String,Object>>();
	
	
	public String getIsProbation() {
		return isProbation;
	}
	public void setIsProbation(String isProbation) {
		this.isProbation = isProbation;
	}
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getPackageType() {
		return packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	public int getPackagePriceMontn() {
		return packagePriceMontn;
	}
	public void setPackagePriceMontn(int packagePriceMontn) {
		this.packagePriceMontn = packagePriceMontn;
	}
	public int getPackagePriceYear() {
		return packagePriceYear;
	}
	public void setPackagePriceYear(int packagePriceYear) {
		this.packagePriceYear = packagePriceYear;
	}
	public int getPackageOldPriceMonth() {
		return packageOldPriceMonth;
	}
	public void setPackageOldPriceMonth(int packageOldPriceMonth) {
		this.packageOldPriceMonth = packageOldPriceMonth;
	}
	public int getPackageOldPriceYear() {
		return packageOldPriceYear;
	}
	public void setPackageOldPriceYear(int packageOldPriceYear) {
		this.packageOldPriceYear = packageOldPriceYear;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public List<Map<String, Object>> getScope() {
		return scope;
	}
	public void setScope(List<Map<String, Object>> scope) {
		this.scope = scope;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getMontnType() {
		return montnType;
	}
	public void setMontnType(String montnType) {
		this.montnType = montnType;
	}
	public String getYearType() {
		return yearType;
	}
	public void setYearType(String yearType) {
		this.yearType = yearType;
	}
	
	
	
	
}
