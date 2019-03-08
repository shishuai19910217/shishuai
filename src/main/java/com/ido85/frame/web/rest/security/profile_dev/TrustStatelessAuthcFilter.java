package com.ido85.frame.web.rest.security.profile_dev;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.context.annotation.Profile;

import com.ido85.frame.common.utils.HmacSHA256Utils;
import com.ido85.frame.common.utils.StringUtils;
import com.ido85.frame.web.rest.security.StatelessPrincipal;
import com.ido85.frame.web.rest.security.TrustAccessKeyEnum;
import com.ido85.frame.web.shiro.session.RestSessionUtil;
import com.ido85.frame.web.utils.StatelessParams;

/**
 */
@Profile(value = "dev")
public class TrustStatelessAuthcFilter extends AccessControlFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		Subject subject = getSubject(request, response);
		return subject.isAuthenticated();
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {

//		long requestTime = StatelessParams.getDatetime(request);
		
		// 客户端传入的用户身份
		String accessKeyID = StatelessParams.getAccessKeyID(request);
		// 客户端请求的参数列表
		String strToSign = StatelessParams.getStrToSign(request);
		// 客户端传回的消息摘要
		String signature = StatelessParams.getSignature(request);
				
		System.out.println("------trust--devmode:-----------"+accessKeyID+"------------url======"+((HttpServletRequest) request).getRequestURI()+"----sessionId===="+RestSessionUtil.getRestSessionId(accessKeyID));

//		if (requestTime < System.currentTimeMillis() - 15 * 60 * 1000
//				|| requestTime > System.currentTimeMillis() + 15 * 60 * 1000) {
//			onTimeout(response);
//			return false;
//		}
		
		if (checkUserLegal(accessKeyID, strToSign, signature)) {
			return true;
		} else {
			joinContext(accessKeyID);
			return true;
		}
	}
	
	/**
	 * 检查身份合法性
	 * @param accessKeyID
	 * @param strToSign
	 * @param signature
	 * @return
	 */
	private boolean checkUserLegal(String accessKeyID, String strToSign, String signature){
		if(!StringUtils.isNotBlank(accessKeyID)){
			return false;
		}
		if(null == accessKeyID || !TrustAccessKeyEnum.WI_ACCESS_KEY_ID.getCode().equals(accessKeyID)){
			return false;
		}
		
		String serverDigest = HmacSHA256Utils.digest(
				TrustAccessKeyEnum.WI_SECURITY_KEY.getCode(),
				strToSign);

		if (serverDigest.equals(signature)) {
			joinContext(accessKeyID);
			return true;
		}
		//请求加密不合法
		return false;
	}
	
	/**
	 * 加入上下文
	 * @param accessKeyID
	 */
	private void joinContext(String accessKeyID){
		StatelessPrincipal principal = new StatelessPrincipal(accessKeyID);
		ThreadContext.put("principal", principal);
	}


//	// 请求过期默认返回403状态码
//	private void onTimeout(ServletResponse response) throws IOException {
//		HttpServletResponse httpResponse = (HttpServletResponse) response;
//		response.setContentType("application/json;charset=UTF-8");
//		Resource<String> ret = new Resource<String>("请求超时",
//				ProcessStatus.REQUEST_TIMEOUT);
//		ret.setResponseStatus(new ResponseStatus(403, "请求超时"));
//		httpResponse.getWriter().write(ret.toJsonString());
//		httpResponse.getWriter().flush();
//		httpResponse.getWriter().close();
//	}
}
