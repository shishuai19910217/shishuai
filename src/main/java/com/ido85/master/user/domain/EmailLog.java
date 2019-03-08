package com.ido85.master.user.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

@Entity
@Table(name = "tl_b_emaillog")
@NamedQueries(value = {})
public class EmailLog implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String emailLogId;
	private String userId;
	private String username;
	private String email;
	private String mobile;
	private String flag;
	private String emailType;
	private String delFlag;
	private String userType;
	private Date createDate;
	private String rsvFiled1;
	public String getEmailLogId() {
		return emailLogId;
	}
	public void setEmailLogId(String emailLogId) {
		this.emailLogId = emailLogId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getEmailType() {
		return emailType;
	}
	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getRsvFiled1() {
		return rsvFiled1;
	}
	public void setRsvFiled1(String rsvFiled1) {
		this.rsvFiled1 = rsvFiled1;
	}
	

}
