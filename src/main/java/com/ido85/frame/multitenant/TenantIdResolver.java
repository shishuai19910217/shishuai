/**
 * 
 */
package com.ido85.frame.multitenant;

import javax.inject.Named;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.ThreadContext;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Value;

import com.ido85.frame.spring.InstanceFactory;
import com.ido85.frame.web.UserInfo;
import com.ido85.frame.web.rest.security.StatelessPrincipal;
import com.ido85.frame.web.rest.utils.RestUserUtils;

/**
 * @author rongxj
 *
 */
@Named("tenantIdResolver")
//@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TenantIdResolver implements CurrentTenantIdentifierResolver{

	@Value("${spring.jpa.multitenant.default-id}")
	private String defaultTenant;
	
	@Override
	public String resolveCurrentTenantIdentifier() {
		try {
			StatelessPrincipal principal = (StatelessPrincipal) SecurityUtils.getSubject().getPrincipal();
			if (null == principal) {
				principal = (StatelessPrincipal) ThreadContext.get("principal");
			}
			if(null == principal.getTenantID() || "".equals(principal.getTenantID())){
				UserInfo currentUser = RestUserUtils.getUserInfo();
				if(currentUser != null){
					return "seo_nodes_"+currentUser.getTenantID()+"";
				}
			}
			System.out.println("llz===Tenant==getTenantID"+principal.getTenantID());
			return "seo_nodes_"+ principal.getTenantID()+"";
		} catch (Exception e) {
//			e.printStackTrace();
		}
		
		return defaultTenant+"";
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}
	
	public static String currentTenant() {
		try {
			UserInfo currentUser = RestUserUtils.getUserInfo();
			if(currentUser != null){
				return currentUser.getTenantID()+"";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return InstanceFactory.getEnvironment().getProperty("spring.jpa.multitenant.default-id");
	}
}
