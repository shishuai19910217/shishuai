package com.ido85.master.keyword.application;



import java.util.List;
import java.util.Map;

import com.ido85.frame.common.Page;
import com.ido85.frame.common.restful.Resource;
import com.ido85.master.keyword.domain.GroKeyRel;
import com.ido85.master.keyword.domain.Group;
import com.ido85.master.keyword.domain.GroupKeyRel;
import com.ido85.master.keyword.domain.Keyword;
import com.ido85.master.keyword.domain.ProKeyword;
import com.ido85.master.keyword.dto.FuzzyKeywordDto;
import com.ido85.master.keyword.dto.InAddKeywordDto;
import com.ido85.master.keyword.dto.InKeyRankDto;
import com.ido85.master.keyword.dto.InKeywordDto;
import com.ido85.master.keyword.dto.InKeywordRankDto;
import com.ido85.master.keyword.dto.InProjectKeywordsDto;
import com.ido85.master.keyword.dto.InRankSearchVisiDto;
import com.ido85.master.keyword.dto.OutKeyRankNumItemDto;

/**
 * 关键词应用服务业务逻辑层
 * @author fire
 *
 */
public interface KeywordApplication  {
	/**
	 * 保存关键词
	 * @param keywords
	 * @return
	 */
	boolean saveKeyword(Keyword keywords) throws Exception;
	/**
	 * 批量保存关键词
	 * @param keywords
	 * @return
	 */
	boolean batchSaveKeywords(List<Keyword> keywords)throws Exception;

//	/**
//	 * 批量保存关键词  单独使用关键词名称
//	 * @param keywords
//	 * @return
//	 */
//	boolean batchSaveKeywords(List<Keyword> keywords);
	/**
	 * 保存关键词与项目的关系
	 * @param proKeyword
	 * @return
	 */
	boolean saveProKeyword(ProKeyword proKeyword);
	/**
	 * 批量保存关键词与项目的关系，首先校验关键词是否已经添加到了项目中
	 * @param proKeyword
	 * @return
	 */
	List<ProKeyword> batchSaveNewProKeywords(List<String> keywords, String projectId) throws Exception ;
	/**
	 * 保存关键词的分组
	 * @param groupKeyRel
	 * @return
	 */
	Resource<String> saveGroKeyword(GroupKeyRel groupKeyRel);
	/**
	 * 保存关键词的分组
	 * @param newProkeywordRelIds 
	 * @param groupId
	 * @param projectId
	 * @param flag 是否需要校验关键词是否已经添加到项目中
	 * @return
	 */
	Resource<String> batchSaveGroKeyword(List<String> newProkeywordRelIds, List<String> groupId, String projectId, String flag) throws Exception;
	/**
	 * 批量保存关键词的分组
	 * @param groupKeyRel
	 * @return
	 */
	boolean batchSaveGroKeyword(GroupKeyRel groupKeyRel);
	/**
	 * 添加关键词并且进行分组,进行事务管理   
	 * @param inKeywordDto
	 * @return
	 */
	void addKeywordAndGroup(InAddKeywordDto inKeywordDto) throws Exception;
	/***
	 * 获取所有的关键词
	 * @return
	 */
	public List<Keyword> getKeyWords();
	/**
	 * 获取项目下所有的关键词
	 * @param projectId
	 * @return
	 */
	public List<Keyword> getProKeyWords(String projectId);
	/**
	 * 根据项目id和品牌 获取项目下所有的关键词id
	 * @param projectId
	 * @return
	 */
	public List<String> getProKeyWords(String projectId, String brand);
	/**
	 * 校验项目分组是否全部存在，是 返回true
	 * @param groups 项目的分组的id
	 * @param projectId
	 * @return
	 */
	boolean chechGroups(List<String> groups, String projectId);
	
	/**
	 * 获取项目下的关键词的排名
	 * @param keywordId 关键词id
	 * @param projectId
	 * @return
	 */
	int getProKeywordRank(String keywordId, String projectId, String engine, String date);
	
	/**
	 * 将关键词添加到分组下
	 * @param keyword
	 * @param groupId
	 * @return
	 */
	boolean saveGroupKeywordRel(String keyword, String groupId);
	/**
	 * 将关键词添加到分组下
	 * @param keyword
	 * @param groupId
	 * @return
	 */
	boolean batckSaveGroupKeywordRel(List<GroupKeyRel> groupKeyRel);
	/**
	 * 将关键词添加到分组下
	 * @param keyword
	 * @param groupId
	 * @return
	 */
	boolean batckSaveGroupKeywordRel(List<String> groupIds, List<String> proKeyRelIds);
	
	/**
	 * 获取项目下的关键词的所有分组
	 * @param keyword
	 * @param projectId
	 * @return
	 */
	List<Group> getProKeywordGroup(String keyword, String projectId);
	
	/**
	 * 获取项目下的关键词的优化难度,优化难度是一个以字符 , 分割的字符串
	 * @param keyword
	 * @param projectId
	 * @return
	 */
	String getDifficultyNum(String keyword, String projectId) throws Exception;
	
	/**
	 * 校验关键词是否已经在字典表存在,将不存在的所有关键词以List<Keyword>方式返回
	 * @param keywords
	 * @return
	 * @throws Exception 
	 */
	List<Keyword> checkNewKeyword(List<String> keywords) throws Exception;
	
	/**
	 * 获取某一关键词的排名趋势变化
	 * @param in
	 * @return
	 */
	Resource<Object> getRankTrend(InKeywordRankDto in);
	
	/**
	 * 获取某一关键词的SERP搜索报告
	 * @param in
	 * @return
	 */
	Resource<Object> getSERPReport(InKeywordRankDto in);
	/**
	 *删除项目关键词接口 
	 * @param ids  keywordID   projectId
	 * @return
	 */
	int deleteKeywordByKeywordIds(Map<String, Object> param) throws Exception;
	/***
	 * 删除分组
	 * @param param
	 * @return
	 */
	int deleteGroup(Map<String, Object> param)throws Exception;
	/**
	 * 给项目添加分组
	 * @param group
	 * @return
	 */
	boolean addProjectGroup(List<Group> groups);
	/**
	 * 根据关键词id和项目id，批量获取项目下的关键词信息
	 * @param projectId
	 * @param keywordIds
	 * @return
	 */
	List<ProKeyword> getProKeywordInfo(String projectId, List<Integer> keywordIds);
	
	/**
	 * 根据关键词id和项目id，批量设置品牌
	 * @param projectId
	 * @param keywordIds
	 * @return
	 */
	int setKeywordBrand(String projectId, List<String> keywordIds);
	
	/**
	 * 模糊查询下拉列表
	 * @param projectId
	 * @param keywordIds
	 * @return
	 */
	List<FuzzyKeywordDto> getFuzzyKeyword(Page<FuzzyKeywordDto> page, InKeywordDto in);
	
	
	/***
	 * 删除关键词品牌和分组接口
	 * @param param
	 *          branded	是否删除关键词品牌	string	
				groupId	是否删除关键词分组	array<string>	
				keywords	所有要删除的关键词id	array<string>	数组
				projectId
	 * @return
	 */
	int deleteBrandOrGroup(Map<String, Object> param) throws Exception;
	/**
	 * 调用hbase计算用户选中的部分的关键词的搜索可见性/排名/前30
	 * @param in
	 * @return
	 */
	Resource<Object> getKeywordRankBaseInfo(InKeyRankDto in);
	/**
	 * 调用hbase计算用户选中的部分的关键词的本站的搜索可见性
	 * @param in
	 * @return
	 */
	Map<String, Object> getRankSearchVisibility(InRankSearchVisiDto in);
	/**
	 * 根据分组id获取项目的分组的所有的关键词
	 * @param groupIds
	 * @return
	 */
	List<GroKeyRel> getAllRelByGroupId(List<Integer> groupIds) throws Exception;
	
	/**
	 * 根据项目和关键词关系id 获取项目的所有的关键词id
	 * @param proKeyIds
	 * @return
	 */
	List<Integer> getProKeyById(List<Integer> proKeyIds);
	/**
	 * 调用hbase计算用户选中的部分的关键词在某一搜索引擎、某一时间点的排名
	 * @param in
	 * @return
	 */
	Resource<Object> getRanks(InRankSearchVisiDto in);
	/**
	 * 调用hbase计算用户选中的部分的关键词在某一搜索引擎、某一时间点的排名详情
	 * @param in
	 * @return
	 */
	Resource<Object> getAllKeywordsRanks(InKeyRankDto in);
	/**
	 * 调用hbase计算用户选中的部分的关键词在某一搜索引擎、某一时间点的排名详情
	 * @param in
	 * @return
	 */
	Resource<Object> getAllEngineVisibility(InRankSearchVisiDto in);
	/**
	 * 调用hbase计算用户选中的部分的关键词在不同搜索引擎、某一时间点的排名详情
	 * 排名-----所选关键词不同两个搜索引擎的排名  接口
	 * @param in
	 * @return
	 */
	Resource<Object> getDiffEngineKeywordsRanks(InKeyRankDto in);
	
	/**
	 * 排名-----所选关键词排名竞争分析
	 * @param in
	 * @return
	 */
	Resource<Object> getRankingAnalysis(InRankSearchVisiDto in);
	/**
	 * 排名-竞争对手tab下排名图表
	 * 274
	 * @param in
	 * @return
	 */
	List<OutKeyRankNumItemDto> getAllSearchResult(InRankSearchVisiDto in);
	
	/*
	 * **********上方是应用服务私有方法不对别的模块和别的应用直接提供提供***********
	 */
	
	/*
	 * ********************此处之下是service服务层调用的公共方法，若要修改，请慎重操作*********************
	 */
	
	/**
	 * 校验关键词是否在项目中全部存在,返回项目中存在的关键词个数
	 * @param keyword
	 * @return
	 */
	int checkProExistKeyword(List<String> keywords, String projectId);
	/**
	 * 获取项目的所有关键词的总数
	 * @param projectId
	 * @return
	 */
	int getProjectKeywordNum(String projectId);
	
	/***
	 * 批量保存项目的关键词
	 * @param prokeywords
	 * @return
	 */
	public void batchSaveProKeyword(List<ProKeyword> prokeywords) throws Exception;
	/**
	 * 获取项目下的所有分组
	 * @param projectId
	 * @return
	 */
	List<Group> getAllProGroups(String projectId);
	/**
	 * 获取项目下的所有分组
	 * @param projectId
	 * @return
	 */
	List<ProKeyword> getAllProKeyword(Integer projectId);
	
	/**根据：是否品牌词、分组--获取项目下的关键词
	 * @param In
	 * @return
	 */
	List<FuzzyKeywordDto> getProjectKeyword(InProjectKeywordsDto In);
	
}
