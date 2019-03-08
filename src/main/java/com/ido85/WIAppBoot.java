package com.ido85;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

/**
 * start
 * @author rongxj
 */
// @Configuration
@ServletComponentScan
@ComponentScan(basePackages = {"com.ido85"}, includeFilters = {
		@ComponentScan.Filter(value = Service.class, type = FilterType.ANNOTATION),
		@ComponentScan.Filter(value = Named.class, type = FilterType.ANNOTATION),
		@ComponentScan.Filter(value = Inject.class, type = FilterType.ANNOTATION) })
// @EnableAutoConfiguration
@SpringBootApplication
@EnableScheduling
//@EnableConfigurationProperties(ShiroProperties.class)
@EnableCaching
@EnableEurekaClient
public class WIAppBoot {
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(WIAppBoot.class);
		application.setWebEnvironment(true);
		ApplicationContext context = application.run();
		
		StandardEvaluationContext ctx = new StandardEvaluationContext();  
	    ctx.setBeanResolver(new BeanFactoryResolver(context)); 
	}
}