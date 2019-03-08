package com.ido85.seo.dashboard.resources;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.seo.dashboard.domain.DashKeyword;
import com.ido85.seo.dashboard.dto.KeywordRankNumDto;

public interface DashboardKeywordResourcesd extends JpaRepository<DashKeyword, Long> {
	/***
	 * 项目的某个搜索引擎的 排名
	 * 
	 * @param projectId
	 * @param competitorId
	 * @param crawlDate
	 * @param engineType
	 * @return
	 */
	@Query("select t from DashKeyword t where t.projectId= :projectId " + " and t.competitorId = :competitorId "
			+ " and t.crawlDate = :crawlDate " + " and t.engineType = :engineType " + " and t.delFlag = '0'")
	public List<DashKeyword> getKeywordTop(@Param("projectId") Integer projectId,
			@Param("competitorId") Integer competitorId, @Param("crawlDate") Date crawlDate,
			@Param("engineType") Integer engineType);
	/***
	 * 
	 *	排名在1-10位的关键词个数
	 * @param projectId
	 * @param i
	 * @param engineType
	 * @param crawlTimes
	 * @return
	 */
	@Query("select new com.ido85.seo.dashboard.dto.KeywordRankNumDto(t.crawlDate,t.projectId,t.competitorId,count(1) ) from "
			+ " DashKeyword  t where  "
			+ " t.projectId = :projectId "
			+ " and t.rank <= :i "
			+ " and t.engineType = :engineType "
			+ " and t.crawlDate in :crawlTimes "
			+ " and t.delFlag='0' "
			+ " GROUP BY t.competitorId,t.crawlDate")
	public List<KeywordRankNumDto> getkeywordRankNum(@Param("projectId") Integer projectId, @Param("i") int i,
			@Param("engineType") Integer engineType, @Param("crawlTimes") List<Date> crawlTimes);
	
	
	/***
	 * 
	 * 搜索情况概览  排名情况
	 * @param projectId
	 * @param competitorId
	 * @param crawlDate
	 * @param engineType
	 * @return
	 */
	@Query("select t from DashKeyword t where t.projectId= :projectId "
			+ " and competitorId= :competitorId "
			+ " and crawlDate in :crawlDate "
			+ " and engineType= :engineType "
			+ " and delFlag = '0'")
	public List<DashKeyword> getkeywordRank(@Param("projectId") Integer projectId,
			@Param("competitorId") Integer competitorId,
			@Param("crawlDate") List<Date> crawlDate,
			@Param("engineType") Integer engineType);
}
