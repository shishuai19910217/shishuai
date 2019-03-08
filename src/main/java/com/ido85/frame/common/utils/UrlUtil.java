package com.ido85.frame.common.utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/***
 * 
 * @author shishuai
 *
 */
public class UrlUtil {
	private static URL url;
	private final static Pattern pattern = Pattern.compile("\\S*[?]\\S*");  
	private static HttpURLConnection con;
	private static int state = -1;

	/**
	 * 功能：检测当前URL是否可连接或是否有效,
	 * 描述：最多连接网络 5 次, 如果 5 次都不成功，视为该地址不可用
	 * @param urlStr 指定URL网络地址
	 * @return URL
	 */
	public static synchronized String  isConnect(String urlStr) {
		int counts = 0;
		String returnstr = "0";
		if (urlStr == null || urlStr.length() <= 0) {                       
			return null;                 
		}
		while (counts < 1) {
			try {
				url = new URL(urlStr);
				con = (HttpURLConnection) url.openConnection();
				con.setConnectTimeout(10000);
				state = con.getResponseCode();
				System.out.println(counts +"= "+state);
				if (state == 200) {
					System.out.println("URL可用！");
					returnstr = "1";
				}else{
					returnstr = "0";
				}
					
				break;
			}catch (Exception ex) {
				ex.printStackTrace();
				counts++; 
				System.out.println("URL不可用，连接第 "+counts+" 次");
				urlStr = null;
				returnstr = "0";
				continue;
			}
		}
		return returnstr ;
	}
	/** 
     * 获取链接的后缀名 
     * @return 
     */  
    @SuppressWarnings("unused")
	private static String parseSuffix(String url) {  
  
        Matcher matcher = pattern.matcher(url);  
  
        String[] spUrl = url.toString().split("/");  
        int len = spUrl.length;  
        String endUrl = spUrl[len - 1];  
  
        if(matcher.find()) {  
            String[] spEndUrl = endUrl.split("\\?");  
            return spEndUrl[0].split("\\.")[1];  
        }  
        return endUrl.split("\\.")[1];  
    }  
    
}
