package com.ido85.master.project.application;


import java.util.List;
import java.util.Map;

import com.ido85.frame.common.Page;
import com.ido85.frame.common.restful.Resource;
import com.ido85.master.project.domain.Competitor;
import com.ido85.master.project.domain.Project;
import com.ido85.master.project.domain.ProjectTenantExampleRel;
import com.ido85.master.project.dto.CheckUrlDto;
import com.ido85.master.project.dto.CheckUrlResultDto;
import com.ido85.master.project.dto.InUpdateProjectDto;
import com.ido85.master.project.dto.OutCheckProjectDto;
import com.ido85.master.project.dto.OutProComKwInfoDto;
import com.ido85.master.project.dto.OutProjectBaseInfoDto;
import com.ido85.master.project.dto.ProjectPageDto;

/***
 * 项目模块应用
 * @author Administrator
 *
 */
public interface ProjectApplication {
	/***
	 * 添加项目
	 * @param param
	 * @return 
	 * @throws Exception 
	 */
	public Resource<Map<String,Object>> addProject(Map<String,Object> param) throws Exception;
	/***
	 * 
	 * @param tenantPackageId
	 * @param projectState
	 * @return
	 */
	public int getProjectNumBy(String tenantPackageId,String projectState);
	/***
	 * 检测项目信息
	 * @param param
	 * @return
	 */
	public OutCheckProjectDto checkProject(Map<String,Object> param);
	/***
	 * 项目管理 列表
	 * @param page
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page getProjectList(Page<ProjectPageDto> page, Map<String, Object> param) throws Exception;
	/**
	 * 活跃项目书
	 * @param tenantPackageId
	 * @return
	 */
	public Object getActiveNum(String tenantPackageId);
	/**
	 * 根据实例id获取创建的项目数（不包括已经删除的）
	 * @param tenantPackageId
	 * @return
	 */
	public int getProjectNum(String tenantPackageId);

	/***
	 * 设置活跃项目归档、删除、激活接口
	 * @param param
	 */
	public String updateProjectState(Map<String,Object> param) throws Exception;
	/**
	 * 修改项目信息,同时如果入参有竞品信息修改，则一同修改竞品信息
	 * @param project
	 * @return
	 */
	boolean updateProjectInfo(InUpdateProjectDto in) throws Exception;
	/**
	 * 修改项目的竞品信息
	 * @param competitor
	 * @return
	 */
	boolean updateCompetitorInfo(Competitor competitor) throws Exception;
	
	/***
	 * 项目由暂定到激活
	 * @param param
	 * @return
	 */
	public String updateProjectStateStopToActive(String projectId) throws Exception;
	/**
	 * 查询租户是否有归档项目
	 * @return
	 */
	public String checkArchive(String state) throws Exception;
	
	/*
	 * **********上方是应用服务私有方法不对别的模块和别的应用直接提供提供***********
	 */

	/*
	 * ********************此处之下是service服务层调用的公共方法，若要修改，请慎重操作*********************
	 */
	/**
	 * 根据项目id获取项目和租户、套餐的关系
	 * @param projectId
	 * @return
	 */
	ProjectTenantExampleRel getProTenRelById(String projectId);
	/***
	 * 保存项目
	 * @param pro
	 */
	void saveProject(ProjectTenantExampleRel r);
	/**
	 * 根据项目id获取项目的所有竞品信息
	 * @param projectId
	 * @return
	 */
	List<Competitor> getCompetitorsInfoByProjectId(String projectId);
	/**
	 * 根据项目id获取项目信息
	 * @param projectId
	 * @return
	 */
	OutProjectBaseInfoDto getProjectInfoById(String projectId);
	
    /**根据项目id获取Project实体
     * @param projectId
     * @return
     */
    Project getProjectById(String projectId);
	/**
	 * 根据项目id获取项目竞品信息
	 * @param projectId
	 * @return
	 */
	List<Competitor> getCompetitorByProjectId(String projectId);
	/***
	 * 删除竞品
	 * @param param
	 * @return
	 */
	public String delCompetitor(Map<String, Object> param) throws Exception;
	/***
	 * 添加竞品
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String addCompetitor(Map<String, Object> param)  throws Exception;
	/***
	 * 根据项目id 查询竞品数
	 * @param projectid
	 * @return
	 */
	public int getComNumBy(String projectId);
	
	/**根据项目ID获取项目、竞品、关键词信息
	 * @param projectId
	 * @return
	 */
	public OutProComKwInfoDto getProComKwInfoById(Integer projectId);
	
	/**检查url
	 * @param checkUrlDto
	 * @return
	 */
	public CheckUrlResultDto checkUrl(CheckUrlDto checkUrlDto) throws Exception;
	/**项目自动归档
	 * 
	 */
	public void autoArchive();
}
