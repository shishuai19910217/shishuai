package com.ido85.master.packages.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ido85.seo.common.BaseEntity;


/***
 * 套餐实例与元素的关系
 * 
 * @author shishuai
 *
 */
@Entity
@Table(name = "tf_f_tenant_package_elements_rel")
@NamedQueries(value = {})
public class TenantPackageElementsRel extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@Column(name="exam_ele_rel_id")
	private Integer examEleRelId;
	
	@Column(name="valid_flag")
	private String validFlag;
	
	@Column(name="package_id")
	private Integer packageId;
	
	@Column(name="used_min")
	private int usedMin;
	
	@Column(name="used_max")
	private int usedMax;
	
	@Column(name="del_flag")
	private String delFlag;
	
	@Column(name="end_date")
	private Date endDate;
	
	@Column(name="valid_type")
	private String validType;
	
	@Column(name="element_value")
	private String 	elementValue;
	private String elementType;
	private String unit;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "tenant_package_id")
	private TenantPackage tenantPackage;

	@OneToOne
	@JoinColumn(name = "element_id")
	private Elements elements;

	
	
	

	public String getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	public int getUsedMin() {
		return usedMin;
	}

	public void setUsedMin(int usedMin) {
		this.usedMin = usedMin;
	}

	public int getUsedMax() {
		return usedMax;
	}

	public void setUsedMax(int usedMax) {
		this.usedMax = usedMax;
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

	public TenantPackage getTenantPackage() {
		return tenantPackage;
	}

	public void setTenantPackage(TenantPackage tenantPackage) {
		this.tenantPackage = tenantPackage;
	}

	public Elements getElements() {
		return elements;
	}

	public void setElements(Elements elements) {
		this.elements = elements;
	}

	public String getValidType() {
		return validType;
	}

	public void setValidType(String validType) {
		this.validType = validType;
	}

	public String getElementValue() {
		return elementValue;
	}

	public void setElementValue(String elementValue) {
		this.elementValue = elementValue;
	}

	public String getElementType() {
		return elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getExamEleRelId() {
		return examEleRelId;
	}

	public void setExamEleRelId(Integer examEleRelId) {
		this.examEleRelId = examEleRelId;
	}

	public Integer getPackageId() {
		return packageId;
	}

	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}

}
