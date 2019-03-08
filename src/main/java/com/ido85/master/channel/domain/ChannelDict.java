package com.ido85.master.channel.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name="td_b_channel_dict")
@Where(clause = "del_flag='0'")
@NamedQuery(name="ChannelDict.findAll", query="SELECT t FROM ChannelDict t")
public class ChannelDict implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Integer engineType;
	private String engineName;
	private String delFlag;
	
	public Integer getEngineType() {
		return engineType;
	}
	public void setEngineType(Integer engineType) {
		this.engineType = engineType;
	}
	public String getEngineName() {
		return engineName;
	}
	public void setEngineName(String engineName) {
		this.engineName = engineName;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
}
