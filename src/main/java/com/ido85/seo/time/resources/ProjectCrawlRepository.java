package com.ido85.seo.time.resources;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.seo.time.domain.ProjectCrawl;

public interface ProjectCrawlRepository extends JpaRepository<ProjectCrawl, Integer> {
	/**
	 * 获取项目在开一段时间内的所有抓取日期
	 * @param startTime
	 * @param endTime
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	@Query(" select t.crawlDate from ProjectCrawl t where t.crawlDate between :startTime and :endTime and t.projectId = :projectId and delFlag = '0' ")
	List<Date> getCrawlDate(@Param("startTime")Date startTime, @Param("endTime")Date endTime, @Param("projectId")Integer projectId) throws Exception;
	/**
	 * 获取项目最新的7日抓取日期
	 * @param startTime
	 * @param endTime
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	@Query(" select t.crawlDate from ProjectCrawl t where t.projectId = :projectId and delFlag = '0' order by t.crawlDate desc")
	List<Date> getNewestCrawlDate(@Param("projectId")Integer projectId) throws Exception;
}
