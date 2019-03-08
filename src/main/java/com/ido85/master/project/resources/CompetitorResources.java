package com.ido85.master.project.resources;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.master.project.domain.Competitor;

public interface CompetitorResources extends JpaRepository<Competitor, String> {
	/***
	 * 根据项目id查询项目的所有竞品的信息
	 * 
	 * @param projectId
	 * @return
	 */
	@Query("select t from Competitor t where t.projectId = :projectId and t.delFlag = '0'")
	List<Competitor> getCompetitorsInfo(@Param("projectId") Integer projectId);

	@Transactional
	@Modifying
	@Query("update Competitor r set r.delFlag=:delFlag,r.updateBy = :updateBy , r.updateDate=:updateDate "
			+ "   where r.projectId=:projectId")
	int deleteByProjectId(@Param("delFlag") String delFlag, @Param("updateBy") Integer updateBy,
			@Param("updateDate") Date updateDate, @Param("projectId") Integer projectId);

	/**
	 * 根据项目id获取项目竞品信息
	 * 
	 * @param projectId
	 * @return
	 */
	@Query(" select t from Competitor t where t.projectId = :projectId and t.delFlag = '0'")
	List<Competitor> getCompetitorByProjectId(@Param("projectId") Integer projectId);

	/**
	 * 根据项目id获取项目竞品信息
	 * 
	 * @param projectId
	 * @return
	 */
	@Query(" select t from Competitor t where t.projectId in :projectIds and t.delFlag = '0'")
	List<Competitor> getCompetitorByProjectIds(@Param("projectIds") List<Integer> projectIds);

	@Transactional
	@Modifying
	@Query("update Competitor r set r.delFlag=:delFlag,r.updateBy = :updateBy , r.updateDate=:updateDate , r.endDate = :endDate "
			+ "   where r.projectId=:projectId and r.competitorId in :competitorIds")
	int deleteByComId(@Param("delFlag") String delFlag, @Param("updateBy") Integer updateBy,
			@Param("updateDate") Date updateDate, @Param("endDate") Date endDate, @Param("projectId") Integer projectId,
			@Param("competitorIds") List<Integer> competitorIds);

	/***
	 * 根据项目id 查询竞品数
	 * 
	 * @param projectid
	 * @return
	 */
	@Query("select COUNT(c) from Competitor c " + " where 1=1 " + " and c.projectId= :projectId" + " and c.delFlag='0'")
	public Object getComNumBy(@Param("projectId") Integer projectId);
}
