package com.ido85.seo.allSite.dto;

import java.io.Serializable;
import java.util.Date;

import org.jboss.logging.FormatWith;

import com.ido85.frame.common.utils.DateUtils;

public class OutIssuesItemDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4877058830923564289L;
	private Long issuesNum;
	private Date date;
	private String issuesDate; 
	public OutIssuesItemDto(Date date,Long issuesNum){
		super();
		if(null != date){
			this.issuesDate = DateUtils.formatDateTime(date);
			this.issuesNum = issuesNum;
		}
		
		
	}
	public Long getIssuesNum() {
		return issuesNum;
	}
	public void setIssuesNum(Long issuesNum) {
		this.issuesNum = issuesNum;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getIssuesDate() {
		return issuesDate;
	}
	public void setIssuesDate(String issuesDate) {
		this.issuesDate = issuesDate;
	}
	
	
	
}
