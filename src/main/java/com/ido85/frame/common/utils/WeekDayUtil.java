package com.ido85.frame.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/** 
 * 获取某一时间段特定星期几的日期 
 * @author finder.zhou 
 */  
public class WeekDayUtil {  
	//    public static void main(String[] args) {  
	//    	getWeekDay("2016-06-05", "2016-06-19","7");  
	//    }  

	/** 
	 * 获取某一时间段特定星期几的日期 
	 * @param dateFrom 开始时间 
	 * @param dateEnd 结束时间 
	 * @param weekDays 星期 
	 * @return 返回时间数组 
	 */  
	@SuppressWarnings("rawtypes")
	public static List getWeekDay(String dateFrom, String dateEnd, String weekDays) {  
		long time = 1l;  
		long perDayMilSec = 24 * 60 * 60 * 1000;  
		List<String> dateList = new ArrayList<String>();  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		//需要查询的星期系数  
		String strWeekNumber = weekForNum(weekDays);  
		try {  
			dateFrom = sdf.format(sdf.parse(dateFrom).getTime() - perDayMilSec);  
			while (true) {  
				time = sdf.parse(dateFrom).getTime();  
				time = time + perDayMilSec;  
				Date date = new Date(time);  
				dateFrom = sdf.format(date);  
				if (dateFrom.compareTo(dateEnd) <= 0) {  
					//查询的某一时间的星期系数  
					Integer weekDay = dayForWeek(date);                      
					//判断当期日期的星期系数是否是需要查询的  
					if (strWeekNumber.indexOf(weekDay.toString())!=-1) {  
						dateList.add(dateFrom);  
					}  
				} else {  
					break;  
				}  
			}  
		} catch (ParseException e1) {  
			e1.printStackTrace();  
		}  
		return dateList;  
	}  

	//等到当期时间的周系数。星期日：1，星期一：2，星期二：3，星期三：4，星期四：5，星期五：6，星期六：7  
	public static Integer dayForWeek(Date date) {  
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);  
		return calendar.get(Calendar.DAY_OF_WEEK);  
	}  

	/** 
	 * 得到对应星期的系数  星期日：1，星期一：2，星期二：3，星期三：4，星期四：5，星期五：6，星期六：7 
	 * @param weekDays 星期格式  星期一|星期二 
	 */  
	public static String weekForNum(String weekDays){  
		//返回结果为组合的星期系数  
		String weekNumber = "";  
		//解析传入的星期  
		if(weekDays.indexOf("|")!=-1){//多个星期数  
			String []strWeeks = weekDays.split("\\|");  
			for(int i=0;i<strWeeks.length;i++){  
				weekNumber = weekNumber + "" + getWeekNum(strWeeks[i]).toString();  
			}  
		}else{//一个星期数  
			weekNumber = getWeekNum(weekDays).toString();  
		}  

		return weekNumber;  

	}  

	//将星期转换为对应的系数  星期日：1，星期一：2，星期二：3，星期三：4，星期四：5，星期五：6，星期六：7  
	public static Integer getWeekNum(String strWeek){  
		Integer number = 1;//默认为星期日  
		if("7".equals(strWeek)){  
			number = 1;  
		}else if("1".equals(strWeek)){  
			number = 2;  
		}else if("2".equals(strWeek)){  
			number = 3;  
		}else if("3".equals(strWeek)){  
			number = 4;  
		}else if("4".equals(strWeek)){  
			number = 5;  
		}else if("5".equals(strWeek)){  
			number = 6;  
		}else if("6".equals(strWeek)){  
			number = 7;  
		}else{  
			number = 1;  
		}  
		return number;  
	}  

}  