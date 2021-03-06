package com.ido85.master.spider;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Named;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

@Named
public class RediectUrl {

	private final static int timeOut = 5000;
	/** 连接超时时间 */
	private final static int retryCount = 3;
	/** 抓取失败重试次数 */
	private final static boolean useProIp = false;
	/** 是否使用代理ip */

	private final static String UA = "Baiduspider+(+http://www.baidu.com/search/spider.htm)";

	final static CloseableHttpClient client = new DefaultHttpClient();
	private static final Pattern p_charset = Pattern.compile("charset\\s?=\\s?([a-zA-Z0-9\\-]+)");
	private static final Pattern p_encoding = Pattern.compile("encoding=\"([a-zA-Z0-9\\-]+)\"");
	private static final Pattern P_PINCODE = Pattern.compile("pincode");

	public List<Map<String, String>> redirect(List<Map<String, String>> maplist) {
		for (Map<String, String> map : maplist) {
			String url = map.get("url");
			String flag = getContent(url);
			if ("timeout".equals(flag)) {
				map.put("urlFlag", "2");
			} else if (!"ok".equals(flag)) {
				map.put("urlFlag", "1");
				map.put("redirectUrl", flag);
			}
		}

		return maplist;
	}

	public synchronized static String getContent(String eurl) {

		String url = getHttpUrl(eurl);

		int trycount = 0;
		URL target = null;
		try {
			target = new URL(url);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		HttpGet httpGet = null;
		CloseableHttpResponse httpResponse = null;
		String content = "";
		while (trycount < retryCount) {
			try {
				httpGet = new HttpGet(url);
				httpGet.addHeader("Accept-Language", "zh-cn,zh;q=0.5");
				httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
				httpGet.addHeader("User-Agent", UA);
				httpGet.addHeader("Host", target.getHost());
				httpGet.addHeader("Referer", "http://" + target.getHost() + "/");

				/** 设置连接超时时间 */
				HttpConnectionParams.setConnectionTimeout(httpGet.getParams(), timeOut);
				HttpConnectionParams.setSoTimeout(httpGet.getParams(), timeOut);
				HttpParams params = httpGet.getParams();
				params.setParameter(ClientPNames.HANDLE_REDIRECTS, false);
				httpResponse = client.execute(httpGet);
				String url1 = "";
				if (httpResponse.getFirstHeader("Location") != null) {
					url1 = httpResponse.getFirstHeader("Location").getValue();
				}
				if (httpResponse.getFirstHeader("Referer") != null) {
					url1 = httpResponse.getFirstHeader("Referer").getValue();
				}
				if (!"".equals(url1)) {
					if (!getHost(url1).equals(getHost(target.getHost()))) {
						return url1;
					} else {
						return "ok";
					}
				} else {
					return "ok";
				}
			} catch (Exception e) {
				e.printStackTrace();
				httpGet.abort();
				trycount++;
				System.out.println("--------------------------");
				continue;
			} finally {
				try {
					if (httpResponse != null) {
						httpResponse.close();
					}
					client.getConnectionManager().closeExpiredConnections();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		}
		return "timeout";
	}

	private static String entityToString(HttpEntity entity) {

		String charset = EntityUtils.getContentCharSet(entity);
		try {
			byte[] bytes = EntityUtils.toByteArray(entity);

			if (charset == null) {

				Matcher m = p_charset.matcher(new String(bytes));
				if (m.find()) {
					charset = m.group(1).trim();
					if ("GB2312".equalsIgnoreCase(charset)) {
						charset = "GBK";
					}
				} else {
					m = p_encoding.matcher(new String(bytes));
					if (m.find()) {
						charset = m.group(1).trim();
						if ("GB2312".equalsIgnoreCase(charset)) {
							charset = "GBK";
						}
					}
				}
			}

			charset = charset == null ? "GBK" : charset;

			String content = new String(bytes, charset);

			Matcher matcher = P_PINCODE.matcher(content);
			if (matcher.find()) {
				return "";
			}
			return content;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				entity.getContent().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	private static String getHttpUrl(String url) {
		url = url.trim();
		if (url.startsWith("http://") || url.startsWith("https://")) {
			return url;
		} else {
			return "http://" + url;
		}

	}

	/**
	 * 获取网站的主域名
	 * 
	 * @Title: getHost @Description: TODO @param @param seurl @param @return
	 * 设定文件 @return String 返回类型 @throws
	 */
	public static String getHost(String seurl) {

		String url1 = seurl.replaceAll(" ", "");
		String url = url1.replaceAll("http://", "");

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
