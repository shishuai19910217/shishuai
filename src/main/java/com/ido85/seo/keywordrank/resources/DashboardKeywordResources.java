package com.ido85.seo.keywordrank.resources;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.seo.keywordrank.domain.DashboardKeyword;

public interface DashboardKeywordResources extends JpaRepository<DashboardKeyword, Integer> {
	/**
	 * 获取概览中的项目下的关键词的排名信息
	 * @param projectId
	 * @return
	 */
	@Query("select t from DashboardKeyword t where t.crawlDate = :crawlDate and t.projectId = :projectId and t.competitorId = :projectId and t.keywordId = :keywordId and t.engineType = :engineType and t.delFlag = '0' ")
	DashboardKeyword getDashKeywordInfo(@Param("crawlDate")Date crawlDate, @Param("projectId")Integer projectId, @Param("keywordId")Integer keywordId, @Param("engineType")Integer engineType);
}
