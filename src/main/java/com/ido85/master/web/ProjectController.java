package com.ido85.master.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ido85.config.properties.BussinessMsgCodeProperties;
import com.ido85.config.properties.WiConstantsProperties;
import com.ido85.frame.common.Page;
import com.ido85.frame.common.exceptions.BusinessException;
import com.ido85.frame.common.restful.Resource;
import com.ido85.frame.common.restful.Resources;
import com.ido85.frame.common.utils.BeanMapUtil;
import com.ido85.frame.common.utils.DateUtils;
import com.ido85.frame.common.utils.JsonUtil;
import com.ido85.frame.common.utils.ListUntils;
import com.ido85.frame.common.utils.StringUtils;
import com.ido85.frame.web.rest.security.Constants;
import com.ido85.frame.web.rest.utils.CacheUtils;
import com.ido85.frame.web.rest.utils.RestUserUtils;
import com.ido85.master.common.MasterSequenceUtil;
import com.ido85.master.keyword.domain.Group;
import com.ido85.master.keyword.domain.Keyword;
import com.ido85.master.packages.domain.TenantPackage;
import com.ido85.master.packages.domain.TenantPackageElementsRel;
import com.ido85.master.project.application.ProjectApplication;
import com.ido85.master.project.domain.Competitor;
import com.ido85.master.project.domain.Project;
import com.ido85.master.project.dto.CheckUrlDto;
import com.ido85.master.project.dto.CheckUrlResultDto;
import com.ido85.master.project.dto.CompetitorDto;
import com.ido85.master.project.dto.CrawlProject;
import com.ido85.master.project.dto.InProjectDto;
import com.ido85.master.project.dto.InUpdateProjectDto;
import com.ido85.master.project.dto.OutCheckProjectDto;
import com.ido85.master.project.dto.OutProComKwInfoDto;
import com.ido85.master.project.dto.OutProCompetitorDto;
import com.ido85.master.project.dto.OutProjectBaseInfoDto;
import com.ido85.master.project.dto.OutProjectManageDto;
import com.ido85.master.project.dto.ProjectPageDto;
import com.ido85.master.project.dto.UrlDto;
import com.ido85.services.keyword.KeywordApi;
import com.ido85.services.packages.PackageApi;
import com.ido85.services.project.ProjectApi;
import com.ido85.services.project.dto.ProjectCrawlDto;

@RestController
public class ProjectController {
	@Inject
	private ProjectApplication projectApp;
	@Inject
	private BussinessMsgCodeProperties msg;
	@Inject
	private PackageApi packageService;
	@Inject
	private KeywordApi keywordService;
	@Inject
	private ProjectApi projectService;
	@Inject
	private MasterSequenceUtil masterSequenceUtil;
	@Inject
	private WiConstantsProperties wiConstantsProperties;

	/****
	 * 添加项目
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/seo/addProject")
	public Resource<Map<String, Object>> addProject(@Valid @RequestBody InProjectDto dto)
			throws BusinessException, Exception {
		if (null == dto) {
			return new Resource<Map<String, Object>>(new HashMap<String, Object>(),
					msg.getProcessStatus("PARAM_IS_NULL"));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map = BeanMapUtil.transBean2Map(dto);
		if (map.isEmpty()) {
			return new Resource<Map<String, Object>>(new HashMap<String, Object>(),
					msg.getProcessStatus("PARAM_IS_NULL"));
		}
		map.put("appType", "0");
		/* 查询套餐当前租户的实例规格 */
		String tenantId = RestUserUtils.getTencentId().toString();
		System.out.println("--------------------------addProject---------------tenantId---------" + tenantId);
		List<TenantPackage> tenanPackageList = packageService.getPackageExampleListByTenantId(tenantId,
				map.get("appType").toString());
		if (null == tenanPackageList || tenanPackageList.size() <= 0) {
			throw new BusinessException(msg.getProcessStatus("PACKAGE_NOT_EXIST"));
		}
		if (tenanPackageList.size() > 1) {
			return new Resource<Map<String, Object>>(new HashMap<String, Object>(),
					msg.getProcessStatus("PACKAGE_ERROR"));// 此租户含有多个此应用下的套餐
		}
		TenantPackage tenanPackage = tenanPackageList.get(0);// 当前的套餐实例
		map.put("tenanPackage", tenanPackage);
		String proNumelementId = wiConstantsProperties.getValue("proNum").toString();// 项目数
		String comNumelementId = wiConstantsProperties.getValue("comNum").toString();// 竞品数
		String keyordNumelementId = wiConstantsProperties.getValue("keywordNum").toString();// 关键词书
		int proNum = 0;// 规定项目数
		int comNum = 0;// 竞品数
		int keyordNum = 0;// 关键词数
		// 规格
		List<TenantPackageElementsRel> tenantPackageElementsRelList = tenanPackage.getTenantPackageElementsRelList();
		if (null == tenantPackageElementsRelList || tenantPackageElementsRelList.size() <= 0) {
			throw new BusinessException(msg.getProcessStatus("PACKAGE_NOT_RULE"));
		}
		for (TenantPackageElementsRel rel : tenantPackageElementsRelList) {
			if (proNumelementId.equals(rel.getElements().getElementId().toString())) {// 项目数
				if ("1".equals(rel.getValidType())) {// 取最大值
					proNum = rel.getUsedMax();
				}
			}
			if (comNumelementId.equals(rel.getElements().getElementId().toString())) {// 竞品书
				if ("1".equals(rel.getValidType())) {// 取最大值
					comNum = rel.getUsedMax();
				}
			}
			if (keyordNumelementId.equals(rel.getElements().getElementId().toString())) {// 关键词个数
				if ("1".equals(rel.getValidType())) {// 取最大值
					keyordNum = rel.getUsedMax();
				}
			}
		}
		// 判断项目数
		int num = projectApp.getProjectNumBy(tenanPackage.getTenantPackageId().toString(), "1");// 已有的
		if (num >= proNum) {
			return new Resource<Map<String, Object>>(msg.getProcessStatus("PROJECT_NOT_ADD"));// 不能添加
		}
		// 判断精品书
		List<Map<String, Object>> competitors = (List<Map<String, Object>>) map.get("competitors");
		if (null != competitors && competitors.size() > 0) {
			// 判断精品
			if (competitors.size() > comNum) {
				return new Resource<Map<String, Object>>(msg.getProcessStatus("COM_NOT_ADD"));// 不能添加
			}
		}
		// 判断关键此
		List<String> keyWordListTmp = (List<String>) map.get("keywords");
		List<String> keyWordList = new ArrayList<String>();
		if (null == keyWordListTmp || keyWordListTmp.size() <= 0) {
		} else {
			// 去除字符串前后的空格
			for (String str : keyWordListTmp) {
				str = StringUtils.trim(str);
			}
			// 去重
			keyWordList = ListUntils.duplicateRemoval(keyWordListTmp);
		}
		List<Keyword> newKeyWordList = new ArrayList<Keyword>();// 需要添加的字典表的
		List<Keyword> KeyWordListexist = new ArrayList<Keyword>();// 字典表已存在的
		// List<ProKeyword> prokeywords = new ArrayList<ProKeyword>();

		if (null != keyWordList && keyWordList.size() > 0) {
			if (keyWordList.size() > keyordNum) {
				return new Resource<Map<String, Object>>(msg.getProcessStatus("KEYWORD_NUM_ERROR"));// 不能添加
			}
			for (String str : keyWordList) {
				if (CacheUtils.get(Constants.KEYWORD_CACHE, str) == null) {// 缓存中没有找到
																			// 就是新的
					Keyword k = new Keyword();
					Integer keywordId = masterSequenceUtil.getKeywordSeq();
					k.setKeywordId(keywordId);
					k.setKeywordName(str);
					k.setCreateBy(RestUserUtils.getUserInfo().getUserId());
					k.setCreateDate(new Date());
					k.setDelFlag("0");
					k.setEndDate(new Date());
					k.setUpdateBy(RestUserUtils.getUserInfo().getUserId());
					k.setUpdateDate(new Date());
					newKeyWordList.add(k);
				} else {// 缓存中已经存在的
					Keyword k = (Keyword) CacheUtils.get(Constants.KEYWORD_CACHE, str);
					// keyword.delFlag 此字段暂时没有意义,程序中并没有修改它的地方
					// 所以此处并不做merge保存
					k.setDelFlag("0");
					// k.setEndDate(new Date());
					k.setUpdateBy(RestUserUtils.getUserInfo().getUserId());
					k.setUpdateDate(new Date());
					KeyWordListexist.add(k);
				}
			}
			// 保存关键词字典
			keywordService.batchSaveKeywords(newKeyWordList);
			List<Keyword> tmp = new ArrayList<Keyword>();// 所有需要添加关系的关键词
			tmp.addAll(KeyWordListexist);
			tmp.addAll(newKeyWordList);
			// 将所有新的或者已存在的关键词,放入map,以便添加关键词与项目关系
			map.put("keywordDomains", tmp);
		}
		return projectApp.addProject(map);
	}

	/***
	 * 添加项目时检测
	 * 
	 * @param param
	 *            competitorUrl 竞品url name 项目名称 url 项目url
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/seo/checkProject")
	public Resource<OutCheckProjectDto> checkProject(@Valid @RequestBody String param)
			throws BusinessException, Exception {
		OutCheckProjectDto o = new OutCheckProjectDto();
		if (StringUtils.isNull(param)) {
			return new Resource<OutCheckProjectDto>(o, msg.getProcessStatus("PARAM_IS_NULL"));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map = (Map<String, Object>) JsonUtil.jsonToMap(param);
		if (null == map || map.isEmpty()) {
			return new Resource<OutCheckProjectDto>(o, msg.getProcessStatus("PARAM_IS_NULL"));
		}
		o = projectApp.checkProject(map);
		return new Resource<OutCheckProjectDto>(o, msg.getProcessStatus("COMMON_SUCCESS"));
	}

	/***
	 * 检测url
	 * @param param
	 * @return
	 */
	@RequestMapping("/seo/checkUrl")
	public Resource<CheckUrlResultDto> checkUrl(@Valid @RequestBody CheckUrlDto checkUrlDto) throws Exception{
		CheckUrlResultDto dto = new CheckUrlResultDto();
		dto = projectApp.checkUrl(checkUrlDto);
		return new Resource<CheckUrlResultDto>(dto,msg.getProcessStatus("COMMON_SUCCESS"));
	}

	/***
	 * 项目管理 + 我的项目列表
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/seo/getProjectList")
	public Resource<OutProjectManageDto> getProjectList(@Valid @RequestBody Map<String, Object> param)
			throws BusinessException, Exception {
		OutProjectManageDto out = new OutProjectManageDto();
		System.out.println(new Resource<OutProjectManageDto>(out, msg.getProcessStatus("REGISTER_ERROR")));
		if (null == param || param.isEmpty()) {
			return new Resource<OutProjectManageDto>(out, msg.getProcessStatus("PARAM_ERROR"));
		}

		Page<ProjectPageDto> page = null;
		page = new Page<ProjectPageDto>(
				param.get("pageNo") == null ? 0 : Integer.parseInt(param.get("pageNo").toString()),
				param.get("pageSize") == null ? 10 : Integer.parseInt(param.get("pageSize").toString()));

		param.put("appType", "0");

		String tenantId = RestUserUtils.getTencentId().toString();
		List<TenantPackage> tenanPackageList = packageService.getPackageExampleListByTenantId(tenantId,
				param.get("appType").toString());

		if (null == tenanPackageList || tenanPackageList.size() <= 0) {
			throw new BusinessException(msg.getProcessStatus("PACKAGE_NOT_EXIST"));

		}
		if (tenanPackageList.size() > 1) {
			// 此租户含有多个此应用下的套餐
			return new Resource<OutProjectManageDto>(msg.getProcessStatus("PACKAGE_MORE"));
		}
		TenantPackage tenanPackage = tenanPackageList.get(0);// 当前的套餐实例小

		Object num = projectApp.getActiveNum(tenanPackage.getTenantPackageId().toString());
		if (null != param.get("activeFlag") && !"".equals(param.get("activeFlag").toString())) {
			if (!("0".equals(param.get("activeFlag").toString()) || "1".equals(param.get("activeFlag").toString()))) {
				return new Resource<OutProjectManageDto>(out, msg.getProcessStatus("PARAM_ERROR"));
			}
		}

		page = projectApp.getProjectList(page, param);

		out.setActiveProNum(num == null ? 0 : Integer.parseInt(num.toString()));
		out.setCount((int) page.getCount());
		if (null == param.get("activeFlag") || "".equals(param.get("activeFlag").toString())) {
			out.setPageNo(page.getPageNo());
			out.setPageSize(page.getPageSize());
		} else if ("0".equals(param.get("activeFlag").toString())) {

			out.setPageNo(page.getPageNo());
			out.setPageSize(page.getPageSize());
		}
		// 将输出的项目信息放入dto
		out.setProjectInfos(page.getList());
		if ((num == null ? 0 : Integer.parseInt(num.toString())) > 0) {
			out.setNoProject("1");
		} else {
			out.setNoProject("0");
		}

		return new Resource<OutProjectManageDto>(out, msg.getProcessStatus("COMMON_SUCCESS"));
	}

	/***
	 * 设置活跃项目归档、删除、激活接口
	 * 
	 * @param param
	 *            : projectId activeFlag :0：设置项目归档，1：设置项目激活，2：设置项目删除
	 * @return
	 */
	@RequestMapping("/seo/updateProjectState")
	public Resource<String> updateProjectState(@Valid @RequestBody Map<String, Object> param)
			throws BusinessException, Exception {

		if (null == param || param.isEmpty()) {
			return new Resource<String>("", msg.getProcessStatus("PARAM_IS_NULL"));
		}
		if (null == param.get("activeFlag") || null == param.get("projectId")) {
			return new Resource<String>("", msg.getProcessStatus("PARAM_IS_NULL"));
		}
		if (!(param.get("activeFlag").equals("0") || param.get("activeFlag").equals("1")
				|| param.get("activeFlag").equals("2"))) {
			return new Resource<String>("", msg.getProcessStatus("PARAM_ERROR"));
		}
		param.put("appType", "0");
		String str = projectApp.updateProjectState(param);
		if (str.equals("1")) {
			return new Resource<String>("", msg.getProcessStatus("PROJECT_ERROR"));
		} else if (str.equals("0")) {
			return new Resource<String>("", msg.getProcessStatus("COMMON_SUCCESS"));
		} else if (str.equals("2")) {
			return new Resource<String>("", msg.getProcessStatus("PROJECT_STATE"));
		} else if (str.equals("3")) {// 未发现套餐
			return new Resource<String>("", msg.getProcessStatus("PACKAGE_NOT_EXIST"));
		} else if (str.equals("4")) {
			return new Resource<String>("", msg.getProcessStatus("PROJECT_NOT_ADD"));
		}
		return null;

	}

	/**
	 * 获取项目基本信息接口 数据获取
	 * 
	 * @param request
	 * @param projectId
	 * @return
	 */
	@RequestMapping("/seo/getProjectBaseInfo")
	public Resource<OutProjectBaseInfoDto> getProjectBaseInfo(HttpServletRequest request,
			@Valid @RequestBody String projectId) throws BusinessException, Exception {
		// 获取项目信息，同时作为检验项目id是否正确
		projectId = JsonUtil.getJsonValue(projectId, "projectId") + "";
		if (null == projectId || "".equals(projectId)) {
			return new Resource<OutProjectBaseInfoDto>(msg.getProcessStatus("PARAM_IS_NULL"));
		}

		OutProjectBaseInfoDto out = projectApp.getProjectInfoById(projectId);
		if (null != out) {
			List<Competitor> list = projectApp.getCompetitorsInfoByProjectId(projectId);
			if (null != list && !"".equals(list) && list.size() > 0) {
				List<CompetitorDto> competitorDtos = new ArrayList<CompetitorDto>();
				CompetitorDto competitorDto = null;
				for (int i = 0; i < list.size(); i++) {
					competitorDto = new CompetitorDto(list.get(i).getCompetitorId().toString(),
							list.get(i).getCompetitorName(), list.get(i).getCompetitorUrl());
					competitorDtos.add(competitorDto);
				}
				out.setCompetitors(competitorDtos);
			}

			List<Group> groups = keywordService.getAllProjectGroups(projectId);
			if (null != groups && groups.size() > 0) {
				List<String> groupList = new ArrayList<String>();
				for (int i = 0, len = groups.size(); i < len; i++) {
					groupList.add(groups.get(i).getGroupId().toString());
				}
				out.setGroups(groupList);
			}

			out.setKeywordNum(keywordService.getProjectKeywordNum(projectId));
			Project pro = projectApp.getProjectById(projectId);
			if (pro.getCrawlDate() != null) {
				out.setUpdateDate(DateUtils.formatDate(pro.getCrawlDate(), "yyyyMMddHHmmss"));
			}
			// 获取上一个月最后一次更新日期，此处待定
			// out.setPreMonLastUpdate(comm.getPreMonLastUpdate(new Date()));
			return new Resource<OutProjectBaseInfoDto>(out);
		}

		return new Resource<OutProjectBaseInfoDto>(msg.getProcessStatus("PARAM_ERROR"));
	}

	/**
	 * 修改项目基本信息接口
	 * 
	 * @param request
	 * @param in
	 *            competitors 项目竞品子节点 array<object> 非必传 id 竞品id string name 竞品名称
	 *            string url 竞品url string projectId 项目id string projectName 项目名称
	 *            string 非必传 receiveEmail 是否接收邮件 string 非必传，0：否，1：是
	 * @return
	 * @throws BusinessException,Exception
	 */
	@RequestMapping("/seo/updateProjectInfo")
	public Resource<String> updateProjectBaseInfo(HttpServletRequest request, @Valid @RequestBody InUpdateProjectDto in,
			Model model) throws BusinessException, Exception {
		// 校验dto数据
		if (in == null) {
			return new Resource<String>(msg.getProcessStatus("PARAM_ERROR"));
		}

		if (StringUtils.isBlank(in.getProjectId())) {
			return new Resource<String>(msg.getProcessStatus("PARAM_ERROR"));
		}

		// 修改项目基本信息
		if (projectApp.updateProjectInfo(in)) {
			return new Resource<String>("");
		}
		return new Resource<String>(msg.getProcessStatus("COMMON_ERROR"));
	}

	/**
	 * 删除项目基本信息接口--需求修改，放弃此功能，此接口暂时放弃
	 * 
	 * @param request
	 * @param in
	 *            competitorId 要删除的项目竞品id array<string> projectId 项目id string
	 * @return
	 */
	public Resource<String> deleteProjectBaseInfo(HttpServletRequest request, @RequestBody Map<String, Object> in) {
		// 校验入参
		if (null == in || "".equals(in) || !in.containsKey("projectId")) {
			return new Resource<String>(msg.getProcessStatus("PARAM_ERROR"));
		}

		// 删除项目基本信息

		return null;
	}

	/**
	 * 校验租户是否有归档项目，有返回1，没有返回0
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/seo/checkArchiveProject")
	public Resource<Map<String, String>> checkArchiveProject(HttpServletRequest request) {
		try {
			// 校验用户是否有归档项目
			String res = projectApp.checkArchive("0");
			if (null != res && ("0".equals(res) || "1".equals(res))) {
				Map<String, String> resMap = new HashMap<String, String>();
				resMap.put("flag", res);
				return new Resource<Map<String, String>>(resMap);
			}
			return new Resource<Map<String, String>>(msg.getProcessStatus("ARCHIVE_ERROR"));
		} catch (Exception e) {
			e.printStackTrace();
			return new Resource<Map<String, String>>(msg.getProcessStatus("ARCHIVE_ERROR"));
		}
	}

	/****
	 * 删除竞品
	 * 
	 * @param param
	 *            projectId competitorId 集合
	 * @return
	 */
	@RequestMapping("/seo/delCompetitor")
	public Resource<String> delCompetitor(@Valid @RequestBody Map<String, Object> param)
			throws BusinessException, Exception {
		if (null == param || param.isEmpty()) {
			return new Resource<String>("", msg.getProcessStatus("PARAM_IS_NULL"));
		}
		if (null == param.get("competitorId") || null == param.get("projectId")) {
			return new Resource<String>("", msg.getProcessStatus("PARAM_IS_NULL"));
		}
		String string = projectApp.delCompetitor(param);
		if ("0".equals(string)) {
			return new Resource<String>(msg.getProcessStatus("COMMON_SUCCESS"));
		} else {
			return new Resource<String>(msg.getProcessStatus("COMMON_ERROR"));
		}
	}

	/****
	 * 添加竞品
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping("/seo/addCompetitor")
	public Resource<String> addCompetitor(@Valid @RequestBody Map<String, Object> param)
			throws BusinessException, Exception {

		if (null == param || param.isEmpty()) {
			return new Resource<String>("", msg.getProcessStatus("PARAM_IS_NULL"));
		}
		if (null == param.get("projectId")) {
			return new Resource<String>("", msg.getProcessStatus("PARAM_IS_NULL"));
		}
		if (null == param.get("name") || "".equals(param.get("name").toString()) || null == param.get("url")
				|| "".equals(param.get("url").toString())) {
			return new Resource<String>("", msg.getProcessStatus("PARAM_IS_NULL"));
		}
		param.put("appType", "0");
		String string = projectApp.addCompetitor(param);
		if ("0".equals(string)) {
			return new Resource<String>("", msg.getProcessStatus("COMMON_SUCCESS"));
		} else {
			return new Resource<String>("", msg.getProcessStatus("COMMON_ERROR"));
		}
	}

	/**
	 * 接口详情 (id: 995) 接口名称 通过项目id获取项目所有竞品（包含项目本身） 请求类型 get 请求Url
	 * /crawl/getProjectInfoById
	 * 
	 * @return
	 * @throws BusinessException
	 * @throws Exception
	 */
	@RequestMapping("/crawl/getProjectInfoById/{projectId}")
	public Resource<List<CrawlProject>> getProjectInfoById(@PathVariable Integer projectId) throws Exception {
		if (null == projectId || projectId <= 0) {
			return new Resource<List<CrawlProject>>(msg.getProcessStatus("PARAM_ERROR"));
		}
		List<CrawlProject> out = new ArrayList<CrawlProject>();

		// 获取所有的要爬取的项目id,然后根据项目id去获取所有的套餐
		OutProComKwInfoDto projectInfo = projectApp.getProComKwInfoById(projectId);

		CrawlProject item = new CrawlProject();
		item.setIsSubdomain(projectInfo.getSubdomain());
		item.setProjectId(projectId);
		item.setProjectUrl(projectInfo.getProjectUrl());
		item.setTenantId(projectInfo.getTenantId());
		item.setIsCompetitor(false);
		out.add(item);

		// 竞品
		List<OutProCompetitorDto> competitors = projectInfo.getCompetitors();
		if (!ListUntils.isNull(competitors)) {
			for (OutProCompetitorDto outProCompetitorDto : competitors) {
				item = new CrawlProject();
				item.setIsCompetitor(true);
				item.setCompetitorId(outProCompetitorDto.getId());
				item.setProjectId(projectId);
				item.setTenantId(projectInfo.getTenantId());
				item.setProjectUrl(outProCompetitorDto.getUrl());
				
				out.add(item);
			}
		}

		return new Resource<List<CrawlProject>>(out);
	}

	/**
	 * 接口详情 (id: 994) Mock数据 接口名称 通过项目id获取渠道list 请求类型 get 请求Url
	 * /crawl/getEnginesByProjectId
	 * 
	 * @return
	 * @throws BusinessException
	 * @throws Exception
	 */
	@RequestMapping("/crawl/getEnginesByProjectId/{projectId}")
	public Resource<List<String>> getEnginesByProjectId(@PathVariable Integer projectId) throws Exception {
		if (null == projectId || projectId <= 0) {
			return new Resource<List<String>>(msg.getProcessStatus("PARAM_ERROR"));
		}
		List<String> out = new ArrayList<String>();

		// 获取所有的要爬取的项目id,然后根据项目id去获取所有的套餐
		OutProjectBaseInfoDto projectBaseInfoDto = projectApp.getProjectInfoById("" + projectId);

		// 根据租户id获取每个项目所对应的套餐的渠道
		List<TenantPackage> packages = packageService
				.getPackageExampleListByTenantId(projectBaseInfoDto.getTenantId() + "", "0");

		List<Map<String, Object>> temp = packageService.getScope(packages.get(0).getTenantPackageElementsRelList());
		String engine = packageService.getEngine(temp);
		if (StringUtils.isNotBlank(engine)) {
			// 构造返回结果
			for (String str : engine.split("\\,")) {
				out.add(str);
			}
		}

		return new Resource<List<String>>(out);
	}

	@RequestMapping("/crawl/getAllCrawlPro")
	public Resources<ProjectCrawlDto> getAllCralwPro() throws Exception {
		List<ProjectCrawlDto> list = projectService.getAllCralwPro();
		return new Resources<ProjectCrawlDto>(list, msg.getProcessStatus("COMMON_SUCCESS"));
	}
	
	@RequestMapping("/crawl/autoArchive")
	public void autoArchive(){
		projectApp.autoArchive();
	}
}
