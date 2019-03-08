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
 * tf_f_pro_links 实体
 * @author fire
 *
 */
@Entity
@Table(name = "tf_f_pro_links")
@NamedQueries(value = {})
public class ProLinks extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -8264198921161704095L;
	
	@Id
	@Column(name="pro_ext_id")
	private Long proExtId;
	@Column(name="tenant_id")
	private Integer tenantId;
	@Column(name="project_id")
	private Integer projectId;
	@Column(name="down_url")
	private String downUrl;
	@Column(name="link_url")
	private String linkUrl;
	@Column(name="anchor")
	private String anchor;
	@Column(name="flag_frame")
	private String flagFrame;
	@Column(name="flag_nofollow")
	private String flagNofollow;
	@Column(name="flag_mention") 	
	private String flagMention;
	@Column(name="flag_alttext")
	private String flagAlttext;
	@Column(name="flag_deleted")
	private String flagDeleted;
	@Column(name="flag_images")
	private String flagImages;
	@Column(name="flag_redirect")
	private String flagRedirect;
	@Column(name="date_lost")
	private Date dateLost;
	@Column(name="last_seen_date")
	private Date lastSeenDate;
	@Column(name="first_indexed_date")
	private Date firstIndexedDate;
	@Column(name="crawl_date")
	private Date crawlDate;
	public Long getProExtId() {
		return proExtId;
	}
	public void setProExtId(Long proExtId) {
		this.proExtId = proExtId;
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
	public String getDownUrl() {
		return downUrl;
	}
	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getAnchor() {
		return anchor;
	}
	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}
	public String getFlagFrame() {
		return flagFrame;
	}
	public void setFlagFrame(String flagFrame) {
		this.flagFrame = flagFrame;
	}
	public String getFlagNofollow() {
		return flagNofollow;
	}
	public void setFlagNofollow(String flagNofollow) {
		this.flagNofollow = flagNofollow;
	}
	public String getFlagMention() {
		return flagMention;
	}
	public void setFlagMention(String flagMention) {
		this.flagMention = flagMention;
	}
	public String getFlagAlttext() {
		return flagAlttext;
	}
	public void setFlagAlttext(String flagAlttext) {
		this.flagAlttext = flagAlttext;
	}
	public String getFlagDeleted() {
		return flagDeleted;
	}
	public void setFlagDeleted(String flagDeleted) {
		this.flagDeleted = flagDeleted;
	}
	public String getFlagImages() {
		return flagImages;
	}
	public void setFlagImages(String flagImages) {
		this.flagImages = flagImages;
	}
	public String getFlagRedirect() {
		return flagRedirect;
	}
	public void setFlagRedirect(String flagRedirect) {
		this.flagRedirect = flagRedirect;
	}
	public Date getDateLost() {
		return dateLost;
	}
	public void setDateLost(Date dateLost) {
		this.dateLost = dateLost;
	}
	public Date getLastSeenDate() {
		return lastSeenDate;
	}
	public void setLastSeenDate(Date lastSeenDate) {
		this.lastSeenDate = lastSeenDate;
	}
	public Date getFirstIndexedDate() {
		return firstIndexedDate;
	}
	public void setFirstIndexedDate(Date firstIndexedDate) {
		this.firstIndexedDate = firstIndexedDate;
	}
	public Date getCrawlDate() {
		return crawlDate;
	}
	public void setCrawlDate(Date crawlDate) {
		this.crawlDate = crawlDate;
	}
	

}
