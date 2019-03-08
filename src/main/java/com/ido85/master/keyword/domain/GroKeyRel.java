package com.ido85.master.keyword.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

import com.ido85.seo.common.BaseEntity;

/**
 * td_b_seo_group_key_rel 实体
 * @author fire
 *
 */
@Entity
@Table(name = "td_b_seo_group_key_rel")

public class GroKeyRel extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="group_key_id")
	private Integer groupKeyId;
	
	@Column(name="group_id")
	private Integer groupId;
	
	@Column(name="pro_key_rel_id")
	private Integer proKeyRelId;
	
	@Column(name="del_flag")
	private String delFlag;
	
	@Column(name="end_date")
	private Date endDate;
	
	@Column(name="tenant_id")
	private Integer tenantId;
	
	
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public Integer getProKeyRelId() {
		return proKeyRelId;
	}
	public void setProKeyRelId(Integer proKeyRelId) {
		this.proKeyRelId = proKeyRelId;
	}
	public Integer getGroupKeyId() {
		return groupKeyId;
	}
	public void setGroupKeyId(Integer groupKeyId) {
		this.groupKeyId = groupKeyId;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getTenantId() {
		return tenantId;
	}
	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}
}
