package com.ido85.master.user.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户基本信息
 * 此用户表实体类不对应实体表,因修改合并wi和orm系统用户类去掉和实体表关系;
 * 其他未做修改需要注意此用户类和实体表之间的差别,例如主键类型等
 * @author fire
 * @since 20160324
 */
//@Entity
//@Table(name = "tf_f_user")
//@NamedQueries(value = {
//		@NamedQuery(name = "User.getUserInfo", query = "select u from User u where u.userId = :userId") })
//@UuidGenerator(name="uuid")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	//	private static final long serialVersionUID = 1L;
	//TODO 添加租户id，用户登录和注册时是否要特殊处理，登录的时候是否要单独使用一个实体类
//	@Id
//	@GeneratedValue(generator="uuid")
//	@Column(name = "user_id")
	private String userId;
//	@Column(name="username",nullable=false)
	private String username;
//	@Column(name="password",nullable=false)
	private String password;
	private String salt;
	private String name;
//	@Column(nullable=false)
	private String email;
//	@Column(name = "email_type", nullable=false,length=1)
	private String emailType;//邮箱是否验证通过
//	@Column(nullable=false)
	private String mobile;
	private String phone;
	private String photo;
//	@Column(name="user_type",nullable=true,length=1)
	private String userType;//是否是席位用户
//	@Column(name = "del_flag", nullable=false)
	private String delFlag;
//	@Column(name = "login_date", nullable=false)
	private Date loginDate;//用户上次登陆时间
//	@Column(name = "create_by", nullable=false)
	private String createBy;
//	@Column(name = "create_date", nullable=false)
	private Date createDate;
//	@Column(name = "update_by")
	private String updateBy;
//	@Column(name = "update_date")
	private Date updateDate;
//	@Column(name = "access_key_id")
	private String accessKeyID;// 授权标识

//	@Column(name = "access_security_key")
	private String securityKey;// 认证标识
	private String remarks;
//	@Column(name = "reserve_field1")
	private String reserveField1;
//	@Column(name = "reserve_field2")
	private String reserveField2;
	private String tenantId;
	
	
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
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
		return delFlag;
	}
	public void setDelTlag(String delFlag) {
		this.delFlag = delFlag;
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
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
