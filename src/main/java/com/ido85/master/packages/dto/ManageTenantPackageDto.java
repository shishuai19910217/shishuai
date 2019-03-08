package com.ido85.master.packages.dto;

import java.util.Date;

public class ManageTenantPackageDto {
	private String tenantId;
	private Date packageEndDate;
	private String packageId;
	private String packageName;
	private Date packageStartDate;
	private String  appType;
	private String tenantPackageId;
	
	public ManageTenantPackageDto(){}
	
	public ManageTenantPackageDto(Integer tenantId, Date packageEndDate, Integer packageId,
			Date packageStartDate, String appType, Integer tenantPackageId) {
		super();
		this.tenantId = tenantId+"";
		this.packageEndDate = packageEndDate;
		this.packageId = packageId+"";
		this.packageStartDate = packageStartDate;
		this.appType = appType;
		this.tenantPackageId = tenantPackageId+"";
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Date getPackageEndDate() {
		return packageEndDate;
	}

	public void setPackageEndDate(Date packageEndDate) {
		this.packageEndDate = packageEndDate;
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

	public Date getPackageStartDate() {
		return packageStartDate;
	}

	public void setPackageStartDate(Date packageStartDate) {
		this.packageStartDate = packageStartDate;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getTenantPackageId() {
		return tenantPackageId;
	}

	public void setTenantPackageId(String tenantPackageId) {
		this.tenantPackageId = tenantPackageId;
	}
}