package com.ido85.master.keyword.dto;

import java.util.List;

/**
 * 关键词排名页面 排名-----关键词的可见性 自然搜索排名 前30 接口出参
 * @author fire
 *
 */
public class OutKeyRankNumDto {
	private String crawltime;
	private List<OutKeyRankNumItemDto> list;
	public String getCrawltime() {
		return crawltime;
	}
	public void setCrawltime(String crawltime) {
		this.crawltime = crawltime;
	}
	public List<OutKeyRankNumItemDto> getList() {
		return list;
	}
	public void setList(List<OutKeyRankNumItemDto> list) {
		this.list = list;
	}

}