package com.ido85.master.spider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class KeWordExtendThreadMain {
	
	public static ExecutorService thread = Executors.newFixedThreadPool(100); //使用多线程抓取
	
	public class ThreadTask implements Runnable {
		
		String keyword;
		List<String> keywordList;
		CountDownLatch threadSignal;

		public ThreadTask(String keyword, List<String> keywordList, CountDownLatch threadSignal){
			this.keyword = keyword;
			this.keywordList = keywordList;
			this.threadSignal = threadSignal;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			KeyWordExtend.keywordranks(keyword, keywordList);
			threadSignal.countDown();
		}
	}
	
	/**
	 * ------------便捷工具-------------
	  * Threadmain 关键词拓展
	  * TODO(这里描述这个方法适用条件 – 可选)
	  * TODO(这里描述这个方法的执行流程 – 可选)
	  * TODO(这里描述这个方法的使用方法 – 可选)
	  * TODO(这里描述这个方法的注意事项 – 可选)
	  *
	  *	百度搜索 下面 关键词拓展，拓展三层
	  * @Title: Threadmain
	  * @Description: TODO
	  * @param @param keyword  关键词
	  * @param @return
	  * @param @throws InterruptedException    设定文件
	  * @return Map<String,Integer>    返回类型
	  * @throws
	 */
	public static List<String>  Threadmain(String keyword) throws InterruptedException {
				
		KeWordExtendThreadMain th = new KeWordExtendThreadMain();
		List<String> keywordList = new ArrayList<>();
		
		KeyWordExtend.keywordranks(keyword, keywordList);
		
		CountDownLatch threadSignal = new CountDownLatch(keywordList.size());
	//	System.out.println("第一层关键词拓展个数：" + keywordList.size());
		for(String key : keywordList){
			thread.execute(th.new ThreadTask(key, keywordList, threadSignal));
		}
		threadSignal.await();
	//	System.out.println("第二层关键词拓展个数：" + keywordList.size());
		CountDownLatch threadSignal2 = new CountDownLatch(keywordList.size());
		
		for(String key : keywordList){
			thread.execute(th.new ThreadTask(key, keywordList, threadSignal2));
		}
		threadSignal2.await();
//		thread.shutdown();
		//System.out.println("第三层关键词拓展个数：" + keywordList.size());
//		for(String key : keywordList){
//			System.out.println(key);
//		}
		return keywordList;
	}
	

//	public static void main(String[] args) {
//		try {
//			Threadmain("seo");
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
