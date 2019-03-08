package com.ido85.master.packages.dto;

import java.util.List;

public class PackageDTO {
	
	private String packageId;
	private String packageName;
	private List<PackageScopeDto> scope;
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public List<PackageScopeDto> getScope() {
		return scope;
	}
	public void setScope(List<PackageScopeDto> scope) {
		this.scope = scope;
	}
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
}
