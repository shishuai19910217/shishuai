package com.ido85.master.spider;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class untils {
	
	private static BlockingQueue<String> PC_USERAGENTS;// useragent队列
	private static BlockingQueue<String> HTTP_PROXIES = null;// http代理队列
	
	
	public static void BlockingQueueCreate() throws IOException{
		// 加载useragent队列
		//System.out.println(URLDecoder.decode(System.class.getResource("/").getPath()));
	/*	String[][] pcuseragents = (String[][]) GsonHelper.configure("/pc_useragents.json", String[][].class);
		if (pcuseragents != null) {
			PC_USERAGENTS = new LinkedBlockingQueue<String>(pcuseragents.length);
			for (String[] useragent : pcuseragents) {
				PC_USERAGENTS.add(useragent[1]);
			}
		}*/ 
 
		// 加载http代理队列main\resources\proxies.json
		//GsonHelper gson = new GsonHelper();
		String[] httpproxies = (String[]) GsonHelper.configure("proxies.json", String[].class);
		if (httpproxies != null) {
			HTTP_PROXIES = new LinkedBlockingQueue<String>(httpproxies.length);
			for (String proxy : httpproxies) {
				HTTP_PROXIES.add(proxy);
			}
		}
				
	}
	
	// 获取下一个httpproxy
	public static synchronized String lockNextHttpProxy() throws InterruptedException {
		if(HTTP_PROXIES != null && HTTP_PROXIES.size() > 0){
			String proxy = HTTP_PROXIES.take();
			return proxy;
		}else{
			try {
				BlockingQueueCreate();
			} catch (IOException e) {
				e.printStackTrace();
			}//初始化队列
			String proxy = HTTP_PROXIES.take();
			return proxy;
		}
		
	}

	// 用完后释放一个代理
	public static void releaseHttpProxy(String proxy) throws InterruptedException {
		HTTP_PROXIES.put(proxy);
	}
	
	// 获取下一个UserAgent
	public static String getNextUserAgent() throws InterruptedException {
		String useragent = PC_USERAGENTS.take();
		PC_USERAGENTS.put(useragent);
		return useragent;
	}
	
	
}
