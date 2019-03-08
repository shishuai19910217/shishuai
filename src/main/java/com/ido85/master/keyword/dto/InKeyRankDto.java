package com.ido85.master.keyword.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.ido85.frame.common.utils.DateUtils;
import com.ido85.frame.common.utils.StringUtils;

/**
 * 关键词排名页面部分接口入参
 * @author fire
 *
 */
public class InKeyRankDto {
	private String brand;
	@NotNull
	@Length(min=1, max=25)
	private String endTime;
	@NotNull
	@Length(min=1, max=100)
	private String engineType;
	private List<String> keywordGroupList;
	private List<String> keywordList;
	@NotNull
	@Length(min=1, max=100)
	private String projectId;
	@NotNull
	@Length(min=1, max=25)
	private String startTime;
	private String visibility;
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
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