/**
 * 
 */
package com.ido85.config.properties;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * shiro 安全属性配置
 * @author rongxj
 * 
 */
@Named(value = "shiroProperties")
@ConfigurationProperties(prefix="shiro", locations = "application.properties")
public class ShiroProperties {

	@Value("${shiro.encrypt.salt-disabled}")
	private boolean saltDisabled = false;
	@Value("${shiro.encrypt.hash-interations}")
	private int hashInterations = 1024;
	
	@Value("${shiro.session.active-session-cache-name}")
	private String activeSessionsCacheName;
	
	@Value("${shiro.encrypt.remember-me.cipher-key}")
	private String cipherKey;

	
	public boolean isSaltDisabled() {
		return saltDisabled;
	}

	public void setSaltDisabled(boolean saltDisabled) {
		this.saltDisabled = saltDisabled;
	}

	public int getHashInterations() {
		return hashInterations;
	}

	public void setHashInterations(int hashInterations) {
		this.hashInterations = hashInterations;
	}

	public String getActiveSessionsCacheName() {
		return activeSessionsCacheName;
	}

	public void setActiveSessionsCacheName(String activeSessionsCacheName) {
		this.activeSessionsCacheName = activeSessionsCacheName;
	}

	public String getCipherKey() {
		return cipherKey;
	}

	public void setCipherKey(String cipherKey) {
		this.cipherKey = cipherKey;
	}
	
	
}
