package com.ido85.master.spider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ido85.master.spider.vo.Page;

public class PageCheck {


	public static Page getPageAnaly(String keyword, String url) {
		
		Page page = new Page();
		
		Map<String, Integer> keywords = new HashMap<String, Integer>();
		List<String> errorlist = new ArrayList<>();

		keywords.put("url",getkeywordsum(keyword,url));

		int i = 0;
		Connection.Response response = null;
		Document doc = null;
		long time1 = 0;
		long time2 = 0;
		while (i < 3) {
			try {
				time1=System.currentTimeMillis();  
				response = Jsoup.connect(url).userAgent("Baiduspider+(+http://www.baidu.com/search/spider.htm)")
						.ignoreHttpErrors(true).timeout(10000).followRedirects(true).execute();
				time2=System.currentTimeMillis();  
				if (response != null) {
					doc = response.parse();
					long interval=time2-time1; 
					if(interval > 3000){
						errorlist.add("20102");
					}
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(url + " : 链接失败，正在进行重连");
				i++;
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					// e1.getMessage();
				}
			}
		}
		if (i == 3) {
			errorlist.add("30103");
			page.setErrorlist(errorlist);
			return page;
		}

		boolean redirects = false;
		boolean altimg = false;

		String responseString = response.url().toString();
		if (responseString.substring(responseString.length() - 1).equals("/")) {
			responseString = responseString.substring(0, responseString.length() - 1).replaceAll(" ", "");
		}

		int re = 0;

		char[] responsearray = responseString.toCharArray();
		for (int ia = 0; ia < responsearray.length; ia++) {
			if ('/' == responsearray[ia]) {
				re++;
			}
		}

		// 通过response获取网站最后访问的地址，进行网站的跳转判断
		if (!responseString.equals(url)) {
			if (!getHost(responseString).equals(getHost(url)) || re > 3) {
				System.out.println("网站：" + url + "存在跳转，跳转后地址：" + responseString);
				redirects = true;
			}
		}
		
		String title = doc.title();
		keywords.put("title",getkeywordsum(keyword,title));
		if(title.length()>80){
			errorlist.add("10104");
		}
		
		String description = doc.select("meta[name=description]").get(0).attr("content");
		keywords.put("description",getkeywordsum(keyword,description));
		if(description.length()>200){
			errorlist.add("10105");
		}
		
		if(keywords.get("title")==0 || keywords.get("description")==0){
			errorlist.add("20104");
		}

		Elements es = doc.body().select("img");
		for (Element e : es) {
			if(keywords.containsKey("imageAlt")){
				keywords.put("imageAlt", getkeywordsum(keyword,e.attr("alt"))+keywords.get("imageAlt"));
			}else{
				keywords.put("imageAlt", getkeywordsum(keyword,e.attr("alt")));
			}
			if ("".equals(e.attr("alt"))) {
				altimg = true;
			}
		}

		Elements bes = doc.select("b");
		for (Element e : bes){
			if(keywords.containsKey("bold")){
				keywords.put("bold", getkeywordsum(keyword,e.text())+keywords.get("bold"));
			}else{
				keywords.put("bold", getkeywordsum(keyword,e.text()));
			}
		}
		
		
		
		Elements h1es = doc.body().select("h1");
		if(h1es.size() != 1){
			errorlist.add("10100");
		}
		if(h1es.size() > 0 ){
			for(Element e : h1es){
				if(keywords.containsKey("h1")){
					keywords.put("h1", getkeywordsum(keyword,e.text())+keywords.get("h1"));
				}else{
					keywords.put("h1", getkeywordsum(keyword,e.text()));
				}
			}
		}else{
			keywords.put("h1",0);
		}
		
		
		Elements h2es = doc.body().select("h2");
		if(h2es.size() > 0 ){
			for(Element e : h2es){
				if(keywords.containsKey("h2")){
					keywords.put("h2", getkeywordsum(keyword,e.text())+keywords.get("h2"));
				}else{
					keywords.put("h2", getkeywordsum(keyword,e.text()));
				}
			}
		}
		Elements h3es = doc.body().select("h3");
		if(h3es.size() > 0 ){
			for(Element e : h3es){
				if(keywords.containsKey("h2")){
					keywords.put("h2", getkeywordsum(keyword,e.text())+keywords.get("h2"));
				}else{
					keywords.put("h2", getkeywordsum(keyword,e.text()));
				}
			}
		}else{
			if(!keywords.containsKey("h2")){
				keywords.put("h2",0);
			}
		}
		
		if(h2es.size() == 0 || h3es.size() == 0){
			errorlist.add("10106");
		}
		if(redirects == true){
			errorlist.add("30104");
		}
		if(altimg == true){
			errorlist.add("10101");
		}
		page.setErrorlist(errorlist);
		page.setKeywords(keywords);
		return page;
	}
	
	/**
	 * 获取一个字符串在另一个字符串的个数
	  * getkeywordsum(这里用一句话描述这个方法的作用)
	  * TODO(这里描述这个方法适用条件 – 可选)
	  * TODO(这里描述这个方法的执行流程 – 可选)
	  * TODO(这里描述这个方法的使用方法 – 可选)
	  * TODO(这里描述这个方法的注意事项 – 可选)
	  *
	  * @Title: getkeywordsum
	  * @Description: TODO
	  * @param @param keyword
	  * @param @param str
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	 */
	public static int getkeywordsum(String keyword, String str){
		int counter = 0;
		if("".equals(str) || keyword.length()>str.length()){
			return counter;
		}
		if (str.indexOf(keyword) != -1) {
			while (str.indexOf(keyword) != -1) {
				counter++;
				str = str.substring(str.indexOf(keyword) + keyword.length());
			}
		}
		return counter;
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
