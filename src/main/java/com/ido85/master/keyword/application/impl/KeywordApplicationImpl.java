package com.ido85.master.keyword.application.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ido85.config.properties.BussinessMsgCodeProperties;
import com.ido85.frame.common.Page;
import com.ido85.frame.common.exceptions.BusinessException;
import com.ido85.frame.common.restful.Resource;
import com.ido85.frame.common.restful.ResourceUtils;
import com.ido85.frame.common.utils.JsonUtil;
import com.ido85.frame.common.utils.StringUtils;
import com.ido85.frame.web.rest.security.Constants;
import com.ido85.frame.web.rest.utils.CacheUtils;
import com.ido85.frame.web.rest.utils.RestUserUtils;
import com.ido85.master.common.MasterSequenceUtil;
import com.ido85.master.keyword.application.KeywordApplication;
import com.ido85.master.keyword.domain.GroKeyRel;
import com.ido85.master.keyword.domain.Group;
import com.ido85.master.keyword.domain.GroupKeyRel;
import com.ido85.master.keyword.domain.Keyword;
import com.ido85.master.keyword.domain.ProKeyword;
import com.ido85.master.keyword.dto.FuzzyKeywordDto;
import com.ido85.master.keyword.dto.GroKeyRelDto;
import com.ido85.master.keyword.dto.InAddKeywordDto;
import com.ido85.master.keyword.dto.InKeyRankDto;
import com.ido85.master.keyword.dto.InKeywordDto;
import com.ido85.master.keyword.dto.InKeywordRankDto;
import com.ido85.master.keyword.dto.InProjectKeywordsDto;
import com.ido85.master.keyword.dto.InRankSearchVisiDto;
import com.ido85.master.keyword.dto.KeywordGroupInfoDto;
import com.ido85.master.keyword.dto.OutKeyRankNumItemDto;
import com.ido85.master.keyword.resources.GroKeyRelResources;
import com.ido85.master.keyword.resources.GroupKeyRelResources;
import com.ido85.master.keyword.resources.GroupResources;
import com.ido85.master.keyword.resources.KeywordResources;
import com.ido85.master.keyword.resources.ProKeywordResources;
import com.ido85.master.project.dto.ProjectDto;
import com.ido85.services.Integrate.IntegrateApi;
//import com.ido85.wi.service.CommonService;
//import com.ido85.wi.service.IntegrateServices;
//import com.ido85.wi.service.ProjectService;
//import com.ido85.wi.user.util.UserUtils;
import com.ido85.services.project.ProjectApi;

/**
 * 关键词应用业务处理层
 * @author fire
 *
 */
@Named
public class KeywordApplicationImpl implements KeywordApplication {
	private static transient final Logger log = LoggerFactory.getLogger(KeywordApplicationImpl.class);
	@Inject
	private KeywordResources keywordRes;
	
	@PersistenceContext(unitName="system")
	private EntityManager entity;
	@Inject
	private ProjectApi proService;
	@Inject
	private ProKeywordResources proKeyRes;
	@Inject
	private GroupResources groupResources;
	@Inject
	private GroupKeyRelResources groupKeyRelResources;
	@Inject
	private GroKeyRelResources groKeyRelResources;
	@Inject
	private IntegrateApi integrateServices;
	@Inject
	private MasterSequenceUtil masterSequenceUtil;
	@Inject
	private BussinessMsgCodeProperties prop;
//	@Inject
//	private HttpUrlProperties http;
//	@Inject
//	private CommonService common;
	
	
	@Override
	public boolean saveProKeyword(ProKeyword proKeyword) {
//		proRes.findAll(new PageRequest(0, 12));
		return false;
	}
	@Override
	@Transactional
	public List<ProKeyword> batchSaveNewProKeywords(List<String> keywords, String projectId) throws Exception {
		// TODO 首先校验关键词是否已经添加到字典表和项目中,然后添加
		//首先使用缓存进行校验关键词是否已经存在于字典表
		List<Keyword> keywordList = this.checkNewKeyword(keywords);
		
		this.batchSaveKeywords(keywordList);
		//校验关键词是否已经添加到项目当中
		List<Integer> keywordIds = new ArrayList<Integer>();
		for (String keyword : keywords) {
			if(null != CacheUtils.get(Constants.KEYWORD_CACHE,keyword) 
				&& !"".equals(CacheUtils.get(Constants.KEYWORD_CACHE,keyword))){
				Keyword kw = (Keyword)CacheUtils.get(Constants.KEYWORD_CACHE,keyword);
				if(kw != null){
					keywordIds.add(Integer.parseInt(kw.getKeywordId()));
				}
			}
		}
		List<ProKeyword> proKeywords = proKeyRes.getProKeywordInfo(Integer.parseInt(projectId), keywordIds);
		
		//已经添加的关键词进行剔除
		if(null != proKeywords && proKeywords.size() > 0){
			for (ProKeyword proKeyword : proKeywords) {
				for (int i = 0; i < keywordIds.size(); i++) {
					if(proKeyword.getKeywordId().equals(keywordIds.get(i))){
						keywordIds.remove(i);
						i--;
						continue;
					}
				}
			}
		}
		//将新关键词添加到项目
		if(null == keywordIds || "".equals(keywordIds) || keywordIds.size() <= 0){
			return proKeywords;
		}
		List<ProKeyword> saveList = new ArrayList<ProKeyword>();
		ProKeyword proKeyword = null;
		Integer tenantId = RestUserUtils.getTencentId();
		for (int i = 0; i < keywordIds.size(); i++) {
			proKeyword = new ProKeyword();
			proKeyword.setProKeyRelId(masterSequenceUtil.getCommonSeq());
			proKeyword.setKeywordId(keywordIds.get(i));
			proKeyword.setProjectId(Integer.parseInt(projectId));
			proKeyword.setDelFlag("0");
			proKeyword.setIsBrand("0");
			proKeyword.preInsert();
			proKeyword.setTenantId(tenantId);
			saveList.add(proKeyword);
		}
		proKeywords.addAll(proKeyRes.save(saveList));
		
		return proKeywords;
	}
	@Override
	@Transactional
	public Resource<String> saveGroKeyword(GroupKeyRel groupKeyRel) {
		//校验已有关键词是否存在
//		ArrayList<String> existKeyword = keywords.get("exitKeywords");
//		if (null != existKeyword && !"".equals(existKeyword) && existKeyword.size() > 0) {
//			if(!chechExistKeyword(existKeyword, projectDto.getProjectId())){
//				//用户选择的已有关键词有部分不存在
//				return new Resource<String>(null, prop.getProcessStatus("KEYWORD_NOT_EXIST"));
//			}
//			//用户添加了已有的关键词，校验通过，需要将其添加到分组中
//			
//		}else {
//			//用户未选择已添加的关键词进行分组
//			
//		}
//		
		return  new Resource<String>("");
	}
	@Override
	@Transactional
	public Resource<String> batchSaveGroKeyword(List<String> newProkeywordRelIds, List<String> groups, String projectId, String falg) throws Exception {
		List<Integer> list = new ArrayList<Integer>();
		if(newProkeywordRelIds != null && newProkeywordRelIds.size()>0){
			for(String str:newProkeywordRelIds){
				list.add(Integer.parseInt(str));
			}
		}
//		//校验已有关键词是否存在
		if("1".equals(falg) && proKeyRes.getProKeywordInfos(Integer.parseInt(projectId), list).size() < newProkeywordRelIds.size()){
			//用户选择的已有关键词有部分不存在
			throw new BusinessException(prop.getBussinessMsg("KEYWORD_NOT_EXIST"));
		}
		if (null != newProkeywordRelIds && !"".equals(newProkeywordRelIds) && newProkeywordRelIds.size() > 0 && null != groups
				&& !"".equals(groups) && groups.size() > 0) {
			//校验项目下的关键词是否已经添加到了分组中，已经添加的不再进行添加
			List<KeywordGroupInfoDto> infoDtos = proKeyRes.getAllKeywordGroupInfo(Integer.parseInt(projectId), list);
			if(null != infoDtos && infoDtos.size() > 0){
				//剔除已经添加关系的关键词
				for (int j = 0; j < newProkeywordRelIds.size(); j++) {
					for (int i = 0,len = infoDtos.size(); i < len; i++) {
						if(infoDtos.get(i).getProKeyRelId().equals(newProkeywordRelIds.get(j))){
							newProkeywordRelIds.remove(j);
							j--;
							break;
						}
					}
				}
			}
			//用户添加了已有的关键词，校验通过，需要将其添加到分组中
			List<GroupKeyRel> groupKeyRels = null;
			Group group = null;
			ProKeyword proKeyword = null;
			Integer tenantId = RestUserUtils.getTencentId();
			for (int i = 0; i < groups.size(); i++) {
				group = new Group();
				group.setGroupId(Integer.parseInt(groups.get(i)));
				groupKeyRels = new ArrayList<GroupKeyRel>();
				GroupKeyRel groupKeyRel = null;
				for (int j = 0; j < newProkeywordRelIds.size(); j++) {
					proKeyword = new ProKeyword();
					proKeyword.setProKeyRelId(Integer.parseInt(newProkeywordRelIds.get(j)));
					groupKeyRel = new GroupKeyRel();
					groupKeyRel.setGroupKeyId(masterSequenceUtil.getCommonSeq());
					groupKeyRel.setGroup(group);
					groupKeyRel.setDelFlag("0");
					groupKeyRel.setProKeyword(proKeyword);
					groupKeyRel.preInsert();
					groupKeyRel.setTenantId(tenantId);
					groupKeyRels.add(groupKeyRel);
				}
				if(groupKeyRels.size() > 0){
					groupKeyRelResources.save(groupKeyRels);
				}
			}
		}else  {
			//用户未选择已添加的关键词进行分组
			return new Resource<String>("success");
		}
		
		return  new Resource<String>("success");
	}
	@Override
	public boolean batchSaveGroKeyword(GroupKeyRel groupKeyRel) {
		return false;
	}
	
	@Override
	@Transactional
	public void addKeywordAndGroup(InAddKeywordDto inKeywordDto) throws Exception {
		//获取项目基本信息
		ProjectDto projectDto = proService.getProjectInfoById(inKeywordDto.getProjectId());
		if(projectDto == null || (projectDto != null && !"0".equals(projectDto.getDelFlag()))){
			throw new BusinessException(prop.getBussinessMsg("PROJECT_ERROR"));
		}
		if(null != projectDto && !"".equals(projectDto)  || "1".equals(projectDto.getDelFlag())
				|| "0".equals(projectDto.getProjectState())){
			//项目校验成功,开始校验项目是否可以添加关键词
			Map<String, ArrayList<String>> keywords = inKeywordDto.getKeywords();
			int keywordNum = keywords.get("newKeywords").size();
			int leftNum = 0;
			//判断项目是否可以继续添加关键词
			if(!integrateServices.checkAddKeyword(projectDto.getProjectId(), keywordNum, leftNum)){
				//新添加的关键词个数超过了项目可以添加的关键词个数
				throw new BusinessException(prop.getBussinessMsg("KEYWORD_NUM_ERROR"));
			}
			
			//校验用户是否选择分组
			List<String> groupsList = inKeywordDto.getGroups();
			
			if (null != groupsList && !"".equals(groupsList) && groupsList.size() > 0) {
				//用户选择了对添加的关键词进行分组,校验用户添加的分组是否存在
				if(!chechGroups(groupsList, inKeywordDto.getProjectId())){
					//用户选择的分组有部分不存在
					throw new BusinessException(prop.getBussinessMsg("GROUP_NOT_EXIST"));
				}
				//先给项目添加新关键词并且添加到分组
				List<String> newKeywords = keywords.get("newKeywords");
				
				//对新关键词进行排重
				HashSet<String> hashSet = new HashSet<String>(newKeywords);
				newKeywords.clear();
				newKeywords.addAll(hashSet);
				
				List<ProKeyword> newProKeywordRelList = this.batchSaveNewProKeywords(newKeywords, inKeywordDto.getProjectId());
				
				if(null != newProKeywordRelList && !"".equals(newProKeywordRelList) && newProKeywordRelList.size() > 0){
					//return new Resource<String>(null,prop.getProcessStatus("KEYWORD_ADD_FAIL"));
					List<String> newProKeywordRelId = new ArrayList<String>();
					for (int i = 0; i < newProKeywordRelList.size(); i++) {
						newProKeywordRelId.add(newProKeywordRelList.get(i).getProKeyRelId()+"");
					}
					Resource<String> res = this.batchSaveGroKeyword(newProKeywordRelId, groupsList, inKeywordDto.getProjectId(), "0");
					if(null == res.getData() || !"success".equals(res.getData())){
						throw new BusinessException(prop.getBussinessMsg("KEYWORD_ADD_FAIL"));
					}
				}
			}else{
				//添加关键词，首先校验新添加的关键词是否已经存在于项目当中
				List<String> newKeywords = keywords.get("newKeywords");
				List<ProKeyword> newKeywordId = this.batchSaveNewProKeywords(newKeywords, inKeywordDto.getProjectId());
//				if(null != newKeywordId && !"".equals(newKeywordId)){
//					
//				}
			}
			
		}
	}
	
	@Override
	public List<Group> getProKeywordGroup(String keyword, String projectId) {
		//获取关键词和项目关系id
		List<ProKeyword> proKeywords = proKeyRes.getProKeywordInfo(StringUtils.toInteger(projectId), StringUtils.toInteger(keyword));
		List<Group> groups = null;
		if (null != proKeywords && !"".equals(proKeywords) && proKeywords.size() > 0) {
			//获取关键词所有分组信息
			groups = groupResources.getProKeywordGroup(proKeywords.get(0).getProKeyRelId(), Integer.parseInt(projectId));
		}
		
		return groups;
	}
	
	@Override
	public String getDifficultyNum(String keyword, String projectId)throws Exception {
//		String res = "";
//		// 校验关键词是否存在于此项目当中
//		List<String> keywordList = new ArrayList<String>();
//		keywordList.add(keyword);
//		if(checkProExistKeyword(keywordList, projectId) == keywordList.size()){
//			// 关键词存在，查询难度
//			List<Keyword> keywords = keywordRes.getDifficultyNum(keyword);
//			if(null != keywords && !"".equals(keywords) && keywords.size() > 0){
//				res = keywords.get(0).getDifficulyScore();
//				keywords = null;
//			}
//		}
//		
//		return res;
		return null;
	}
	
	@Override
	public boolean saveGroupKeywordRel(String keyword, String groupId) {
		//先获取关键词和项目关系表id，然后添加数据
		
		return false;
	}
	@Override
	public List<Keyword> checkNewKeyword(List<String> keywords) throws Exception {
		//首先使用缓存进行校验关键词是否已经存在于字典表
		List<Keyword> keywordList = new ArrayList<Keyword>(); 
		for (String keyword : keywords) {
			System.out.println("==="+CacheUtils.get(Constants.KEYWORD_CACHE,keyword + ""));
			if(null == CacheUtils.get(Constants.KEYWORD_CACHE,keyword + "")){
				Keyword key = new Keyword(masterSequenceUtil.getKeywordSeq(),keyword,"0",new Date());
				keywordList.add(key);
			}
		}
		return keywordList;
	}
	
	@Override
	public int getProKeywordRank(String keywordId, String projectId, String engine, String date) {
		int res = -1;
		// 校验关键词是否存在
		List<String> keywordList = new ArrayList<String>();
		keywordList.add(keywordId);
		if(checkProExistKeyword(keywordList, projectId) == keywordList.size()){
			// 关键词存在，调用Hbase查询排名
			Map<String, String> map = new HashMap<String, String>();
			map.put("projectId", projectId);
			map.put("keywordId", keywordId);
//			map.put("date", common.getLastUpdateTimeByDate(new Date(), "yyyy-MM-dd"));
//			//map.put("date", "2016-05-16");
			map.put("engine", engine);
			
			log.info("llz====getProKeywordRank=map={}", map);
//			String resInfo = HttpUtils.post(http.getKeywordRanks(), map);
//			log.info("llz====getProKeywordRank=rank={}", resInfo);
//			if(ResourceUtils.checkResult(resInfo)){
//				res = ResourceUtils.getDataIntValue(resInfo, "rank");
//			}else if(ResourceUtils.checkResultData(resInfo)){
//				res = 0;
//			}
		}
		return res;
	}
	
	@Override
	public Resource<Object> getRankTrend(InKeywordRankDto in) {
//		// 校验关键词是否存在于此项目当中
//		List<String> keywordList = new ArrayList<String>();
//		keywordList.add(in.getKeyword());
//		if(checkProExistKeyword(keywordList, in.getProjectId()) == keywordList.size()){
//			//关键词存在，查询hbase中的排名趋势  获取排名趋势的开始时间和结束时间 InRankTrendDto
//			Map<String, String> map = new HashMap<String, String>();
//			
//			ProjectDto project = proService.getProjectInfoById(in.getProjectId());
//			if(null == project || "1".equals(project.getDelFlag()) || "0".equals(project.getProjectState())){//项目不存在
//				return new Resource<Object>(prop.getProcessStatus("PROJECT_ERROR"));
//			}
//			
//			map.put("keywordId", in.getKeyword());
//			map.put("projectId", in.getProjectId());
//			map.put("date", common.getLastUpdateTimeByDate(new Date(), "yyyy-MM-dd"));
////			map.put("date", "2016-05-16");
//			map.put("projectUrl", project.getProjectUrl());
//			map.put("engine", in.getEngine());
//			map.put("subdomain", project.getIsSubdomain());
//			map.put("startDate", DateUtils.formatDateTime(DateUtils.addDays(new Date(), -57), "yyyyMMdd"));
//			
//			String resInfo = HttpUtils.post(http.getKeywordRankTrend(), map);
//			log.info("llz====获取关键词排名=rank={}", resInfo);
//			if(ResourceUtils.checkResult(resInfo)){
//				return new Resource<Object>(JsonUtil.getJsonValue(resInfo, "data"));
//			}else if(ResourceUtils.checkResultData(resInfo)){
//				return new Resource<Object>("");
//			}
//			return new Resource<Object>(prop.getProcessStatus("QUERY_ERROR"));
//		}
//		return new Resource<Object>(prop.getProcessStatus("PRO_KEYWORD_NOT_EXIST"));
		return null;
	}
	
	@Override
	public Resource<Object> getSERPReport(InKeywordRankDto in) {
		// 校验关键词是否存在于此项目当中
//		List<String> keywordList = new ArrayList<String>();
//		keywordList.add(in.getKeyword());
//		if(checkProExistKeyword(keywordList, in.getProjectId()) == keywordList.size()){
//			//关键词存在，查询hbase项目中的SERP报告
//			Map<String, String> map = new HashMap<String, String>();
//			
//			ProjectDto project = proService.getProjectInfoById(in.getProjectId());
//			if(null == project || "1".equals(project.getDelFlag()) || "0".equals(project.getProjectState())){//项目不存在
//				return new Resource<Object>(prop.getProcessStatus("PROJECT_ERROR"));
//			}
//			
//			map.put("keywordId", in.getKeyword());
//			map.put("projectId", in.getProjectId());
////			map.put("date", "2016-05-16");
//			map.put("date", common.getLastUpdateTimeByDate(new Date(), "yyyy-MM-dd"));
//			map.put("engine", in.getEngine());
//			
//			String resInfo = HttpUtils.post(http.getKeywordSerpReport(), map);
//			log.info("llz====获取关键词SERP报告=SERP={}", resInfo);
//			if(ResourceUtils.checkResult(resInfo)){
//				return new Resource<Object>(JsonUtil.getJsonValue(resInfo, "data"));
//			}
//			return new Resource<Object>(prop.getProcessStatus("QUERY_ERROR"));
//		}
//		return new Resource<Object>(prop.getProcessStatus("PRO_KEYWORD_NOT_EXIST"));
		return null;
	}
	/***
	 * 删除 关键词    keywordId projectId
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public int deleteKeywordByKeywordIds(Map<String, Object> param) throws Exception {
		String  projectId = param.get("projectId").toString();
		Integer userId = RestUserUtils.getUserInfo().getUserId();
		List<String> keywords = (List<String>) param.get("keywords");
		if(null==keywords||keywords.size()<=0){
			return 0;
		}else{
			List<Integer> keywordList = new ArrayList<>();
			for(String str : keywords){
				Integer keyword = Integer.parseInt(str);
				keywordList.add(keyword);
			}
			//查询关键词 与项目关系
			List<ProKeyword> proKeywords =  proKeyRes.getProKeywordInfo(Integer.parseInt(projectId), keywordList);
			if(null==proKeywords||proKeywords.size()<=0){
				return 0;
			}
			//关系id
			List<Integer> reList  = new ArrayList<Integer>();
			for(ProKeyword p : proKeywords){
				reList.add(p.getProKeyRelId());
			}
			//根据关键词与项目关系删除 分组与关键词的关系
			if(reList.size()>0){
				groupKeyRelResources.deleteByRel("1", userId, new Date(), reList);
			}
			//删除关键词与项目之间的关系
			int temp = proKeyRes.deleteByProjectIdAndKeywordId("1", userId, new Date(), Integer.parseInt(projectId), keywordList);
			return temp;
		}
	}
	/***
	 * 删除分组 单个删除
	 * @param param
	 * 		 groupId
	 * 		 projectId
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public int deleteGroup(Map<String, Object> param) throws Exception {
		//TODO 未删除分组与项目的关键词关系表
//		String projectId = param.get("projectId").toString();
		Integer userId = RestUserUtils.getUserInfo().getUserId();
		String groupId = param.get("groupId").toString();
		
		//查询分组与项目关键词的关系表 得到关键词与项目 的关系
		List<GroupKeyRel> groupKeyrels = groupKeyRelResources.getGroupKeyRelBygroupId(Integer.parseInt(groupId));//一个组对应好多关系
		List<Integer> groupKeyRelIds = new ArrayList<Integer>();
		if(null!=groupKeyrels&&groupKeyrels.size()>0){
			for(GroupKeyRel rel : groupKeyrels){
				if(null!=rel.getProKeyword()){
					groupKeyRelIds.add(rel.getProKeyword().getProKeyRelId());
				}
			}
		}
		//根据关键词与项目的关系id  删除关键词与项目的关系  就是删除关键词
//		if(groupKeyRelIds.size()>0){
//			proKeyRes.deleteByProkeywordRelIds("1", userId, new Date(), groupKeyRelIds);
//		}
		//根据分组id删除seo分组和项目的关键词关系表
		List<Integer> list = new ArrayList<Integer>();
		list.add(Integer.parseInt(groupId));
		groupKeyRelResources.deleteBygroupIds("1", userId, new Date(), list);
		//删除 分组表
		int temp = groupResources.deleteByGroupId("1", userId, new Date(), Integer.parseInt(groupId));
		return temp;
	}
	
	/***
	 * 删除关键词品牌和分组接口
	 * @param param
	 *          branded	是否删除关键词品牌	string	
				groupId	是否删除关键词分组	array<string>	
				keywords	所有要删除的关键词id	array<string>	数组
				projectId
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public int deleteBrandOrGroup(Map<String, Object> param) throws Exception {
		String projectId = param.get("projectId").toString();
		List<String> keywordIds = (List<String>) param.get("keywords");
		Integer userId = RestUserUtils.getUserInfo().getUserId();
		List<Integer> keywordIdList = new ArrayList<>();		
		for(String str : keywordIds){
			Integer keywordId = Integer.parseInt(str);
			keywordIdList.add(keywordId);
		}
		//查询关键词 与项目关系
		List<ProKeyword> proKeywords = proKeyRes.getProKeywordInfo(Integer.parseInt(projectId), keywordIdList);
		if(null==proKeywords||proKeywords.size()<=0){
			return 0;
		}
		List<Integer> reList = null;
		if(null != keywordIds && keywordIds.size() > 0){
			//关系id
			reList = new ArrayList<Integer>();
			for(ProKeyword p : proKeywords){
				reList.add(p.getProKeyRelId());
			}
		}
		if(null!=param.get("branded")&&"1".equals(param.get("branded").toString())){//需要删除品牌
			if(reList.size() > 0){
				proKeyRes.updateBrandByProkeywordRelIds("0", userId, new Date(), reList);
			}
		}
		//删除关键词分组
		int teml = 0;
		if(!param.containsKey("groupId")){
			return 0;
		}
		List<String> groupIds = (List<String>) param.get("groupId");	
		if(groupIds != null && groupIds.size()>0){
			List<Integer> groupIdList = new ArrayList<>();
			for(String str : groupIds){
				Integer groupId = Integer.parseInt(str);
				groupIdList.add(groupId);
			}
			 teml = groupKeyRelResources.deleteBygroupIds("1", userId, new Date(), groupIdList, reList);
		}
		return teml;
	}
	
	/***
	 * 删除分组 多个个删除
	 * @param param
	 * 		 groupIds list<String>
	 * 		 projectId
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public int deleteGroupArr(Map<String, Object> param) throws Exception {
//		String  projectId = param.get("projectId").toString();
//		String userId = UserUtils.getUser()==null?"1":UserUtils.getUser().getUserId();
//		String userId = "";
//		if(null==param.get("groupId")){
//			return 0;
//		}
//		List<String > groupIds = (List<String>) param.get("groupId");
//		
//		//查询分组与项目关键词的关系表 得到关键词与项目 的关系
//		List<GroupKeyRel> groupKeyrels = groupKeyRelResources.getGroupKeyRelBygroupIds(groupIds);//一个组对应好多关系
//		List<String> groupKeyRelIds = new ArrayList<String >();
//		if(null!=groupKeyrels&&groupKeyrels.size()>0){
//			for(GroupKeyRel rel : groupKeyrels){
//				if(null!=rel.getProKeyword()){
//					groupKeyRelIds.add(rel.getProKeyword().getProKeyRelId());
//				}
//			}
//		}
//		//根据关键词与项目的关系id  删除关键词与项目的关系  就是删除关键词
//		if(groupKeyRelIds.size()>0){
//			proKeyRes.deleteByProkeywordRelIds("1", userId, new Date(), groupKeyRelIds);
//		}
		//根据分组id删除seo分组和项目的关键词关系表
//		groupKeyRelResources.deleteBygroupIds("1", userId, new Date(), groupIds);
		//删除 分组表
//		int temp = groupResources.deleteByGroupIds("1", userId, new Date(), groupIds);
//		return temp;
		return 0;
	}
	
	

	@Override
	@Transactional
	public boolean addProjectGroup(List<Group> groups) {
		// 添加分组
		groupResources.save(groups);
		return true;
	}
	@Override
	public List<ProKeyword> getProKeywordInfo(String projectId,
			List<Integer> keywordIds) {
		return proKeyRes.getProKeywordInfo(Integer.parseInt(projectId), keywordIds);
	}
	
	@Override
	public List<FuzzyKeywordDto> getProjectKeyword(InProjectKeywordsDto In){
		StringBuilder jpaSql= new StringBuilder(" SELECT NEW com.ido85.master.keyword.dto.FuzzyKeywordDto(spk.isBrand, spk.keywordId, k.keywordName)"
				+ " FROM ProKeyword spk, Keyword k LEFT JOIN GroKeyRel gkr ON spk.proKeyRelId = gkr.proKeyRelId and gkr.delFlag='0' WHERE spk.projectId = :projectId " +
				" and k.keywordId = spk.keywordId  AND spk.delFlag = '0' " +
				"and k.delFlag = '0' ");
						
		Map<String, Object> map = new HashMap<String, Object>();
		List<Integer> groupList = null;
		if(null !=In.getGroups() && !"".equals(In.getGroups()) && In.getGroups().size()>0){
			groupList = new ArrayList<>();
			for(String str : In.getGroups()){
				Integer groupId = Integer.parseInt(str);
				groupList.add(groupId);
			}
			jpaSql.append(" and gkr.groupId in :groups ");
			map.put("groups", groupList);
		}
		
		if(null != In.getBranded() && !"".equals(In.getBranded())){
			jpaSql.append(" and spk.isBrand = :isBrand ");
			map.put("isBrand", In.getBranded());
		}
		Query query = entity.createQuery(jpaSql.toString(), FuzzyKeywordDto.class);
		query.setParameter("projectId", Integer.parseInt(In.getProjectId()));
		
		if(map.size() > 0){
			Set<String> set = map.keySet();
			for (String str : set) {
				query.setParameter(str, map.get(str));
			}
		}		
		@SuppressWarnings("unchecked")
		List<FuzzyKeywordDto> res = query.getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FuzzyKeywordDto> getFuzzyKeyword(Page<FuzzyKeywordDto> page,InKeywordDto in) {
		/**
		 * 根据关键词id和项目id，批量获取项目下的关键词信息
		 */
		StringBuilder jpaSql= new StringBuilder(" SELECT NEW com.ido85.master.keyword.dto.FuzzyKeywordDto(spk.isBrand, spk.keywordId, k.keywordName)"
				+ " FROM ProKeyword spk, Keyword k LEFT JOIN GroKeyRel gkr ON spk.proKeyRelId = gkr.proKeyRelId and gkr.delFlag='0' WHERE spk.projectId = :projectId " +
				" and k.keywordId = spk.keywordId  AND spk.delFlag = '0' " +
				"and k.delFlag = '0' ");
		
				
		Map<String, Object> map = new HashMap<String, Object>();
		//原来是根据关键词id来查询的，现在修改为对关键词名称进行模糊查询
		if(null != in.getKeyword() && !"".equals(in.getKeyword()) && in.getKeyword().size() > 0){
//			jpaSql = jpaSql + " and spk.keywordId IN :keywordIds ";
//			map.put("keywordIds", in.getKeyword());
			jpaSql.append(" and k.keywordName like :keywordName ");
			map.put("keywordName", "%" + in.getKeyword().get(0) + "%");
		}
		
		List<Integer> groupList = null;
		if(null !=in.getGroups() && !"".equals(in.getGroups()) && in.getGroups().size()>0){
			groupList = new ArrayList<>();
			for(String str : in.getGroups()){
				Integer groupId = Integer.parseInt(str);
				groupList.add(groupId);
			}
			jpaSql.append(" and gkr.groupId in :groups ");
			map.put("groups", groupList);
		}
		
		if(null != in.getBranded() && !"".equals(in.getBranded())){
			jpaSql.append(" and spk.isBrand = :isBrand ");
			map.put("isBrand", in.getBranded());
		}
		Query query = entity.createQuery(jpaSql.toString(), FuzzyKeywordDto.class);
		query.setParameter("projectId", Integer.parseInt(in.getProjectId()));
		
		if(map.size() > 0){
			Set<String> set = map.keySet();
			for (String str : set) {
				query.setParameter(str, map.get(str));
			}
		}
		query.setFirstResult(page.getPageNo() <= 0?0:(page.getPageNo() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		
		List<FuzzyKeywordDto> res = query.getResultList();
		
		/**
		 * 根据关键词id和项目id、是否品牌词、分组id等，获取模糊查询总条数
		 */
		StringBuffer countSql = new StringBuffer(" select count(1) "
				+ " FROM ProKeyword spk, Keyword k LEFT JOIN GroKeyRel gkr ON spk.proKeyRelId = gkr.proKeyRelId and gkr.delFlag='0' WHERE spk.projectId = :projectId \n" +
				" and k.keywordId = spk.keywordId AND spk.delFlag = '0' ");
		
		map = new HashMap<String, Object>();

		
		//原来是根据关键词id来查询的，现在修改为对关键词名称进行模糊查询
		if(null != in.getKeyword() && !"".equals(in.getKeyword()) && in.getKeyword().size() > 0){
//			jpaSql = jpaSql + " and spk.keywordId IN :keywordIds ";
//			map.put("keywordIds", in.getKeyword());
			countSql.append(" and k.keywordName like :keywordName ");
			map.put("keywordName", "%" + in.getKeyword().get(0) + "%");
		}
		
		if(null !=in.getGroups() && !"".equals(in.getGroups()) && in.getGroups().size()>0){
			countSql.append(" and gkr.groupId in :groups ");
			map.put("groups", groupList);
		}
		
		if(null != in.getBranded() && !"".equals(in.getBranded())){
			countSql.append(" and spk.isBrand = :isBrand ");
			map.put("isBrand", in.getBranded());
		}
		
		Query countQuery = entity.createQuery(countSql.toString());
		countQuery.setParameter("projectId", Integer.parseInt(in.getProjectId()));
		
		if(map.size() > 0){
			Set<String> set = map.keySet();
			for (String str : set) {
				countQuery.setParameter(str, map.get(str));
			}
		}
		
		Object count = countQuery.getSingleResult();
		page.setCount(StringUtils.toLong(count));
		
		/**
		 * 根据项目id获取项目下的所有的关键词分组信息
		 */
		String sql= " select NEW com.ido85.master.keyword.dto.GroKeyRelDto(r.groupKeyId, g.groupName, g.groupId, spk.proKeyRelId, spk.keywordId)"
				+"from Group g, GroKeyRel r, ProKeyword spk " +
				"WHERE spk.proKeyRelId = r.proKeyRelId and r.groupId = g.groupId and g.projectId = :projectId \n" +
				"AND g.delFlag = '0' and r.delFlag = '0' and spk.delFlag = '0' ";
		
		
		Query queryGroup = entity.createQuery(sql, GroKeyRelDto.class);
		queryGroup.setParameter("projectId", Integer.parseInt(in.getProjectId()));
		
		List<GroKeyRelDto> groKeyRelDtos = null;
		if(null != queryGroup.getResultList() && queryGroup.getResultList().size() > 0){
			groKeyRelDtos = queryGroup.getResultList();
		}else {
			return res;
		}
		
		Group group = null;
		List<Group> groups = null;
		int flag = 0;
		for (int i = 0; i < res.size(); i++) {
			FuzzyKeywordDto temp = res.get(i);
			groups = new ArrayList<Group>();
			for (int j = 0,lenth = groKeyRelDtos.size(); j < lenth; j++) {
				if(temp.getKeywordId().equals(groKeyRelDtos.get(j).getKeywordId())){
					group = new Group();
					group.setGroupId(Integer.parseInt(groKeyRelDtos.get(j).getGroupId()));
					group.setGroupName(groKeyRelDtos.get(j).getGroupName());
					groups.add(group);
				}
			}
			if(null != in.getGroups() && in.getGroups().size() > 0){
				for (int j = 0; j < in.getGroups().size(); j++) {
					for (int k = 0; k < groups.size(); k++) {
						if(groups.get(k).getGroupId().equals(in.getGroups().get(j))){
							flag = 1;
							break;
						}
					}
					if(flag == 1)
						break;
				}
				if(flag == 1){
					temp.setGroups(groups);
					flag = 0;
				}else{
					res.remove(i);
					i--;
				}
			}else {
				temp.setGroups(groups);
			}
		}

		return res;
	}
	
	@Override
	@Transactional
	public boolean batckSaveGroupKeywordRel(List<GroupKeyRel> groupKeyRel) {
//		boolean res = false;
//		try {
//			if(null != groupKeyRel && groupKeyRel.size() > 0){
//				for (int i = 0; i < groupKeyRel.size(); i++) {
////					groupKeyRel.get(i).preInsert();
//				}
//				groupKeyRelResources.save(groupKeyRel);
//				res = true;
//			}else {
//				res = true;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			res = false;
//		}
//		return res;
		return true;
	}
	@Override
	@Transactional
	public boolean batckSaveGroupKeywordRel(List<String> groupIds, List<String> proKeyRelIds) {
		boolean res = false;
		try {
			if(null != groupIds && groupIds.size() > 0 && null != proKeyRelIds && proKeyRelIds.size() > 0){
				List<GroKeyRel> list = new ArrayList<GroKeyRel>();
				List<Integer> groupIdParams = new ArrayList<>();
				for(String str : groupIds){
					Integer groupId = Integer.parseInt(str);
					groupIdParams.add(groupId);
				}
				//根据分组id，获取所有此分组和关键词的关系
				List<GroKeyRel> groKeyRels = groKeyRelResources.getAllRelByGroupId(groupIdParams);
				GroKeyRel temp = null;
				int flag = 0;
				for (int i = 0; i < groupIds.size(); i++) {
					for (int j = 0; j < proKeyRelIds.size(); j++) {
						if(null != groKeyRels && groKeyRels.size() > 0){
							for (int k = 0; k < groKeyRels.size(); k++) {
								if (groKeyRels.get(k).getGroupId().toString().equals(groupIds.get(i)) 
										&& groKeyRels.get(k).getProKeyRelId().toString().equals(proKeyRelIds.get(j))){
									flag = 1;
									break;
								}
							}
						}
						if(flag > 0){
							flag = 0;
							continue;
						}
						temp = new GroKeyRel();
						Integer id = masterSequenceUtil.getCommonSeq();
						temp.setGroupKeyId(id);
						temp.setTenantId(RestUserUtils.getTencentId());
						temp.setDelFlag("0");
						temp.setCreateDate(new Date());
						temp.setGroupId(Integer.parseInt(groupIds.get(i)));
						temp.setProKeyRelId(Integer.parseInt(proKeyRelIds.get(j)));
						temp.preInsert();
						list.add(temp);
					}
				}
				groKeyRelResources.save(list);
				res = true;
			}else {
				res = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			res = false;
		}
		return res;
	}
	
	@Override
	@Transactional
	public int setKeywordBrand(String projectId, List<String> keywordIds) {
		List<Integer> keywordList = new ArrayList<>();
		if(keywordIds != null && keywordIds.size()>0){
			for(String str : keywordIds){
				Integer keyword = Integer.parseInt(str);
				keywordList.add(keyword);
			}
		}
		return proKeyRes.setKeywordBrand(Integer.parseInt(projectId), keywordList);
	}
	
	@Override
	public Resource<Object> getKeywordRankBaseInfo(InKeyRankDto in) {
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("keywordList", null);
//		if(null != in.getKeywordList() && in.getKeywordList().size() > 0){
//			map.put("keywordList", in.getKeywordList().toString());
//		}
//		map.put("projectId", in.getProjectId()); 
//		map.put("engineType", in.getEngineType());
//		map.put("endTime", in.getEndTime());
//		map.put("startTime", in.getStartTime());
//		map.put("visibility", in.getVisibility());
//		
//		String resInfo = HttpUtils.post(http.getKeywordRankBaseInfo(), map);
//		log.info("llz===getKeyRankBaseInfo=={}", resInfo);
//		if(ResourceUtils.checkResult(resInfo)){
//			return new Resource<Object>(JsonUtil.getJsonValue(resInfo, "data"));
//		}else if(ResourceUtils.checkResultData(resInfo)){
//			return new Resource<Object>(prop.getProcessStatus("RETURN_IS_NULL"));
//		}
//		return new Resource<Object>(prop.getProcessStatus("QUERY_ERROR"));
		return null;
	}

	@Override
	public Map<String, Object> getRankSearchVisibility(InRankSearchVisiDto in) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("keywordList", null);
		if(null != in.getKeywordList() && in.getKeywordList().size() > 0){
			map.put("keywordList", in.getKeywordList().toString());
		}
		map.put("projectId", in.getProjectId()); 
		map.put("engineType", in.getEngineType());
		map.put("endTime", in.getEndTime());
		map.put("startTime", in.getStartTime());
		map.put("isWeek", in.getIsWeek());
		
//		String resInfo = HttpUtils.post(http.getRankSearchVisibility(), map);
		String resInfo ="";
		log.info("llz===getRankSearchVisibility=={}", resInfo);
		if(ResourceUtils.checkResult(resInfo)){
			return (Map<String, Object>)((Resource)JsonUtil.jsonToBeanDateSerializer(resInfo, Resource.class, "yyyy-MM-dd")).getData();
		}else if(ResourceUtils.checkResultData(resInfo)){
			return null;
		}
		return null;
	}
	@Override
	public List<GroKeyRel> getAllRelByGroupId(List<Integer> groupIds) throws Exception {
		return groKeyRelResources.getAllRelByGroupId(groupIds);
	}
	@Override
	public List<Integer> getProKeyById(List<Integer> proKeyIds) {
		return proKeyRes.getProKeyById(proKeyIds);
	}
	@Override
	public Resource<Object> getRanks(InRankSearchVisiDto in) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("keywordList", null);
		if(null != in.getKeywordList() && in.getKeywordList().size() > 0){
			map.put("keywordList", in.getKeywordList().toString());
		}
		map.put("projectId", in.getProjectId()); 
		map.put("engineType", in.getEngineType());
		map.put("endTime", in.getEndTime());
		map.put("startTime", in.getStartTime());
		map.put("isWeek", in.getIsWeek());
		
//		String resInfo = HttpUtils.post(http.getRanks(), map);
		String resInfo="";
		log.info("llz===getRanks=={}", resInfo);
		if(ResourceUtils.checkResult(resInfo)){
			return new Resource<Object>(JsonUtil.getJsonValue(resInfo, "data"));
		}else if(ResourceUtils.checkResultData(resInfo)){
			return new Resource<Object>(prop.getProcessStatus("RETURN_IS_NULL"));
		}
		return new Resource<Object>(prop.getProcessStatus("QUERY_ERROR"));
	}
	@Override
	public Resource<Object> getAllKeywordsRanks(InKeyRankDto in) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("keywordList", null);
		if(null != in.getKeywordList() && in.getKeywordList().size() > 0){
			map.put("keywordList", in.getKeywordList().toString());
		}
		map.put("projectId", in.getProjectId()); 
		map.put("engineType", in.getEngineType());
		map.put("endTime", in.getEndTime());
		map.put("startTime", in.getStartTime());
		
//		String resInfo = HttpUtils.post(http.getAllKeywordsRanks(), map);
		String resInfo ="";
		log.info("llz===getAllKeywordsRanks=={}", resInfo);
		if(ResourceUtils.checkResult(resInfo)){
			return new Resource<Object>(JsonUtil.getJsonValue(resInfo, "data"));
		}else if(ResourceUtils.checkResultData(resInfo)){
			return new Resource<Object>(prop.getProcessStatus("RETURN_IS_NULL"));
		}
		return new Resource<Object>(prop.getProcessStatus("QUERY_ERROR"));
	}
	@Override
	public Resource<Object> getAllEngineVisibility(InRankSearchVisiDto in) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("keywordList", null);
		if(null != in.getKeywordList() && in.getKeywordList().size() > 0){
			map.put("keywordList", in.getKeywordList().toString());
		}
		map.put("projectId", in.getProjectId()); 
		map.put("engineType", in.getEngineType());
		map.put("endTime", in.getEndTime());
		map.put("startTime", in.getStartTime());
		map.put("isWeek", in.getIsWeek());
		
//		String resInfo = HttpUtils.post(http.getAllEngineVisibility(), map);
		String resInfo ="";
		log.info("llz===getAllEngineVisibility=={}", resInfo);
		if(ResourceUtils.checkResult(resInfo)){
			return new Resource<Object>(JsonUtil.getJsonValue(resInfo, "data"));
		}else if(ResourceUtils.checkResultData(resInfo)){
			return new Resource<Object>(prop.getProcessStatus("RETURN_IS_NULL"));
		}
		return new Resource<Object>(prop.getProcessStatus("QUERY_ERROR"));
	}
	@Override
	public Resource<Object> getDiffEngineKeywordsRanks(InKeyRankDto in) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("keywordList", null);
		if(null != in.getKeywordList() && in.getKeywordList().size() > 0){
			map.put("keywordList", in.getKeywordList().toString());
		}
		map.put("projectId", in.getProjectId()); 
		map.put("engineType", in.getEngineType());
		map.put("endTime", in.getEndTime());
		map.put("startTime", in.getStartTime());
		
//		String resInfo = HttpUtils.post(http.getDiffEngineRanks(), map);
		String resInfo ="";
		log.info("llz===getDiffEngineKeywordsRanks=={}", resInfo);
		if(ResourceUtils.checkResult(resInfo)){
			return new Resource<Object>(JsonUtil.getJsonValue(resInfo, "data"));
		}else if(ResourceUtils.checkResultData(resInfo)){
			return new Resource<Object>(prop.getProcessStatus("RETURN_IS_NULL"));
		}
		return new Resource<Object>(prop.getProcessStatus("QUERY_ERROR"));
	}
	@Override
	public Resource<Object> getRankingAnalysis(InRankSearchVisiDto in) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("keywordList", null);
		if(null != in.getKeywordList() && in.getKeywordList().size() > 0){
			map.put("keywordList", in.getKeywordList().toString());
		}
		map.put("projectId", in.getProjectId()); 
		map.put("engineType", in.getEngineType());
		map.put("endTime", in.getEndTime());
		map.put("startTime", in.getStartTime());
		
//		String resInfo = HttpUtils.post(http.getRankingAnalysis(), map);
		String resInfo ="";
		log.info("llz===getRankingAnalysis=={}", resInfo);
		if(ResourceUtils.checkResult(resInfo)){
			return new Resource<Object>(JsonUtil.getJsonValue(resInfo, "data"));
		}else if(ResourceUtils.checkResultData(resInfo)){
			return new Resource<Object>(prop.getProcessStatus("RETURN_IS_NULL"));
		}
		return new Resource<Object>(prop.getProcessStatus("QUERY_ERROR"));
	}
	
	@Override
	public List<OutKeyRankNumItemDto> getAllSearchResult(InRankSearchVisiDto in) {
		List<OutKeyRankNumItemDto> res = null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("keywordList", null);
		if(null != in.getKeywordList() && in.getKeywordList().size() > 0){
			map.put("keywordList", in.getKeywordList().toString());
		}
		map.put("projectId", in.getProjectId()); 
		map.put("engineType", in.getEngineType());
		map.put("endTime", in.getEndTime());
		map.put("startTime", in.getStartTime());
		map.put("name", in.getProjectName());
		map.put("isWeek", in.getIsWeek());
		
//		String resInfo = HttpUtils.post(http.getAllSearchResult(), map);
		String resInfo ="";
		log.info("llz===getAllSearchResult=={}", resInfo);
		if(ResourceUtils.checkResult(resInfo)){
			List<Map<String, String>> resList = JsonUtil.jsonToListStr(JsonUtil.getJsonValue(resInfo, "data") + "");
			if(null != resList && resList.size() > 0){
				res = new ArrayList<OutKeyRankNumItemDto>();
				OutKeyRankNumItemDto item = null;
				for (int i = 0, len = resList.size(); i < len; i++) {
					item = new OutKeyRankNumItemDto();
					item.setCrawlTime(resList.get(i).get("crawlTime"));
					item.setId(resList.get(i).get("id"));
					item.setIsCom(resList.get(i).get("isCom"));
					item.setName(resList.get(i).get("name"));
					item.setNum(resList.get(i).get("num"));
					res.add(item);
				}
			}
			
			return res;
		}else if(ResourceUtils.checkResultData(resInfo)){
			return null;
		}
		return null;
	}
	
	/*
	 * **********上方是应用服务私有方法不对别的模块和别的应用直接提供提供***********
	 */
	
	/*chechExistKeyword
	 * ********************此处之下是service服务层调用的公共方法，若要修改，请慎重操作*********************
	 */
	
	@Override
	public boolean chechGroups(List<String> groups, String projectId) {
		// 校验项目分组是否存在
		boolean res = false;
		List<Integer> list = new ArrayList<Integer>();
		if(groups != null && groups.size()>0){
			for(String str:groups){
				list.add(Integer.parseInt(str));
			}
		}
		if(groupResources.chechGroups(list, Integer.parseInt(projectId)) == groups.size())
			res = true;
		return res;
	}
	
	@Override
	public int checkProExistKeyword(List<String> keywords, String projectId) {
		int res = 0;
		List<Integer> keywordList = new ArrayList<Integer>();
		if(keywords != null && keywords.size()>0){
			for(String str : keywords){
				Integer keyword = Integer.parseInt(str);
				keywordList.add(keyword);
			}
		}
		List<ProKeyword> list = proKeyRes.getProKeywordInfo(Integer.parseInt(projectId), keywordList);
		if(null != list && list.size() > 0){
			return list.size();
		}
		return res;
	}
	
	@Override
	public int getProjectKeywordNum(String projectId) {
		return proKeyRes.getProjectKeywordNum(Integer.parseInt(projectId));
	}
	/**
	 * 查询所有的关键词
	 * @return
	 */
	public List<Keyword> getKeyWords() {
		return keywordRes.getKeyWords();
	}
	
	@Override
	public List<Keyword> getProKeyWords(String projectId) {
		return keywordRes.getProKeyWords(Integer.parseInt(projectId));
	}
	@Override
	public List<String> getProKeyWords(String projectId, String brand) {
//		return keywordRes.getProKeyWords(projectId, brand);
		return null;
	}
	/***
	 * 批量保存项目的关键词，首先校验关键词是否已经添加到了项目中
	 * @param prokeywords
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void batchSaveProKeyword(List<ProKeyword> prokeywords) throws Exception{
		//TODO 首先校验关键词是否已经添加到了项目中
		if(null != prokeywords && prokeywords.size() > 0){
			for (int i = 0; i < prokeywords.size(); i++) {
				prokeywords.get(i).preInsert();
			}
			proKeyRes.save(prokeywords);
		}
	}
	@Transactional
	public boolean batchSaveKeywords(List<Keyword> keywords) throws Exception {
		boolean res = false;
		if(null != keywords && keywords.size() > 0){
			for (int i = 0; i < keywords.size(); i++) {
				if(keywords.get(i).getKeywordName().length() <= 0){
					keywords.remove(i);
					i--;
					continue;
				}else if(keywords.get(i).getKeywordName().length() >= 76){
					throw BusinessException.getErrorMsg(keywords.get(i).getKeywordName(), prop.getProcessStatus("KEYWORD_LEN_ERROR"));
				}
				keywords.get(i).setKeywordName(keywords.get(i).getKeywordName().trim());
				keywords.get(i).preInsert();
			}
			List<Keyword> result = keywordRes.save(keywords);
			for (Keyword keyword : result) {
				CacheUtils.put(Constants.KEYWORD_CACHE, keyword.getKeywordName(), keyword);
				res = true;
			}
		}else
			res = true;
			
		return res;
	}
		
	@Transactional
	public boolean saveKeyword(Keyword keywords) throws Exception {
		//校验关键词是否已经存在
		keywords.preInsert();
		entity.persist(keywords);
		CacheUtils.put("keywordCache", keywords.getKeywordName().hashCode() + "", keywords.getKeywordId());
		return true;
	}
	@Override
	public List<Group> getAllProGroups(String projectId) {
		return groupResources.getAllProGroups(Integer.parseInt(projectId));
	}
	@Override
	public List<ProKeyword> getAllProKeyword(Integer projectId) {
		return proKeyRes.getKeyWords(projectId);
	}	
}
