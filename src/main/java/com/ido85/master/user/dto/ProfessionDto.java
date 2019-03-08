package com.ido85.master.user.dto;

import java.util.Date;

public class ProfessionDto {
	private String professionInfoId;
	private String userId;
	private String companyName;
	private String companyUrl;
	private String professionId;//行业字典表行业id
	private String vocationId;//职位和行业关系表id
	private String professionName;//行业字典表行业名称
	private String vocationName;//职位和行业关系表名称
	private String delFlag;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;
	private String reserveField1;
	private String reserveField2;
	
	
	public String getProfessionName() {
		return professionName;
	}
	public void setProfessionName(String professionName) {
		this.professionName = professionName;
	}
	public String getVocationName() {
		return vocationName;
	}
	public void setVocationName(String vocationName) {
		this.vocationName = vocationName;
	}
	public String getVocationId() {
		return vocationId;
	}
	public void setVocationId(String vocationId) {
		this.vocationId = vocationId;
	}
	public String getProfessionInfoId() {
		return professionInfoId;
	}
	public void setProfessionInfoId(String professionInfoId) {
		this.professionInfoId = professionInfoId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyUrl() {
		return companyUrl;
	}
	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}
	public String getProfessionId() {
		return professionId;
	}
	public void setProfessionId(String professionId) {
		this.professionId = professionId;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
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
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getReserveField1() {
		return reserveField1;
	}
	public void setReserveField1(String reserveField1) {
		this.reserveField1 = reserveField1;
	}
	public String getReserveField2() {
		return reserveField2;
	}
	public void setReserveField2(String reserveField2) {
		this.reserveField2 = reserveField2;
	}
	
}
