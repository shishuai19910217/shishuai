package com.ido85.master.user.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

import com.ido85.seo.common.BaseEntity;

@Entity
@Table(name = "tf_f_vocation")
@NamedQueries(value = {
//		@NamedQuery(name = "Profession.getProfessionInfo", query = "select u from Profession u where professionInfoId = :professionInfoId") 
})
public class Vocation extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String vocationId;
	private String vocationName;
	private String delFlag;
	private String reserveField1;
	
	public String getVocationId() {
		return vocationId;
	}
	public void setVocationId(String vocationId) {
		this.vocationId = vocationId;
	}
	public String getVocationName() {
		return vocationName;
	}
	public void setVocationName(String vocationName) {
		this.vocationName = vocationName;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getReserveField1() {
		return reserveField1;
	}
	public void setReserveField1(String reserveField1) {
		this.reserveField1 = reserveField1;
	}
	
	
}
