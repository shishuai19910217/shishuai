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
 * tf_f_pro_down_url 实体
 * @author fire
 *
 */
@Entity
@Table(name = "tf_f_pro_down_url")
@NamedQueries(value = {})
public class ProDownUrl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="pro_down_id")
	private Long proDownId;
	@Column(name="tenant_id")
	private Integer tenantId;
	@Column(name="project_id")
	private Integer projectId;
	@Column(name="url")
	private String url;
	@Column(name="title")
	private String title;
	@Column(name="crawl_result")
	private String crawlResult;
	@Column(name="redirect_url") 	
	private String redirectUrl;
	@Column(name="ext_links")
	private long extLinks;
	@Column(name="ref_domains")
	private long refDomains;
	@Column(name="last_date")
	private Date lastDate;
	@Column(name="crawl_date")
	private Date crawlDate;
	public Long getProDownId() {
		return proDownId;
	}
	public void setProDownId(Long proDownId) {
		this.proDownId = proDownId;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCrawlResult() {
		return crawlResult;
	}
	public void setCrawlResult(String crawlResult) {
		this.crawlResult = crawlResult;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public long getExtLinks() {
		return extLinks;
	}
	public void setExtLinks(long extLinks) {
		this.extLinks = extLinks;
	}
	public long getRefDomains() {
		return refDomains;
	}
	public void setRefDomains(long refDomains) {
		this.refDomains = refDomains;
	}
	public Date getLastDate() {
		return lastDate;
	}
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
	public Date getCrawlDate() {
		return crawlDate;
	}
	public void setCrawlDate(Date crawlDate) {
		this.crawlDate = crawlDate;
	}
	
	
}
