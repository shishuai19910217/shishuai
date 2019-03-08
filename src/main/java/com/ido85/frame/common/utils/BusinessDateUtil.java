package com.ido85.frame.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class BusinessDateUtil {

//	public static void main(String[] args) {
//		
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
//		
//		List days = new ArrayList();
//				//MonthDayUtil.dispeveyday("2016-04-01","2016-04-09");
//		days.add("2016-04-23"); // 6
//		days.add("2016-04-22"); // 5  
//		days.add("2016-05-14"); // 6
//		days.add("2016-05-20"); //
//		days.add("2016-05-02");
//		days.add("2016-05-29");
//		days.add("2016-06-05");
//		days.add("2016-06-01");
//		days.add("2016-06-20");
//		days.add("2016-06-26");
//		
//		List susinessdate;
//		try {
//			susinessdate = getBusinessdate("2016-04-08","2016-06-26",days,0);
//			for (int i=0;i<susinessdate.size();i++) {
//					if ( susinessdate.get(i)!=null) {
//						
//						System.out.println(sdf.format((Date)susinessdate.get(i)));
//					}
//				
//			}
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		
//
//	}
	
	/**
	 * 获取抓取时间
	 * @param fromday
	 * @param endDay
	 * @param days
	 * @param flag 1：按周返回，2：按月返回
	 * @return
	 * @throws ParseException
	 */
   public static List getBusinessdate(String fromday ,String endDay,List days,int flag) throws ParseException {
		//取出时间内每个月的最后一天
	  List dayList = null;
	  if(flag==1) {
		  dayList=WeekDayUtil.getWeekDay(fromday, endDay, "7");
	  }else {
		  dayList=MonthDayUtil.getMonthlastBydays(fromday,endDay);
	  }
	  
	  List resultList = new ArrayList ();
	  for (int i=0;i<dayList.size();i++) {
		  String monthLastDay = dayList.get(i)+"";
		  // 根据数据库中数据进行对比取最接近的一天
		  if(!resultList.contains(getMostNearDay(monthLastDay,days))) {
			  resultList.add(getMostNearDay(monthLastDay,days));
		  }
		
		 
	  }
		return resultList;
	}

   public static Date getMostNearDay(String LastDay,List days) throws ParseException {
	   Date nearday=null;
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	   Date lastday = sdf.parse(LastDay);
	   List dayList = getDescSortdateList(days);
	   
	   if(dayList != null && dayList.size() > 0) {
		   for(int i=0;i<dayList.size();i++) {
			   //首先取出比last小于等于的日期，如果等于，直接返回
			   Date day = (Date)dayList.get(i);
			   if (compareDate(lastday,day)>=0) {
				   nearday= day;
				  break;
			   }
	   }
	   }
	
	return nearday;
   
   }
   // 转换成日期并按倒序排列
   public static List getDescSortdateList(List days) throws ParseException {
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    List  dateList =new ArrayList ();
    for (int i=0;i<days.size();i++) {
    	dateList.add(sdf.parse(days.get(i).toString()));
    }
    
    Collections.sort(dateList,new Comparator(){
        public int compareto(Date arg0, Date arg1) {
            return arg0.compareTo(arg1);
        }

		@Override
		public int compare(Object d1, Object d2) {
			  if (((Date)d1).getTime() > ((Date)d2).getTime()) {
		           return -1;
		       } else if (((Date)d1).getTime() < ((Date)d2).getTime()) {
		           return 1;
		       } else {//相等
		           return 0;
		       }
		}
    });
	   return dateList;
   }
   
   
   
   //两个日期比较
   public static int compareDate(Date d1,Date d2){
       if (d1.getTime() > d2.getTime()) {
           return 1;
       } else if (d1.getTime() < d2.getTime()) {
           return -1;
       } else {//相等
           return 0;
       }
}
   

}
