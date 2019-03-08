package com.ido85.master.user.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ido85.seo.common.BaseEntity;

/**
 * 用户基本信息
 * 
 * @author fire
 * @since 20160324
 */
@Entity
@Table(name = "tf_f_tenant_app")
@NamedQueries(value = {
		@NamedQuery(name = "TenantApp", query = "select t from TenantApp t where t.tenantAppId = :tenantAppId") })
public class TenantApp extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "tenant_app_id")
	private String tenantAppId;
	@Column(name="TENANT_ID", insertable=false, updatable=false)
	private String tenantId;
	private String appType;
	private Date startDate;
	private Date endDate;
	private String delFlag;
	public String getTenantAppId() {
		return tenantAppId;
	}
	public void setTenantAppId(String tenantAppId) {
		this.tenantAppId = tenantAppId;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	
}
