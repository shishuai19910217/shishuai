package com.ido85.master.packages.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ido85.seo.common.BaseEntity;

/***
 * 套餐实例表
 * 
 * @author shishuai
 *
 */
@Entity
@Table(name = "tf_f_tenant_package")
@NamedQueries(value = {})
/*@Multitenant(MultitenantType.SINGLE_TABLE)
@TenantDiscriminatorColumn(contextProperty="tenant.id", columnDefinition="TENANT_ID")*/
public class TenantPackage extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tenant_package_id")
	private Integer tenantPackageId;
	
	@Column(name="tenant_id")
	private Integer tenantId;
	
	@Column(name="package_id")
	private Integer packageId;
	
	@Column(name="app_type")
	private String appType;
	
	@Column(name="del_flag")
	private String delFlag;
	
	@Column(name="package_start_date")
	private Date packageStartDate;
	
	@Column(name="package_end_date")
	private Date packageEndDate;
	
	@Column(name="is_probation")
	private String isProbation;
	
	private String payType;
	
	@Column(name="package_level")
	private String packageLevel;
	/***
	 * 对应的元素关系
	 */
	@OneToMany(mappedBy = "tenantPackage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TenantPackageElementsRel> tenantPackageElementsRelList = new ArrayList<>();


	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Date getPackageStartDate() {
		return packageStartDate;
	}

	public void setPackageStartDate(Date packageStartDate) {
		this.packageStartDate = packageStartDate;
	}

	public Date getPackageEndDate() {
		return packageEndDate;
	}

	public void setPackageEndDate(Date packageEndDate) {
		this.packageEndDate = packageEndDate;
	}

	public String getIsProbation() {
		return isProbation;
	}

	public void setIsProbation(String isProbation) {
		this.isProbation = isProbation;
	}

	public List<TenantPackageElementsRel> getTenantPackageElementsRelList() {
		return tenantPackageElementsRelList;
	}

	public void setTenantPackageElementsRelList(List<TenantPackageElementsRel> tenantPackageElementsRelList) {
		this.tenantPackageElementsRelList = tenantPackageElementsRelList;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPackageLevel() {
		return packageLevel;
	}

	public void setPackageLevel(String packageLevel) {
		this.packageLevel = packageLevel;
	}

	public Integer getTenantId() {
		return tenantId;
	}

	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

	public Integer getPackageId() {
		return packageId;
	}

	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}

	public void setTenantPackageId(Integer tenantPackageId) {
		this.tenantPackageId = tenantPackageId;
	}

	public Integer getTenantPackageId() {
		return tenantPackageId;
	}

}
