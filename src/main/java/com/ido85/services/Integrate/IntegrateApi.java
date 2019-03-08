package com.ido85.services.Integrate;

public interface IntegrateApi {
	
	/**
	 * 判断项目是否还可以添加关键词，
	 * 并且返回项目添加完此次关键词之后还能够添加多少个关键词
	 * @param projectId
	 * @param keywordNum 
	 * @param leftNum  添加完此次关键词之后还能够添加多少个关键词
	 * @return 
	 */
	public boolean checkAddKeyword(String projectId, int keywordNum, int leftNum);
}
