package com.ido85.frame.web.security.domain;

import java.util.List;

import com.ido85.frame.web.UserInfo;

/**
 * 此用户类是为了方便wi系统调用orm系统获取用户信息所创建,并未对应实体表
 * @author fire
 *
 */
public class UserOrm implements UserInfo{
	private static final long serialVersionUID = 1L;
	private String createDate;
	private String email;
	private String lastLoginDate;
	private String mobile;
	private String name;
	private String username;
	private Integer areaId;
	private String companyName;
	private Integer professionId;
	private String site;
	private Integer vocationId;
	private String accessKeyID;// 授权标识
	private String securityKey;// 认证标识
	private Integer userId;
	private Integer tenantId;
	private String photo;
	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}
	public String getAccessKeyID() {
		return accessKeyID;
	}
	public void setAccessKeyID(String accessKeyID) {
		this.accessKeyID = accessKeyID;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Integer getProfessionId() {
		return professionId;
	}
	public void setProfessionId(Integer professionId) {
		this.professionId = professionId;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public Integer getVocationId() {
		return vocationId;
	}
	public void setVocationId(Integer vocationId) {
		this.vocationId = vocationId;
	}
	public Integer getTenantId() {
		return tenantId;
	}

	public void setAccessSecurityKey(String accessSecurityKey) {
		this.securityKey = accessSecurityKey;
	}

	@Override
	public String getAccessSecurityKey() {
		return this.securityKey;
	}

	@Override
	public Integer getTenantID() {
		return tenantId;
	}

	@Override
	public List<String> getUserTenants() {
		return null;
	}

	@Override
	public Integer getUserId() {
		return this.userId;
	}
	
	public String getSecurityKey() {
		return securityKey;
	}

	public void setSecurityKey(String securityKey) {
		this.securityKey = securityKey;
	}
	
}
