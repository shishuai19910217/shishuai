package com.ido85.seo.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ido85.config.properties.BussinessMsgCodeProperties;
import com.ido85.frame.common.restful.Resource;
import com.ido85.frame.common.utils.CompareUtil;
import com.ido85.frame.common.utils.DateUtils;
import com.ido85.frame.common.utils.ListUntils;
import com.ido85.frame.common.utils.StringUtils;
import com.ido85.master.keyword.domain.Group;
import com.ido85.master.project.dto.ProjectDto;
import com.ido85.seo.common.utils.Util;
import com.ido85.seo.keywordrank.application.DashboardKeywordApplication;
import com.ido85.seo.keywordrank.application.KeywordSERPApplication;
import com.ido85.seo.keywordrank.domain.DashboardKeyword;
import com.ido85.seo.keywordrank.domain.SerpDetail;
import com.ido85.seo.keywordrank.dto.InKeywordRankDto;
import com.ido85.seo.keywordrank.dto.OutKeywordRankDto;
import com.ido85.seo.keywordrank.dto.OutRankTrendDto;
import com.ido85.seo.keywordrank.dto.OutSERPReportDto;
import com.ido85.services.channel.application.ChannelApi;
import com.ido85.services.keyword.KeywordApi;
import com.ido85.services.project.ProjectApi;
import com.ido85.services.time.TimeInsideApi;

@RestController
public class KeywordSERPController {
	@Inject
	private KeywordSERPApplication serpApp;
	@Inject
	private ProjectApi projectApi;
	@Inject
	private TimeInsideApi timeApi;
	@Inject
	private BussinessMsgCodeProperties prop;
	@Inject
	private ChannelApi channelApi;
	@Inject
	private KeywordApi keywordApi;
//	@Inject
//	private KeywordRankApplication rankApp;
	@Inject
	private DashboardKeywordApplication dashKeywordApp;

	
	/**
	 *  接口详情 (id: 263)
		接口名称 关键词分析排名和优化难度
		请求类型 post
		请求Url  /seo/getKeywordDifficulty
	 * @param inDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/seo/getKeywordDifficulty")
	public Resource<OutKeywordRankDto> getKeywordDifficulty(@RequestBody @Valid InKeywordRankDto inDto) throws Exception{
		OutKeywordRankDto res = new OutKeywordRankDto();
		//校验项目和竞品是否正确
		ProjectDto projectDto = projectApi.getArchiveProjectInfoById(inDto.getProjectId());
		if(null == projectDto || "".equals(projectDto)){
			return new Resource<OutKeywordRankDto>(prop.getProcessStatus("PROJECT_NOT_EXIST"));
		}
		
		//校验渠道  调用套餐,获取套餐中的搜索引擎类型
		List<Integer> engines = new ArrayList<Integer>();
		engines.add(StringUtils.toInteger(inDto.getEngine()));
		if(StringUtils.isBlank(inDto.getEngine()) || channelApi.getChannelByIds(engines).size() <= 0){
			return new Resource<OutKeywordRankDto>(prop.getProcessStatus("ENGINE_ERROR"));
		}
		
		//调用时间工具类,获取最新的抓取时间
		List<Date> crawlDates = timeApi.getNewestCrawlDate(StringUtils.toInteger(projectDto.getProjectId()), 1);
		if(null == crawlDates || crawlDates.size() <= 0){
			return new Resource<OutKeywordRankDto>(res);
		}
		
		//获取关键词排名信息和获取关键词的优化难度
//		ProDetail proDetail = rankApp.getKeywordRankInfo(crawlDates.get(0), StringUtils.toInteger(projectDto.getProjectId()),
//				StringUtils.toInteger(inDto.getKeyword()), StringUtils.toInteger(inDto.getEngine()));
		DashboardKeyword dashKeyword = dashKeywordApp.getDashKeywordInfo(crawlDates.get(0), StringUtils.toInteger(inDto.getProjectId()), 
				StringUtils.toInteger(inDto.getKeyword()), StringUtils.toInteger(inDto.getEngine()));
		if(null != dashKeyword){
			res.setKeywordRank(dashKeyword.getRank() > 0 ? dashKeyword.getRank() + "" : "--");
			res.setDifficulty(Util.keywordOptimization(dashKeyword.getBaiduIndex(), dashKeyword.getBaiduRecord()) + "");
		}
		
		//获取关键词的分组信息
		List<Group> groups = keywordApi.getProKeywordGroup(inDto.getProjectId(), inDto.getKeyword());
		if(null != groups && !"".equals(groups) && groups.size() > 0){
			List<String> groupName = new ArrayList<String>();
			for (int i = 0; i < groups.size(); i++) {
				groupName.add(groups.get(i).getGroupName());
			}
			res.setGroups(groupName);
		}
		
		return new Resource<OutKeywordRankDto>(res);
	}
	
	/**
	 *  接口详情 (id: 264)
		接口名称 排名变化趋势
		请求类型 post
		请求Url  /seo/getRankTrend
	 * @param inDto
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/seo/getRankTrend")
	public Resource<Map<String, List<OutRankTrendDto>>> getRankTrend(@RequestBody @Valid InKeywordRankDto inDto) throws Exception{
		Map<String, List<OutRankTrendDto>> res = new HashMap<String, List<OutRankTrendDto>>();
		//校验项目和竞品是否正确
		ProjectDto projectDto = projectApi.getArchiveProjectInfoById(inDto.getProjectId());
		if(null == projectDto || "".equals(projectDto)){
			return new Resource<Map<String, List<OutRankTrendDto>>>(prop.getProcessStatus("PROJECT_NOT_EXIST"));
		}
		
		//校验渠道  调用套餐,获取套餐中的搜索引擎类型
		List<Integer> engines = new ArrayList<Integer>();
		engines.add(StringUtils.toInteger(inDto.getEngine()));
		if(StringUtils.isBlank(inDto.getEngine()) || channelApi.getChannelByIds(engines).size() <= 0){
			return new Resource<Map<String, List<OutRankTrendDto>>>(prop.getProcessStatus("ENGINE_ERROR"));
		}
		
		//调用时间工具类,获取前8次的抓取时间
		List<Date> crawlDates = timeApi.getNewestCrawlDate(StringUtils.toInteger(projectDto.getProjectId()), 8);
		if(null == crawlDates || crawlDates.size() <= 0){
			return new Resource<Map<String, List<OutRankTrendDto>>>(res);
		}
		
		//获取分析排名变化趋势
		List<SerpDetail> serps = serpApp.getSerpRankTrend(crawlDates, StringUtils.toInteger(inDto.getEngine()), StringUtils.toInteger(inDto.getKeyword()), projectDto);
		Collections.sort(serps, CompareUtil.createComparator(1, "crawlDate"));
		if(!ListUntils.isNull(serps)){
			List<OutRankTrendDto> out = new ArrayList<OutRankTrendDto>();
			OutRankTrendDto item = null;
			for (SerpDetail serpDetail : serps) {
				item = new OutRankTrendDto();
				item.setRank(serpDetail.getRank());
				item.setRankDate(DateUtils.formatDateTime(serpDetail.getCrawlDate(), "yyyyMMddHHmmss"));
				item.setTitle(serpDetail.getTitle());
				item.setUrl(serpDetail.getUrl());
				
				out.add(item);
			}
			res.put("rankTrend", out);
		}
		
		return new Resource<Map<String, List<OutRankTrendDto>>>(res);
	}

	/**
	 *  接口详情 (id: 265)
		接口名称 搜索结果报告
		请求类型 post
		请求Url  /seo/getSERPReport
	 * @param inDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/seo/getSERPReport")
	public Resource<Map<String, List<OutSERPReportDto>>> getSERPReport(@RequestBody @Valid InKeywordRankDto inDto) throws Exception{
		Map<String, List<OutSERPReportDto>> res = new HashMap<String, List<OutSERPReportDto>>();
		//校验项目和竞品是否正确
		ProjectDto projectDto = projectApi.getArchiveProjectInfoById(inDto.getProjectId());
		if(null == projectDto || "".equals(projectDto)){
			return new Resource<Map<String, List<OutSERPReportDto>>>(prop.getProcessStatus("PROJECT_NOT_EXIST"));
		}
		
		//校验渠道  调用套餐,获取套餐中的搜索引擎类型
		List<Integer> engines = new ArrayList<Integer>();
		engines.add(StringUtils.toInteger(inDto.getEngine()));
		if(StringUtils.isBlank(inDto.getEngine()) || channelApi.getChannelByIds(engines).size() <= 0){
			return new Resource<Map<String, List<OutSERPReportDto>>>(prop.getProcessStatus("ENGINE_ERROR"));
		}
		
		//调用时间工具类,获取前8次的抓取时间
		List<Date> crawlDates = timeApi.getNewestCrawlDate(StringUtils.toInteger(projectDto.getProjectId()), 1);
		if(null == crawlDates || crawlDates.size() <= 0){
			return new Resource<Map<String, List<OutSERPReportDto>>>(res);
		}
		
		//获取分析serp报告
		List<SerpDetail> serps = serpApp.getSerpDetailInfos(crawlDates, StringUtils.toInteger(inDto.getEngine()), StringUtils.toInteger(inDto.getKeyword()));
		if(!ListUntils.isNull(serps)){
			List<OutSERPReportDto> out = new ArrayList<OutSERPReportDto>();
			OutSERPReportDto item = null;
			for (SerpDetail serpDetail : serps) {
				item = new OutSERPReportDto();
				item.setRank(serpDetail.getRank());
				item.setUrl(serpDetail.getUrl());
				item.setLinkPageUrl(serpDetail.getUrl());
				item.setTitle(serpDetail.getTitle());
				item.setLinkPageUrl(serpDetail.getLinkUrl());
				
				out.add(item);
			}
			res.put("searchResult", out);
		}
		
		return new Resource<Map<String, List<OutSERPReportDto>>>(res);
	}

}
