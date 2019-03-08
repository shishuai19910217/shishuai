package com.ido85.seo.time.application;

import java.util.Date;
import java.util.List;

public interface TimeApplication {
	/**
	 * 获取项目在一段时间内的所有抓取日期,按周期返回
	 * @param startTime
	 * @param endTime
	 * @param projectId
	 * @param flag //0 按天 1 按周 2按月 3按年 必传 
	 * @return
	 * @throws Exception
	 */
	List<Date> getCrawlDate(Date startTime, Date endTime, Integer projectId, String flag) throws Exception;
	/**
	 * 获取项目最新的limitNum日抓取日期,按周期返回
	 * @param projectId
	 * @param flag //0 按天 1 按周 2按月 3按年 必传 
	 * @return
	 * @throws Exception
	 */
	List<Date> getNewestCrawlDate(Integer projectId, Integer limitNum) throws Exception;
	

}
