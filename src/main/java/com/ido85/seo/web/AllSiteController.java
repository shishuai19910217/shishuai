package com.ido85.seo.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ido85.config.properties.BussinessMsgCodeProperties;
import com.ido85.frame.common.exceptions.BusinessException;
import com.ido85.frame.common.restful.Resource;
import com.ido85.master.project.dto.ProjectDto;
import com.ido85.seo.allSite.application.AllSiteApplication;
import com.ido85.seo.allSite.domain.PageMain;
import com.ido85.seo.allSite.dto.InIssuesInfoDto;
import com.ido85.seo.allSite.dto.OutIssuesInfoDto;
import com.ido85.seo.allSite.dto.OutIssuesItemDto;
import com.ido85.seo.allSite.dto.OutSearchQuestionDto;
import com.ido85.seo.allSite.dto.SiteQuestionDto;
import com.ido85.services.allSite.application.AllSiteApi;
import com.ido85.services.project.ProjectApi;

@RestController
public class AllSiteController {
	
	@Inject
	private BussinessMsgCodeProperties msg;
	
	@Inject
	private ProjectApi projectApi;
	
	@Inject
	private AllSiteApi allSiteApi;
	
	@Inject
	private AllSiteApplication allSiteApplication;
	/**
	 * 
	* @Title: getChartIssuesInfo 
	* @Description: 严重问题 列表 5个节点 ---折线图
	* @param @param in
	* @param @return
	* @param @throws Exception    设定文件 
	* @return Resource<OutIssuesItemDto>    返回类型 
	* @throws
	 */
	@RequestMapping("/seo/getChartIssuesInfo")
	public Resource<List<OutIssuesItemDto>> getChartIssuesInfo(@Valid @RequestBody InIssuesInfoDto in) throws Exception {
		List<OutIssuesItemDto> dtoList = new ArrayList<OutIssuesItemDto>();
		
		//参数不能为空  问题等级不能为空
		if(in == null || StringUtils.isBlank(in.getIssueType())){
			return new Resource<List<OutIssuesItemDto>>(msg.getProcessStatus("PARAM_ERROR"));
		}
		// 校验项目id是否正确
		ProjectDto projectDto = projectApi.getProjectInfoById(in.getProjectId()+"");
		if (null == projectDto || "1".equals(projectDto.getDelFlag()) || "0".equals(projectDto.getProjectState())) {
			return new Resource<List<OutIssuesItemDto>>(msg.getProcessStatus("PROJECT_ERROR"));
		}
		dtoList = allSiteApplication.getChartIssuesInfo(in);
		return new Resource<List<OutIssuesItemDto>>(dtoList, msg.getProcessStatus("COMMON_SUCCESS"));
	}

	/**
	* @Description: 整站问题接口  
	* @param @param param
	* @param @return
	* @param @throws Exception    设定文件 
	* @return Resource<SiteQuestionDto>    返回类型 
	* @throws
	 */
	@RequestMapping("/seo/getQuestion")
	public Resource<SiteQuestionDto> getQuestion(@RequestBody Map<String, Object> param) throws Exception {
		int projectId = Integer.parseInt(param.get("projectId").toString());
		SiteQuestionDto dto = allSiteApplication.getQuestion(projectId);
		return new Resource<SiteQuestionDto>(dto, msg.getProcessStatus("COMMON_SUCCESS"));

	}
	/**
	* @Description: 问题列表 
	* @param @param in
	* @param @return
	* @param @throws Exception    设定文件 
	* @return Resource<List<OutIssuesInfoDto>>    返回类型 
	* @throws
	 */
	@RequestMapping("/seo/getIssuesInfoDetail")
	public Resource<List<OutIssuesInfoDto>> getIssuesInfoDetail(@Valid @RequestBody InIssuesInfoDto in) throws Exception {
		List<OutIssuesInfoDto> dto = new ArrayList<OutIssuesInfoDto>();
		dto = allSiteApplication.getIssuesInfoDetail(in);
		return new Resource<List<OutIssuesInfoDto>>(dto, msg.getProcessStatus("COMMON_SUCCESS"));

	}
	
	@RequestMapping("/seo/getIssuesInfo")
	public Resource<PageMain> getIssuesInfo(@RequestBody Map<String, Object> param) throws Exception {
		
		if(param.get("projectId")==null||"".equals(param.get("projectId"))){
			return new Resource<PageMain>(msg.getProcessStatus("PARAM_IS_NULL"));
		}
		PageMain pageMain = allSiteApplication.getIssuesInfo(param);
		return new Resource<PageMain>(pageMain, msg.getProcessStatus("COMMON_SUCCESS"));

	}
	
	
	/**
	 * 
	* @Description: 页面问题分析
	* @param @param param
	* @param @return
	* @param @throws Exception    设定文件 
	* @return Resource<Map<String,Object>>    返回类型 
	* @throws
	 */
	@RequestMapping("/seo/getPageQuestion")
	public Resource<Map<String, Object>> getPageQuestion(@RequestBody Map<String, Object> param) throws Exception {
		Map<String, Object> dto = new HashMap<String, Object>();
		if(null==param||param.isEmpty()){
			return new Resource<Map<String, Object>>(msg.getProcessStatus("PARAM_IS_NULL"));
		}
		if(param.get("projectId")==null||"".equals(param.get("projectId"))){
			return new Resource<Map<String, Object>>(msg.getProcessStatus("PARAM_IS_NULL"));
		}
		if(param.get("url")==null||"".equals(param.get("url"))){
			return new Resource<Map<String, Object>>(msg.getProcessStatus("PARAM_IS_NULL"));
		}
		int projectId = Integer.parseInt(param.get("projectId").toString());
		String url = param.get("url").toString();
		dto = allSiteApplication.getPageQuestion(projectId,url);
		return new Resource<Map<String, Object>>(dto, msg.getProcessStatus("COMMON_SUCCESS"));

	}
	 
	 @RequestMapping("/seo/getQuestionByWeekOrM")
	public Resource<OutSearchQuestionDto> getQuestionByWeekOrM(@RequestBody Map<String, Object> param) throws Exception {
		 OutSearchQuestionDto dto = new OutSearchQuestionDto();
		if(null==param||param.isEmpty()){
			return new Resource<OutSearchQuestionDto>(msg.getProcessStatus("PARAM_IS_NULL"));
		}
		if(param.get("projectId")==null||"".equals(param.get("projectId"))){
			return new Resource<OutSearchQuestionDto>(msg.getProcessStatus("PARAM_IS_NULL"));
		}
		if(param.get("isWeek")==null||"".equals(param.get("isWeek"))){
			return new Resource<OutSearchQuestionDto>(msg.getProcessStatus("PARAM_IS_NULL"));
		}
		int projectId = Integer.parseInt(param.get("projectId").toString());
		int isWeek = Integer.parseInt(param.get("isWeek").toString());
		
		// 检测当前项目 根据id取项目
		ProjectDto projectDto = projectApi.getProjectInfoById(projectId+"");
		if (null == projectDto) {
			throw new BusinessException(msg.getProcessStatus("PROJECT_NOT_EXIST"));
		}
				
		dto = allSiteApplication.getQuestionByWeekOrM(projectId,isWeek);
		return new Resource<OutSearchQuestionDto>(dto, msg.getProcessStatus("COMMON_SUCCESS"));

	}
	 
	 @RequestMapping("/seo/getCrawInfo")
	public Resource<Map<String, Object>> getCrawInfo(@RequestBody Map<String, Object> param) throws Exception {
		Map<String, Object> dto = new HashMap<String, Object>();
		dto = allSiteApi.getCrawInfo(21);
		return new Resource<Map<String, Object>>(dto, msg.getProcessStatus("COMMON_SUCCESS"));

	}
}
