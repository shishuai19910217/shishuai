/**
 * jeesite-master
 * laula
 * 2015-9-19
 */
package com.ido85.frame.web.utils;


import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.ido85.frame.common.utils.DateUtils;
import com.ido85.frame.common.utils.StringUtils;

/**
 * 获得accessKeyid 和 Signature信息
 */
public class StatelessParams {
	
	public static final String getAccessKeyID(ServletRequest request){
		String sid = request.getParameter("AccessKeyID");
		if (StringUtils.isNotBlank(sid)) {
        	return sid;
		}else{
			HttpServletRequest httpRequest = (HttpServletRequest)request;
			String header = httpRequest.getHeader("Authorization");
			if(StringUtils.isNotBlank(header)){
				String[] s = header.split(":");
				if(s.length > 0 && StringUtils.isNotBlank(s[0])){
					return s[0];
				}
			}
		}
		return null;
	}
	
	public static final String getSignature(ServletRequest request){
		String sid = request.getParameter("Signature");
		if (StringUtils.isNotBlank(sid)) {
        	return sid;
		}else{
			HttpServletRequest httpRequest = (HttpServletRequest)request;
			String header = httpRequest.getHeader("Authorization");
			if(StringUtils.isNotBlank(header)){
				String[] s = header.split(":");
				if(s.length > 1 && StringUtils.isNotBlank(s[1])){
					return s[1];
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 请求时间
	 * @param request
	 * @return
	 */
	public static final long getDatetime(ServletRequest request){
		try {
			HttpServletRequest httpRequest = (HttpServletRequest)request;
			String header = httpRequest.getHeader("Datetime");
			if(StringUtils.isNotBlank(header)){
					return DateUtils.parseISO8601Date(header).getTime();
			}
		} catch (Exception e) {
		}
		return 0;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public static final long getExpires(ServletRequest request){
		String sid = request.getParameter("Expires");
		if (StringUtils.isNotBlank(sid)) {
        	return StringUtils.toLong(sid);
		}else{
			HttpServletRequest httpRequest = (HttpServletRequest)request;
			String header = httpRequest.getHeader("Expires");
			if(StringUtils.isNotBlank(header)){
				String[] s = header.split(":");
				if(s.length > 2 && StringUtils.isNotBlank(s[2])){
					return StringUtils.toLong(s[2]);
				}
			}
		}
		return 0;
	}
	
	public static final String getDeviceID(ServletRequest request){
		String sid = request.getParameter("DeviceID");
		if (StringUtils.isNotBlank(sid)) {
        	return sid;
		}else{
			HttpServletRequest httpRequest = (HttpServletRequest)request;
			String header = httpRequest.getHeader("DeviceID");
			if(StringUtils.isNotBlank(header)){
				return header;
			}
		}
		return null;
	}
	
	/**
	 * @param request
	 * @return
	 */
//	public static final Map<String, String> getAuthorization(ServletRequest request){
//		Map<String, String> res = Maps.newHashMap();
//		String sid = request.getParameter(Global.getConfig("stateless.security.authorization.param.accesskey.name"));
//		String signature = request.getParameter(Global.getConfig("stateless.security.authorization.param.signature.name"));
//		if (StringUtils.isNotBlank(sid) && StringUtils.isNotBlank(signature) ) {
//			res.put(Global.getConfig("stateless.security.authorization.param.accesskey.name"), sid);
//			res.put(Global.getConfig("stateless.security.authorization.param.signature.name"), signature);
//			res.put(Global.getConfig("stateless.security.authorization.param.expires.name"), request.getParameter(Global.getConfig("stateless.security.authorization.param.expires.name")));
//        	return res;
//		}else{
//			HttpServletRequest httpRequest = (HttpServletRequest)request;
//			String header = httpRequest.getHeader(Global.getConfig("stateless.security.authorization.header.name"));
//			if(StringUtils.isNotBlank(header)){
//				String[] s = header.split(":");
//				res.put(Global.getConfig("stateless.security.authorization.param.accesskey.name"), s.length > 1?s[0]:"");
//				res.put(Global.getConfig("stateless.security.authorization.param.signature.name"), s.length > 1?s[1]:"");
//				res.put(Global.getConfig("stateless.security.authorization.param.expires.name"), s.length > 2?s[2]:"");
//			}
//		}
//		return null;
//	}
	
	public static final String getStrToSign(ServletRequest request){
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		StringBuffer sb = new StringBuffer();
		String httpVerb = httpRequest.getMethod();
		String uri = httpRequest.getRequestURI();
		String date = httpRequest.getHeader("Datetime");
//    	String contentMd5 = httpRequest.getHeader("Content-MD5");
    	/*String contentType = httpRequest.getContentType();
    	String date = httpRequest.getHeader("DATE");
    	long expires = getExpires(request);
    	
    	String uri = httpRequest.getRequestURI();// request.getRequestURL().toString();
    	sb.append(httpVerb==null?"":httpVerb);
//    	sb.append("\n");
//    	sb.append(contentMd5==null?"":contentMd5);
    	sb.append("\n"+(contentType==null?"":contentType)+"\n"+(date==null?"":date)+"\n"+uri+"\n"+(expires==0?"":expires));*/
		sb.append(httpVerb+uri+date);
    	return sb.toString();
	}
}
