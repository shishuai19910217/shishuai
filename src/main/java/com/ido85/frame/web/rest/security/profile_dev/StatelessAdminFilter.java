package com.ido85.frame.web.rest.security.profile_dev;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.context.annotation.Profile;

import com.ido85.frame.common.utils.StringUtils;
import com.ido85.frame.web.rest.security.StatelessPrincipal;
import com.ido85.frame.web.utils.StatelessParams;

/**
 */
@Profile(value = "dev")
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

		System.out.println("-------------------" + accessKeyID + "------------url======"
				+ ((HttpServletRequest) request).getRequestURI());

		// if (requestTime < System.currentTimeMillis() - 15 * 60 * 1000
		// || requestTime > System.currentTimeMillis() + 15 * 60 * 1000) {
		// onTimeout(response);
		// return false;
		// }

		if (checkUserLegal(accessKeyID, strToSign, signature)) {
			return true;
		} else {
			joinContext(accessKeyID);
			return true;
		}
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

	/**
	 * 加入上下文
	 * 
	 * @param accessKeyID
	 */
	private void joinContext(String accessKeyID) {
//		StatelessPrincipal principal = new StatelessPrincipal(accessKeyID, String.valueOf(RestUserUtils.getUserInfo(accessKeyID).getTenantID()));
		StatelessPrincipal principal = new StatelessPrincipal(accessKeyID);
		ThreadContext.put("principal", principal);
	}
}
