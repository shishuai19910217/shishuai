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
@Table(name = "tf_f_tenant")
@NamedQueries(value = {
		@NamedQuery(name = "Tenant", query = "select t from Tenant t where t.tenantId = :tenantId") })
public class Tenant extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "tenant_id")
	private String tenantId;
	private String tenantName;
	private Date delDate;
	private String delFalg;
	private String reserveField1;
	private String reserveField2;
	
	
	public Date getDelDate() {
		return delDate;
	}
	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public String getDelFalg() {
		return delFalg;
	}
	public void setDelFalg(String delFalg) {
		this.delFalg = delFalg;
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
