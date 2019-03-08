package com.ido85.master.project.resources;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.master.project.domain.Project;
import com.ido85.master.project.dto.OutProComKwInfoDto;
import com.ido85.master.project.dto.OutProjectBaseInfoDto;
import com.ido85.master.project.dto.ProjectDto;


public interface ProjectWiResources  extends JpaRepository<Project, String> {
	/***
	 * 根据项目id查询项目
	 * @param projectId
	 * @return
	 */
	@Query("select new com.ido85.master.project.dto.ProjectDto(p.projectId, p.projectName, p.businessName ,"
			+ " p.projectUrl , p.isSubdomain, p.projectState , p.archiveDate, p.delFlag, p.endDate) from Project p  where 1=1 "
			+ " and p.projectId = :projectId")
	ProjectDto getProjectInfoById(@Param("projectId")Integer projectId);
	/***
	 * 根据项目id查询项目
	 * @param projectId
	 * @return
	 */
	@Query("select new com.ido85.master.project.domain.Project(p.tenantId,p.projectId, p.projectName, p.businessName ,"
			+ " p.projectUrl , p.isSubdomain, p.projectState , p.archiveDate, p.delFlag, p.endDate,p.receiveEmail,"
			+ "p.createBy, p.createDate,p.updateBy,p.updateDate, p.crawlDate, p.nextCrawlDate) from Project p  where 1=1 "
			+ " and p.projectId = :projectId"
			+ "")
	Project getProjectById(@Param("projectId")Integer projectId);
	
	
	/***
	 * 根据项目id查询项目
	 * @param projectId
	 * @return
	 */
	@Query("select new com.ido85.master.project.domain.Project(p.tenantId,p.projectId, p.projectName, p.businessName ,"
			+ " p.projectUrl , p.isSubdomain, p.projectState , p.archiveDate, p.delFlag, p.endDate,p.receiveEmail,"
			+ "p.createBy, p.createDate,p.updateBy,p.updateDate, p.crawlDate, p.nextCrawlDate) from Project p  where 1=1 "
			+ " and p.projectId in :projectId"
			+ "")
	List<Project> getProjectListById(@Param("projectId")List<Integer> projectIds);
	
	/***
	 * 修改项目状态
	 * @param projectId
	 */
	@Query("UPDATE  Project  p set p.projectState='1' where p.projectId=:projectId")
	void upDataProject(@Param("projectId")Integer projectId);
	
	
	/**
	 * 根据项目id获取项目信息
	 * @param projectId
	 * @returnprojectId
	 */
	@Query("select new com.ido85.master.project.dto.OutProjectBaseInfoDto(t.createDate, t.projectId, "
			+ "t.projectName, t.projectUrl, t.receiveEmail, t.isSubdomain, t.tenantId) from Project t"
			+ " where t.projectId = :projectId and t.delFlag = '0'")
	OutProjectBaseInfoDto getProjectBaseInfo(@Param("projectId")Integer projectId);
	
	/**
	 * 根据项目id获取项目信息
	 * @param projectId
	 * @returnprojectId
	 */
	@Query("select new com.ido85.master.project.dto.OutProComKwInfoDto(t.createDate, t.projectId, "
			+ "t.projectName, t.projectUrl, t.receiveEmail, t.isSubdomain, t.tenantId) from Project t"
			+ " where t.projectId = :projectId and t.delFlag = '0'")
	OutProComKwInfoDto getProjectBaseInfo2Dto(@Param("projectId")Integer projectId);

	/***
	 * 更新项目状态
	 * @param projectState
	 * @param archiveDate
	 * @param updateBy
	 * @param updateDate
	 * @param projectId
	 */
	@Transactional
	@Modifying
	@Query("update Project p set p.projectState=:projectState,p.archiveDate=:archiveDate ,p.updateBy=:updateBy,p.updateDate=:updateDate where p.projectId=:projectId")
	int updateProjectState(@Param("projectState") String projectState,@Param("archiveDate") Date archiveDate,
			@Param("updateBy") Integer updateBy,@Param("updateDate") Date updateDate,
			@Param("projectId")Integer projectId);
	
	
	/***
	 * 批量修改项目的 状态
	 * @param projectState
	 * @param archiveDate
	 * @param updateBy
	 * @param updateDate
	 * @param projectIds
	 */
	@Transactional
	@Modifying
	@Query("update Project p set p.projectState=:projectState ,p.updateBy=:updateBy,p.updateDate=:updateDate where p.projectId in :projectIds")
	int batchUpdateProjectState(@Param("projectState") String projectState,
			@Param("updateBy") Integer updateBy,@Param("updateDate") Date updateDate,
			@Param("projectIds")List<Integer> projectIds);
	/****
	 * 所有活跃的项目
	 * @return
	 */
	@Query("select p from Project p where p.delFlag='0' and p.projectState='1'")
	public List<Project> getAllList();
	
	/****
	 * 所有需要爬取的项目
	 * @return
	 */
	@Query("select p from Project p where p.delFlag='0' and p.projectState='1'")
	public List<Project> getAllCralwPro();
	
	/**
	 * 所有归档的项目
	 * @return
	 */
	@Query("select count(p) from Project p where p.delFlag = '0' and p.projectState = :state")
	public int checkArchive(@Param("state")String state);
	
	/**
	 * 所有需要被归档的项目
	 * @return
	 */
	@Query("select p from Project p where p.delFlag = '0' and p.projectState = '1' and "
			+ "exists (select 1 from TenantPackage tp where p.tenantId = tp.tenantId "
			+ "and tp.packageEndDate <= :date and tp.delFlag = '0')")
	public List<Project> getProject2Archive(@Param("date")Date date);
	
}
