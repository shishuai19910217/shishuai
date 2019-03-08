package com.ido85.master.user.dto;

import java.util.List;

import com.ido85.master.packages.dto.TenantPackageDto;



public class OutUserInfoDto {
	private String createDate;
	private String email;
	private String emailType;
	private String lastLoginDate;
	private String mobile;
	private String name;
	private List<TenantPackageDto> packageInfos;
	private String photo;
	private ProfessionDto professionInfos;
	private int projectNum;
	private int activeNum;
	public int getActiveNum() {
		return activeNum;
	}
	public void setActiveNum(int activeNum) {
		this.activeNum = activeNum;
	}
	private String username;
	
	public List<TenantPackageDto> getPackageInfos() {
		return packageInfos;
	}
	public void setPackageInfos(List<TenantPackageDto> packageInfos) {
		this.packageInfos = packageInfos;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public ProfessionDto getProfessionInfos() {
		return professionInfos;
	}
	public void setProfessionInfos(ProfessionDto professionInfos) {
		this.professionInfos = professionInfos;
	}
	public int getProjectNum() {
		return projectNum;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmailType() {
		return emailType;
	}
	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}
	public String getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setProjectNum(int projectNum) {
		this.projectNum = projectNum;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
