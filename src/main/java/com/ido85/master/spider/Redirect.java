package com.ido85.master.spider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Named;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

@Named
public class Redirect {
	
	public List<Map<String, String>> Redirect(List<Map<String, String>> maplist) {
				
		for (Map<String, String> map : maplist){
			String url = map.get("competitorUrl");
			String flag = redirect(url);
			if("timeout".equals(flag)){
				map.put("competitorFlag", "2");
			}else if (!"ok".equals(flag)) {
				map.put("competitorFlag", "1");
				map.put("redirectUrl", flag);
			}
		}
		
		return maplist;
	}
	
	
	
	public static  String redirect(String url) {
				
		Connection.Response response = null;
		
		int i = 0;
		while (i < 3) {
			try {
				
				response = Jsoup.connect("http://" + url) .userAgent(
				 "Baiduspider+(+http://www.baidu.com/search/spider.htm)")
				 .ignoreHttpErrors(true).timeout(10000)
				 .followRedirects(true).execute();
				 
//				doc = Jsoup
//						.connect("http://" + url)
//						.userAgent(
//								"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.56 Safari/537.17")
//						.ignoreHttpErrors(true).timeout(20000).followRedirects(true).execute().parse();	
				//去掉访问页面中的空格和最后一个/
				String responseString = response.url().toString();
				
				if (responseString.substring(responseString.length() - 1).equals("/")) {
					responseString = responseString.substring(0, responseString.length() - 1).replaceAll(" ", "");
				}
			
				//通过response获取网站最后访问的地址，进行网站的跳转判断
				if(!getHost(responseString).equals(getHost(url))){
					return responseString;	
				}
				System.out.println("网站：" + url + " 正常 " + responseString);
				return "ok";
			} catch (Exception e) {
				i++;
				System.out.println(url + " :开始进行网站连接重试,第" + i + "次连接");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					//e1.getMessage();
				}
			}
		}
		return "timeout";
	}	
	
	
	
	/**
	 * 获取网站的主域名
	 * 
	 * @Title: getHost
	 * @Description: TODO
	 * @param @param seurl
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getHost(String seurl) {

		String url1 = seurl.replaceAll(" ", "");
		String url = url1.replaceAll("http://", "");

		Pattern pattern = Pattern
				.compile("[^\\.]+(\\.com\\.cn|\\.net\\.cn|\\.org\\.cn|\\.gov\\.cn|\\.com|\\.net|\\.cn|\\.org|\\.cc|\\.me|\\.tel|\\.mobi|\\.asia|\\.biz|\\.info|\\.name|\\.tv|\\.hk|\\.公司|\\.中国|\\.网络)");
		URL u = null;

		try {
			u = new URL("https://" + url);
			// return u.getHost();
		} catch (MalformedURLException e) {
			e.getMessage();
		}
		if (u == null)
			return null;
		String host = u.getHost().toLowerCase();// 此处获取值转换为小写

		Matcher matcher = pattern.matcher(host);
		List<String> hosturl = new ArrayList<String>();
		while (matcher.find()) {
			hosturl.add(matcher.group());
		}
		if (hosturl.size() != 0) {
			return hosturl.get(hosturl.size() - 1);
		} else {
			return null;
		}
	}

	
}
