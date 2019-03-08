package com.ido85.master.user.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class UserDto {
	@NotNull
	private String username;
	private String password;
	private String name;
	@NotNull
	private String email;
	private String emailType;//邮箱是否验证通过
	@NotNull
	private String mobile;
	private String phone;
	private String photo;
	private String userType;//是否是席位用户
	private String delTlag;
	private Date loginDate;//用户上次登陆时间
	private String createBy;
	private Date createDate;
	private String updateBy;
	private String updateDate;
	private String accessKeyID;// 授权标识
	private String securityKey;// 认证标识
	private String remarks;
	private String packageId; //套餐id,当probationFlag为1时，即试用注册时，传此字段
	private String probationFlag;//是否是试用注册  0-否，1-是
	private String smsCode;//手机短信验证码
	
	
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getProbationFlag() {
		return probationFlag;
	}
	public void setProbationFlag(String probationFlag) {
		this.probationFlag = probationFlag;
	}
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getDelTlag() {
		return delTlag;
	}
	public void setDelTlag(String delTlag) {
		this.delTlag = delTlag;
	}
	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getAccessKeyID() {
		return accessKeyID;
	}
	public void setAccessKeyID(String accessKeyID) {
		this.accessKeyID = accessKeyID;
	}
	public String getSecurityKey() {
		return securityKey;
	}
	public void setSecurityKey(String securityKey) {
		this.securityKey = securityKey;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
