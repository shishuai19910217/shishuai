package com.ido85.master.spider;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class KeyWordRanks {
	
	private static String splitString = "€_€";
	
	public static void keywordranksmain(String siteurl ,String keyword,Map<String, String> sitemap,int id) {
		String sort = "";
		for(int no=0;no<3;no++){
			sort = keywordranks(keyword, 0, siteurl, id);
			if(sort != ""){
				break;
			}
		}
		sitemap.put(keyword, sort);		
	}
	
	
	
	
	public static String keywordranks(String keyword, int count , String siteurl, int id){
		
		String siteurl0 = getHost(siteurl);
		
		int i = 0;
		boolean crawltag = false;
		Document doc = null;
		
		while (i  < 3) {
			try {
				doc = Jsoup.connect("http://www.baidu.com/s?wd=" + keyword + "&pn=" + count*10 + "&tn=baiduhome_pg&ie=utf-8").timeout(10000).get();
				crawltag = true;
				break;
			} catch (IOException e) {
				i++;
				System.out.println(" 百度链接失败，网络中断或百度服务器错误-----");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		if(crawltag == false || doc == null){
			return "";
		}
		
		Elements es = doc.body().getElementById("content_left").getElementsByClass("c-container");
		
		int sort = 0;
		for(Element e : es){
			String baiduurl = e.getElementsByClass("c-showurl").text();
			sort++;
			if(id == 0){
				if(baiduurl.contains(siteurl0)){
					return sort+count*10 + splitString + baiduurl;
				}
			}else{
				if(baiduurl.equals(siteurl)){
					return "";
				}
			}
		}
		return "";
	}
	
	/**
	 * 获取网站的主域名
	 * 
	 * @Title: getHost @Description: TODO @param @param seurl @param @return
	 *         设定文件 @return String 返回类型 @throws
	 */
	public static String getHost(String seurl) {

		String url1 = seurl.replaceAll(" ", "");
		String url = url1.replaceAll("http://", "").replaceAll("https://", "");

		Pattern pattern = Pattern.compile(
				"[^\\.]+(\\.com\\.cn|\\.net\\.cn|\\.org\\.cn|\\.gov\\.cn|\\.com|\\.net|\\.cn|\\.org|\\.cc|\\.me|\\.tel|\\.mobi|\\.asia|\\.biz|\\.info|\\.name|\\.tv|\\.hk|\\.公司|\\.中国|\\.网络)");
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
