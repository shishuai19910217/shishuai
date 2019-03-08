package com.ido85.master.spider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ido85.frame.common.utils.StringUtils;
import com.ido85.master.spider.vo.KeyWordScore;

public class ArticlesOriginalCheck {

	public static ExecutorService thread = Executors.newFixedThreadPool(50); // 使用多线程抓取

	private static double articleScoreBase = 0.00d;

	// 使用UnicodeScript方法判断
	public static boolean isChineseByScript(char c) {
		Character.UnicodeScript sc = Character.UnicodeScript.of(c);
		if (sc == Character.UnicodeScript.HAN) {
			return true;
		}
		return false;
	}

	public static KeyWordScore originalcheck(String str) {

		KeyWordScore ks = new KeyWordScore();
		Map<String, Integer> originalList = new HashMap<>();

		List<String> articleList = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			String article = str.substring(0, 38);
			if (!isChineseByScript(article.charAt(0))) {
				article = article.substring(1);
			}
			articleList.add(article);
			str = str.substring(38, str.length());
			if (str.length() < 38) {
				break;
			}
		}

		articleScoreBase = StringUtils.div(10, articleList.size(), 4);
		CountDownLatch threadSignal = new CountDownLatch(articleList.size());
		Map<String, String> articleScoreMap = new HashMap<>();

		ArticlesOriginalCheck articlesCheck = new ArticlesOriginalCheck();
		for (String article : articleList) {
			thread.execute(articlesCheck.new ThreadTask(article, articleScoreMap, threadSignal));
		}

		try {
			threadSignal.await();
			// thread.shutdown();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double articleScore = 0d;
		for (String article : articleScoreMap.keySet()) {
			double score = Double.parseDouble(articleScoreMap.get(article).split("_")[0]);
			articleScore = StringUtils.sum(articleScore, score);
			originalList.put(article, Integer.parseInt(articleScoreMap.get(article).split("_")[1]));
		}
		ks.setOriginal(articleScore);
		ks.setOriginalList(originalList);
		return ks;
	}

	public class ThreadTask implements Runnable {

		CountDownLatch threadSignal = null;
		String article = "";
		Map<String, String> articleScoreMap = new HashMap<>();

		public ThreadTask(String article, Map<String, String> articleScoreMap, CountDownLatch threadSignal) {
			this.article = article;
			this.articleScoreMap = articleScoreMap;
			this.threadSignal = threadSignal;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			articlesjsoup(article, articleScoreMap);
			threadSignal.countDown();
		}

	}

	public void articlesjsoup(String article, Map<String, String> articleScoreMap) {

		int i = 0;
		Document doc = null;
		while (i < 3) {
			try {

				doc = Jsoup.connect("http://www.baidu.com/s?wd=" + article + "ie=utf-8&tn=11000002_ie_dg")
						.userAgent("Baiduspider+(+http://www.baidu.com/search/spider.htm)").ignoreHttpErrors(true)
						.timeout(10000).followRedirects(true).execute().parse();
				if (null != doc) {
					break;
				}
			} catch (Exception e) {
				i++;
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					// e1.getMessage();
				}
			}
		}
		if (i == 3) {
			return;
		}

		if (null == doc.body().select("div[id=content_left]").first()) {
			System.out.println("---------------百度封锁IP访问-------------------");
			return;
		}

		Elements es = doc.body().select("div[id=content_left]").first().select("div[class=result c-container ]");

		int keyno = 0;
		for (Element e : es) {
			if (e.text().toString().replaceAll("[\\pP‘’“”]", "").contains(article.replaceAll("[\\pP‘’“”]", ""))) {
				keyno++;
			}
		}
		articleScoreMap.put(article, keyno * articleScoreBase + "_" + keyno);
	}
}
