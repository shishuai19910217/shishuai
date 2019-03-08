package com.ido85.seo.time.application.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.ido85.frame.common.utils.DateUtils;
import com.ido85.seo.common.TimeUntil;
import com.ido85.seo.time.application.TimeApplication;
import com.ido85.seo.time.resources.ProjectCrawlRepository;

@Named
public class TimeApplicationImpl implements TimeApplication {
	@Inject
	private ProjectCrawlRepository crawlDateRes;
	
	
	@Override
	public List<Date> getCrawlDate(Date startTime, Date endTime,
			Integer projectId, String flag) throws Exception {
		if(null == startTime || null == endTime){
			return null;
		}
		if(startTime.compareTo(endTime) > 0){
			return null;
		}
		List<Date> dates = crawlDateRes.getCrawlDate(startTime, endTime, projectId);
		if(null != dates && dates.size() > 0){
			//调用工具类，返回对应的时间点
			return TimeUntil.timeByCycle(DateUtils.formatDate(startTime), DateUtils.formatDate(endTime), dates, flag);
		}
		return null;
	}


	@Override
	public List<Date> getNewestCrawlDate(Integer projectId, Integer limitNum)
			throws Exception {
		List<Date> dates = crawlDateRes.getNewestCrawlDate(projectId);
		if(null != dates && dates.size() > 0){
			if(dates.size() > limitNum){
				dates = dates.subList(0, limitNum);
			}
			//判断是否返回值是否已经超limitNum天
			if(DateUtils.getDistanceOfTwoDate(dates.get(0), dates.get(dates.size() - 1)) >= limitNum){
				Date startTime = DateUtils.getDateAfterDayByDate(limitNum, dates.get(dates.size() - 1));
				for (int i = 0; i < dates.size(); i++) {
					if(dates.get(i).compareTo(startTime) < 0){
						dates.remove(i);
						i--;
					}
				}
			}
			return dates;
		}
		return null;
	}
	
}
