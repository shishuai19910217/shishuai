package com.ido85.seo.dashboard.application.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import com.ido85.config.properties.BussinessMsgCodeProperties;
import com.ido85.frame.common.exceptions.BusinessException;
import com.ido85.frame.common.utils.CompareUtil;
import com.ido85.frame.common.utils.DateUtils;
import com.ido85.frame.common.utils.ListUntils;
import com.ido85.frame.common.utils.StringUtils;
import com.ido85.master.keyword.dto.OutProKeywordDto;
import com.ido85.master.project.domain.Competitor;
import com.ido85.master.project.dto.OutProComKwInfoDto;
import com.ido85.master.project.dto.OutProCompetitorDto;
import com.ido85.master.project.dto.ProjectDto;
import com.ido85.seo.common.BaseApplication;
import com.ido85.seo.dashboard.application.DashboardVisibilityApplication;
import com.ido85.seo.dashboard.domain.DashKeyword;
import com.ido85.seo.dashboard.domain.DashboardVisibility;
import com.ido85.seo.dashboard.domain.ProLinksView;
import com.ido85.seo.dashboard.dto.InExtLinkDto;
import com.ido85.seo.dashboard.dto.InKeywordRankDto;
import com.ido85.seo.dashboard.dto.InKeywordTopDto;
import com.ido85.seo.dashboard.dto.InSearchQuestionDto;
import com.ido85.seo.dashboard.dto.InSearchRankDto;
import com.ido85.seo.dashboard.dto.InVisibilityDto;
import com.ido85.seo.dashboard.dto.KeywordRankNumDto;
import com.ido85.seo.dashboard.dto.KeywordRankRangeNumDto;
import com.ido85.seo.dashboard.dto.OutExtLinkDomainsDto;
import com.ido85.seo.dashboard.dto.OutExtLinkDto;
import com.ido85.seo.dashboard.dto.OutKeywordRankDto;
import com.ido85.seo.dashboard.dto.OutKeywordTopDto;
import com.ido85.seo.dashboard.dto.OutSearchQuestionDto;
import com.ido85.seo.dashboard.dto.OutSearchRankDto;
import com.ido85.seo.dashboard.dto.OutVisibilityDto;
import com.ido85.seo.dashboard.dto.projectEngineTypeVisibilityDto;
import com.ido85.seo.dashboard.resources.DashboardKeywordResourcesd;
import com.ido85.seo.dashboard.resources.DashboardVisibilityResources;
import com.ido85.seo.dashboard.resources.ProDomainResourced;
import com.ido85.seo.dashboard.resources.ProLinkViewResourced;
import com.ido85.services.project.ProjectApi;
import com.ido85.services.time.TimeInsideApi;

@Named
public class DashboardVisibilityApplicationImpl extends BaseApplication implements DashboardVisibilityApplication {
	
	@Inject
	private ProjectApi projectService;
	@Inject
	private BussinessMsgCodeProperties msg;
	@Inject
	private TimeInsideApi timeApi;
	@Inject
	private DashboardVisibilityResources dashiboardRes;
	@Inject
	private DashboardKeywordResourcesd dashboardKeywordRes;
	@Inject
	private ProDomainResourced proDomainRes;
	@Inject
	private ProLinkViewResourced proLinkViewRes;

	@Override
	public OutVisibilityDto getVisibilityListByEngineType(InVisibilityDto in) throws Exception {
		System.out.println("-------------------------getVisibilityListByEngineType------------------------------");
		OutVisibilityDto dto = new OutVisibilityDto();
		Integer projectId = StringUtils.toInteger(in.getProjectId());
		Integer engineType = StringUtils.toInteger(in.getEngineType());
		List<Date> tempDates = new ArrayList<>();// 需要补齐的时间点
		// 检测当前项目 根据id取项目
		ProjectDto projectDto = projectService.getProjectInfoById(in.getProjectId());
		List<Competitor> comList = projectService.getCompetitorsByProjectId(projectId.toString());
		Map<String, Competitor> competitorMap = new HashMap<>();

		if (!ListUntils.isNull(comList)) {
			competitorMap = ListUntils.listToMap(comList, "CompetitorId");
		}
		if (null == projectDto) {
			throw new BusinessException(msg.getProcessStatus("PROJECT_NOT_EXIST"));
		}
		List<Date> crawlDates = timeApi.getTimesByIsWeek(StringUtils.toInteger(projectDto.getProjectId()),
				in.getIsWeek(), 8);
		if (ListUntils.isNull(crawlDates)) {
			/*throw new BusinessException(msg.getProcessStatus("NOT_CRAWL"));*/
			logger.info(msg.getProcessStatus("NOT_CRAWL").getRetMsg());
			return dto;
			
		}
		// 品牌的 某一个搜索引擎的
		List<DashboardVisibility> dashboardVisibilityList = dashiboardRes.getVisibility(crawlDates, projectId,
				engineType);

		// 是否补齐项目以及竞品（或许有的品牌在这个搜索引擎上没有） 现在没有补齐

		if (ListUntils.isNull(dashboardVisibilityList)) {
			logger.info(msg.getProcessStatus("NOT_ENGINETYPE_CRAWL").getRetMsg());
			return dto;
			/*throw new BusinessException(msg.getProcessStatus("NOT_ENGINETYPE_CRAWL"));*/
		}
		Map<String, List<DashboardVisibility>> brandMap = new HashMap<>();// 按品牌拆分

		for (DashboardVisibility visibility : dashboardVisibilityList) {
			if (null == brandMap.get(visibility.getCompetitorId().toString())) {
				List<DashboardVisibility> tempList = new ArrayList<>();
				tempList.add(visibility);
				brandMap.put(visibility.getCompetitorId().toString(), tempList);
			} else {
				brandMap.get(visibility.getCompetitorId().toString()).add(visibility);
			}
		}

		for (String brandId : brandMap.keySet()) {
			List<DashboardVisibility> tempList = brandMap.get(brandId);
			// 按照抓取时间补齐
			for (Date date : crawlDates) {
				int i = 0;
				for (DashboardVisibility visibility : tempList) {
					if (DateUtils.isSameDate(date, visibility.getCrawlDate())) {//
						i++;
					}
				}
				if (i <= 0) {
					tempDates.add(date);
				}
			}
			// 补齐时间点
			if (!ListUntils.isNull(tempDates)) {
				DashboardVisibility visibility = null;
				for (Date date : tempDates) {
					visibility = new DashboardVisibility();
					visibility.setCrawlDate(date);
					visibility.setEngineType(engineType);
					visibility.setProjectId(projectId);
					visibility.setCompetitorId(StringUtils.toInteger(brandId));
					visibility.setVisibility(0D);
					dashboardVisibilityList.add(visibility);
				}
				tempDates.clear();
			}

		}

		List<projectEngineTypeVisibilityDto> proVislist = this.beanToDto(dashboardVisibilityList, "0", projectId);// 项目本身
		List<projectEngineTypeVisibilityDto> comVislist = this.beanToDto(dashboardVisibilityList, "1", projectId);// 所有竞品
		// fuzhi
		this.projectEngineTypeVisibilityDtosToDto(proVislist, dto);
		for (Map<String, Object> map : dto.getVisibilityList()) {
			if (map.get("id").toString().equals(projectDto.getProjectId())) {

				map.put("name", projectDto.getProjectName());
			}

		}
		if ("1".equals(in.getIsHave())) {// 包含竞品
			this.projectEngineTypeVisibilityDtosToDto(comVislist, dto);
			// 获取竞品
			for (Map<String, Object> map : dto.getVisibilityList()) {
				if (!map.get("id").toString().equals(projectDto.getProjectId())) {
					if (competitorMap.get(map.get("id").toString()) != null) {
						map.put("name", competitorMap.get(map.get("id").toString()) == null ? ""
								: competitorMap.get(map.get("id").toString()).getCompetitorName());
					}

				}
			}
		}
		return dto;
	}

	@Override
	public OutVisibilityDto getVisibilityListByMoreEngine(InVisibilityDto in) throws Exception {
		OutVisibilityDto dto = new OutVisibilityDto();
		Integer projectId = StringUtils.toInteger(in.getProjectId());

		List<Date> tempDates = new ArrayList<>();// 需要补齐的时间点
		// 检测当前项目 根据id取项目
		ProjectDto projectDto = projectService.getProjectInfoById(in.getProjectId());
		if (null == projectDto) {
			throw new BusinessException(msg.getProcessStatus("PROJECT_NOT_EXIST"));
			
			
		}
		List<Date> crawlDates = timeApi.getTimesByIsWeek(StringUtils.toInteger(projectDto.getProjectId()),
				in.getIsWeek(), 8);
		if (ListUntils.isNull(crawlDates)) {
			logger.info("方法-getVisibilityListByMoreEngine()"+msg.getProcessStatus("NOT_CRAWL").getRetMsg());
			return dto;
//			throw new BusinessException(msg.getProcessStatus("NOT_CRAWL"));
		}
		String[] engineTypes = in.getEngineType().split(",");

		List<Integer> engineTypeList = new ArrayList<Integer>();//// 搜索引擎 list

		for (String s : engineTypes) {
			engineTypeList.add(StringUtils.toInteger(s));
		}
		// 项目本身的 某一个搜索引擎的
		List<DashboardVisibility> dashboardVisibilityList = dashiboardRes.getVisibilityListByMoreEngine(crawlDates,
				projectId, projectId, engineTypeList);

		// 是否补齐项目以及竞品（或许有的品牌在这个搜索引擎上没有） 现在没有补齐

		if (ListUntils.isNull(dashboardVisibilityList)) {
			//throw new BusinessException(msg.getProcessStatus("NOT_ENGINETYPE_CRAWL"));
			logger.info("方法-getVisibilityListByMoreEngine()"+msg.getProcessStatus("NOT_ENGINETYPE_CRAWL").getRetMsg());
			return dto;
		}
		Map<String, List<DashboardVisibility>> engineTypeMap = new HashMap<>();// 按搜索引擎拆分

		for (DashboardVisibility visibility : dashboardVisibilityList) {
			if (null == engineTypeMap.get(visibility.getEngineType().toString())) {
				List<DashboardVisibility> tempList = new ArrayList<>();
				tempList.add(visibility);
				engineTypeMap.put(visibility.getEngineType().toString(), tempList);
			} else {
				engineTypeMap.get(visibility.getEngineType().toString()).add(visibility);
			}
		}

		for (String engineType : engineTypeMap.keySet()) {
			List<DashboardVisibility> tempList = engineTypeMap.get(engineType);
			// 按照抓取时间补齐
			for (Date date : crawlDates) {
				int i = 0;
				for (DashboardVisibility visibility : tempList) {
					if (DateUtils.isSameDate(date, visibility.getCrawlDate())) {//
						i++;
					}
				}
				if (i <= 0) {
					tempDates.add(date);
				}
			}
			// 补齐时间点
			if (!ListUntils.isNull(tempDates)) {
				DashboardVisibility visibility = null;
				for (Date date : tempDates) {
					visibility = new DashboardVisibility();
					visibility.setCrawlDate(date);
					visibility.setEngineType(StringUtils.toInteger(engineType));
					visibility.setProjectId(projectId);
					visibility.setCompetitorId(projectId);
					visibility.setVisibility(0D);
					dashboardVisibilityList.add(visibility);
				}
				tempDates.clear();
			}
		}

		List<projectEngineTypeVisibilityDto> proVislist = this.beanToDto(dashboardVisibilityList, "0", projectId);// 项目本身
		// fuzhi
		this.projectMoreEngineTypeVisibilityDtosToDto(proVislist, dto);
		for (Map<String, Object> map : dto.getVisibilityList()) {
			map.put("name", projectDto.getProjectName());
		}
		
		return dto;
	}

	@Override
	public OutKeywordTopDto getKeywordTop(InKeywordTopDto in) throws Exception {
		OutKeywordTopDto dto = new OutKeywordTopDto();
		Integer projectId = StringUtils.toInteger(in.getProjectId());

		List<Date> tempDates = new ArrayList<>();// 需要补齐的时间点
		// 检测当前项目 根据id取项目
		OutProComKwInfoDto projectDto = projectService.getProComKwInfoById(in.getProjectId().toString());

		if (null == projectDto) {
			throw new BusinessException(msg.getProcessStatus("PROJECT_NOT_EXIST"));
		}
		Map<String, OutProCompetitorDto> competitorMap = new HashMap<>();
		if (!ListUntils.isNull(projectDto.getCompetitors())) {
			competitorMap = ListUntils.listToMap(projectDto.getCompetitors(), "Id");
		}
		List<OutProKeywordDto> keywordDtoList = projectDto.getKeywords();
		Map<String, OutProKeywordDto> keywordDtoMap = new HashMap<>();
		if (!ListUntils.isNull(keywordDtoList)) {
			keywordDtoMap = ListUntils.listToMap(keywordDtoList, "KeywordId");
		}
		List<Date> crawlDates = timeApi.getTimesByIsWeek(StringUtils.toInteger(projectDto.getProjectId()),
				in.getIsWeek(), 1);
		if (ListUntils.isNull(crawlDates)) {
			//throw new BusinessException(msg.getProcessStatus("NOT_CRAWL"));
			logger.info("方法-getKeywordTop()"+msg.getProcessStatus("NOT_CRAWL").getRetMsg());
			return dto;
		}
		Collections.sort(crawlDates, CompareUtil.createComparator(-1));
		Date time = crawlDates.get(0);// 最新一次 按周或者按月
		List<DashKeyword> dashKeywordList = dashboardKeywordRes.getKeywordTop(projectId, projectId, time,
				StringUtils.toInteger(in.getEngineType()));
		if (ListUntils.isNull(dashKeywordList)) {
			logger.info("方法-getKeywordTop()"+msg.getProcessStatus("NOT_CRAWL").getRetMsg());
			return dto;
			//throw new BusinessException(msg.getProcessStatus("NOT_CRAWL"));
		}
		if (null != dashKeywordList && dashKeywordList.size() > 0) {

			for (DashKeyword r : dashKeywordList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("engineType", r.getEngineType());
				map.put("id", r.getKeywordId());
				map.put("name", keywordDtoMap.get(r.getKeywordId().toString()) == null ? ""
						: keywordDtoMap.get(r.getKeywordId().toString()).getKeywordName());
				map.put("rank", r.getRank());
				map.put("sortRank", r.getRank());// 专门为排序用的

				dto.getKeywordList().add(map);
			}
			dto.setUpdateDate(DateUtils.formatDate(time, "yyyyMMddHHmmss"));
		}
		if (dto.getKeywordList().size() > 0) {
			Collections.sort(dto.getKeywordList(), CompareUtil.createComparator(1, "rank"));
		}

		return dto;
	}

	/***
	 * 转换Bean
	 * 
	 * @param list
	 * @param isCom
	 *            是返回项目本身 还是所有的竞品的
	 * @return
	 */
	public List<projectEngineTypeVisibilityDto> beanToDto(List<DashboardVisibility> list, String isCom,
			Integer projectId) {
		List<projectEngineTypeVisibilityDto> dtoList = new ArrayList<>();
		projectEngineTypeVisibilityDto dto = null;
		if (ListUntils.isNull(list)) {
			return null;
		}
		for (DashboardVisibility visibility : list) {
			dto = new projectEngineTypeVisibilityDto();
			if ("0".equals(isCom)) {
				if (projectId.intValue() == visibility.getCompetitorId().intValue()) {// 是项目本身
					dto.setBrandId(visibility.getProjectId());
					dto.setIsCom(isCom);
					dto.setEngineType(visibility.getEngineType());
					dto.setCrawlDate(visibility.getCrawlDate());
					dto.setDashboardVisibilityId(visibility.getDashboardVisibilityId());
					dto.setVisibility(visibility.getVisibility());
					dtoList.add(dto);
				}

			} else {
				if (projectId.intValue() != visibility.getCompetitorId().intValue()) {
					dto.setBrandId(visibility.getCompetitorId());
					dto.setIsCom(isCom);
					dto.setEngineType(visibility.getEngineType());
					dto.setCrawlDate(visibility.getCrawlDate());
					dto.setDashboardVisibilityId(visibility.getDashboardVisibilityId());
					dto.setVisibility(visibility.getVisibility());
					dtoList.add(dto);
				}

			}

		}
		return dtoList;
	}

	/**
	 * 单搜索引擎 多品牌 升迁情况
	 * 
	 * @param vislist
	 * @param dto
	 */
	public void projectEngineTypeVisibilityDtosToDto(List<projectEngineTypeVisibilityDto> vislist,
			OutVisibilityDto dto) {
		if (ListUntils.isNull(vislist)) {
			return;
		}
		Map<String, List<projectEngineTypeVisibilityDto>> brandMap = new HashMap<>();// 按品牌拆分

		for (projectEngineTypeVisibilityDto visibility : vislist) {
			if (null == brandMap.get(visibility.getBrandId().toString())) {
				List<projectEngineTypeVisibilityDto> tempList = new ArrayList<>();
				tempList.add(visibility);
				brandMap.put(visibility.getBrandId().toString(), tempList);
			} else {
				brandMap.get(visibility.getBrandId().toString()).add(visibility);
			}
		}
		for (String brandId : brandMap.keySet()) {
			List<projectEngineTypeVisibilityDto> proVislist = brandMap.get(brandId);
			Collections.sort(proVislist, CompareUtil.createComparator(-1, "crawlDate"));// 到叙
			// 赋值
			if (!ListUntils.isNull(proVislist)) {
				for (int i = 0; i < proVislist.size(); i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					if (i < proVislist.size() && i + 1 < proVislist.size()) {

						Double nowv = 0.0;
						Double oldv = 0.0;
						if (null != proVislist.get(i).getVisibility()) {
							nowv = Double.valueOf(proVislist.get(i).getVisibility());
						}
						if (null != proVislist.get(i + 1).getVisibility()) {
							oldv = Double.valueOf(proVislist.get(i + 1).getVisibility());
						}
						map.put("upOrDown", StringUtils.sub(nowv, oldv));
						map.put("crawlDate", DateUtils.formatDate(proVislist.get(i).getCrawlDate(), "yyyyMMddHHmmss"));
						map.put("engineType", proVislist.get(i).getEngineType());
						map.put("id", proVislist.get(i).getBrandId());
						map.put("visibility", nowv);
						map.put("isCom", proVislist.get(i).getIsCom());
						dto.getVisibilityList().add(map);
					} else {
						Double nowv = 0.0;
						Double oldv = 0.0;
						if (null != proVislist.get(i).getVisibility()) {
							nowv = Double.valueOf(proVislist.get(i).getVisibility());
						}
						map.put("upOrDown", 0);
						map.put("crawlDate", DateUtils.formatDate(proVislist.get(i).getCrawlDate(), "yyyyMMddHHmmss"));
						map.put("engineType", proVislist.get(i).getEngineType());
						map.put("id", proVislist.get(i).getBrandId());
						map.put("visibility", nowv);
						map.put("isCom", proVislist.get(i).getIsCom());
						dto.getVisibilityList().add(map);
					}

				}
			}
		}

	}

	/***
	 * 多搜索引擎 但品牌
	 * 
	 * @param vislist
	 * @param dto
	 */
	public void projectMoreEngineTypeVisibilityDtosToDto(List<projectEngineTypeVisibilityDto> vislist,
			OutVisibilityDto dto) {
		if (ListUntils.isNull(vislist)) {
			return;
		}
		Map<String, List<projectEngineTypeVisibilityDto>> engineTypeMap = new HashMap<>();// 按品牌拆分

		for (projectEngineTypeVisibilityDto visibility : vislist) {
			if (null == engineTypeMap.get(visibility.getEngineType().toString())) {
				List<projectEngineTypeVisibilityDto> tempList = new ArrayList<>();
				tempList.add(visibility);
				engineTypeMap.put(visibility.getEngineType().toString(), tempList);
			} else {
				engineTypeMap.get(visibility.getEngineType().toString()).add(visibility);
			}
		}
		for (String engineType : engineTypeMap.keySet()) {
			List<projectEngineTypeVisibilityDto> proVislist = engineTypeMap.get(engineType);
			Collections.sort(proVislist, CompareUtil.createComparator(-1, "crawlDate"));// 到叙
			// 赋值
			if (!ListUntils.isNull(proVislist)) {
				for (int i = 0; i < proVislist.size(); i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					if (i < proVislist.size() && i + 1 < proVislist.size()) {

						Double nowv = 0.0;
						Double oldv = 0.0;
						if (null != proVislist.get(i).getVisibility()) {
							nowv = Double.valueOf(proVislist.get(i).getVisibility());
						}
						if (null != proVislist.get(i + 1).getVisibility()) {
							oldv = Double.valueOf(proVislist.get(i + 1).getVisibility());
						}
						System.out.println("=======================" + nowv + "===================================");
						map.put("upOrDown", StringUtils.sub(nowv, oldv));
						map.put("crawlDate", DateUtils.formatDate(proVislist.get(i).getCrawlDate(), "yyyyMMddHHmmss"));
						map.put("engineType", proVislist.get(i).getEngineType());
						map.put("id", proVislist.get(i).getBrandId());
						map.put("visibility", nowv);
						map.put("isCom", proVislist.get(i).getIsCom());
						dto.getVisibilityList().add(map);
					} else {
						Double nowv = 0.0;
						Double oldv = 0.0;
						if (null != proVislist.get(i).getVisibility()) {
							nowv = Double.valueOf(proVislist.get(i).getVisibility());
						}
						System.out.println("=======================" + nowv + "===================================");
						map.put("upOrDown", 0);
						map.put("crawlDate", DateUtils.formatDate(proVislist.get(i).getCrawlDate(), "yyyyMMddHHmmss"));
						map.put("engineType", proVislist.get(i).getEngineType());
						map.put("id", proVislist.get(i).getBrandId());
						map.put("visibility", nowv);
						map.put("isCom", proVislist.get(i).getIsCom());
						dto.getVisibilityList().add(map);
					}

				}
			}
		}

	}

	@SuppressWarnings("unused")
	public OutKeywordRankDto getkeywordRankNum(InKeywordRankDto in) throws Exception {
		OutKeywordRankDto dto = new OutKeywordRankDto();
		Integer projectId = StringUtils.toInteger(in.getProjectId());

		List<Date> tempDates = new ArrayList<>();// 需要补齐的时间点
		// 关键词书
		int keywordNum = 0;
		// 检测当前项目 根据id取项目
		OutProComKwInfoDto projectDto = projectService.getProComKwInfoById(in.getProjectId().toString());
		if (null == projectDto) {
			throw new BusinessException(msg.getProcessStatus("PROJECT_NOT_EXIST"));
		}
		keywordNum = projectDto.getKeywordNum();
		Map<String, OutProCompetitorDto> competitorMap = new HashMap<>();
		if (!ListUntils.isNull(projectDto.getCompetitors())) {
			competitorMap = ListUntils.listToMap(projectDto.getCompetitors(), "Id");
		}
		List<Date> crawlDates = timeApi.getTimesByIsWeek(StringUtils.toInteger(projectDto.getProjectId()),
				in.getIsWeek(), 2);
		if (ListUntils.isNull(crawlDates)) {
			logger.info("方法-getkeywordRankNum()"+msg.getProcessStatus("NOT_CRAWL").getRetMsg());
			return dto;
			//throw new BusinessException(msg.getProcessStatus("NOT_CRAWL"));
		}
		Collections.sort(crawlDates, CompareUtil.createComparator(-1));// 倒叙
		List<Date> crawlTimes = new ArrayList<>();
		if (crawlDates.size() < 2) {
			crawlTimes.add(crawlDates.get(0));
		} else {
			crawlTimes.add(crawlDates.get(0));
			crawlTimes.add(crawlDates.get(1));
		}
		Collections.sort(crawlTimes, CompareUtil.createComparator(-1));// 倒叙
		List<KeywordRankNumDto> keywordRankNumDtoList = dashboardKeywordRes.getkeywordRankNum(projectId, in.getNum(),
				StringUtils.toInteger(in.getEngineType()), crawlTimes);
		Map<String, List<KeywordRankNumDto>> brandMap = new HashMap<>();
		if (!ListUntils.isNull(keywordRankNumDtoList)) {
			for (KeywordRankNumDto keywordRankNumDto : keywordRankNumDtoList) {
				if (brandMap.get(keywordRankNumDto.getCompetitorId().toString()) == null) {
					List<KeywordRankNumDto> temp = new ArrayList<>();
					temp.add(keywordRankNumDto);
					brandMap.put(keywordRankNumDto.getCompetitorId().toString(), temp);
				} else {
					brandMap.get(keywordRankNumDto.getCompetitorId().toString()).add(keywordRankNumDto);
				}
			}
		}

		for (String brand : brandMap.keySet()) {
			List<KeywordRankNumDto> temp = brandMap.get(brand);
			if (StringUtils.toInteger(brand).intValue() == projectId.intValue()) {// 项目本身
				if (temp.size() >= 2) {// 可以比较
					if (temp.get(0).getCrawlDate().before(temp.get(1).getCrawlDate())) {// temp.get(1)就是最新一次
						dto.getProject().put("currentNum", temp.get(1).getNum());
						dto.getProject().put("id", projectId.toString());
						dto.getProject().put("name", projectDto.getProjectName());
						dto.getProject().put("keywordNum", keywordNum);
						dto.getProject().put("lastNum", temp.get(0).getNum());
					} else {
						dto.getProject().put("currentNum", temp.get(0).getNum());
						dto.getProject().put("id", projectId.toString());
						dto.getProject().put("name", projectDto.getProjectName());
						dto.getProject().put("keywordNum", keywordNum);
						dto.getProject().put("lastNum", temp.get(1).getNum());
					}
				} else {
					dto.getProject().put("currentNum", temp.get(0).getNum());
					dto.getProject().put("id", projectId.toString());
					dto.getProject().put("name", projectDto.getProjectName());
					dto.getProject().put("keywordNum", keywordNum);
					dto.getProject().put("lastNum", 0);
				}

			} else {
				if (temp.size() >= 2) {// 可以比较
					if (temp.get(0).getCrawlDate().before(temp.get(1).getCrawlDate())) {// temp.get(1)就是最新一次
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("currentNum", temp.get(1).getNum());
						map.put("id", temp.get(1).getCompetitorId());
						map.put("name", competitorMap.get(temp.get(1).getCompetitorId().toString()) == null ? ""
								: competitorMap.get(temp.get(1).getCompetitorId().toString()).getName());
						dto.getCompetitorList().add(map);
					} else {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("currentNum", temp.get(0).getNum());
						map.put("id", temp.get(0).getCompetitorId());
						map.put("name", competitorMap.get(temp.get(0).getCompetitorId().toString()) == null ? ""
								: competitorMap.get(temp.get(0).getCompetitorId().toString()).getName());
						dto.getCompetitorList().add(map);
					}
				} else {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("currentNum", temp.get(0).getNum());
					map.put("id", temp.get(0).getCompetitorId());
					map.put("name", competitorMap.get(temp.get(0).getCompetitorId().toString()) == null ? ""
							: competitorMap.get(temp.get(0).getCompetitorId().toString()).getName());
					dto.getCompetitorList().add(map);
				}

			}

		}
		dto.setUpdateDate(DateUtils.formatDate(crawlTimes.get(0), "yyyyMMddHHmmss"));
		return dto;
	}

	@Override
	public OutSearchRankDto getSearchRank(InSearchRankDto in) throws Exception {
		OutSearchRankDto dto = new OutSearchRankDto();
		// ToDo

		Integer projectId = StringUtils.toInteger(in.getProjectId());

		List<Date> tempDates = new ArrayList<>();// 需要补齐的时间点
		// 关键词书
		int keywordNum = 0;
		// 检测当前项目 根据id取项目
		OutProComKwInfoDto projectDto = projectService.getProComKwInfoById(in.getProjectId().toString());
		Map<String, OutProKeywordDto> keywordDtoMap = new HashMap<>();
		int nobrandNum = 0;
		int brandNum = 0;

		if (null == projectDto) {
			throw new BusinessException(msg.getProcessStatus("PROJECT_NOT_EXIST"));
		}
		if (!ListUntils.isNull(projectDto.getKeywords())) {
			for (OutProKeywordDto dt : projectDto.getKeywords()) {
				keywordNum++;
				if ("0".equals(dt.getBranded())) {
					nobrandNum++;
				} else {
					brandNum++;
				}
			}
			keywordDtoMap = ListUntils.listToMap(projectDto.getKeywords(), "KeywordId");
		}
		List<Date> crawlDates = timeApi.getTimesByIsWeek(StringUtils.toInteger(projectDto.getProjectId()),
				in.getIsWeek(), 2);
		if (ListUntils.isNull(crawlDates)) {
			logger.info("方法-getSearchRank()"+msg.getProcessStatus("NOT_CRAWL").getRetMsg());
			return dto;
			//throw new BusinessException(msg.getProcessStatus("NOT_CRAWL"));
		}
		Collections.sort(crawlDates, CompareUtil.createComparator(-1));// 倒叙
		List<Date> crawlTimes = new ArrayList<>();
		if (crawlDates.size() < 2) {
			crawlTimes.add(crawlDates.get(0));
		} else {
			crawlTimes.add(crawlDates.get(0));
			crawlTimes.add(crawlDates.get(1));
		}
		dto.setBrand(brandNum);
		dto.setNoBrand(nobrandNum);
		List<DashKeyword> dashKeywordList = dashboardKeywordRes.getkeywordRank(projectId, projectId, crawlTimes,
				StringUtils.toInteger(in.getEngineType()));
		if (ListUntils.isNull(dashKeywordList)) {
			logger.info("方法-getSearchRank()"+msg.getProcessStatus("NOT_ENGINETYPE_CRAWL").getRetMsg());
			return dto;

			//throw new BusinessException(msg.getProcessStatus("NOT_ENGINETYPE_CRAWL"));
		}
		List<KeywordRankRangeNumDto> keywordRankRangeNumList = new ArrayList<>();
		for (DashKeyword keyword : dashKeywordList) {
			KeywordRankRangeNumDto tdDto = new KeywordRankRangeNumDto();
			tdDto.setCompetitorId(projectId);
			tdDto.setCrawlDate(keyword.getCrawlDate());
			tdDto.setDashboardKeywordId(keyword.getDashboardKeywordId());
			tdDto.setEngineType(keyword.getEngineType());
			tdDto.setIsBrand("0");
			tdDto.setKeywordId(keyword.getKeywordId());
			tdDto.setProjectId(projectId);
			tdDto.setKeywordName(keywordDtoMap.get(keyword.getKeywordId().toString()) == null ? ""
					: keywordDtoMap.get(keyword.getKeywordId().toString()).getKeywordName());
			tdDto.setRank(keyword.getRank());
			keywordRankRangeNumList.add(tdDto);
		}
		dto.setUpNum(this.getUpOrDownByEngineType("0", keywordRankRangeNumList));
		dto.setDownNum(this.getUpOrDownByEngineType("1", keywordRankRangeNumList));
		Collections.sort(crawlTimes, CompareUtil.createComparator(-1));// 倒叙
		Date newDate = crawlTimes.get(0);
		// 获取最新一次 各个排名范围的关键词数量
		dto.setRangeList(this.getRandRangekeywordNumByCralwDate(keywordRankRangeNumList, newDate));
		dto.setUpdateDate(DateUtils.formatDate(newDate, "yyyyMMdd"));
		return dto;
	}

	/***
	 * 获取 某个项目 某个搜索引擎下的 上升 下降情况
	 * 
	 * @param type
	 *            0 上升 1 下降
	 * @param list
	 * @return
	 */
	public int getUpOrDownByEngineType(String type, List<KeywordRankRangeNumDto> list) {
		int num = 0;
		if (ListUntils.isNull(list)) {
			return 0;
		}
		// 按时间分割
		Map<String, List<KeywordRankRangeNumDto>> crawlMap = new HashMap<>();
		for (KeywordRankRangeNumDto keyword : list) {
			if (crawlMap.get(DateUtils.formatDate(keyword.getCrawlDate(), "yyyyMMdd")) == null) {
				List<KeywordRankRangeNumDto> temp = new ArrayList<>();
				temp.add(keyword);
				crawlMap.put(DateUtils.formatDate(keyword.getCrawlDate(), "yyyyMMdd"), temp);
			} else {
				crawlMap.get(DateUtils.formatDate(keyword.getCrawlDate(), "yyyyMMdd")).add(keyword);
			}
		}
		List<String> times = new ArrayList<>();
		for (String s : crawlMap.keySet()) {
			times.add(s);
		}
		if ("0".equals(type)) {// 上升
			if (times.size() == 2) {// 两次时间比较
				List<KeywordRankRangeNumDto> newList = null;
				List<KeywordRankRangeNumDto> oldList = null;
				if (DateUtils.parseDate(times.get(0)).before(DateUtils.parseDate(times.get(1)))) {// times.get(1)是新的
					newList = crawlMap.get(times.get(1));
					oldList = crawlMap.get(times.get(0));

				} else {
					newList = crawlMap.get(times.get(0));
					oldList = crawlMap.get(times.get(1));
				}
				for (KeywordRankRangeNumDto newk : newList) {
					for (KeywordRankRangeNumDto oldk : oldList) {
						if (newk.getKeywordId().intValue() == oldk.getKeywordId().intValue()) {
							if (newk.getRank() < oldk.getRank()) {// 排名上升
								num++;
							}
						}
					}
				}
			}
		} else {
			if (times.size() == 2) {// 两次时间比较
				List<KeywordRankRangeNumDto> newList = null;
				List<KeywordRankRangeNumDto> oldList = null;
				if (DateUtils.parseDate(times.get(0)).before(DateUtils.parseDate(times.get(1)))) {// times.get(1)是新的
					newList = crawlMap.get(times.get(1));
					oldList = crawlMap.get(times.get(0));

				} else {
					newList = crawlMap.get(times.get(0));
					oldList = crawlMap.get(times.get(1));
				}
				for (KeywordRankRangeNumDto newk : newList) {
					for (KeywordRankRangeNumDto oldk : oldList) {
						if (newk.getKeywordId().intValue() == oldk.getKeywordId().intValue()) {
							if (newk.getRank() > oldk.getRank()) {// 排名上升
								num++;
							}
						}
					}
				}
			}
		}
		return num;
	}

	/**
	 * 计算各个范围的数量
	 * 
	 * @param list
	 * @param crawlDate
	 * @return
	 */
	public List<Map<String, Object>> getRandRangekeywordNumByCralwDate(List<KeywordRankRangeNumDto> list,
			Date crawlDate) {
		List<Map<String, Object>> randRange = new ArrayList<>();
		Map<String, Map<String, Object>> randRangeMap = new HashMap<>();
		if (ListUntils.isNull(list)) {
			return null;
		}
		Map<String, Object> map = null;
		for (KeywordRankRangeNumDto keyword : list) {

			if (DateUtils.isSameDate(crawlDate, keyword.getCrawlDate())) {// 同一天

				if (1 <= keyword.getRank() && keyword.getRank() <= 3) {
					if (randRangeMap.get("1-3") == null) {
						map = new HashMap<>();
						map.put("brand", 0);
						map.put("noBrand", 0);
						randRangeMap.put("1-3", map);
					}
					if (keyword.getIsBrand().equals("1")) {// 平牌
						int brand = 0;
						brand = StringUtils.toInteger(randRangeMap.get("1-3").get("brand")).intValue() + 1;
						randRangeMap.get("1-3").put("brand", brand);
					} else {
						int nobrand = 0;
						nobrand = StringUtils.toInteger(randRangeMap.get("1-3").get("noBrand")).intValue() + 1;
						randRangeMap.get("1-3").put("noBrand", nobrand);
					}
					randRangeMap.get("1-3").put("range", "1-3");
				} else if (4 <= keyword.getRank() && keyword.getRank() <= 10) {
					if (randRangeMap.get("4-10") == null) {
						map = new HashMap<>();
						map.put("brand", 0);
						map.put("noBrand", 0);
						randRangeMap.put("4-10", map);
					}
					if (keyword.getIsBrand().equals("1")) {// 平牌
						int brand = 0;
						brand = StringUtils.toInteger(randRangeMap.get("4-10").get("brand")).intValue() + 1;
						randRangeMap.get("4-10").put("brand", brand);
					} else {
						int nobrand = 0;
						nobrand = StringUtils.toInteger(randRangeMap.get("4-10").get("noBrand")).intValue() + 1;
						randRangeMap.get("4-10").put("noBrand", nobrand);
					}
					randRangeMap.get("4-10").put("range", "4-10");
				} else if (11 <= keyword.getRank() && keyword.getRank() <= 20) {
					if (randRangeMap.get("11-20") == null) {
						map = new HashMap<>();
						map.put("brand", 0);
						map.put("noBrand", 0);
						randRangeMap.put("11-20", map);
					}
					if (keyword.getIsBrand().equals("1")) {// 平牌
						int brand = 0;
						brand = StringUtils.toInteger(randRangeMap.get("11-20").get("brand")).intValue() + 1;
						randRangeMap.get("11-20").put("brand", brand);
					} else {
						int nobrand = 0;
						nobrand = StringUtils.toInteger(randRangeMap.get("11-20").get("noBrand")).intValue() + 1;
						randRangeMap.get("11-20").put("noBrand", nobrand);
					}
					randRangeMap.get("11-20").put("range", "11-20");
				} else if (21 <= keyword.getRank() && keyword.getRank() <= 30) {
					if (randRangeMap.get("21-30") == null) {
						map = new HashMap<>();
						map.put("brand", 0);
						map.put("noBrand", 0);
						randRangeMap.put("21-30", map);
					}
					if (keyword.getIsBrand().equals("1")) {// 平牌
						int brand = 0;
						brand = StringUtils.toInteger(randRangeMap.get("21-30").get("brand")).intValue() + 1;
						randRangeMap.get("21-30").put("brand", brand);
					} else {
						int nobrand = 0;
						nobrand = StringUtils.toInteger(randRangeMap.get("21-30").get("noBrand")).intValue() + 1;
						randRangeMap.get("21-30").put("noBrand", nobrand);
					}
					randRangeMap.get("21-30").put("range", "21-30");
				}
			}
		}
		for (Map<String, Object> map2 : randRangeMap.values()) {
			randRange.add(map2);
		}
		return randRange;
	}

	@Override
	public OutSearchQuestionDto getQuestionByWeekOrM(InSearchQuestionDto in) throws Exception {
		OutSearchQuestionDto dto = new OutSearchQuestionDto();
		Integer projectId = StringUtils.toInteger(in.getProjectId());
		List<Date> tempDates = new ArrayList<>();// 需要补齐的时间点
		// 检测当前项目 根据id取项目
		ProjectDto projectDto = projectService.getProjectInfoById(in.getProjectId());
		projectDto = new ProjectDto();
		projectDto.setProjectId("1");
		projectDto.setProjectName("asdas");
		if (null == projectDto) {
			throw new BusinessException(msg.getProcessStatus("PROJECT_NOT_EXIST"));
		}
		List<Date> crawlDates = timeApi.getTimesByIsWeek(StringUtils.toInteger(projectDto.getProjectId()),
				in.getIsWeek(), 8);
		if (ListUntils.isNull(crawlDates)) {
			throw new BusinessException(msg.getProcessStatus("NOT_CRAWL"));
		}

		return null;
	}

	@Override
	public OutExtLinkDomainsDto getExtLinkDomains(InExtLinkDto in) throws Exception {
		OutExtLinkDomainsDto dto = new OutExtLinkDomainsDto();
		Integer projectId = StringUtils.toInteger(in.getProjectId());
		List<Date> tempDates = new ArrayList<>();// 需要补齐的时间点
		// 检测当前项目 根据id取项目
		OutProComKwInfoDto projectDto = projectService.getProComKwInfoById(in.getProjectId().toString());

		if (null == projectDto) {
			throw new BusinessException(msg.getProcessStatus("PROJECT_NOT_EXIST"));
		}
		// 获取竞品名称
		Map<String, OutProCompetitorDto> competitorMap = new HashMap<>();
		if (!ListUntils.isNull(projectDto.getCompetitors())) {
			competitorMap = ListUntils.listToMap(projectDto.getCompetitors(), "Id");
		}
		List<Date> crawlDates = timeApi.getTimesByIsWeek(StringUtils.toInteger(projectDto.getProjectId()),
				in.getIsWeek(), 2);
		if (ListUntils.isNull(crawlDates)) {
			logger.info("方法-getExtLinkDomains()"+msg.getProcessStatus("NOT_CRAWL").getRetMsg());
			return dto;

			//throw new BusinessException(msg.getProcessStatus("NOT_CRAWL"));
		}
		List<Date> crawlTimes = new ArrayList<>();
		Collections.sort(crawlDates, CompareUtil.createComparator(-1));// 倒叙
		if (crawlDates.size() < 2) {
			crawlTimes.add(crawlDates.get(0));
		} else {
			crawlTimes.add(crawlDates.get(0));
			crawlTimes.add(crawlDates.get(1));
		}
		Collections.sort(crawlTimes, CompareUtil.createComparator(-1));// 倒叙
		// 项目本身的
		List<ProLinksView> proLinksViewList = proLinkViewRes.getExtLinkView(projectId, crawlTimes);
		if (ListUntils.isNull(proLinksViewList)) {
			logger.info("方法-getExtLinkDomains()"+msg.getProcessStatus("NOT_CRAWL").getRetMsg());
			return dto;
			//throw new BusinessException(msg.getProcessStatus("NOT_CRAWL"));
		}
		if (proLinksViewList.size() == 2) {// 两次都有
			Collections.sort(proLinksViewList, CompareUtil.createComparator(-1, "crawlDate"));// 倒叙
			dto.setDomainsNum(proLinksViewList.get(0).getRefDomains());
			dto.setUpDate(DateUtils.formatDate(proLinksViewList.get(0).getCrawlDate(), "yyyyMMddHHmmss"));
			dto.setUpOrDown(proLinksViewList.get(0).getRefDomains() - proLinksViewList.get(1).getRefDomains());
		} else {
			dto.setDomainsNum(proLinksViewList.get(0).getRefDomains());
			dto.setUpDate(DateUtils.formatDate(proLinksViewList.get(0).getCrawlDate(), "yyyyMMddHHmmss"));
			dto.setUpOrDown(0L);
		}
		// 竞品的
		List<ProLinksView> comLinksViewList = proLinkViewRes.getComExtLinkView(projectId, crawlTimes.get(0));
		if (!ListUntils.isNull(comLinksViewList)) {
			Map<String, Object> map = null;
			for (ProLinksView p : comLinksViewList) {
				map = new HashMap<String, Object>();
				map.put("competitorId", p.getCompetitorId().toString());
				map.put("competitorName", competitorMap.get(p.getCompetitorId().toString()) == null ? ""
						: competitorMap.get(p.getCompetitorId().toString()).getName());
				map.put("num", p.getRefDomains());
				dto.getComDomainsList().add(map);
			}

		}
		return dto;
	}

	@Override
	public OutExtLinkDto getExtLink(InExtLinkDto in) throws Exception {
		OutExtLinkDto dto = new OutExtLinkDto();
		Integer projectId = StringUtils.toInteger(in.getProjectId());
		List<Date> tempDates = new ArrayList<>();// 需要补齐的时间点
		// 检测当前项目 根据id取项目
		OutProComKwInfoDto projectDto = projectService.getProComKwInfoById(in.getProjectId().toString());

		if (null == projectDto) {
			throw new BusinessException(msg.getProcessStatus("PROJECT_NOT_EXIST"));
		}
		Map<String, OutProCompetitorDto> competitorMap = new HashMap<>();
		if (!ListUntils.isNull(projectDto.getCompetitors())) {
			competitorMap = ListUntils.listToMap(projectDto.getCompetitors(), "Id");
		}
		List<Date> crawlDates = timeApi.getTimesByIsWeek(StringUtils.toInteger(projectDto.getProjectId()),
				in.getIsWeek(), 2);
		if (ListUntils.isNull(crawlDates)) {
			logger.info("方法-getExtLink()"+msg.getProcessStatus("NOT_CRAWL").getRetMsg());
			return dto;
			//throw new BusinessException(msg.getProcessStatus("NOT_CRAWL"));
		}
		List<Date> crawlTimes = new ArrayList<>();
		if (ListUntils.isNull(crawlDates)) {
			throw new BusinessException(msg.getProcessStatus("NOT_CRAWL"));
		}
		Collections.sort(crawlDates, CompareUtil.createComparator(-1));// 倒叙
		if (crawlDates.size() < 2) {
			crawlTimes.add(crawlDates.get(0));
		} else {
			crawlTimes.add(crawlDates.get(0));
			crawlTimes.add(crawlDates.get(1));
		}
		Collections.sort(crawlTimes, CompareUtil.createComparator(-1));// 倒叙
		// 项目本身的
		List<ProLinksView> proLinksViewList = proLinkViewRes.getExtLinkView(projectId, crawlTimes);
		if (ListUntils.isNull(proLinksViewList)) {
			logger.info("方法-getExtLink()"+msg.getProcessStatus("NOT_CRAWL").getRetMsg());
			return dto;
			//throw new BusinessException(msg.getProcessStatus("NOT_CRAWL"));
		}
		if (proLinksViewList.size() == 2) {// 两次都有
			Collections.sort(proLinksViewList, CompareUtil.createComparator(-1, "crawlDate"));// 倒叙
			dto.setLinkNum(proLinksViewList.get(0).getExtBackLinks());
			dto.setUpDate(DateUtils.formatDate(proLinksViewList.get(0).getCrawlDate(), "yyyyMMddHHmmss"));
			dto.setUpOrDown(proLinksViewList.get(0).getExtBackLinks() - proLinksViewList.get(1).getExtBackLinks());
		} else {
			dto.setLinkNum(proLinksViewList.get(0).getExtBackLinks());
			dto.setUpDate(DateUtils.formatDate(proLinksViewList.get(0).getCrawlDate(), "yyyyMMddHHmmss"));
			dto.setUpOrDown(0L);
		}
		// 竞品的
		List<ProLinksView> comLinksViewList = proLinkViewRes.getComExtLinkView(projectId, crawlTimes.get(0));
		if (!ListUntils.isNull(comLinksViewList)) {
			Map<String, Object> map = null;
			for (ProLinksView p : comLinksViewList) {
				map = new HashMap<String, Object>();
				map.put("competitorId", p.getCompetitorId().toString());
				map.put("competitorName", competitorMap.get(p.getCompetitorId().toString()) == null ? ""
						: competitorMap.get(p.getCompetitorId().toString()).getName());
				map.put("num", p.getExtBackLinks());
				dto.getComLinkList().add(map);
			}

		}
		return dto;
	}

	
}
