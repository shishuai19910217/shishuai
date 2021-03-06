package com.ido85.log.utils;

import com.ido85.log.ContextKeyConstant;

import javax.servlet.*;
import java.io.IOException;

/**
 * User: zjzhai
 * Date: 11/27/13
 * Time: 11:01 AM
 */
public abstract class BusinessLogServletFilter implements Filter {

    /**
     * 将需要用到的信息放入日志上下文
     *
     * @param req
     * @param resp
     * @param chain
     */
    public abstract void beforeFilter(ServletRequest req, ServletResponse resp, FilterChain chain);


    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        beforeFilter(req, resp, chain);
        chain.doFilter(req, resp);
    }

    public void addUserContext(String user){
        ThreadLocalBusinessLogContext.put(ContextKeyConstant.BUSINESS_OPERATION_USER, user);
    }

    public void addIpContext(String ip){
        ThreadLocalBusinessLogContext.put(ContextKeyConstant.BUSINESS_OPERATION_IP, ip);
    }

    public String getIp(ServletRequest req){
        return req.getRemoteAddr();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
