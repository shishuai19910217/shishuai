/**
 * 
 */
package com.ido85.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

import com.ido85.frame.web.encrypt.EncryptService;
import com.ido85.frame.web.encrypt.SHA1EncryptService;
import com.ido85.frame.web.rest.security.StatelessDefaultSubjectFactory;
import com.ido85.frame.web.shiro.session.CacheSessionDAO;
import com.ido85.frame.web.shiro.session.SessionManager;

/**
 * shiro 安全配置
 * 
 * @author rongxj
 *
 */
@Configuration
@ComponentScan
@DependsOn({"ehCacheManagerFactory", "shiroProperties"})
@ConfigurationProperties(prefix="shiro", locations = "application.properties")
@Profile(value="prod")
public class ShiroConfiguration {
	
	@Value("${shiro.encrypt.salt-disabled}")
	private boolean saltDisabled = false;
	@Value("${shiro.encrypt.hash-interations}")
	private int hashInterations = 2;
	
	@Value("${shiro.session.active-session-cache-name}")
	private String activeSessionsCacheName = "activeSessionsCache";
	
	@Value("${shiro.encrypt.remember-me.cipher-key}")
	private String cipherKey = "04feRNI3snfin3VDAF3DA==";

//	@Inject//(name = "ehCacheManagerFactory")
//	private EhCacheManagerFactoryBean ehCacheManager;
	/**
	 * 密码加密 service
	 * 
	 * @return
	 */
	@Bean
	public EncryptService encryptService() {
		SHA1EncryptService service = new SHA1EncryptService();
		service.setSaltDisabled(saltDisabled);
		service.setHashIterations(hashInterations);
		return service;
	}


	/**
	 * shiro 生命周期
	 * @return
	 */
	@Bean(name="lifecycleBeanPostProcessor")
	public org.apache.shiro.spring.LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new org.apache.shiro.spring.LifecycleBeanPostProcessor();
	}
	
	
	/**
	 * 配置shiro filter过滤器
	 * @return
	 */
//	@Bean
//    public FilterRegistrationBean filterRegistrationBean() {
//        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
//        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
//        //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理  
//        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
//        filterRegistration.setEnabled(true);
//        filterRegistration.addUrlPatterns("/*");
//        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);
//        System.out.println("\n\n\n\n\nddddddddddddddddd\n\n\n");
//        return filterRegistration;
//    }



	/**
	 * shiro 过滤器链
	 * @return
	 */
	@Bean(name = "shiroFilter")
	@DependsOn("securityManager")
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
	    ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
	    shiroFilter.setLoginUrl("/login");
	    shiroFilter.setSuccessUrl("/index");
	    shiroFilter.setUnauthorizedUrl("/unauthorized");
	    Map<String, String> filterChainDefinitionMapping = new HashMap<String, String>();
	    filterChainDefinitionMapping.put("/**", "anon");
	    filterChainDefinitionMapping.put("/ws/**", "statelessAuthc");
	    filterChainDefinitionMapping.put("/seo/**", "statelessAuthc");
	    //互信机制
	    filterChainDefinitionMapping.put("/trust/**", "trustStatelessAuthc");

		shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMapping);
		shiroFilter.setSecurityManager(securityManager);
		Map<String, Filter> filters = new HashMap<String, Filter>();
	    filters.put("anon", new AnonymousFilter());
	    filters.put("authc", new FormAuthenticationFilter());
	    filters.put("logout", new LogoutFilter());
	    filters.put("roles", new RolesAuthorizationFilter());
	    filters.put("user", new UserFilter());
		filters.put("statelessAuthc", new com.ido85.frame.web.rest.security.StatelessAuthcFilter());
		//互信机制
	    filters.put("trustStatelessAuthc", new com.ido85.frame.web.rest.security.TrustStatelessAuthcFilter());
	    shiroFilter.setFilters(filters);
	    System.out.println(shiroFilter.getFilters().size());
	    return shiroFilter;
	}

	/**
	 * shiro安全管理器
	 * @return
	 */
	@Bean(name = "securityManager")
	@DependsOn({"shiroCacheManager", "sessionManager"})
	public org.apache.shiro.mgt.SecurityManager securityManager(EhCacheManager shiroCacheManager, SessionManager sessionManager) {
	    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
	    // 设置为无状态
	    DefaultSessionStorageEvaluator evaluator = (DefaultSessionStorageEvaluator)((DefaultSubjectDAO)securityManager.getSubjectDAO()).getSessionStorageEvaluator();
	    evaluator.setSessionStorageEnabled(false);
	    securityManager.setCacheManager(shiroCacheManager);
	    securityManager.setSessionManager(sessionManager);
//	    securityManager.setRealm(realm());
	    securityManager.setSubjectFactory(subjectFactory());
//	    securityManager.setRealms(realms());
	    return securityManager;
	}
	
	@Bean
	public SubjectFactory subjectFactory(){
		return new StatelessDefaultSubjectFactory();
	}
	/**
	 * 认证器
	 * @return
	 */
	@SuppressWarnings("unused")
	private Collection<Realm> realms(){
		Collection<Realm> realms = new ArrayList<Realm>();
		return realms;
	}

//	@Bean(name = "realm")
//	@DependsOn("lifecycleBeanPostProcessor")
//	public PropertiesRealm realm() {
//	    PropertiesRealm propertiesRealm = new PropertiesRealm();
//	    propertiesRealm.init();
//	    return propertiesRealm;
//	}
	
	/**
	 * shiro 缓存管理器
	 * @return
	 */
	@Bean(name = "shiroCacheManager")
	@DependsOn("ehCacheManagerFactory")
	public EhCacheManager shiroCacheManager(EhCacheManagerFactoryBean ehCacheManager){
		EhCacheManager shiroCacheManager = new EhCacheManager();
		shiroCacheManager.setCacheManager(ehCacheManager.getObject());
		return shiroCacheManager;
	}
	
	
	
	
	/**
	 * shiro session管理器
	 * @return
	 */
	@Bean(name = "sessionManager")
	@DependsOn("sessionDAO")
	public org.apache.shiro.session.mgt.SessionManager sessionManager(SessionDAO sessionDAO){
		SessionManager sessionManager = new SessionManager();
		sessionManager.setSessionDAO(sessionDAO);
		return sessionManager;
	}
	
	/**
	 * session查询
	 * @return
	 */
	@Bean(name = "sessionDAO")
	@DependsOn("shiroCacheManager")
	public SessionDAO sessionDAO(EhCacheManager cacheManager){
		CacheSessionDAO sessionDao = new CacheSessionDAO();
		sessionDao.setCacheManager(cacheManager);
		sessionDao.setActiveSessionsCacheName(activeSessionsCacheName);
		return sessionDao;
	}
	
	/**
	 * AOP 方法级检查
	 * @return
	 */
	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator aopMethodAdvisor(){
		DefaultAdvisorAutoProxyCreator aop = new DefaultAdvisorAutoProxyCreator();
		aop.setProxyTargetClass(true);
		return aop;
	}
	
	@Bean
	@DependsOn("securityManager")
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}
	
//	public MethodInvokingFactoryBean methodInvokingFactoryBean(){
//		MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
//		bean.setTargetObject(shiroFilter());
//		bean.setTargetMethod("setFilterChainResolver");
//		bean.setArguments(arguments);
//	}
}
