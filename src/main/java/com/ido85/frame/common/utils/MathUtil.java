package com.ido85.frame.common.utils;

import java.math.BigDecimal;

public class MathUtil {
	/**
	 * scale表示表示需要精确到小数点以后几位
	 * @param d1
	 * @param d2
	 * @param scale
	 * @return
	 */
	public static double div(Double d1, Double d2, int scale){
		BigDecimal b1 = new BigDecimal(Double.toString(d1));  
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		
		double res = b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		return res;
	}
	/**
	 * 对传入参数进行四舍五入保留 scale 位小数
	 * @param d
	 * @param scale
	 * @return
	 */
	public static double rounding(Double d, int scale){
		if(null == d || "".equals(d)){
			return 0d;
		}
		BigDecimal bd = new BigDecimal(Double.toString(d));
		return bd.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
