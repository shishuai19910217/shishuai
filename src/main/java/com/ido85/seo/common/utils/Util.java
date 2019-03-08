package com.ido85.seo.common.utils;

import com.ido85.frame.web.rest.security.Constants;

public class Util {
	public static boolean checkEngine(String[] engine, String[] param) {
		boolean res = false;
		if(null != engine && null != param && engine.length > 1 && param.length > 1){
			int flag = 0;
			for (int i = 0; i < param.length; i++) {
				for (int j = 0; j < engine.length; j++) {
					if(param[i].equals(engine[j])){
						flag = 1;
						break;
					}
				}
				if(flag == 0){
					return false;
				}
				flag = 0;
			}
			res = true;
		}
		
		return res;
	}
	
	/**
	 * 关键词优化难易度算法
	 * @param baiduIndex
	 * @param baiduRecord
	 * @return
	 */
	public static int keywordOptimization(int baiduIndex, int baiduRecord) {
		int score = Constants.BAIDU_INDEX.length;
		for (int i = Constants.BAIDU_INDEX.length - 1; i >= 0; i--) {
			if(baiduIndex <= Constants.BAIDU_INDEX[i]){
				score = i;
			}
		}
		int tem = Constants.BAIDU_RECORD.length;
		for (int i = Constants.BAIDU_RECORD.length - 1; i >= 0; i--) {
			if(baiduRecord <= Constants.BAIDU_RECORD[i]){
				tem = i;
			}
		}
		score = score + tem;
		return score;
	}
}
