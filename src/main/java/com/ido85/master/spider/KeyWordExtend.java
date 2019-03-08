package com.ido85.master.spider;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class KeyWordExtend {

	public static void keywordranks(String keyword, List<String> keywordList) {

		int i = 0;
		Document doc = null;
		boolean crawltag = false;
		
		while (i < 3) {
			try {
				doc = Jsoup.connect(
						"http://www.baidu.com/s?wd=" + keyword + "&pn=0&tn=baiduhome_pg&ie=utf-8")
						.timeout(5000).get();
				crawltag = true;
				break;
			} catch (IOException e) {
				i++;
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		if(crawltag == false || doc == null || doc.body().getElementById("rs") == null){
			return;
		}
	
		Elements es = doc.body().getElementById("rs").getElementsByTag("a");
		for(Element e : es){
			if(!keywordList.contains(e.text())){
				keywordList.add(e.text().toString());
			}
		}
	}
}
