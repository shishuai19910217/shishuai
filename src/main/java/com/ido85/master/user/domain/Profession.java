package com.ido85.master.user.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

import com.ido85.seo.common.BaseEntity;


@Entity
@Table(name = "tf_f_profession")
@NamedQueries(value = {
//		@NamedQuery(name = "Profession.getProfessionInfo", query = "select u from Profession u where professionInfoId = :professionInfoId") 
})
public class Profession extends BaseEntity implements Serializable {
private static final long serialVersionUID = 1L;
	
	@Id
	private String professionId;
	private String professionName;
	private String delFlag;
	private String reserveField1;
	public String getProfessionId() {
		return professionId;
	}
	public void setProfessionId(String professionId) {
		this.professionId = professionId;
	}
	public String getProfessionName() {
		return professionName;
	}
	public void setProfessionName(String professionName) {
		this.professionName = professionName;
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
