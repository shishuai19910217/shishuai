package com.ido85.master.keyword.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.ido85.frame.common.utils.DateUtils;
import com.ido85.frame.common.utils.StringUtils;

/**
 * 关键词排名页面排名tab中搜索可见性下拉列表接口入参
 * @author fire
 *
 */
public class InRankSearchVisiDto {
	private String brand;
	@NotNull
	@Length(min=1, max=25)
	private String endTime;
	@NotNull
	@Length(min=1, max=20)
	private String engineType;
	private List<String> keywordGroupList;
	private List<String> keywordList;
	@NotNull
	@Length(min=1, max=100)
	private String projectId;
	@NotNull
	@Length(min=1, max=25)
	private String startTime;
	@NotNull
	@Length(min=1, max=2)
	private String isWeek;
	private String ishave;
	private String projectName;
	
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getIsWeek() {
		return isWeek;
	}
	public void setIsWeek(String isWeek) {
		this.isWeek = isWeek;
	}
	public String getIshave() {
		return ishave;
	}
	public void setIshave(String ishave) {
		this.ishave = ishave;
	}
	public String getBrand() {
		if(StringUtils.isNotBlank(brand)){
			if("1".equals(brand)){
				brand = "0";
			}else if("0".equals(brand)){
				brand = "1";
			}
		}
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getEndTime() {
		endTime = DateUtils.utc2local(endTime, "yyyyMMdd");
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getEngineType() {
		return engineType;
	}
	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}
	public List<String> getKeywordGroupList() {
		return keywordGroupList;
	}
	public void setKeywordGroupList(List<String> keywordGroupList) {
		this.keywordGroupList = keywordGroupList;
	}
	public List<String> getKeywordList() {
		return keywordList;
	}
	public void setKeywordList(List<String> keywordList) {
		this.keywordList = keywordList;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getStartTime() {
		startTime = DateUtils.utc2local(startTime, "yyyyMMdd");
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
}
