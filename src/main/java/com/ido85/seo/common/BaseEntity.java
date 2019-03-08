package com.ido85.seo.common;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.ido85.frame.web.UserInfo;
import com.ido85.frame.web.rest.utils.RestUserUtils;

/**
 * 实体的基类
 */
@MappedSuperclass
public class BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@Column(name="create_by")
	protected Integer createBy;
	@Column(name="create_date")
	protected Date createDate;
	@Column(name="update_by")
	protected Integer updateBy; 
	@Column(name="update_date")
	protected Date updateDate;
	
	public BaseEntity(){}
	public BaseEntity(Integer createBy, Date createDate,Integer updateBy,Date updateDate){
		this.createBy = createBy;
		this.createDate = createDate;
		this.updateBy = updateBy;
		this.updateDate = updateDate;
	}
	
	public Integer getCreateBy() {
		return createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public Integer getUpdateBy() {
		return updateBy;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	
	public void preInsert() throws Exception{
		UserInfo user = RestUserUtils.getUserInfo();
		if(user != null){
			this.updateBy = user.getUserId();
			this.createBy = user.getUserId();
		}
		
		this.updateDate = new Date();
		this.createDate = this.updateDate;
	}
	
	public void preUpdate() throws Exception{
		UserInfo user = RestUserUtils.getUserInfo();
		if(user != null){
			this.updateBy = user.getUserId();
			this.createBy = user.getUserId();
		}
		
		this.updateDate = new Date();
	}
	
	public Date getUpdateDate(String newTag) {
		if("1".equals(newTag))
			return new Date();
		return updateDate;
	}
	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
