package com.ido85.master.keyword.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ido85.seo.common.BaseEntity;

/**
 * group 实体
 * @author fire
 *
 */
@Entity
@Table(name = "tf_f_seo_group")

public class Group extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="group_id")
	private Integer groupId;
	
	@Column(name="project_id")
	private Integer projectId;
	
	@Column(name="group_name")
	private String groupName;
	
	@Column(name="tenant_id")
	private Integer tenantId;
	
	@Column(name="del_flag")
	private String delFlag;
	
	@Column(name="end_date")
	private Date endDate;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
	@JsonIgnore
	private List<GroupKeyRel> groupKeyRels;
	
	public List<GroupKeyRel> getGroupKeyRels() {
		return groupKeyRels;
	}
	public void setGroupKeyRels(List<GroupKeyRel> groupKeyRels) {
		this.groupKeyRels = groupKeyRels;
	}
	public String getGroupId() {
		return groupId == null ? null : groupId.toString();
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
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
