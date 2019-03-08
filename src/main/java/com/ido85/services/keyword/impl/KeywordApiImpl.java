package com.ido85.services.keyword.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.ido85.frame.common.utils.ListUntils;
import com.ido85.master.keyword.application.KeywordApplication;
import com.ido85.master.keyword.domain.GroKeyRel;
import com.ido85.master.keyword.domain.Group;
import com.ido85.master.keyword.domain.Keyword;
import com.ido85.master.keyword.domain.ProKeyword;
import com.ido85.services.keyword.KeywordApi;

@Named
public class KeywordApiImpl implements KeywordApi{
	@Inject
	private KeywordApplication keywordApp;
	
	/**
	 * 查询所有的关键词
	 * @return
	 */
	@Override
	public List<Keyword> getKeyWords() {
		List<Keyword> list =  keywordApp.getKeyWords();
		return list;
	}
	
	/***
	 * 批量插入
	 * @param prokeywords
	 * @return
	 * @throws Exception 
	 */
	@Override
	@Transactional
	public void batchsaveProKeyword(List<ProKeyword> prokeywords) throws Exception{
		keywordApp.batchSaveProKeyword(prokeywords);
	}
	/***
	 * 批量插入关键词的字典表
	 * @param keywords
	 * @return
	 * @throws Exception 
	 */
	@Override
	@Transactional
	public boolean batchSaveKeywords(List<Keyword> keywords) throws Exception{
		return keywordApp.batchSaveKeywords(keywords);
	}
	@Override
	public boolean saveKeyword(Keyword keywords) throws Exception{
		return keywordApp.saveKeyword(keywords);
	}
	/**
	 * 根据项目id获取项目下所有有效的分组信息
	 * @param projectId
	 * @return
	 */
	@Override
	public List<Group> getAllProjectGroups(String projectId){
		return keywordApp.getAllProGroups(projectId);
	}
	/**
	 * 根据项目id获取项目下所有有效的关键词总数
	 * @param projectId
	 * @return
	 */
	@Override
	public int getProjectKeywordNum(String projectId){
		return keywordApp.getProjectKeywordNum(projectId);
	}

	@Override
	public List<Group> getProKeywordGroup(String projectId, String keywordId) {
		return keywordApp.getProKeywordGroup(keywordId, projectId);
	}

	@Override
	public List<Integer> getKeywordsInfo(Integer projectId, List<Integer> groupIds) throws Exception {
		List<GroKeyRel> groKeyRels = keywordApp.getAllRelByGroupId(groupIds);
		List<Integer> rel = new ArrayList<Integer>();
		if(!ListUntils.isNull(groKeyRels)){
			for (GroKeyRel groKeyRel : groKeyRels) {
				rel.add(groKeyRel.getProKeyRelId());
			}
			return keywordApp.getProKeyById(rel);
		}
		return null;
	}
	@Override
	public List<ProKeyword> getAllProKeyword(Integer projectId){
		return keywordApp.getAllProKeyword(projectId);
	}

}
