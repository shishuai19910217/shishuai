package com.ido85.master.packages.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ido85.seo.common.BaseEntity;


/***
 * 套餐表
 * @author Administrator
 *
 */
@Entity
@Table(name="tf_f_package")
@NamedQueries(value = {
		 })
public class Packages extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="package_id")
	private Integer packageId;
	
	@Column(name="package_name")
	private String packageName;
	
	@Column(name="package_type")
	private String packageType;
	
	@Column(name="package_price_montn")
	private int packagePriceMontn;
	
	@Column(name="package_price_year")
	private int packagePriceYear;
	
	@Column(name="package_old_price_month")
	private int packageOldPriceMonth;
	
	@Column(name="package_old_price_year")
	private int packageOldPriceYear;
	
	@Column(name="app_type")
	private String appType;
	
	@Column(name="del_flag")
	private String delFlag;
	
	@Column(name="end_date")
	private Date endDate;
	
	
	@Column(name="reserve_field1")
	private String reserveField1;
	
	@Column(name="reserve_field2")
	private String reserveField2;
	@Column(name="package_level")
	private String packageLevel;
	private String isProbation;
	
	public String getIsProbation() {
		return isProbation;
	}
	public void setIsProbation(String isProbation) {
		this.isProbation = isProbation;
	}
	public Packages(){
		
	}
	public Packages(Integer packageId,String packageName, String packageType,
			int packagePriceMontn, int packagePriceYear, int packageOldPriceMonth, 
			int packageOldPriceYear, String appType, String delFlag, Date endDate, 
			String reserveField1, String reserveField2,String packageLevel){
		this.packageId=packageId;

		this.packageName=packageName;

		this.packageType=packageType;

		this.packagePriceMontn=packagePriceMontn;

		this.packagePriceYear=packagePriceYear;

		this.packageOldPriceMonth=packageOldPriceMonth;

		this.packageOldPriceYear=packageOldPriceYear;

		this.appType=appType;

		this.delFlag=delFlag;

		this.endDate=endDate;

		this.reserveField1=reserveField1;

		this.reserveField2=reserveField2;
		this.packageLevel = packageLevel;
	}
	/***
	 * 对应的元素关系
	 */
	@OneToMany(mappedBy = "packages", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<PackageElementsRel> packageElementsRelList = new ArrayList<PackageElementsRel>();
	
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getPackageType() {
		return packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	public int getPackagePriceMontn() {
		return packagePriceMontn;
	}
	public void setPackagePriceMontn(int packagePriceMontn) {
		this.packagePriceMontn = packagePriceMontn;
	}
	public int getPackagePriceYear() {
		return packagePriceYear;
	}
	public void setPackagePriceYear(int packagePriceYear) {
		this.packagePriceYear = packagePriceYear;
	}
	public int getPackageOldPriceMonth() {
		return packageOldPriceMonth;
	}
	public void setPackageOldPriceMonth(int packageOldPriceMonth) {
		this.packageOldPriceMonth = packageOldPriceMonth;
	}
	public int getPackageOldPriceYear() {
		return packageOldPriceYear;
	}
	public void setPackageOldPriceYear(int packageOldPriceYear) {
		this.packageOldPriceYear = packageOldPriceYear;
	}
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
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getReserveField1() {
		return reserveField1;
	}
	public void setReserveField1(String reserveField1) {
		this.reserveField1 = reserveField1;
	}
	public String getReserveField2() {
		return reserveField2;
	}
	public void setReserveField2(String reserveField2) {
		this.reserveField2 = reserveField2;
	}
	public List<PackageElementsRel> getPackageElementsRelList() {
		return packageElementsRelList;
	}
	public void setPackageElementsRelList(List<PackageElementsRel> packageElementsRelList) {
		this.packageElementsRelList = packageElementsRelList;
	}
	public String getPackageLevel() {
		return packageLevel;
	}
	public void setPackageLevel(String packageLevel) {
		this.packageLevel = packageLevel;
	}
	public Integer getPackageId() {
		return packageId;
	}
	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}
	
}