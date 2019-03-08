package com.ido85.log.utils;

import java.security.Principal;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * User: zjzhai Date: 11/27/13 Time: 11:01 AM
 */
@WebFilter(filterName = "myFilter", urlPatterns = "/*")
public class LogFilter extends BusinessLogServletFilter {
	/**
	 * 将需要用到的信息放入日志上下文
	 * @param req
	 * @param resp
	 * @param chain
	 */
	@Override
	public void beforeFilter(ServletRequest req, ServletResponse resp, FilterChain chain) {
		addIpContext(getIp(req));
		Principal principal = ((HttpServletRequest) req).getUserPrincipal();
		if (principal != null && !principal.getName().isEmpty()) {
			addUserContext(principal.getName());
		} else {
			addUserContext("");
		}
	}
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// To change body of implemented methods use File | Settings | File Templates.
	}
	@Override
	public void destroy() {
		// To change body of implemented methods use File | Settings | File Templates.
	}
}
