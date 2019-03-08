package com.ido85.frame.web.rest.security;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.context.annotation.Profile;

import com.ido85.frame.common.restful.ProcessStatus;
import com.ido85.frame.common.restful.Resource;
import com.ido85.frame.common.restful.ResponseStatus;
import com.ido85.frame.common.utils.StringUtils;
import com.ido85.frame.web.utils.StatelessParams;

/**
 */
@Profile(value = "prod")
public class StatelessAdminFilter extends AccessControlFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		Subject subject = getSubject(request, response);
		return subject.isAuthenticated();
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// 客户端传入的用户身份
		String accessKeyID = StatelessParams.getAccessKeyID(request);
		// 客户端请求的参数列表
		String strToSign = StatelessParams.getStrToSign(request);
		// 客户端传回的消息摘要
		String signature = StatelessParams.getSignature(request);

		long requestTime = StatelessParams.getDatetime(request);
		System.out.println("-------------------" + accessKeyID + "------------url======"
				+ ((HttpServletRequest) request).getRequestURI());

		 if (requestTime < System.currentTimeMillis() - 15 * 60 * 1000
		 || requestTime > System.currentTimeMillis() + 15 * 60 * 1000) {
			 onTimeout(response);
			 return false;
		 }

		return checkUserLegal(accessKeyID, strToSign, signature);
	}

	/**
	 * 检查身份合法性
	 * 
	 * @param accessKeyID
	 * @param strToSign
	 * @param signature
	 * @return
	 */
	private boolean checkUserLegal(String accessKeyID, String strToSign, String signature) {
		if (!StringUtils.isNotBlank(accessKeyID)) {
			return false;
		}
//		UserInfo info = RestUserUtils.getManageUserInfo(accessKeyID);
//		if (info == null)
//			return false;
//		String serverDigest = HmacSHA256Utils.digest(info.getAccessSecurityKey(), strToSign);
//
//		if (serverDigest.equals(signature)) {
//			joinContext(accessKeyID);
//			return true;
//		}
		return false;
	}

//	/**
//	 * 加入上下文
//	 * 
//	 * @param accessKeyID
//	 */
//	private void joinContext(String accessKeyID) {
//		StatelessPrincipal principal = new StatelessPrincipal(accessKeyID);
//		ThreadContext.put("principal", principal);
//	}
	
	// 请求过期默认返回403状态码
		private void onTimeout(ServletResponse response) throws IOException {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			response.setContentType("application/json;charset=UTF-8");
			Resource<String> ret = new Resource<String>("请求超时",
					ProcessStatus.REQUEST_TIMEOUT);
			ret.setResponseStatus(new ResponseStatus(403, "请求超时"));
			httpResponse.getWriter().write(ret.toJsonString());
			httpResponse.getWriter().flush();
			httpResponse.getWriter().close();
		}
}
