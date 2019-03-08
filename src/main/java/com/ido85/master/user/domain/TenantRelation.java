package com.ido85.master.user.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ido85.seo.common.BaseEntity;

/**
 * 用户基本信息和租户关系表
 * @author fire
 *
 */
@Entity
@Table(name = "tf_f_tenant_relation")
@NamedQueries(value = {
		@NamedQuery(name = "TenantRelation.getTenantRelation", query = "select t from TenantRelation t where t.tenantRelationId = :tenantRelationId") })
public class TenantRelation extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String tenantRelationId;
	private String tenantId;
	private String userId;
	private String appType;
	private String relationFlag;
	private Date delDate;
	private String delFlag;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTenantRelationId() {
		return tenantRelationId;
	}
	public void setTenantRelationId(String tenantRelationId) {
		this.tenantRelationId = tenantRelationId;
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
	public String getRelationFlag() {
		return relationFlag;
	}
	public void setRelationFlag(String relationFalg) {
		this.relationFlag = relationFalg;
	}
	public Date getDelDate() {
		return delDate;
	}
	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFalg) {
		this.delFlag = delFalg;
	}
	

}
