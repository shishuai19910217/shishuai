package com.ido85.master.project.dto;

import java.util.List;
import java.util.Map;
/***
 * 添加项目是检查项目
 * @author shishuai
 *
 */
public class OutCheckProjectDto{
	private List<Map<String,String>> competitors;
	private String nameFlag;//0：否，1：是
	private String urlFlag;//0：既没有重定向也不存在已经创建的项目网址，1：项目网址存在，2：网址有重定向
	public List<Map<String, String>> getCompetitors() {
		return competitors;
	}
	public void setCompetitors(List<Map<String, String>> competitors) {
		this.competitors = competitors;
	}
	public String getNameFlag() {
		return nameFlag;
	}
	public void setNameFlag(String nameFlag) {
		this.nameFlag = nameFlag;
	}
	public String getUrlFlag() {
		return urlFlag;
	}
	public void setUrlFlag(String urlFlag) {
		this.urlFlag = urlFlag;
	}
	

}
