package com.ido85.master.keyword.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ido85.seo.common.BaseEntity;

/**
 * 关键词与项目关系实体
 * @author fire
 *
 */
@Entity
@Table(name="tf_b_seo_pro_keyword")

public class ProKeyword extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="pro_key_rel_id")
	private Integer proKeyRelId;
	
	@Column(name="project_id")
	private Integer projectId;
	
	@Column(name="keyword_id")
	private Integer keywordId;
	
	@Column(name="del_flag")
	private String delFlag;
	
	@Column(name="is_brand")
	private String isBrand;
	
	@Column(name="tenant_id")
	private Integer tenantId;
	
	@OneToMany(mappedBy = "proKeyword")
	private List<GroupKeyRel> groupKeyRels;
	
	public List<GroupKeyRel> getGroupKeyRels() {
		return groupKeyRels;
	}
	public void setGroupKeyRels(List<GroupKeyRel> groupKeyRels) {
		this.groupKeyRels = groupKeyRels;
	}
	public Integer getProKeyRelId() {
		return proKeyRelId;
	}
	public void setProKeyRelId(Integer proKeyRelId) {
		this.proKeyRelId = proKeyRelId;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Integer getKeywordId() {
		return keywordId;
	}
	public void setKeywordId(Integer keywordId) {
		this.keywordId = keywordId;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getIsBrand() {
		return isBrand;
	}
	public void setIsBrand(String isBrand) {
		this.isBrand = isBrand;
	}
	public Integer getTenantId() {
		return tenantId;
	}
	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}
}
