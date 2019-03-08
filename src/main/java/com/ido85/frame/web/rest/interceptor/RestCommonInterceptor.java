package com.ido85.frame.web.rest.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class RestCommonInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
//		response.setHeader("Access-Control-Allow-Origin", "*");
		return super.preHandle(request, response, handler);
	}
	
	/** 
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作    
     * 可在modelAndView中加入数据，比如当前时间 
     */  
    @Override    
    public void postHandle(HttpServletRequest request,    
            HttpServletResponse response, Object handler,    
            ModelAndView modelAndView) throws Exception {
//    	RememberMeAuthenticationToken
//    	org.apache.shiro.web.servlet.SimpleCookie
//    	org.apache.shiro.web.mgt.CookieRememberMeManager
//    	org.apache.shiro.web.mgt.DefaultWebSecurityManager
//    	response.setHeader("Expires", (System.currentTimeMillis()/1000+Global.getExpiresLimit())+"");
//		response.setHeader("DATE", DateUtils.formatISO8601Date(System.currentTimeMillis()));
//		response.setHeader("Datetime", System.currentTimeMillis()+"");
//		response.setHeader("Access-Control-Allow-Origin", "*");
    }
    
    
    /**
     * 异常处理 统一抛出500
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
    		HttpServletResponse response, Object handler, Exception ex)
    		throws Exception {
    	/*try {
    		StatelessPrincipal principal = (StatelessPrincipal)SecurityUtils.getSubject().getPrincipal();
        	if(principal != null){
        		final String sessionId = RestSessionUtil.getRestSessionId(principal.getAccessKeyID());
            	InstanceFactory.getInstance(SessionManager.class).touch(new SessionKey() {
        			
        			@Override
        			public Serializable getSessionId() {
        				return sessionId;
        			}
        		});
        	}
		} catch (Exception e) {
		}
    	
    	
    	if(ex != null){
    		response.setContentType("application/json;charset=UTF-8");
    		Resource<String> bm = null;
    		String msg = ex.getMessage()== null? "服务器错误":ex.getMessage();
    		if(ex instanceof BusinessException){
    			bm = new Resource<String>(msg, ((BusinessException)ex).getStatus());
    		}else
    			bm = new Resource<String>(msg, ProcessStatus.COMMON_ERROR);
    		response.getWriter().write(bm.toJsonString());
    		response.getWriter().flush();
    		response.getWriter().close();
    	}*/
    }
}
