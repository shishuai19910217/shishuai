package com.ido85.seo.allSite.application;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;

import com.ido85.frame.common.restful.Resource;
import com.ido85.seo.allSite.domain.PageMain;
import com.ido85.seo.allSite.dto.InIssuesInfoDto;
import com.ido85.seo.allSite.dto.OutIssuesInfoDto;
import com.ido85.seo.allSite.dto.OutIssuesItemDto;
import com.ido85.seo.allSite.dto.OutSearchQuestionDto;
import com.ido85.seo.allSite.dto.SiteQuestionDto;
import com.ido85.seo.dashboard.dto.OutVisibilityDto;

public interface AllSiteApplication {
	/**
	* @Description: 严重问题 列表 5个
	* @param @param in
	* @param @return
	* @param @throws Exception    设定文件 
	* @return Resource<OutIssuesItemDto>    返回类型 
	* @throws
	 */
	public List<OutIssuesItemDto> getChartIssuesInfo(InIssuesInfoDto in) throws Exception;
	

	/**
	* @Description: 整站问题接口 
	* @param @param project_id
	* @param @return    设定文件 
	* @return SiteQuestionDto    返回类型 
	* @throws
	 */
	public SiteQuestionDto getQuestion(int projectId) throws Exception;
	
	/**
	 * @throws Exception 
	* @Title: getIssuesInfoDetail 
	* @Description: 问题列表
	* @param @param in
	* @param @return    设定文件 
	* @return List<OutIssuesInfoDto>    返回类型 
	* @throws
	 */
	public List<OutIssuesInfoDto> getIssuesInfoDetail(InIssuesInfoDto in) throws Exception;
	
	
	/**
	 * @throws Exception 
	* @Description: 页面分析 
	* @param @param project_id
	* @param @param url
	* @param @return    设定文件 
	* @return Resource<Map<String,Object>>    返回类型 
	* @throws
	 */
	public Map<String, Object> getPageQuestion(int project_id,String url) throws Exception;
	
	public OutSearchQuestionDto getQuestionByWeekOrM(int project,int isWeek) throws Exception;
	
	
	public PageMain getIssuesInfo(Map<String, Object> param) throws Exception;
}
