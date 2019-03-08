package com.ido85.seo.keywordrank.application;

import java.util.Date;

import com.ido85.seo.keywordrank.domain.DashboardKeyword;

public interface DashboardKeywordApplication {
	/**
	 * 获取概览中的项目下的关键词的排名信息
	 * @param crawlDate
	 * @param projectId
	 * @param keywordId
	 * @return
	 */
	DashboardKeyword getDashKeywordInfo(Date crawlDate, Integer projectId, Integer keywordId, Integer engineType);
}
