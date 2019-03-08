package com.ido85.master.spider;

import java.io.IOException;

import org.jsoup.JenkinsHash;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class GetGooglePrApi {
	
	
	
	public static int getGoogleApi(String eurl) {
		GetGooglePrApi obj = new GetGooglePrApi();
		return obj.getPR(eurl);
	}

	public int getPR(String domain) {
		String result = "";
		JenkinsHash jenkinsHash = new JenkinsHash();
		long hash = jenkinsHash.hash(("info:" + domain).getBytes());
		// Append a 6 in front of the hashing value.
		String url = "http://toolbarqueries.google.com/tbr?client=navclient-auto&hl=en&"
				+ "ch=6"
				+ hash
				+ "&ie=UTF-8&oe=UTF-8&features=Rank&q=info:"
				+ domain;

		Document doc = null;
		boolean bl = false;
		int i = 0;
		while (i < 3) {
			try {
				doc = Jsoup.connect(url).get();
				bl = true;
				break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				i++;
				//e.printStackTrace();
				System.out.println("====" + url);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
			}
		}
		if (bl == true) {
			if (doc.toString().contains("Rank_")) {
				result = doc.toString().substring(
						doc.toString().lastIndexOf(":") + 1,
						doc.toString().lastIndexOf(":") + 2);
			}
			if ("".equals(result)) {
				return 0;
			} else {
				return Integer.valueOf(result);
			}
		} else {
			return -1;
		}

	}

}
