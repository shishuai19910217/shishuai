package com.ido85.seo.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import com.ido85.frame.common.utils.MathUtil;
import com.ido85.frame.common.utils.StringUtils;
import com.ido85.master.keyword.domain.Group;
import com.ido85.master.keyword.domain.ProKeyword;
import com.ido85.master.keyword.dto.OutProKeywordDto;
import com.ido85.master.project.domain.Competitor;
import com.ido85.master.project.dto.OutProComKwInfoDto;
import com.ido85.master.project.dto.ProjectDto;
import com.ido85.seo.keywordrank.application.KeywordRankApplication;
import com.ido85.seo.keywordrank.dto.InKeyRankDto;
import com.ido85.seo.keywordrank.dto.InRankSearchVisiDto;
import com.ido85.seo.keywordrank.dto.OutDiffEngineRankDto;
import com.ido85.seo.keywordrank.dto.OutKeyRankDto;
import com.ido85.seo.keywordrank.dto.OutKeyRankNumItemDto;
import com.ido85.seo.keywordrank.dto.OutKeywordRankInfoDto;
import com.ido85.seo.keywordrank.dto.OutRankAnalysisDto;
import com.ido85.seo.keywordrank.dto.OutRankAnalysisItemDto;
import com.ido85.seo.keywordrank.dto.OutRankDto;
import com.ido85.seo.keywordrank.dto.OutRankSearchVisiDto;
import com.ido85.seo.keywordrank.dto.OutSearchVisibilityDto;
import com.ido85.seo.keywordrank.dto.VisibilityDto;
import com.ido85.seo.keywordrank.vo.KeywordRankCompareVo;
import com.ido85.seo.keywordrank.vo.ProRankVo;
import com.ido85.seo.keywordrank.vo.RankSearchVisibilityVo;
import com.ido85.services.channel.application.ChannelApi;
import com.ido85.services.channel.dto.ChannelDto;
import com.ido85.services.keyword.KeywordApi;
import com.ido85.services.project.ProjectApi;
import com.ido85.services.time.TimeInsideApi;

@RestController
public class KeywordRankController {
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
	@Inject
	private KeywordRankApplication rankApp;

	
	/**
	 *  接口详情 (id: 268)
		接口名称 排名-关键词的可见性 自然搜索排名 前30
		请求类型 get
		请求Url  /seo/getKeywordRankBaseInfo
	 * @param inDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/seo/getKeywordRankBaseInfo")
	public Resource<OutKeyRankDto> getKeywordRankBaseInfo(@RequestBody @Valid InKeyRankDto inDto) throws Exception{
		OutKeyRankDto res = new OutKeyRankDto();
		//校验项目和竞品是否正确
		ProjectDto projectDto = projectApi.getArchiveProjectInfoById(inDto.getProjectId());
		if(null == projectDto || "".equals(projectDto)){
			return new Resource<OutKeyRankDto>(prop.getProcessStatus("PROJECT_ERROR"));
		}
		
		//校验渠道  调用套餐,获取套餐中的搜索引擎类型
		List<Integer> engines = new ArrayList<Integer>();
		engines.add(StringUtils.toInteger(inDto.getEngineType()));
		if(StringUtils.isBlank(inDto.getEngineType()) || channelApi.getChannelByIds(engines).size() <= 0){
			return new Resource<OutKeyRankDto>(prop.getProcessStatus("ENGINE_ERROR"));
		}
		
		//调用时间工具类,获取最新的抓取时间
		List<Date> crawlDates = timeApi.getCrawlDate(DateUtils.parseDate(inDto.getStartTime()), DateUtils.parseDate(inDto.getEndTime()), StringUtils.toInteger(projectDto.getProjectId()), "1");
		if(null == crawlDates || crawlDates.size() <= 0){
			return new Resource<OutKeyRankDto>(res);
		}
		Collections.sort(crawlDates);
		
		//获取用户选择的关键词
		List<Integer> keywordIds = new ArrayList<Integer>();
		
		this.getAllRelKeyword(keywordIds, StringUtils.toInteger(projectDto.getProjectId()), inDto.getBrand(), 
				ListUntils.listStrToInteger(inDto.getKeywordGroupList()), ListUntils.listStrToInteger(inDto.getKeywordList()));
		if(ListUntils.isNull(keywordIds)){
			return new Resource<OutKeyRankDto>(res);
		}
		
		//查询所有的关键词的搜索可见性和排名升降情况
		res = rankApp.getKeywordRankBaseInfo(crawlDates, StringUtils.toInteger(projectDto.getProjectId()), keywordIds, StringUtils.toInteger(inDto.getEngineType()));
		
		return new Resource<OutKeyRankDto>(res);
	}
	
	/**
	 * 根据传入参数获取所有的关键词id
	 * @param keywordIds
	 * @param projectId
	 * @param brand
	 * @param groupIds
	 * @param keywords
	 * @return
	 */
	private List<Integer> getAllRelKeyword(List<Integer> keywordIds, Integer projectId, String brand, List<Integer> groupIds, List<Integer> keywords) throws Exception {
		if(ListUntils.isNull(keywords) && StringUtils.isBlank(brand)
				&& ListUntils.isNull(groupIds)){//查询项目的所有关键词的搜索可见性
			//查询项目的所有关键词id
			List<ProKeyword> proKeywords = keywordApi.getAllProKeyword(projectId);
			if(!ListUntils.isNull(proKeywords)){
				for (ProKeyword proKeyword : proKeywords) {
					keywordIds.add(proKeyword.getKeywordId());
				}
			}
			
		}else {
			if(null != brand && !"".equals(brand)){
				List<ProKeyword> proKeywords = keywordApi.getAllProKeyword(projectId);
				if(!ListUntils.isNull(proKeywords)){
					for (ProKeyword proKeyword : proKeywords) {
						if(proKeyword.getIsBrand().equals(brand + "")){
							keywordIds.add(proKeyword.getKeywordId());
						}
					}
				}
			}
			if(null != groupIds && groupIds.size() > 0){
				List<Integer> tem = keywordApi.getKeywordsInfo(projectId, groupIds);
				if(!ListUntils.isNull(tem)){
					keywordIds.addAll(tem);
				}
			}
		}
		if(!ListUntils.isNull(keywords)){
			keywordIds.addAll(keywords);
		}
		//排重
		HashSet<Integer> hashSet = new HashSet<Integer>(keywordIds);
		keywordIds.clear();
		keywordIds.addAll(hashSet);
		return keywordIds;
	}
	
	/**
	 *  接口详情 (id: 267) 
		接口名称 排名--历史数据对比/竞争对比tab下的搜索可见性
		请求类型 post
		请求Url  /seo/getRankSearchVisibility
	 * @param inDto
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/seo/getRankSearchVisibility")
	public Resource<Map<String, List<OutRankSearchVisiDto>>> getRankSearchVisibility(@RequestBody @Valid InRankSearchVisiDto inDto) throws Exception{
		Map<String, List<OutRankSearchVisiDto>> res = new HashMap<String, List<OutRankSearchVisiDto>>();
		//校验项目和竞品是否正确
		ProjectDto projectDto = projectApi.getArchiveProjectInfoById(inDto.getProjectId());
		if(null == projectDto || "".equals(projectDto)){
			return new Resource<Map<String, List<OutRankSearchVisiDto>>>(prop.getProcessStatus("PROJECT_ERROR"));
		}
		
		//校验渠道  调用套餐,获取套餐中的搜索引擎类型
		List<Integer> engines = new ArrayList<Integer>();
		engines.add(StringUtils.toInteger(inDto.getEngineType()));
		if(StringUtils.isBlank(inDto.getEngineType()) || channelApi.getChannelByIds(engines).size() <= 0){
			return new Resource<Map<String, List<OutRankSearchVisiDto>>>(prop.getProcessStatus("ENGINE_ERROR"));
		}
		
		//调用时间工具类,获取抓取时间
		List<Date> crawlDates = timeApi.getCrawlDateForRank(DateUtils.parseDate(inDto.getStartTime()), DateUtils.parseDate(inDto.getEndTime()), 
				StringUtils.toInteger(inDto.getProjectId()), "0".equals(inDto.getIsWeek()) ? "1" : "2");
		if(null == crawlDates || crawlDates.size() <= 0){
			return new Resource<Map<String, List<OutRankSearchVisiDto>>>(res);
		}
		Collections.sort(crawlDates);
		
		//获取用户选择的关键词
		List<Integer> keywordIds = new ArrayList<Integer>();
		
		this.getAllRelKeyword(keywordIds, StringUtils.toInteger(projectDto.getProjectId()), inDto.getBrand(), 
				ListUntils.listStrToInteger(inDto.getKeywordGroupList()), ListUntils.listStrToInteger(inDto.getKeywordList()));
		if(ListUntils.isNull(keywordIds)){
			return new Resource<Map<String,List<OutRankSearchVisiDto>>>(res);
		}
		
		//是否包含竞品
		List<Integer> competitorIds = new ArrayList<Integer>();
		competitorIds.add(StringUtils.toInteger(inDto.getProjectId()));
		List<Competitor> competitors = this.getAllCompetitorIds(competitorIds, inDto.getIshave(), inDto.getProjectId());
		
		//查询项目和竞品维度所有的关键词的搜索可见性
		List<RankSearchVisibilityVo> ranks = rankApp.getRankSearchVisibility(crawlDates, StringUtils.toInteger(inDto.getProjectId()), competitorIds, keywordIds, StringUtils.toInteger(inDto.getEngineType()));
		Collections.sort(ranks, CompareUtil.createComparator(1, "crawlDate"));
		
		List<OutRankSearchVisiDto> out = new ArrayList<OutRankSearchVisiDto>();
		OutRankSearchVisiDto item = null;
		if(!ListUntils.isNull(ranks)){
			for (RankSearchVisibilityVo rankSearchVisibilityVo : ranks) {
				item = new OutRankSearchVisiDto();
				if(!ListUntils.isNull(competitors)){
					for (Competitor competitor : competitors) {
						if(competitor.getCompetitorId().equals(rankSearchVisibilityVo.getCompetitorId())){
							item.setIsCom("1");
							item.setName(competitor.getCompetitorName());
							item.setCrawlDate(DateUtils.formatDate(rankSearchVisibilityVo.getCrawlDate(), "yyyyMMddHHmmss"));
							item.setId(rankSearchVisibilityVo.getCompetitorId() + "");
							item.setVisibility(MathUtil.rounding(rankSearchVisibilityVo.getVisibility(), 2));
							break;
						}
					}
				}
				if(projectDto.getProjectId().equals(rankSearchVisibilityVo.getCompetitorId() + "")){
					item.setIsCom("0");
					item.setName(projectDto.getProjectName());
					item.setCrawlDate(DateUtils.formatDate(rankSearchVisibilityVo.getCrawlDate(), "yyyyMMddHHmmss"));
					item.setId(rankSearchVisibilityVo.getCompetitorId() + "");
					item.setVisibility(MathUtil.rounding(rankSearchVisibilityVo.getVisibility(), 2));
				}
				out.add(item);
			}
		}
		
		res.put("visibilityList", out);
		
		return new Resource<Map<String, List<OutRankSearchVisiDto>>>(res);
	}
	
	private List<Competitor> getAllCompetitorIds(List<Integer> competitorIds, String flag, String projectId){
		List<Competitor> competitors = null;
		if("1".equals(flag)){
			//获取所有项目的竞品信息
			competitors = projectApi.getCompetitorsByProjectId(projectId);
			if(!ListUntils.isNull(competitors)){
				for (Competitor competitor : competitors) {
					competitorIds.add(competitor.getCompetitorId());
				}
			}
		}
		return competitors;
	}
	
	/**
	 *  接口详情 (id: 269)     Mock数据
		接口名称 排名-历史数据对比tab下排名
		请求类型 post
		请求Url  /seo/getRanks
	 * @param inDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/seo/getRanks")
	public Resource<Map<String, List<OutRankDto>>> getRanks(@RequestBody @Valid InRankSearchVisiDto inDto) throws Exception{
		Map<String, List<OutRankDto>> res = new HashMap<String, List<OutRankDto>>();
		//校验项目和竞品是否正确
		ProjectDto projectDto = projectApi.getArchiveProjectInfoById(inDto.getProjectId());
		if(null == projectDto || "".equals(projectDto)){
			return new Resource<Map<String, List<OutRankDto>>>(prop.getProcessStatus("PROJECT_ERROR"));
		}
		
		//校验渠道  调用套餐,获取套餐中的搜索引擎类型
		List<Integer> engines = new ArrayList<Integer>();
		engines.add(StringUtils.toInteger(inDto.getEngineType()));
		if(StringUtils.isBlank(inDto.getEngineType()) || channelApi.getChannelByIds(engines).size() <= 0){
			return new Resource<Map<String, List<OutRankDto>>>(prop.getProcessStatus("ENGINE_ERROR"));
		}
		
		//调用时间工具类,获取抓取时间
		List<Date> crawlDates = timeApi.getCrawlDateForRank(DateUtils.parseDate(inDto.getStartTime()), DateUtils.parseDate(inDto.getEndTime()), 
				StringUtils.toInteger(inDto.getProjectId()), "0".equals(inDto.getIsWeek()) ? "1" : "2");
		if(null == crawlDates || crawlDates.size() <= 0){
			return new Resource<Map<String, List<OutRankDto>>>(res);
		}
		Collections.sort(crawlDates);
		
		//获取用户选择的关键词
		List<Integer> keywordIds = new ArrayList<Integer>();
		
		this.getAllRelKeyword(keywordIds, StringUtils.toInteger(projectDto.getProjectId()), inDto.getBrand(), 
				ListUntils.listStrToInteger(inDto.getKeywordGroupList()), ListUntils.listStrToInteger(inDto.getKeywordList()));
		if(ListUntils.isNull(keywordIds)){
			return new Resource<Map<String,List<OutRankDto>>>(res);
		}
		
		//查询项目维度所有的关键词的排名
		List<OutRankDto> out = new ArrayList<OutRankDto>();
		out = rankApp.getProRanks(crawlDates, StringUtils.toInteger(inDto.getProjectId()), keywordIds, StringUtils.toInteger(inDto.getEngineType()));
		res.put("list", out);
		
		return new Resource<Map<String, List<OutRankDto>>>(res);
	}
	
	/**
	 *  接口详情 (id: 271)
		接口名称 排名--关键词排名历史数据对比详情
		请求类型 post
		请求Url  /seo/getAllKeywordsRanks
	 * @param inDto
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/seo/getAllKeywordsRanks")
	public Resource<Map<String, List<OutKeywordRankInfoDto>>> getAllKeywordsRanks(@RequestBody @Valid InKeyRankDto inDto) throws Exception{
		Map<String, List<OutKeywordRankInfoDto>> res = new HashMap<String, List<OutKeywordRankInfoDto>>();
		//校验项目和竞品是否正确
		ProjectDto projectDto = projectApi.getArchiveProjectInfoById(inDto.getProjectId());
		if(null == projectDto || "".equals(projectDto)){
			return new Resource<Map<String, List<OutKeywordRankInfoDto>>>(prop.getProcessStatus("PROJECT_ERROR"));
		}
		
		//校验渠道  调用套餐,获取套餐中的搜索引擎类型
		List<Integer> engines = new ArrayList<Integer>();
		engines.add(StringUtils.toInteger(inDto.getEngineType()));
		if(StringUtils.isBlank(inDto.getEngineType()) || channelApi.getChannelByIds(engines).size() <= 0){
			return new Resource<Map<String, List<OutKeywordRankInfoDto>>>(prop.getProcessStatus("ENGINE_ERROR"));
		}
		
		//调用时间工具类,获取抓取时间
		List<Date> crawlDates = timeApi.getCrawlDate(DateUtils.parseDate(inDto.getStartTime()), DateUtils.parseDate(inDto.getEndTime()), 
				StringUtils.toInteger(inDto.getProjectId()), "1");
		if(null == crawlDates || crawlDates.size() <= 0){
			return new Resource<Map<String, List<OutKeywordRankInfoDto>>>(res);
		}
		Collections.sort(crawlDates);
		
		//获取用户选择的关键词
		List<Integer> keywordIds = new ArrayList<Integer>();
		
		this.getAllRelKeyword(keywordIds, StringUtils.toInteger(projectDto.getProjectId()), inDto.getBrand(), 
				ListUntils.listStrToInteger(inDto.getKeywordGroupList()), ListUntils.listStrToInteger(inDto.getKeywordList()));
		if(ListUntils.isNull(keywordIds)){
			return new Resource<Map<String,List<OutKeywordRankInfoDto>>>(res);
		}
		
		//查询项目维度的某些关键词的排名详细信息
		List<OutKeywordRankInfoDto> out = new ArrayList<OutKeywordRankInfoDto>();
		
		List<KeywordRankCompareVo> temp = rankApp.getAllKeywordsRanks(crawlDates, StringUtils.toInteger(inDto.getProjectId()), keywordIds, StringUtils.toInteger(inDto.getEngineType()));
		
		//构造返回结果
		if(!ListUntils.isNull(temp)){
			OutProComKwInfoDto proInfos = projectApi.getProComKwInfoById(projectDto.getProjectId());
			if(null != proInfos){
				List<OutProKeywordDto> keywordDtos = proInfos.getKeywords();
				if(!ListUntils.isNull(keywordDtos)){
					OutKeywordRankInfoDto item = null;
					List<String> groups = null;
					for (KeywordRankCompareVo keywordRankCompareVo : temp) {
						item = new OutKeywordRankInfoDto();
						item.setKeywordId(keywordRankCompareVo.getKeywordId() + "");
						item.setRanking(keywordRankCompareVo.getRank() + "");
						item.setUpOrDown(keywordRankCompareVo.getUpOrDown());
						item.setUrl(keywordRankCompareVo.getUrl());
						item.setLinkUrl(keywordRankCompareVo.getLinkUrl());
						
						for (OutProKeywordDto outProKeywordDto : keywordDtos) {
							if(keywordRankCompareVo.getKeywordId().equals(outProKeywordDto.getKeywordId())){
								groups = new ArrayList<String>();
								item.setName(outProKeywordDto.getKeywordName());
								item.setBrand(outProKeywordDto.getBranded());
								if(!ListUntils.isNull(outProKeywordDto.getGroups())){
									for (Group group : outProKeywordDto.getGroups()) {
										groups.add(group.getGroupName());
									}
								}
								item.setGroup(groups);
							}
						}
						out.add(item);
					}
				}
			}
		}
		
		res.put("infoList", out);
		
		return new Resource<Map<String, List<OutKeywordRankInfoDto>>>(res);
	}
	
	/**
	 *  接口详情 (id: 272)
		接口名称 排名-搜索引擎对比tab下图表
		请求类型 post
		请求Url  /seo/getAllEngineVisibility
	 * @param inDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/seo/getAllEngineVisibility")
	public Resource<OutSearchVisibilityDto> getAllEngineVisibility(@RequestBody @Valid InRankSearchVisiDto inDto) throws Exception{
		OutSearchVisibilityDto res = new OutSearchVisibilityDto();
		//校验项目和竞品是否正确
		ProjectDto projectDto = projectApi.getArchiveProjectInfoById(inDto.getProjectId());
		if(null == projectDto || "".equals(projectDto)){
			return new Resource<OutSearchVisibilityDto>(prop.getProcessStatus("PROJECT_ERROR"));
		}
		
		//校验渠道  调用套餐,获取套餐中的搜索引擎类型
		List<Integer> engines = new ArrayList<Integer>();
		List<ChannelDto> channelDtos = channelApi.getChannel();
		if(!ListUntils.isNull(channelDtos)){
			for (ChannelDto channelDto : channelDtos) {
				engines.add(StringUtils.toInteger(channelDto.getEngineType()));
			}
		}
		if(ListUntils.isNull(engines)){
			return new Resource<OutSearchVisibilityDto>(prop.getProcessStatus("ENGINE_ERROR"));
		}
		
		//调用时间工具类,获取抓取时间
		List<Date> crawlDates = timeApi.getCrawlDateForRank(DateUtils.parseDate(inDto.getStartTime()), DateUtils.parseDate(inDto.getEndTime()), 
				StringUtils.toInteger(inDto.getProjectId()), "0".equals(inDto.getIsWeek()) ? "1" : "2");
		if(ListUntils.isNull(crawlDates)){
			return new Resource<OutSearchVisibilityDto>(res);
		}
		Collections.sort(crawlDates);
		
		//获取用户选择的关键词
		List<Integer> keywordIds = new ArrayList<Integer>();
		
		this.getAllRelKeyword(keywordIds, StringUtils.toInteger(projectDto.getProjectId()), inDto.getBrand(), 
				ListUntils.listStrToInteger(inDto.getKeywordGroupList()), ListUntils.listStrToInteger(inDto.getKeywordList()));
		if(ListUntils.isNull(keywordIds)){
			return new Resource<OutSearchVisibilityDto>(res);
		}
		
		//查询搜索引擎维度的某些关键词的搜索可见性
		List<RankSearchVisibilityVo> temp = rankApp.getAllEngineVisibility(crawlDates, StringUtils.toInteger(inDto.getProjectId()), keywordIds, engines);
		
		//构造返回结果
		if(!ListUntils.isNull(temp)){
			List<VisibilityDto> visibilityDtos = new ArrayList<VisibilityDto>();
			VisibilityDto item = null;
			for (RankSearchVisibilityVo rankSearchVisibilityVo : temp) {
				item = new VisibilityDto();
				item.setCrawlDate(DateUtils.formatDate(rankSearchVisibilityVo.getCrawlDate(), "yyyyMMdd"));
				item.setEngineType(rankSearchVisibilityVo.getEngineType() + "");
				item.setId(rankSearchVisibilityVo.getCompetitorId() + "");
				item.setIsCom("0");
				item.setVisibility(rankSearchVisibilityVo.getVisibility() + "");
				
				visibilityDtos.add(item);
			}
			res.setEngineType(ListUntils.listIntegerToStr(engines).toArray(new String[engines.size()]));
			res.setId(inDto.getProjectId());
			res.setVisibilityList(visibilityDtos);
		}
		
		return new Resource<OutSearchVisibilityDto>(res);
	}
	
	/**
	 *  接口详情 (id: 273)
		接口名称 排名-关键词排名搜索引擎对比
		请求类型 post
		请求Url  /seo/getDiffEngineRanks
	 * @param inDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/seo/getDiffEngineRanks")
	public Resource<Map<String, List<OutDiffEngineRankDto>>> getDiffEngineRanks(@RequestBody @Valid InKeyRankDto inDto) throws Exception{
		Map<String, List<OutDiffEngineRankDto>> res = new HashMap<String, List<OutDiffEngineRankDto>>();
		//校验项目和竞品是否正确
		ProjectDto projectDto = projectApi.getArchiveProjectInfoById(inDto.getProjectId());
		if(null == projectDto || "".equals(projectDto)){
			return new Resource<Map<String, List<OutDiffEngineRankDto>>>(prop.getProcessStatus("PROJECT_ERROR"));
		}
		
		//校验渠道  调用套餐,获取套餐中的搜索引擎类型
		List<Integer> engines = new ArrayList<Integer>();
		String[] engine = inDto.getEngineType().split("\\,");
		if(null != engine && engine.length > 0){
			engines.addAll(ListUntils.listStrToInteger(Arrays.asList(engine)));
		}
		if(StringUtils.isBlank(inDto.getEngineType()) || channelApi.getChannelByIds(engines).size() < 2){
			return new Resource<Map<String, List<OutDiffEngineRankDto>>>(prop.getProcessStatus("ENGINE_ERROR"));
		}
		
		//调用时间工具类,获取抓取时间
		List<Date> crawlDates = timeApi.getCrawlDate(DateUtils.parseDate(inDto.getStartTime()), DateUtils.parseDate(inDto.getEndTime()), 
				StringUtils.toInteger(inDto.getProjectId()), "1");
		if(null == crawlDates || crawlDates.size() <= 0){
			return new Resource<Map<String, List<OutDiffEngineRankDto>>>(res);
		}
		Collections.sort(crawlDates);
		
		//获取用户选择的关键词
		List<Integer> keywordIds = new ArrayList<Integer>();
		
		this.getAllRelKeyword(keywordIds, StringUtils.toInteger(projectDto.getProjectId()), inDto.getBrand(), 
				ListUntils.listStrToInteger(inDto.getKeywordGroupList()), ListUntils.listStrToInteger(inDto.getKeywordList()));
		if(ListUntils.isNull(keywordIds)){
			return new Resource<Map<String, List<OutDiffEngineRankDto>>>(res);
		}
		
		//查询搜索引擎维度的某些关键词的搜索可见性
		List<OutDiffEngineRankDto> temp = rankApp.getDiffEngineRanks(crawlDates, StringUtils.toInteger(inDto.getProjectId()), keywordIds, engines);
		
		//构造返回结果
		if(!ListUntils.isNull(temp)){
			OutProComKwInfoDto proInfos = projectApi.getProComKwInfoById(projectDto.getProjectId());
			if(null != proInfos){
				List<OutProKeywordDto> keywordDtos = proInfos.getKeywords();
				if(!ListUntils.isNull(keywordDtos)){
					List<String> groups = null;
					for (OutDiffEngineRankDto outDiffEngineRankDto : temp) {
						for (OutProKeywordDto outProKeywordDto : keywordDtos) {
							if(outDiffEngineRankDto.getKeywordId().equals(outProKeywordDto.getKeywordId() + "")){
								groups = new ArrayList<String>();
								outDiffEngineRankDto.setName(outProKeywordDto.getKeywordName());
								outDiffEngineRankDto.setBrand(outProKeywordDto.getBranded());
								if(!ListUntils.isNull(outProKeywordDto.getGroups())){
									for (Group group : outProKeywordDto.getGroups()) {
										groups.add(group.getGroupName());
									}
								}
								outDiffEngineRankDto.setGroup(groups);
							}
						}
					}
					res.put("list", temp);
				}
			}
		}
		
		return new Resource<Map<String, List<OutDiffEngineRankDto>>>(res);
	}
	
	/**
	 *  接口详情 (id: 274)
		接口名称 排名-竞争对手tab下排名图表
		请求类型 post
		请求Url  /seo/getAllSearchResult
	 * @param inDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/seo/getAllSearchResult")
	public Resource<Map<String, List<OutKeyRankNumItemDto>>> getAllSearchResult(@RequestBody @Valid InRankSearchVisiDto inDto) throws Exception{
		Map<String, List<OutKeyRankNumItemDto>> res = new HashMap<String, List<OutKeyRankNumItemDto>>();
		//校验项目和竞品是否正确
		ProjectDto projectDto = projectApi.getArchiveProjectInfoById(inDto.getProjectId());
		if(null == projectDto || "".equals(projectDto)){
			return new Resource<Map<String, List<OutKeyRankNumItemDto>>>(prop.getProcessStatus("PROJECT_ERROR"));
		}
		
		//校验渠道  调用套餐,获取套餐中的搜索引擎类型
		List<Integer> engines = new ArrayList<Integer>();
		engines.add(StringUtils.toInteger(inDto.getEngineType()));
		if(StringUtils.isBlank(inDto.getEngineType()) || channelApi.getChannelByIds(engines).size() <= 0){
			return new Resource<Map<String, List<OutKeyRankNumItemDto>>>(prop.getProcessStatus("ENGINE_ERROR"));
		}
		
		//调用时间工具类,获取抓取时间
		List<Date> crawlDates = timeApi.getCrawlDateForRank(DateUtils.parseDate(inDto.getStartTime()), DateUtils.parseDate(inDto.getEndTime()), 
				StringUtils.toInteger(inDto.getProjectId()), "0".equals(inDto.getIsWeek()) ? "1" : "2");
		if(ListUntils.isNull(crawlDates)){
			return new Resource<Map<String, List<OutKeyRankNumItemDto>>>(res);
		}
		Collections.sort(crawlDates);
		
		//获取用户选择的关键词
		List<Integer> keywordIds = new ArrayList<Integer>();
		
		this.getAllRelKeyword(keywordIds, StringUtils.toInteger(projectDto.getProjectId()), inDto.getBrand(), 
				ListUntils.listStrToInteger(inDto.getKeywordGroupList()), ListUntils.listStrToInteger(inDto.getKeywordList()));
		if(ListUntils.isNull(keywordIds)){
			return new Resource<Map<String, List<OutKeyRankNumItemDto>>>(res);
		}
		//是否包含竞品
		List<Integer> competitorIds = new ArrayList<Integer>();
		competitorIds.add(StringUtils.toInteger(inDto.getProjectId()));
		List<Competitor> competitors = this.getAllCompetitorIds(competitorIds, inDto.getIshave(), inDto.getProjectId());
		
		//查询项目和竞品维度的某些关键词的排名前30个数
		List<ProRankVo> temp = rankApp.getAllSearchResult(crawlDates, StringUtils.toInteger(inDto.getProjectId()), competitorIds, keywordIds, StringUtils.toInteger(inDto.getEngineType()));
		
		//构造返回结果
		if(!ListUntils.isNull(temp)){
			List<OutKeyRankNumItemDto> out = new ArrayList<OutKeyRankNumItemDto>();
			OutKeyRankNumItemDto item = null;
			
			for (ProRankVo itemVo : temp) {
				item = new OutKeyRankNumItemDto();
				item.setId(itemVo.getCompetitorId() + "");
				item.setCrawlTime(DateUtils.formatDate(itemVo.getCrawlDate(), "yyyyMMdd"));
				item.setNum(itemVo.getRankNum() + "");
				if(!ListUntils.isNull(competitors)){
					for (Competitor competitor : competitors) {
						if(competitor.getCompetitorId().equals(itemVo.getCompetitorId())){
							item.setIsCom("1");
							item.setName(competitor.getCompetitorName());
							break;
						}
					}
				}
				if(projectDto.getProjectId().equals(itemVo.getCompetitorId() + "")){
					item.setIsCom("0");
					item.setName(projectDto.getProjectName());
				}
				out.add(item);
			}
			res.put("list", out);
		}
		
		return new Resource<Map<String, List<OutKeyRankNumItemDto>>>(res);
	}
	
	/**
	 *  接口详情 (id: 275)
		接口名称 排名--关键词排名竞争分析 列表
		请求类型 post
		请求Url  /seo/getRankingAnalysis
	 * @param inDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/seo/getRankingAnalysis")
	public Resource<Map<String, List<OutRankAnalysisDto>>> getRankingAnalysis(@RequestBody @Valid InRankSearchVisiDto inDto) throws Exception{
		Map<String, List<OutRankAnalysisDto>> res = new HashMap<String, List<OutRankAnalysisDto>>();
		//校验项目和竞品是否正确
		ProjectDto projectDto = projectApi.getArchiveProjectInfoById(inDto.getProjectId());
		if(null == projectDto || "".equals(projectDto)){
			return new Resource<Map<String, List<OutRankAnalysisDto>>>(prop.getProcessStatus("PROJECT_ERROR"));
		}
		
		//校验渠道  调用套餐,获取套餐中的搜索引擎类型
		List<Integer> engines = new ArrayList<Integer>();
		engines.add(StringUtils.toInteger(inDto.getEngineType()));
		if(StringUtils.isBlank(inDto.getEngineType()) || channelApi.getChannelByIds(engines).size() <= 0){
			return new Resource<Map<String, List<OutRankAnalysisDto>>>(prop.getProcessStatus("ENGINE_ERROR"));
		}
		
		//调用时间工具类,获取抓取时间
		List<Date> crawlDates = timeApi.getCrawlDate(DateUtils.parseDate(inDto.getStartTime()), DateUtils.parseDate(inDto.getEndTime()), 
				StringUtils.toInteger(inDto.getProjectId()), "1");
		if(null == crawlDates || crawlDates.size() <= 0){
			return new Resource<Map<String, List<OutRankAnalysisDto>>>(res);
		}
		Collections.sort(crawlDates);
		
		//获取用户选择的关键词
		List<Integer> keywordIds = new ArrayList<Integer>();
		
		this.getAllRelKeyword(keywordIds, StringUtils.toInteger(projectDto.getProjectId()), inDto.getBrand(), 
				ListUntils.listStrToInteger(inDto.getKeywordGroupList()), ListUntils.listStrToInteger(inDto.getKeywordList()));
		if(ListUntils.isNull(keywordIds)){
			return new Resource<Map<String, List<OutRankAnalysisDto>>>(res);
		}
		//是否包含竞品
		List<Integer> competitorIds = new ArrayList<Integer>();
		competitorIds.add(StringUtils.toInteger(inDto.getProjectId()));
		List<Competitor> competitors = this.getAllCompetitorIds(competitorIds, inDto.getIshave(), inDto.getProjectId());
		
		//查询项目和竞品维度的某些关键词的排名前30个数
		List<ProRankVo> temp = rankApp.getRankingAnalysis(crawlDates, StringUtils.toInteger(inDto.getProjectId()), competitorIds, keywordIds, engines.get(0));
		
		//构造返回结果
		if(!ListUntils.isNull(temp)){
			List<OutRankAnalysisDto> out = new ArrayList<OutRankAnalysisDto>();
			OutRankAnalysisDto outItem = null;
			OutRankAnalysisItemDto item = null;
			List<OutRankAnalysisItemDto> itemDtos = null;
			List<String> groups = null;
			OutProComKwInfoDto proInfos = projectApi.getProComKwInfoById(projectDto.getProjectId());
			List<OutProKeywordDto> keywordDtos = null;
			if(null != proInfos){
				keywordDtos = proInfos.getKeywords();
			}
			for (Integer keywordId : keywordIds) {
				if(!ListUntils.isNull(keywordDtos)){
					//组织关键词信息
					for (OutProKeywordDto outProKeywordDto : keywordDtos) {
						if(outProKeywordDto.getKeywordId().equals(keywordId)){
							outItem = new OutRankAnalysisDto();
							groups = new ArrayList<String>();
							outItem.setName(outProKeywordDto.getKeywordName());
							outItem.setBrand(outProKeywordDto.getBranded());
							if(!ListUntils.isNull(outProKeywordDto.getGroups())){
								for (Group group : outProKeywordDto.getGroups()) {
									groups.add(group.getGroupName());
								}
							}
							outItem.setGroup(groups);
							outItem.setKeywordId(keywordId + "");
							break;
						}
					}
					if(null != outItem){
						//组织项目和竞品数据
						itemDtos = new ArrayList<OutRankAnalysisItemDto>();
						for (ProRankVo itemVo : temp) {
							if(keywordId.equals(itemVo.getKeywordId())){
								item = new OutRankAnalysisItemDto();
								item.setRanking(itemVo.getRank() + "");
								item.setRankingpromotion(itemVo.getRankNum() + "");
								if(!ListUntils.isNull(competitors)){
									for (Competitor competitor : competitors) {
										if(competitor.getCompetitorId().equals(itemVo.getCompetitorId())){
											item.setIsCom("1");
											item.setName(competitor.getCompetitorName());
											break;
										}
									}
								}
								if(projectDto.getProjectId().equals(itemVo.getCompetitorId() + "")){
									item.setIsCom("0");
									item.setName(projectDto.getProjectName());
								}
								itemDtos.add(item);
							}
						}
						outItem.setList(itemDtos);
						out.add(outItem);
					}
				}
			}
			
			res.put("list", out);
		}
		
		return new Resource<Map<String, List<OutRankAnalysisDto>>>(res);
	}
	
	
	
	
	
	
	
	
	
}
