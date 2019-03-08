package com.ido85.services.project;


import java.util.List;
import java.util.Map;

import com.ido85.master.project.domain.Competitor;
import com.ido85.master.project.dto.OutProComKwInfoDto;
import com.ido85.master.project.dto.ProjectDto;
import com.ido85.services.project.dto.ProjectCrawlDto;

/**
 * 项目对本应用内公共接口类
 * @author fire
 *
 */
public interface ProjectApi {
	/**
	 * 获取项目信息,如果根据项目id查询不到此项目，那么返回null
	 * @param projectId
	 * @return
	 */
	public ProjectDto getProjectInfoById(String projectId);
	/**
	 * 获取活跃项目信息,如果根据项目id查询不到此项目或者此项目是归档或者删除状态，那么返回null
	 * @param projectId
	 * @return
	 */
	public ProjectDto getArchiveProjectInfoById(String projectId);
	/**
	 * 根据项目id获取项目竞品信息
	 * @param projectId
	 * @return
	 */
	public List<Competitor> getCompetitorsByProjectId(String projectId);
	/**
	 * 根据套餐实例id获取活跃项目数量
	 * @param tenantId
	 * @return
	 */
	public int getActiveProjectNum(String tenantPackageId);
	/**
	 * 根据实例id获取创建的项目数（不包括已经删除的）
	 * @param tenantId
	 * @return
	 */
	public int getProjectNum(String tenantPackageId);
	
	/**
	 * 根据传入的项目id和项目的状态来校验项目是否存在
	 * 如果项目已经删除则返回false
	 * 存在返回true，否则返回false
	 * @param projectId  不能为空，否则抛出空指针异常
	 * @param state 项目状态 不能为空，否则抛出空指针异常
	 * @return
	 */
	public boolean checkProjectId(String projectId, String state);
	
	/**获取项目、竞品、关键词信息接口
	 * @param projectId
	 * @return
	 */
	public OutProComKwInfoDto getProComKwInfoById(String projectId);
	/***
	 * 所有需要爬取的项目
	 * @return
	 * @throws Exception
	 */
	public List<ProjectCrawlDto> getAllCralwPro() throws Exception;
}
