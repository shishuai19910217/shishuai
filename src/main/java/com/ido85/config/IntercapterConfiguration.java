/**
 * 
 */
package com.ido85.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ido85.frame.web.rest.interceptor.RestCommonInterceptor;

/**
 * 拦截器配置
 * @author rongxj
 *
 */
@Configuration
public class IntercapterConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	@Qualifier("entityManagerFactorySecondary")
	private EntityManagerFactory emf;
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(new RestCommonInterceptor()).addPathPatterns("/**");
		registry.addWebRequestInterceptor(openEntityManagerInViewInterceptor());
		super.addInterceptors(registry);
	}
	
	private OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor(){
		OpenEntityManagerInViewInterceptor interceptor = new org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor();
		interceptor.setEntityManagerFactory(emf);
		return interceptor;
	}
}
