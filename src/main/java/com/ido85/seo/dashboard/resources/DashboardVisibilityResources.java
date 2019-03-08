package com.ido85.seo.dashboard.resources;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.seo.dashboard.domain.DashboardVisibility;

public interface DashboardVisibilityResources extends JpaRepository<DashboardVisibility, Long> {
	/***
	 * 某个项目以及竞品的 某个搜索引擎下的可见性
	 * @param crawlDates
	 * @param projectId
	 * @param engineType
	 * @return
	 */
	@Query("select t from DashboardVisibility t where t.crawlDate in :crawlDates "
			+ " and t.projectId = :projectId "
			+ " and t.engineType = :engineType "
			+ " and t.delFlag ='0'")
	public List<DashboardVisibility> getVisibility(@Param("crawlDates") List<Date> crawlDates,
			@Param("projectId") Integer projectId,
			@Param("engineType") Integer engineType);
	
	/***
	 * 只能是单品牌
	 * 某个项目多个搜索引擎下的可见性对比
	 * @param crawlDates
	 * @param projectId
	 * @param engineType
	 * @return
	 */
	@Query("select t from DashboardVisibility t where t.crawlDate in :crawlDates "
			+ " and t.projectId = :projectId "
			+ " and t.competitorId = :competitorId"
			+ " and t.engineType in :engineTypes "
			+ " and t.delFlag ='0'")
	public List<DashboardVisibility> getVisibilityListByMoreEngine(@Param("crawlDates") List<Date> crawlDates,
			@Param("projectId") Integer projectId,
			@Param("competitorId") Integer competitorId,
			@Param("engineTypes") List<Integer> engineTypes);
}
