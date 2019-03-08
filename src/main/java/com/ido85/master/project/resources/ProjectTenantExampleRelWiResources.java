package com.ido85.master.project.resources;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.master.project.domain.ProjectTenantExampleRel;

public interface ProjectTenantExampleRelWiResources extends JpaRepository<ProjectTenantExampleRel, String>{
	/***
	 * 根据实例id 查询项目数
	 * @param tenantPackageId
	 * @return
	 */
	@Query("select COUNT(rel) from ProjectTenantExampleRel rel join rel.project p "
			+ " where rel.tenantPackageId=:tenantPackageId "
			+ " and p.projectState=:projectState"
			+ " and rel.delFlag='0'"
			+ " and p.delFlag='0'")
	public Object getProjectNumBy(@Param("tenantPackageId")Integer tenantPackageId,@Param("projectState")String projectState );
	
	
	/***
	 * 根据项目名称+url 查询项目
	 * @param tenantPackageId
	 * @return
	 */
	@Query("select rel from ProjectTenantExampleRel rel join rel.project p "
			+ " where rel.tenantPackageId=:tenantPackageId "
			+ " and rel.delFlag='0'"
			+ " and p.delFlag='0'  "
			+ " and p.projectName=:projectName "
			+ " and p.projectUrl = :projectUrl")
	ProjectTenantExampleRel getProjectByNa(@Param("tenantPackageId")Integer tenantPackageId,@Param("projectName")String projectName,@Param("projectUrl")String projectUrl );
	
	/***
	 * 根据项目名 查询项目
	 * @param tenantPackageId
	 * @return
	 */
	@Query("select rel from ProjectTenantExampleRel rel join rel.project p "
			+ " where rel.tenantPackageId=:tenantPackageId "
			+ " and rel.delFlag='0'"
			+ " and p.delFlag='0'  "
			+ " and p.projectName=:name "
			)
	List<ProjectTenantExampleRel> getProjectByName(@Param("tenantPackageId")Integer tenantPackageId,@Param("name")String name );
	/***
	 * 根据项目Url 查询项目
	 * @param tenantPackageId
	 * @return
	 */
	@Query("select rel from ProjectTenantExampleRel rel join rel.project p "
			+ " where rel.tenantPackageId=:tenantPackageId "
			+ " and rel.delFlag='0'"
			+ " and p.delFlag='0'  "
			+ " and p.projectUrl=:url "
			)
	List<ProjectTenantExampleRel> getProjectByUrl(@Param("tenantPackageId")Integer tenantPackageId,@Param("url")String url );
	/***
	 * 活跃项目数
	 * @param tenantPackageId 实例id
	 * @return
	 */
	@Query(" select count(rel) "
			+ " FROM "
			+ " ProjectTenantExampleRel rel  "
			+ " where 1=1 "
			+ " and  rel.tenantPackageId = :tenantPackageId"
			+ " and rel.project.projectState='1'  and rel.delFlag='0' and rel.project.delFlag = '0'"
			)
	public Object getActiveNum(@Param("tenantPackageId")Integer tenantPackageId);
	/***
	 * 根据实例id获取创建的项目数（不包括已经删除的）
	 * @param tenantPackageId 实例id
	 * @return
	 */
	@Query(" select count(rel) "
			+ " FROM "
			+ " ProjectTenantExampleRel rel  "
			+ " where 1=1 "
			+ " and  rel.tenantPackageId = :tenantPackageId "
			+ " and rel.delFlag = '0' and rel.project.delFlag = '0' ")
	public int getProjectNum(@Param("tenantPackageId")Integer tenantPackageId);
	/***
	 * 删除关系
	 * @param delFlag
	 * @param updateBy
	 * @param updateDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("update ProjectTenantExampleRel r set r.delFlag=:delFlag,r.updateBy = :updateBy , r.updateDate=:updateDate,"
			+ "r.endDate=:endDate  where r.project.projectId=:projectId")
	int deleteByProjectId(@Param("delFlag") String delFlag,@Param("updateBy") Integer updateBy ,@Param("updateDate") Date updateDate,
			@Param("endDate") Date  endDate , @Param("projectId") Integer projectId);
	
	/***
	 * 根据租户id修改关系表中的实例id
	 * @param tenantPackageId
	 * @param updateByf
	 * @param updateDate
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("update ProjectTenantExampleRel r "
			+ " set r.tenantPackageId=:tenantPackageId,r.updateBy = :updateBy , r.updateDate=:updateDate where 1=1 "
			)
	int updateTenantPackageIdByTenantId(@Param("tenantPackageId") Integer tenantPackageId,
			@Param("updateBy") Integer updateBy,@Param("updateDate") Date updateDate);
	
	
	
	
	/***
	 * 根据租户id 查询项目
	 * @return
	 */
	@Query("select rel from ProjectTenantExampleRel rel "
			+ " where 1=1 "
			+ " and rel.delFlag='0'"
			
			)
	List<ProjectTenantExampleRel> getProjectListByTenantId( );
	/***
	 * 根据实例id查询项目
	 * @param tenantPackageId
	 * @return
	 */
	@Query("select rel from ProjectTenantExampleRel rel"
			+ " where 1=1 "
			+ " and rel.delFlag='0'"
			+ " and rel.tenantPackageId =:tenantPackageId"
			)
	List<ProjectTenantExampleRel> getProjectListByTenantPackageId(@Param("tenantPackageId") Integer tenantPackageId);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * **********上方是应用服务私有方法不对别的模块和别的应用直接提供提供***********
	 */
	
	/*
	 * ********************此处之下是service服务层调用的公共方法，若要修改，请慎重操作*********************
	 */
	/**
	 * 根据项目id获取项目和租户、套餐的关系
	 * @param projcetId
	 * @return
	 */
	@Query("select t from ProjectTenantExampleRel t where t.project.projectId = :projectId and t.delFlag = '0'")
	public ProjectTenantExampleRel getProTenRelById(@Param("projectId")Integer projcetId);
	
}
