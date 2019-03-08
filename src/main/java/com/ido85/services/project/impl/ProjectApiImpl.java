package com.ido85.services.project.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import com.ido85.frame.common.utils.DateUtils;
import com.ido85.frame.common.utils.ListUntils;
import com.ido85.frame.common.utils.StringUtils;
import com.ido85.master.project.application.ProjectApplication;
import com.ido85.master.project.domain.Competitor;
import com.ido85.master.project.domain.Project;
import com.ido85.master.project.dto.OutProComKwInfoDto;
import com.ido85.master.project.dto.ProjectDto;
import com.ido85.master.project.resources.ProjectWiResources;
import com.ido85.services.project.ProjectApi;
import com.ido85.services.project.dto.ProjectCrawlDto;

@Named
public class ProjectApiImpl implements ProjectApi{

	@Inject
	private ProjectWiResources projectWiResources;
	@Inject
	private ProjectApplication projectApp;
	
	/**
	 * 获取项目信息,如果根据项目id查询不到此项目，那么返回null
	 * @param projectId
	 * @return
	 */
	@Override
	public ProjectDto getProjectInfoById(String projectId){
		return projectWiResources.getProjectInfoById(Integer.parseInt(projectId));
	}
	/**
	 * 获取活跃项目信息,如果根据项目id查询不到此项目或者此项目是归档或者删除状态，那么返回null
	 * @param projectId
	 * @return
	 */
	@Override
	public ProjectDto getArchiveProjectInfoById(String projectId){
		ProjectDto projectDto = projectWiResources.getProjectInfoById(Integer.parseInt(projectId));
		if(null == projectDto || "1".equals(projectDto.getDelFlag()) || "0".equals(projectDto.getProjectState())){
			return null;
		}
		return projectDto;
	}
	/**
	 * 根据项目id获取项目竞品信息
	 * @param projectId
	 * @return
	 */
	@Override
	public List<Competitor> getCompetitorsByProjectId(String projectId) {
		return projectApp.getCompetitorByProjectId(projectId);
	}
	/**
	 * 根据套餐实例id获取活跃项目数量
	 * @param tenantId
	 * @return
	 */
	@Override
	public int getActiveProjectNum(String tenantPackageId) {
		return StringUtils.toInteger(projectApp.getActiveNum(tenantPackageId));
	}
	/**
	 * 根据实例id获取创建的项目数（不包括已经删除的）
	 * @param tenantId
	 * @return
	 */
	@Override
	public int getProjectNum(String tenantPackageId) {
		return projectApp.getProjectNum(tenantPackageId);
	}
	
	/**
	 * 根据传入的项目id和项目的状态来校验项目是否存在
	 * 如果项目已经删除则返回false
	 * 存在返回true，否则返回false
	 * @param projectId  不能为空，否则抛出空指针异常
	 * @param state 项目状态 不能为空，否则抛出空指针异常
	 * @return
	 */
	@Override
	public boolean checkProjectId(String projectId, String state){
		boolean res = false;
		ProjectDto projectDto = projectWiResources.getProjectInfoById(StringUtils.toInteger(projectId));
		if(null != projectDto && "0".equals(projectDto.getDelFlag()) && state.equals(projectDto.getProjectState())){
			res = true;
		}
		return res;
	}
	
	/**获取项目、竞品、关键词信息接口
	 * @param projectId
	 * @return
	 */
	@Override
	public OutProComKwInfoDto getProComKwInfoById(String projectId) {
		return projectApp.getProComKwInfoById(Integer.parseInt(projectId));
	}
	@Override
	public List<ProjectCrawlDto> getAllCralwPro() throws Exception {
		List<ProjectCrawlDto> list = new ArrayList<>();
		Date date = DateUtils.formatDateToPattern(new Date(), "yyyy-MM-dd");
		List<Project> projects = projectWiResources.getAllCralwPro();
		if(ListUntils.isNull(projects)){
			return null;
		}
		ProjectCrawlDto dto = null;
		for(Project project : projects){
			if(DateUtils.isSameDate(project.getNextCrawlDate(), date)){
				dto = new ProjectCrawlDto();
				dto.setIsSubdomain(project.getIsSubdomain());
				dto.setProjectId(project.getProjectId());
				dto.setProjectName(project.getProjectName());
				dto.setProjectUrl(project.getProjectUrl());
				dto.setTenantId(project.getTenantId());
				list.add(dto);
			}
			
		}
		return list;
	}

}
