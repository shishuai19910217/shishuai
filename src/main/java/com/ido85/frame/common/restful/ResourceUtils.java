package com.ido85.frame.common.restful;

import java.util.Map;

import com.ido85.frame.common.utils.JsonUtil;
import com.ido85.frame.common.utils.StringUtils;

public class ResourceUtils {
	
	/**
	 * 根据传入的返回结果字符串判断调用结果是否正确
	 * @param res
	 * @return
	 */
	public static boolean checkResult(String res) {
		boolean result = false;
		if(StringUtils.toInteger(JsonUtil.getJsonValue(res, "code")) == 200){
			String resultInfo = JsonUtil.getJsonValue(res, "result") + "";
			if(StringUtils.toInteger(JsonUtil.getJsonValue(resultInfo, "retCode")) == 0){
				result = true;
			}
		}
		return result;
	}
	/**
	 * 根据传入的返回结果字符串判断调用结果是否是空数据,是空数据返回 true
	 * @param res
	 * @return
	 */
	public static boolean checkResultData(String res) {
		boolean result = false;
		if(StringUtils.toInteger(JsonUtil.getJsonValue(res, "code")) == 200){
			String resultInfo = JsonUtil.getJsonValue(res, "result") + "";
			if(StringUtils.toInteger(JsonUtil.getJsonValue(resultInfo, "retCode")) == -2){
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * 根据key获取data中返回的对应key的数据
	 * 调用此方法之前要调用本类中checkResult方法，
	 * 判断返回结果是否成功返回，成功返回之后才能调用此方法
	 * @param res
	 * @param key
	 * @return String
	 */
	public static String getDataStringValue(String res, String key) {
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) JsonUtil.getJsonValue(res, "data");
		
		return map.get(key) + "";
	}
	/**
	 * 根据key获取data中返回的对应key的数据
	 * 调用此方法之前要调用本类中checkResult方法，
	 * 判断返回结果是否成功返回，成功返回之后才能调用此方法
	 * @param res
	 * @param key
	 * @return int
	 */
	public static int getDataIntValue(String res, String key) {
//		String data = JsonUtil.getJsonValue(res, "data") + "";
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) JsonUtil.getJsonValue(res, "data");
		
		return StringUtils.toInteger(map.get(key));
	}
	
}
