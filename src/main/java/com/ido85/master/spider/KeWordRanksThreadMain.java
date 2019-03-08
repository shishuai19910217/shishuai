package com.ido85.master.spider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KeWordRanksThreadMain {
	
	public static ExecutorService thread = Executors.newFixedThreadPool(50); //使用多线程抓取
	
	public class ThreadTask implements Runnable {
		
		String siteurl;
		String keyword;
		Map<String, String> sitemap;
		int id;
		CountDownLatch threadSignal;

		public ThreadTask(String siteurl, String keyword, Map<String, String> sitemap, int id, CountDownLatch threadSignal){
			this.siteurl = siteurl;
			this.keyword = keyword;
			this.sitemap = sitemap;
			this.id = id;
			this.threadSignal = threadSignal;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			KeyWordRanks.keywordranksmain(siteurl, keyword, sitemap, id);
			threadSignal.countDown();
		}
		
	}
	
	/**
	 * ------------便捷工具-------------
	  * Threadmain 关键词排名主方法（关键词排名和点对点关键词排名）
	  * TODO(这里描述这个方法适用条件 – 可选)
	  * TODO(这里描述这个方法的执行流程 – 可选)
	  * TODO(这里描述这个方法的使用方法 – 可选)
	  * TODO(这里描述这个方法的注意事项 – 可选)
	  *
	  *
	  * @Title: Threadmain
	  * @Description: TODO
	  * @param @param keywordlist  关键词列表list
	  * @param @param siteurl 网站URl
	  * @param @param id 0：关键词排名， 1：点对点关键词
	  * @param @return
	  * @param @throws InterruptedException    设定文件
	  * @return Map<String,Integer>    返回类型
	  * @throws
	 */
	public static Map<String, String> Threadmain(List<String> keywordlist, String siteurl, int id) throws InterruptedException {
				
		KeWordRanksThreadMain th = new KeWordRanksThreadMain();
		Map<String, String> sitemap = new HashMap<>();
		CountDownLatch threadSignal = new CountDownLatch(keywordlist.size());
		
		for(String keyword : keywordlist){
			thread.execute(th.new ThreadTask(siteurl, keyword, sitemap , id, threadSignal));
		}
		threadSignal.await();
		return sitemap;
	}
}
