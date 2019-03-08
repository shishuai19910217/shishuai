package com.ido85.master.web;

import java.util.ArrayList;
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
import com.ido85.frame.common.utils.ListUntils;
import com.ido85.frame.common.utils.StringUtils;
import com.ido85.frame.web.rest.utils.RestUserUtils;
import com.ido85.master.common.MasterSequenceUtil;
import com.ido85.master.keyword.application.KeywordApplication;
import com.ido85.master.keyword.domain.Group;
import com.ido85.master.keyword.domain.Keyword;
import com.ido85.master.keyword.domain.ProKeyword;
import com.ido85.master.keyword.dto.AddGroupParam;
import com.ido85.master.keyword.dto.FuzzyKeywordDto;
import com.ido85.master.keyword.dto.InAddKeywordDto;
import com.ido85.master.keyword.dto.InDropListDto;
import com.ido85.master.keyword.dto.InKeywordDto;
import com.ido85.master.keyword.dto.InProjectKeywordsDto;
import com.ido85.master.keyword.dto.OutDropListDto;
import com.ido85.master.keyword.dto.OutKeywordDto;
import com.ido85.master.keyword.dto.OutProjectKeywordDto;
import com.ido85.master.packages.domain.TenantPackage;
import com.ido85.master.project.dto.ProjectDto;
import com.ido85.services.packages.PackageApi;
import com.ido85.services.project.ProjectApi;
import com.ido85.services.project.dto.ProjectCrawlDto;

/**
 * 关键词对其他应用提供接口
 * 
 * @author fire
 */
@RestController
public class KeywordController {
	@Inject
	private KeywordApplication keywordApp;
	@Inject
	private ProjectApi projectService;
	// @Inject
	// private StatisticsApi statisticsService;
	@Inject
	private PackageApi packageService;
	// @Inject
	// private CommonApi common;
	@Inject
	private BussinessMsgCodeProperties prop;
	@Inject
	private WiConstantsProperties wiConstantsProperties;
	@Inject
	private MasterSequenceUtil masterSequenceUtil;

	/**
	 * 关键词添加并分组
	 * @param request
	 * @param inKeyword
	 * @param model
	 * @return
	 */
	@RequestMapping("/seo/addKeywordAndGroup")
	public Resource<String> addKeywordAndGroup(HttpServletRequest request,@Valid @RequestBody InAddKeywordDto inKeyword) throws Exception{
		List<Keyword> keywordList = null;
		//防止缓存中有数据库中不存在的关键词,在出现错误的时候将所有新添加的关键词从数据库中删除
		List<String> newKeywords = inKeyword.getKeywords().get("newKeywords");
		//对新关键词进行排重
		if(null != newKeywords && newKeywords.size()>0){
			HashSet<String> hashSet = new HashSet<String>(newKeywords);
			newKeywords.clear();
			newKeywords.addAll(hashSet);
			//首先使用缓存进行校验关键词是否已经存在于字典表
			keywordList = keywordApp.checkNewKeyword(newKeywords);
			
			if(null != keywordList && keywordList.size() > 0 && !keywordApp.batchSaveKeywords(keywordList))
				return new Resource<String>(prop.getProcessStatus("KEYWORD_ADD_FAIL"));
		}
		
		//调用app进行添加关键词
		keywordApp.addKeywordAndGroup(inKeyword);
		return new Resource<String>(prop.getProcessStatus("COMMON_SUCCESS"));
	}
	
	/**
	 * 下拉列表模糊查询关键词和分组接口数据获取
	 * 
	 * @param request
	 * @param in
	 *            groups 查询分组关键词 string
	 *            0：不查询分组，1：查询所有分组；非必传，传此字段的时候不能传branded、keyword字段，且只返回groups节点
	 *            projectId 项目id string
	 * @return 返回格式 data 业务数据 array<object> groups 分组子节点 array
	 *         <object> 项目所有的分组，只有在传入groups字段且值为1时才会返回 groupId 分组id string
	 *         groupName 分组名称 string keywords 关键词子节点 array<object> keywordId
	 *         关键词id string keywordName 关键词名称 string result 返回结果编码数据 array
	 *         <object> retCode 业务编码 string 返回值为0时为成功，其他情况均为失败 retMsg 返回信息
	 *         string
	 */
	@RequestMapping("/seo/getProjectGroOrKeywords")
	public Resource<OutDropListDto> dropDownListFuzzyQuery(HttpServletRequest request,
			@Valid @RequestBody InDropListDto in, Model model) throws BusinessException, Exception {
		// 校验dto数据
		if (in == null) {
			return new Resource<OutDropListDto>(prop.getProcessStatus("PARAM_ERROR"));
		}
		if (StringUtils.isBlank(in.getProjectId())) {
			return new Resource<OutDropListDto>(prop.getProcessStatus("PARAM_ERROR"));
		}
		OutDropListDto out = new OutDropListDto();
		if (null != in.getGroups() && "1".equals(in.getGroups())) {
			// 同时返回关键词和分组节点信息
			// 校验项目id是否正确
			ProjectDto projectDto = projectService.getProjectInfoById(in.getProjectId());
			if (null == projectDto || "".equals(projectDto) || "1".equals(projectDto.getDelFlag())
					|| "0".equals(projectDto.getProjectState())) {
				// 项目信息不存在
				return new Resource<OutDropListDto>(prop.getProcessStatus("PROJECT_ERROR"));
			}
			// 获取项目下的所有分组信息
			List<Group> listGroups = keywordApp.getAllProGroups(in.getProjectId());
			out.setGroups(listGroups);
		}
		// 获取项目下的所有的关键词信息
		List<Keyword> keywords = keywordApp.getProKeyWords(in.getProjectId());
		out.setKeywords(keywords);
		return new Resource<OutDropListDto>(out);
	}
	
	@RequestMapping("/seo/getProjectKeywords")
	public Resource<OutProjectKeywordDto> getProjectKeywords(HttpServletRequest request,
			@Valid @RequestBody InProjectKeywordsDto in) throws BusinessException, Exception {
		// 校验dto数据
		if (in == null) {
			return new Resource<OutProjectKeywordDto>(prop.getProcessStatus("PARAM_ERROR"));
		}
		if (StringUtils.isBlank(in.getProjectId())) {
			return new Resource<OutProjectKeywordDto>(prop.getProcessStatus("PARAM_ERROR"));
		}
		List<FuzzyKeywordDto> keywordList = keywordApp.getProjectKeyword(in);
		OutProjectKeywordDto out = new OutProjectKeywordDto();
		out.setKeywords(keywordList);
		return new Resource<OutProjectKeywordDto>(out);
	}

	/**
	 * 添加项目分组接口
	 * 
	 * @param request
	 * @param in
	 *            groupName 分组名称 string projectId 项目id string
	 * @param model
	 * @return data 业务数据 array<object> result 返回结果编码数据 array<object> retCode
	 *         业务编码 string 返回值为0时为成功，其他情况均为失败 retMsg 返回信息 string
	 */
	@RequestMapping("/seo/addProjectGroup")
	public Resource<String> addProjectGroup(HttpServletRequest request, @Valid @RequestBody AddGroupParam addGroupParam)
			throws BusinessException,Exception {
		// 校验项目id是否正确
		ProjectDto projectDto = projectService.getProjectInfoById(addGroupParam.getProjectId());
		if (null == projectDto || "1".equals(projectDto.getDelFlag()) || "0".equals(projectDto.getProjectState())) {
			return new Resource<String>(prop.getProcessStatus("PROJECT_ERROR"));
		}
		projectDto = null;
		// 添加分组
		Group group = new Group();
		group.setTenantId(RestUserUtils.getTencentId());
		group.setGroupId(masterSequenceUtil.getKeywordSeq());
		group.setDelFlag("0");
		group.setGroupName(addGroupParam.getGroupName());
		group.setProjectId(Integer.parseInt(addGroupParam.getProjectId()));
		group.preInsert();
		List<Group> groups = new ArrayList<Group>();
		groups.add(group);
		if (keywordApp.addProjectGroup(groups)) {
			return new Resource<String>("");
		}
		return new Resource<String>(prop.getProcessStatus("COMMON_ERROR"));

	}

	/***
	 * 删除项目关键词接口
	 * 
	 * @param param
	 *            keywords projectId
	 * @return
	 */
	@RequestMapping("/seo/deleteKeyword")
	public Resource<String> deleteKeyword(@RequestBody Map<String, Object> param) throws BusinessException, Exception {
		if (null == param) {
			return new Resource<String>("", prop.getProcessStatus("PARAM_IS_NULL"));
		}
		if (null == param.get("projectId") || "".equals(param.get("projectId").toString())
				|| null == param.get("keywords")) {
			return new Resource<String>("", prop.getProcessStatus("PARAM_IS_NULL"));
		}
		keywordApp.deleteKeywordByKeywordIds(param);
		return new Resource<String>("", prop.getProcessStatus("COMMON_SUCCESS"));
	}

	/***
	 * 删除分组
	 * 
	 * @param pram
	 *            groupId projectId
	 * @return
	 */
	@RequestMapping("/seo/deleteGroup")
	public Resource<String> deleteGroup(@RequestBody Map<String, Object> param) throws BusinessException,Exception{
		if (null == param) {
			return new Resource<String>("", prop.getProcessStatus("PARAM_IS_NULL"));
		}
		if (null == param.get("projectId") || "".equals(param.get("projectId").toString())
				|| null == param.get("groupId")) {
			return new Resource<String>("", prop.getProcessStatus("PARAM_IS_NULL"));
		}
			keywordApp.deleteGroup(param);
		return new Resource<String>("", prop.getProcessStatus("COMMON_SUCCESS"));
	}

	/**
	 * 查询项目所有分组接口
	 * 
	 * @param request
	 * @param in
	 *            projectId 项目id string
	 * @param model
	 * @return data 业务数据 array<object> groups 分组子节点 array<object> groupId 分组id
	 *         string groupName 分组名称 string result 返回结果编码数据 array
	 *         <object> retCode 业务编码 string 返回值为0时为成功，其他情况均为失败 retMsg 返回信息
	 *         string
	 */
	@RequestMapping("/seo/getProAllGroups/{projectId}")
	public Resource<Map<String, List<Group>>> getProjectAllGroups(HttpServletRequest request,
			@PathVariable String projectId, Model model) throws BusinessException, Exception {
		// 校验参数是否正确
		if (null == projectId || "".equals(projectId)) {
			return new Resource<Map<String, List<Group>>>(prop.getProcessStatus("PARAM_ERROR"));
		}
		// 校验项目id是否存在
		ProjectDto projectDto = projectService.getProjectInfoById(projectId);
		if (null == projectDto || "1".equals(projectDto.getDelFlag()) || "0".equals(projectDto.getProjectState())) {
			return new Resource<Map<String, List<Group>>>(prop.getProcessStatus("PROJECT_ERROR"));
		}
		projectDto = null;
		List<Group> list = keywordApp.getAllProGroups(projectId);
		Map<String, List<Group>> map = new HashMap<String, List<Group>>();
		map.put("groups", list);
		return new Resource<Map<String, List<Group>>>(map);
	}

	/***
	 * 删除关键词品牌和分组接口
	 * 
	 * @param param
	 *            branded 是否删除关键词品牌 string groupId 是否删除关键词分组 array
	 *            <string> keywords 所有要删除的关键词id array<string> 数组 projectId
	 * @return
	 */
	@RequestMapping("/seo/deleteBrandOrGroup")
	public Resource<String> deleteBrandOrGroup(@RequestBody Map<String, Object> param)
			throws BusinessException, Exception {
		if (null == param) {
			return new Resource<String>("", prop.getProcessStatus("PARAM_IS_NULL"));
		}
		if (StringUtils.isBlank(param.get("projectId").toString())) {
			return new Resource<String>("", prop.getProcessStatus("PARAM_IS_NULL"));
		}
		@SuppressWarnings("unchecked")
		List<String> keywordIds = (List<String>) param.get("keywords");
		if (!(keywordIds.size() > 0 && keywordIds != null)) {
			return new Resource<String>("", prop.getProcessStatus("PARAM_IS_NULL"));
		}

		keywordApp.deleteBrandOrGroup(param);

		return new Resource<String>("", prop.getProcessStatus("COMMON_SUCCESS"));
	}

	/**
	 * 添加关键词到项目下的分组中
	 * 
	 * @param request
	 * @param inParam
	 *            existKeywodIds 要添加的关键词的id array<string> groups 分组名称 array
	 *            <string> projectId 项目id string
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/seo/addGroupKeywords")
	public Resource<String> addGroupKeywords(HttpServletRequest request,
			@Valid @RequestBody Map<String, Object> inParam, Model model) throws BusinessException, Exception {

		if (null == inParam || "".equals(inParam) || !inParam.containsKey("groups")
				|| !inParam.containsKey("existKeywordIds") || !inParam.containsKey("projectId")) {
			return new Resource<String>(prop.getProcessStatus("PARAM_ERROR"));
		}
		String projectId = inParam.get("projectId") + "";
		// 校验关键词和分组是否存在
		List<String> keywordList = (List<String>) inParam.get("existKeywordIds");
		List<Integer> keywords = new ArrayList<>();
		for (String str : keywordList) {
			Integer keyword = Integer.parseInt(str);
			keywords.add(keyword);
		}
		List<String> groups = (List<String>) inParam.get("groups");
		List<Group> groupList = keywordApp.getAllProGroups(projectId);
		int count = 0;
		if (null == groups || null == groupList || groupList.size() <= 0 || groups.size() <= 0) {
			return new Resource<String>(prop.getProcessStatus("PARAM_ERROR"));
		}
		for (int i = 0; i < groupList.size(); i++) {
			for (int j = 0; j < groups.size(); j++) {
				if (groupList.get(i).getGroupId().toString().equals(groups.get(j))) {
					count++;
				}
			}
		}
		if (groups.size() != count) {
			return new Resource<String>(prop.getProcessStatus("GROUP_PARAM_ERROR"));
		}

		// 添加关键词到分组
		List<ProKeyword> proKeywords = keywordApp.getProKeywordInfo(projectId, keywords);

		if (proKeywords.size() != keywords.size()) {
			return new Resource<String>(prop.getProcessStatus("KEYWORD_PARAM_ERROR"));
		}

		List<String> proKeyRelIds = new ArrayList<String>();
		for (int i = 0; i < proKeywords.size(); i++) {
			proKeyRelIds.add(proKeywords.get(i).getProKeyRelId().toString());
		}
		if (keywordApp.batckSaveGroupKeywordRel(groups, proKeyRelIds)) {
			return new Resource<String>("");
		}

		return new Resource<String>(prop.getProcessStatus("KEYWORD_ADD_GROUP_FAIL"));

	}

	/**
	 * 给关键词添加品牌词属性 接口
	 * 
	 * @param request
	 * @param inParam
	 *            keywodIds 关键词的id array<string> projectId 项目id string
	 * @param model
	 * @return data 业务数据 array<object> result 返回结果编码数据 array<object> retCode
	 *         业务编码 string 返回值为0时为成功，其他情况均为失败 retMsg 返回信息 string
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/seo/setKeywordBrand")
	public Resource<String> setKeywordBrand(HttpServletRequest request, @RequestBody Map<String, Object> inParam,
			Model model) throws BusinessException, Exception {
		// check inParam data
		if (null == inParam || "".equals(inParam) || !inParam.containsKey("projectId")
				|| !inParam.containsKey("keywordIds")) {
			return new Resource<String>(prop.getProcessStatus("PARAM_ERROR"));
		}

		// 校验项目和关键词是否存在
		List<String> keywords = (List<String>) inParam.get("keywordIds");

		int res = keywordApp.checkProExistKeyword(keywords, inParam.get("projectId") + "");
		// update keyword branded
		if (res > 0 && keywordApp.setKeywordBrand(inParam.get("projectId") + "", keywords) > 0) {
			return new Resource<String>("");
		}

		return new Resource<String>(prop.getProcessStatus("ADD_BRAND_FAIL"));
	}

	/**
	 * 模糊查询关键词接口
	 * 
	 * @param request
	 * @param in
	 *            branded 是否查询品牌关键词 string 0：查询非品牌，1：查询品牌；不传此字段则没有品牌限制 groups
	 *            查询分组管家次 array
	 *            <string> 传值为分组id，数组格式，例如：[1874,2s93,445f];不传此字段则没有分组限制 keyword
	 *            输入的要进行模糊匹配的关键词 string projectId 项目id string
	 * @param model
	 * @return data 业务数据 array<object> count 总条数 number keywords 关键词子节点 array
	 *         <object> 一个关键词内容 branded 是否是品牌关键词 string 0：否，1：是 groups 关键词孙子节点
	 *         array<object> groupId 分组id string groupName 分组名称 string keywordId
	 *         关键词id string keywordName 关键词名称 string pageNo 当前页码 number pageSize
	 *         一页显示条数 number result 返回结果编码数据 array<object> retCode 业务编码 string
	 *         返回值为0时为成功，其他情况均为失败 retMsg 返回信息 string
	 */
	@RequestMapping("/seo/getFuzzyKeywordsList")
	public Resource<OutKeywordDto> getFuzzyKeywords(HttpServletRequest request,@Valid @RequestBody InKeywordDto in,
			Model model) throws BusinessException, Exception {
		if (in == null) {
			return new Resource<OutKeywordDto>(prop.getProcessStatus("PARAM_ERROR"));
		}
		if (StringUtils.isBlank(in.getProjectId())) {
			return new Resource<OutKeywordDto>(prop.getProcessStatus("PARAM_ERROR"));
		}
		// 校验项目
		ProjectDto projectDto = projectService.getArchiveProjectInfoById(in.getProjectId());
		if (null == projectDto)
			return new Resource<OutKeywordDto>(prop.getProcessStatus("PROJECT_ERROR"));

		// 模糊查询项目下的所有关键词
		Page<FuzzyKeywordDto> page = null;
//		if (null != in.getKeyword() && !"".equals(in.getKeyword()) && in.getKeyword().size() > 0) {
			int pageNo = in.getPageNo();
			int pageSize = in.getPageSize();
			if (in.getPageNo() == 0) {
				pageNo = 1;
			}
			if (in.getPageSize() == 0) {
				pageSize = 10;
			}
			page = new Page<FuzzyKeywordDto>(pageNo, pageSize);

			List<FuzzyKeywordDto> listKeywords = keywordApp.getFuzzyKeyword(page, in);
			if (null != listKeywords && listKeywords.size() > 0) {
				// 获取项目下的所有分组
				page.setList(listKeywords);
			}
		
		OutKeywordDto out = new OutKeywordDto();
		out.setCount(StringUtils.toInteger(page.getCount()));
		out.setKeywords(page.getList());
		out.setPageNo(page.getPageNo());
		out.setPageSize(page.getPageSize());

		return new Resource<OutKeywordDto>(out);
	}

	/**
	 *  接口详情 (id: 996)     Mock数据
		接口名称 获取所有正常关键词
		请求类型 get
		请求Url  /crawl/getAllKeyword
		接口描述 map<渠道id，list<关键词name>>
	 * @return
	 * @throws BusinessException
	 * @throws Exception
	 */
	@RequestMapping("/crawl/getAllKeyword")
	public Resource<Map<Integer, Set<String>>> getAllKeyword() throws Exception {
		Map<Integer, Set<String>> out = new HashMap<Integer, Set<String>>();
		
		//获取所有的要爬取的项目id,然后根据项目id去获取所有的套餐
		List<ProjectCrawlDto> projectCrawlDtos = projectService.getAllCralwPro();
		if(ListUntils.isNull(projectCrawlDtos)){
			return new Resource<>(prop.getProcessStatus("COMMON_SUCCESS"));
		}
		List<Integer> tenantIds = new ArrayList<Integer>();
		for (ProjectCrawlDto projectCrawlDto : projectCrawlDtos) {
			tenantIds.add(projectCrawlDto.getTenantId());
		}
		List<Keyword> keywords = null;
		Set<String> keywordNames = null;
		List<String> keywordNameTemp = new ArrayList<String>();
		List<Map<String,Object>> temp = null;
		String engine = null;
		
		List<TenantPackage> packages = packageService.getBatchPackageExampleListByTenantId(tenantIds);
		//根据租户id获取每个项目所对应的套餐的渠道
		for (Integer tenantId: tenantIds) {
			
			for (ProjectCrawlDto project : projectCrawlDtos) {
				//获取每个项目所有正常的关键词
				if(tenantId.equals(project.getTenantId())){
					keywords = keywordApp.getProKeyWords(project.getProjectId() + "");
					for (Keyword keyword : keywords) {
						keywordNameTemp.add(keyword.getKeywordName());
					}
				}
				temp = packageService.getScope(packages.get(0).getTenantPackageElementsRelList());
				engine = packageService.getEngine(temp);
				keywordNames = new HashSet<String>();
				if(StringUtils.isNotBlank(engine)){
					//构造返回结果
					for(String str : engine.split("\\,")){
						keywordNames.addAll(keywordNameTemp);
						if(out.containsKey(str) && null != out.get(str)){
							keywordNames.addAll(out.get(str));
						}
						out.put(StringUtils.toInteger(str), keywordNames);
					}
				}
			}
		}
		
		return new Resource<Map<Integer, Set<String>>>(out);
	}
	
	/**
	 *  接口详情 (id: 993)     Mock数据
		接口名称 获取项目的所有正常关键词
		请求类型 get
		请求Url  /crawl/getAllProKeyword
	 * @return
	 * @throws BusinessException
	 * @throws Exception
	 */
	@RequestMapping("/crawl/getAllProKeyword/{projectId}")
	public Resource<List<Keyword>> getAllProKeyword(@PathVariable Integer projectId) throws Exception {
		if(null == projectId || projectId <= 0){
			return new Resource<List<Keyword>>(prop.getProcessStatus("PARAM_ERROR"));
		}
		
		return new Resource<List<Keyword>>(keywordApp.getProKeyWords(projectId + ""));
	}

}
