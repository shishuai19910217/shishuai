package com.ido85.frame.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

 
public class MonthDayUtil {  
//    public static void main(String[] args) throws ParseException {  
//    	List aa = getMonthlastBydays("2016-04-01","2016-06-21");
//    	for (int i=0;i<aa.size();i++) {
//    		System.out.println(aa.get(i));
//    	}
//    }  
  
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getMonthlastBydays(String fromDay,String endDay){
    	List monthLastDays = new ArrayList();
    	try {
			List aa = getMonthByDay(fromDay,endDay);
			for (int i=0;i<aa.size();i++) {
				monthLastDays.add(getLastday_Month(aa.get(i).toString()+"-01 00:00:00"));
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return monthLastDays;
    }
    
    /*
     * 按月获取时间段，结果是一个月中某一天
     */
    @SuppressWarnings("rawtypes")
	public static List getMonthByDay(String minDate,String maxDate ) throws ParseException {
    	  ArrayList<String> result = new ArrayList<String>();
    	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

    	    Calendar min = Calendar.getInstance();
    	    Calendar max = Calendar.getInstance();

    	    min.setTime(sdf.parse(minDate));
    	    min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

    	    max.setTime(sdf.parse(maxDate));
    	    max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

    	    Calendar curr = min;
    	    while (curr.before(max)) {
    	     result.add(sdf.format(curr.getTime()));
    	     curr.add(Calendar.MONTH, 1);
    	    }

    	    return result;
    }
    
    	@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List dispeveyday(String dateFirst, String dateSecond){
    		List  everyDayList = new ArrayList();
    		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    		try{
    			Date dateOne = dateFormat.parse(dateFirst);
    			Date dateTwo = dateFormat.parse(dateSecond);
    			Calendar calendar = Calendar.getInstance();
    			calendar.setTime(dateOne);
    			while(calendar.getTime().before(dateTwo)||calendar.getTime().equals(dateTwo)){	
    				everyDayList.add(dateFormat.format(calendar.getTime()));
    				calendar.add(Calendar.DAY_OF_MONTH, 1);				
    			}
    		}
    		catch(Exception e){
    			e.printStackTrace();
    		}
    		return everyDayList;
    	}
    
    /**
     * 获取某个月最后一天
     * @param date
     * @return
     * @throws ParseException 
     */
    private static String getLastday_Month(String strdate) throws ParseException {
    	 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
         Date date = df.parse(strdate);
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last);
        day_last = endStr.toString();

        return day_last;
    }


}  