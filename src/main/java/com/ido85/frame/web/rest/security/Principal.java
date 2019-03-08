/**
 * 
 */
package com.ido85.frame.web.rest.security;

import java.io.Serializable;

import javax.persistence.Transient;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author rongxj
 *
 */
public abstract class Principal implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public abstract String getId();
//	public abstract String getLoginName();
	
	@JsonIgnore
	@Transient
	public String getSessionId(){
		try{
			return (String) getSession().getId();
		}catch (Exception e) {
			return "";
		}
	}
	
	@JsonIgnore
	@Transient
	public Session getSession(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null){
				session = subject.getSession();
			}
			if (session != null){
				return session;
			}
//			subject.logout();
		}catch (InvalidSessionException e){
			
		}
		return null;
	}
}
