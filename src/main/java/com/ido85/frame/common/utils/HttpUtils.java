package com.ido85.frame.common.utils;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http请求工具类
 * @author fire
 *
 */
public class HttpUtils {
	private static transient final Logger log = LoggerFactory.getLogger(HttpUtils.class);
	
	
	//编码格式。发送编码格式统一用UTF-8
    private static String ENCODING = "UTF-8";
	
    /**
     * 基于HttpClient 4.3的通用POST方法
     *
     * @param url       提交的URL
     * @param paramsMap 提交<参数，值>Map
     * @return 提交响应
     */

    public static String post(String url, Map<String, String> paramsMap) {
    	System.out.println("get hbase data url:"+url);
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
            log.info("llz====httppost==res={}", responseText);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }
    /**
     * 基于HttpClient 4.3的通用POST方法
     *
     * @param url       提交的URL
     * @param paramsMap 提交<参数，值>Map
     * @return 提交响应
     */
    
    public static String post(String url, Object obj) {
    	if (obj == null || obj == null) {
			return null;
		}

    	CloseableHttpClient client = HttpClients.createDefault();
    	String responseText = "";
    	CloseableHttpResponse response = null;
    	try {
    		HttpPost method = new HttpPost(url);
    		if (obj != null) {
    			List<NameValuePair> paramList = new ArrayList<NameValuePair>();
    			PropertyDescriptor[] destDesc = PropertyUtils.getPropertyDescriptors(obj);
    			for (int i = 0; i < destDesc.length; i++) {
    				try {
    				Class origType = PropertyUtils.getPropertyType(obj, destDesc[i].getName());
    					if (null != origType && !Collection.class.isAssignableFrom(origType)) {
    							Object value = PropertyUtils.getProperty(obj, destDesc[i].getName());
    							NameValuePair pair = new BasicNameValuePair(destDesc[i].getName(), String.valueOf(value));
    							paramList.add(pair);
    					}
    				} catch (Exception ex) {}
    			}
    			method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
    		}
    		response = client.execute(method);
    		HttpEntity entity = response.getEntity();
    		if (entity != null) {
    			responseText = EntityUtils.toString(entity);
    		}
    		log.info("llz====httppost==res={}", responseText);
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		try {
    			response.close();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	return responseText;
    }
    

    
}
