package com.ido85.seo.keywordrank.application;

import java.util.Date;
import java.util.List;

import com.ido85.master.project.dto.ProjectDto;
import com.ido85.seo.keywordrank.domain.SerpDetail;

public interface KeywordSERPApplication {
	/**
	 * 根据关键词和渠道/抓取时间获取关键词serp详细信息
	 * @param crawlDate
	 * @param engineType
	 * @param keywordId
	 * @return
	 */
	List<SerpDetail> getSerpDetailInfos(List<Date> crawlDates, Integer engineType, Integer keywordId) throws Exception;
	
	/**
	 * 根据关键词和渠道/抓取时间获取关键词serp排名变化趋势
	 * @param crawlDate
	 * @param engineType
	 * @param keywordId
	 * @return
	 */
	List<SerpDetail> getSerpRankTrend(List<Date> crawlDates, Integer engineType, Integer keywordId, ProjectDto projectDto) throws Exception;
}
