package com.ido85.master.keyword.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ido85.seo.common.BaseEntity;

/**
 * td_b_group_key_rel 实体
 * @author fire
 *
 */
@Entity
@Table(name = "td_b_seo_group_key_rel")

public class GroupKeyRel extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="group_key_id")
	private Integer groupKeyId;
	
	@ManyToOne
	@JoinColumn(name = "group_id")
	@JsonIgnore
	private Group group;
	
	@ManyToOne
	@JoinColumn(name = "pro_key_rel_id")
	@JsonIgnore
	private ProKeyword proKeyword;
	
	@Column(name="del_flag")
	private String delFlag;
	
	@Column(name="end_date")
	private String endDate;
	
	@Column(name="tenant_id")
	private Integer tenantId;
	
	public ProKeyword getProKeyword() {
		return proKeyword;
	}
	public void setProKeyword(ProKeyword proKeyword) {
		this.proKeyword = proKeyword;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
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
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Integer getTenantId() {
		return tenantId;
	}
	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}
	
}
