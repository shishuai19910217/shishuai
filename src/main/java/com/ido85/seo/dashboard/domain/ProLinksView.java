package com.ido85.seo.dashboard.domain;

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
@Table(name = "tf_f_pro_links_view")
@NamedQueries(value = {})
public class ProLinksView extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -2650546307594363470L;
	
	@Id
	@Column(name="pro_links_id")
	private Long proLinksId;
	@Column(name="TENANT_ID")
	private Integer tenantId;
	@Column(name="project_id")
	private Integer projectId;
	@Column(name="competitor_id")
	private Integer competitorId;
	@Column(name="crawl_date")
	private Date crawlDate;
	@Column(name="ext_back_links")
	private Long extBackLinks;
	@Column(name="ref_domains")
	private Long refDomains;
	public Long getProLinksId() {
		return proLinksId;
	}
	public void setProLinksId(Long proLinksId) {
		this.proLinksId = proLinksId;
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
	public Integer getCompetitorId() {
		return competitorId;
	}
	public void setCompetitorId(Integer competitorId) {
		this.competitorId = competitorId;
	}
	public Date getCrawlDate() {
		return crawlDate;
	}
	public void setCrawlDate(Date crawlDate) {
		this.crawlDate = crawlDate;
	}
	public Long getExtBackLinks() {
		return extBackLinks;
	}
	public void setExtBackLinks(Long extBackLinks) {
		this.extBackLinks = extBackLinks;
	}
	public Long getRefDomains() {
		return refDomains;
	}
	public void setRefDomains(Long refDomains) {
		this.refDomains = refDomains;
	}
	
	

}
