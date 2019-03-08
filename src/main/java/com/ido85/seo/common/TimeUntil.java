package com.ido85.seo.common;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ido85.frame.common.utils.BusinessDateUtil;
import com.ido85.frame.common.utils.DateUtils;

public class TimeUntil {
	/***
	 * 时间处理
	 * @param startTime
	 * @param endTime
	 * @param crawlTimes
	 * @param cycle 0:天，1：周，2：月
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Date> timeByCycle(String startTime,String endTime, 
			List<Date> crawlTimes,String cycle){
		
		List<Date> realCrawlTimes = null;
		
		if(cycle.equals("0")){
			realCrawlTimes = crawlTimes;
		}else{
			List<Date> temp = null;
			try {
				temp = BusinessDateUtil.getBusinessdate(startTime, 
						endTime, crawlTimes, Integer.parseInt(cycle));//1  按周 2按月  
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if(temp != null && temp.size() > 0){
				realCrawlTimes = new ArrayList<Date>();
				for(Date date : temp){
					if(null != date && !"".equals(date)){
						realCrawlTimes.add(date);
					}
				}
			}
		}
		return realCrawlTimes;
	}
}
