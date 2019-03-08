package com.ido85.seo.extlinks.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

import com.ido85.seo.common.BaseEntity;

/**
 * tf_f_pro_links_view 实体
 * @author fire
 *
 */
@Entity
@Table(name = "tf_f_pro_anchor")
@NamedQueries(value = {})
public class ProAnchor extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -2650546307594363470L;
	
	@Id
	@Column(name="pro_anchor_id")
	private Long proAnchorId;
	@Column(name="tenant_id")
	private Integer tenantId;
	@Column(name="project_id")
	private Integer projectId;
	@Column(name="anchor")
	private String anchor;
	@Column(name="nofollow_links")
	private long nofollowLinks;
	@Column(name="deleted_links")
	private long deletedLinks;
	@Column(name="total_links")
	private long totalLinks;
	@Column(name="ref_domains")
	private long refDomains;
	@Column(name="crawl_date")
	private Date crawlDate;
	public Long getProAnchorId() {
		return proAnchorId;
	}
	public void setProAnchorId(Long proAnchorId) {
		this.proAnchorId = proAnchorId;
	}
	public Integer getTenantId() {
		return tenantId;
	}
	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getAnchor() {
		return anchor;
	}
	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}
	public long getNofollowLinks() {
		return nofollowLinks;
	}
	public void setNofollowLinks(long nofollowLinks) {
		this.nofollowLinks = nofollowLinks;
	}
	public long getDeletedLinks() {
		return deletedLinks;
	}
	public void setDeletedLinks(long deletedLinks) {
		this.deletedLinks = deletedLinks;
	}
	public long getTotalLinks() {
		return totalLinks;
	}
	public void setTotalLinks(long totalLinks) {
		this.totalLinks = totalLinks;
	}
	public long getRefDomains() {
		return refDomains;
	}
	public void setRefDomains(long refDomains) {
		this.refDomains = refDomains;
	}
	public Date getCrawlDate() {
		return crawlDate;
	}
	public void setCrawlDate(Date crawlDate) {
		this.crawlDate = crawlDate;
	}
	

}
