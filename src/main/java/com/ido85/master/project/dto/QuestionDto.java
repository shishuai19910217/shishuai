package com.ido85.master.project.dto;

import java.util.Date;

import com.ido85.frame.common.utils.DateUtils;

/****
 * 项目管理中的 问题数
 * @author shishuai
 *
 */
public class QuestionDto{
	private int num;
	private String projectId;
	private String  crawlDate;
	public QuestionDto(){
		
	}
	public QuestionDto(int num , String projectId , Date  crawlDate){
		
		this.num = num;
		this.projectId = projectId;
		if (null!=crawlDate) {
			this.crawlDate = DateUtils.formatDate(crawlDate,"yyyyMMddHHmmss");
		}
		
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getCrawlDate() {
		return crawlDate;
	}
	public void setCrawlDate(String crawlDate) {
		this.crawlDate = crawlDate;
	}
}
