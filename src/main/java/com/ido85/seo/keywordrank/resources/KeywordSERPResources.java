package com.ido85.seo.keywordrank.resources;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.seo.keywordrank.domain.SerpDetail;

public interface KeywordSERPResources extends JpaRepository<SerpDetail, Integer> {
	/**
	 * 根据关键词和渠道/抓取时间获取关键词serp详细信息
	 * @param crawlDate
	 * @param engineType
	 * @param keywordId
	 * @return
	 */
	@Query("SELECT t FROM SerpDetail t WHERE t.keywordId = :keywordId and t.crawlDate IN :crawlDates AND t.engineType = :engineType AND t.delFlag = '0' order by t.rank asc ")
	List<SerpDetail> getSerpDetailInfos(@Param("crawlDates")List<Date> crawlDates, @Param("engineType")Integer engineType, @Param("keywordId")Integer keywordId) throws Exception;
}
