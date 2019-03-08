package com.ido85.master.packages.dto;

import java.io.Serializable;

/***
 * 个人中心的订购信息中的套餐信息
 * @author shishuai
 *
 */
public class PackagePersonalDto implements Serializable{
	private String packageName;
	private String packageStartDate;
	private String packageEndDate;
	private int remainingDay;
	private String tenantPackageId;
	private String packageId;
	private int price;
	private String packageLevel;
	private String isExpire;
	public PackagePersonalDto(){
		
	}
	public PackagePersonalDto(   String packageName,String packageStartDate,
			String packageEndDate,int remainingDay,String tenantPackageId,String packageId,String packageLevel){
		this.packageEndDate = packageEndDate;
		this.packageId= packageId;
		this.packageName = packageName;
		this.packageStartDate = packageStartDate;
		this.tenantPackageId = tenantPackageId;
		this.remainingDay = remainingDay;
		this.packageLevel = packageLevel;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getPackageStartDate() {
		return packageStartDate;
	}
	public void setPackageStartDate(String packageStartDate) {
		this.packageStartDate = packageStartDate;
	}
	public String getPackageEndDate() {
		return packageEndDate;
	}
	public void setPackageEndDate(String packageEndDate) {
		this.packageEndDate = packageEndDate;
	}
	public int getRemainingDay() {
		return remainingDay;
	}
	public void setRemainingDay(int remainingDay) {
		this.remainingDay = remainingDay;
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
	public String getPackageLevel() {
		return packageLevel;
	}
	public void setPackageLevel(String packageLevel) {
		this.packageLevel = packageLevel;
	}
	public String getIsExpire() {
		return isExpire;
	}
	public void setIsExpire(String isExpire) {
		this.isExpire = isExpire;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

}
