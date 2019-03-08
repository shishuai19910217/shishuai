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
 * tf_f_pro_domain 实体
 * @author fire
 *
 */
@Entity
@Table(name = "tf_f_pro_domain")
@NamedQueries(value = {})
public class ProDomain extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -826821440739446749L;
	
	@Id
	@Column(name="pro_domain_id")
	private Long proDomainId;
	@Column(name="TENANT_ID")
	private Integer tenantId;
	@Column(name="project_id")
	private Integer projectId;
	@Column(name="domain")
	private String domain;
	@Column(name="ext_links")
	private Long extLinks;
	@Column(name="total_links")
	private Long totalLinks;
	@Column(name="ref_domains")
	private Long refDomains;//引用域
	@Column(name="crawl_date")
	private Date crawlDate;
	public Long getProDomainId() {
		return proDomainId;
	}
	public void setProDomainId(Long proDomainId) {
		this.proDomainId = proDomainId;
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
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public Long getExtLinks() {
		return extLinks;
	}
	public void setExtLinks(Long extLinks) {
		this.extLinks = extLinks;
	}
	public Long getTotalLinks() {
		return totalLinks;
	}
	public void setTotalLinks(Long totalLinks) {
		this.totalLinks = totalLinks;
	}
	public Long getRefDomains() {
		return refDomains;
	}
	public void setRefDomains(Long refDomains) {
		this.refDomains = refDomains;
	}
	public Date getCrawlDate() {
		return crawlDate;
	}
	public void setCrawlDate(Date crawlDate) {
		this.crawlDate = crawlDate;
	}
	
	
}
