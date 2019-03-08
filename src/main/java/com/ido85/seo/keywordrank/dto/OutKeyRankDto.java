package com.ido85.seo.keywordrank.dto;

import java.util.List;
import java.util.Map;

/**
 * 关键词排名页面 排名-----关键词的可见性 自然搜索排名 前30 接口出参
 * @author fire
 *
 */
public class OutKeyRankDto {
	private String downNum;
	private String outRange;
	private List<Map<String, String>> stage;
	private String upNum;
	private String visibility;
	
	public String getDownNum() {
		return downNum;
	}
	public void setDownNum(String downNum) {
		this.downNum = downNum;
	}
	public String getOutRange() {
		return outRange;
	}
	public void setOutRange(String outRange) {
		this.outRange = outRange;
	}
	public List<Map<String, String>> getStage() {
		return stage;
	}
	public void setStage(List<Map<String, String>> stage) {
		this.stage = stage;
	}
	public String getUpNum() {
		return upNum;
	}
	public void setUpNum(String upNum) {
		this.upNum = upNum;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

}