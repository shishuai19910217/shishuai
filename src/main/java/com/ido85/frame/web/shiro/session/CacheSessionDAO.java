/**
 * 
 */
package com.ido85.frame.web.shiro.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;

import com.google.common.collect.Sets;
import com.ido85.frame.common.utils.DateUtils;
import com.ido85.frame.common.utils.StringUtils;
import com.ido85.frame.web.shiro.tools.Servlets;
import com.ido85.frame.web.utils.StatelessParams;

/**
 * Shiro SessionDao
 * @author rongxj
 *
 */
public class CacheSessionDAO extends EnterpriseCacheSessionDAO implements SessionDAO {

	
	public CacheSessionDAO(){
		super();
	}
	
	
	@Override
    protected void doUpdate(Session session) {
    	if (session == null || session.getId() == null) {  
            return;
        }
    	
    	HttpServletRequest request = Servlets.getRequest();
		if (request != null){
			String uri = request.getServletPath();
			// 如果是静态文件，则不更新SESSION
			if (Servlets.isStaticFile(uri)){
				return;
			}
			// 如果是视图文件，则不更新SESSION
//			if (StringUtils.startsWith(uri, Global.getConfig("web.view.prefix"))
//					&& StringUtils.endsWith(uri, Global.getConfig("web.view.suffix"))){
//				return;
//			}
			// 手动控制不更新SESSION
			String updateSession = request.getParameter("updateSession");
			if ("false".equals(updateSession) || "0".equals(updateSession)){
				return;
			}
		}
    	super.doUpdate(session);
//    	logger.debug("update {} {}", session.getId(), request != null ? request.getRequestURI() : "");
    }
	
	/**
	 * 
	 */
	@Override
	public Serializable create(Session session) {
		Serializable sessionId = doCreate(session);
        verifySessionId(sessionId);
        return sessionId;
	}
	
	private void verifySessionId(Serializable sessionId) {
        if (sessionId == null) {
            String msg = "sessionId returned from doCreate implementation is null.  Please verify the implementation.";
            throw new IllegalStateException(msg);
        }
    }
	
	@Override
    protected void doDelete(Session session) {
    	if (session == null || session.getId() == null) {  
            return;
        }
    	
    	super.doDelete(session);
    }

	/**
	 * 
	 */
	@Override
	public Session readSession(Serializable sessionId)
			throws UnknownSessionException {
		try{
    		Session s = null;
    		HttpServletRequest request = Servlets.getRequest();
    		if (request != null){
    			String uri = request.getServletPath();
    			// 如果是静态文件，则不获取SESSION
    			if (Servlets.isStaticFile(uri)){
    				return null;
    			}
    			s = (Session)request.getAttribute("session_"+sessionId);
    		}
    		if (s != null){
    			return s;
    		}

    		Session session = super.readSession(sessionId);
//    		logger.debug("readSession {} {}", sessionId, request != null ? request.getRequestURI() : "");
    		
    		if (request != null && session != null){
    			request.setAttribute("session_"+sessionId, session);
    		}
    		
    		return session;
    	}catch (UnknownSessionException e) {
			return null;
		}
	}

	

	/**
	 * 此处拦截rest sessionID
	 */
	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = null;
		HttpServletRequest request = Servlets.getRequest();
		if (request != null){
			String uri = request.getServletPath();
			// 如果是静态文件，则不创建SESSION
			if (Servlets.isStaticFile(uri)){
		        return null;
			}
			
			if(uri.startsWith("/ws/")){
				// TODO 获取accessID 和 deviceID作为sessionID
				sessionId = StatelessParams.getDeviceID(request);
				session.setAttribute("AccessID", StatelessParams.getAccessKeyID(request));
			}
		}
//		else{
//			Object principal = SecurityUtils.getSubject().getPrincipal();
//			if(principal instanceof StatelessPrincipal){
//				sessionId = ((StatelessPrincipal)principal).getDeviceID();
//			}else{
//				sessionId = generateSessionId(session);
//			}
//		}
		if(sessionId == null){
			sessionId = generateSessionId(session);
		}
        assignSessionId(session, sessionId);
        return sessionId;
	}
	
	/**
	 * 获取活动会话
	 * @param includeLeave 是否包括离线（最后访问时间大于3分钟为离线会话）
	 * @return
	 */
	@Override
	public Collection<Session> getActiveSessions(boolean includeLeave) {
		return getActiveSessions(includeLeave, null, null);
	}
	
	/**
	 * 获取活动会话
	 * @param includeLeave 是否包括离线（最后访问时间大于3分钟为离线会话）
	 * @param principal 根据登录者对象获取活动会话
	 * @param filterSession 不为空，则过滤掉（不包含）这个会话。
	 * @return
	 */
	@Override
	public Collection<Session> getActiveSessions(boolean includeLeave, Object principal, Session filterSession) {
		// 如果包括离线，并无登录者条件。
		if (includeLeave && principal == null){
			return getActiveSessions();
		}
		Set<Session> sessions = Sets.newHashSet();
		for (Session session : getActiveSessions()){
			boolean isActiveSession = false;
			// 不包括离线并符合最后访问时间小于等于3分钟条件。
			if (includeLeave || DateUtils.pastMinutes(session.getLastAccessTime()) <= 3){
				isActiveSession = true;
			}
			// 符合登陆者条件。
			if (principal != null){
				PrincipalCollection pc = (PrincipalCollection)session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
				if (principal.toString().equals(pc != null ? pc.getPrimaryPrincipal().toString() : StringUtils.EMPTY)){
					isActiveSession = true;
				}
			}
			// 过滤掉的SESSION
			if (filterSession != null && filterSession.getId().equals(session.getId())){
				isActiveSession = false;
			}
			if (isActiveSession){
				sessions.add(session);
			}
		}
		return sessions;
	}
}
