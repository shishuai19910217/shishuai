package com.ido85.services.time.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.ido85.frame.common.utils.DateUtils;
import com.ido85.seo.time.application.TimeApplication;
import com.ido85.services.time.TimeInsideApi;

@Named
public class TimeInsideApiImpl implements TimeInsideApi {
	@Inject
	private TimeApplication timeApp;

	@Override
	public List<Date> getCrawlDate(Date startTime, Date endTime,
			Integer projectId, String flag) throws Exception {
		if("1".equals(flag)){//根据新需求按周展示,要取到当前周的最后一天
			endTime = DateUtils.getLastDayOfWeek(endTime);
		}else if ("2".equals(flag)) {////根据新需求按月展示,要取到当前月的最后一天
			endTime = DateUtils.getLastDayOfMonth(endTime);
		}
		return timeApp.getCrawlDate(startTime, endTime, projectId, flag);
	}
	@Override
	public List<Date> getCrawlDateForRank(Date startTime, Date endTime,
			Integer projectId, String flag) throws Exception {
		//按月
//		if("2".equals(flag)){
//			if(DateUtils.isSameMonth(new Date(), DateUtils.parseDate(startTime))){
//				return null;
//			}
//			startTime = DateUtils.getFirstDayOfMonth(startTime);
//			if(DateUtils.isSameMonth(new Date(), DateUtils.parseDate(endTime))){
//				endTime = DateUtils.getLastDayOfLastMonth(endTime);
//			}
//		}
		return this.getCrawlDate(startTime, endTime, projectId, flag);
	}

	@Override
	public List<Date> getNewestCrawlDate(Integer projectId,
			Integer limitNum) throws Exception {
		return timeApp.getNewestCrawlDate(projectId, limitNum);
	}

	@Override
	public List<Date> getTimesByIsWeek(Integer projectId,String isWeek, int num) throws Exception {
		Date startTime = null; 
		
		Date endTime = new Date();//要取到当前周的最后一天
		String flag =null;
		if(isWeek.equals("0")){// 1：按周 2 按月
			startTime = DateUtils.addWeeks(endTime, -num);
			flag = "1";
		}else if (isWeek.equals("1")) {
			startTime = DateUtils.addMonth(endTime, -num);
			flag = "2";
		}
		return this.getCrawlDate(startTime, endTime, projectId, flag);
	}

	
	
}
