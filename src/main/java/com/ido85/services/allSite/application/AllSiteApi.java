package com.ido85.services.allSite.application;

import java.util.Map;

public interface AllSiteApi {
	/**
	* @Description: 获取项目爬取问题数量 和变化值 
	* @param @param project
	* @param @return    设定文件 
	* @return Map    返回类型 
	* @throws
	 */
	public Map getCrawInfo(int projectId) throws Exception;
}
