package com.ido85.services.keyword;

import java.util.List;

import com.ido85.master.keyword.domain.Group;
import com.ido85.master.keyword.domain.Keyword;
import com.ido85.master.keyword.domain.ProKeyword;

/**
 * 关键词对本应用内公共接口类
 * @author fire
 */
public interface KeywordApi {
	/**
	 * 查询所有的关键词
	 * @return
	 */
	public List<Keyword> getKeyWords();
	/***
	 * 批量插入
	 * @param prokeywords
	 * @return
	 * @throws Exception 
	 */
	public void batchsaveProKeyword(List<ProKeyword> prokeywords) throws Exception;
	/***
	 * 批量插入关键词的字典表
	 * @param keywords
	 * @return
	 * @throws Exception 
	 */
	public boolean batchSaveKeywords(List<Keyword> keywords) throws Exception;
	public boolean saveKeyword(Keyword keywords) throws Exception;
	/**
	 * 根据项目id获取项目下所有有效的分组信息
	 * @param projectId
	 * @return
	 */
	public List<Group> getAllProjectGroups(String projectId);
	/**
	 * 根据项目id获取项目下所有有效的关键词总数
	 * @param projectId
	 * @return
	 */
	public int getProjectKeywordNum(String projectId);
	
	/**
	 * 根据关键词id和项目id获取关键词的所有的分组信息
	 * @param keywordId
	 * @return
	 */
	List<Group> getProKeywordGroup(String projectId, String keywordId) throws Exception;
	/**
	 * 根据项目id和关键词分组id获取所有的关键词信息
	 * @param projectId
	 * @param groupIds
	 * @return
	 */
	List<Integer> getKeywordsInfo(Integer projectId, List<Integer> groupIds) throws Exception;
	/**
	 * 根据项目id获取项目下所有有效的关键词
	 * @param projectId
	 * @return
	 */
	List<ProKeyword> getAllProKeyword(Integer projectId) throws Exception;
}
