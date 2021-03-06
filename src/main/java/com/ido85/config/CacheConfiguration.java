/**
 * 
 */
package com.ido85.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 缓存配置（ehcache）
 * 
 * @author rongxj
 *
 */
@Configuration
public class CacheConfiguration extends CachingConfigurerSupport {

	@Bean(name = "cacheManager")
	@Primary
	public CacheManager cacheManager(
			@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
		return new RedisCacheManager(redisTemplate);
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate(
			RedisConnectionFactory factory) {
		StringRedisTemplate template = new StringRedisTemplate(factory);
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
				Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}

	/**
	 * 默认 缓存
	 * 
	 * @param bean
	 * @return
	 */
//	@Bean(name = "ehCacheManager")
//	public CacheManager cacheManager(EhCacheManagerFactoryBean bean) {
//		return new EhCacheCacheManager(bean.getObject());
//	}

	/**
	 * ehcache 管理器
	 */
	@Bean(name = "appEhCacheCacheManager")
	public EhCacheCacheManager ehCacheCacheManager(
			EhCacheManagerFactoryBean bean) {
		return new EhCacheCacheManager(bean.getObject());
	}

	/**
	 * 据shared与否的设置,Spring分别通过CacheManager.create()或new
	 * CacheManager()方式来创建一个ehcache基地.
	 */
	@Bean(name = "ehCacheManagerFactory")
	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
		EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		cacheManagerFactoryBean.setConfigLocation(new ClassPathResource(
				"conf/ehcache-local.xml"));
		cacheManagerFactoryBean.setShared(true);
		return cacheManagerFactoryBean;
	}

	/*
	 * @Bean
	 * 
	 * @DependsOn("keywordService") public Object getKwywords(KeywordService
	 * keywordService){ List<Keyword> list = keywordService.getKeyWords();
	 * if(null!=list&&list.size()>0){ for(Keyword k : list){
	 * CacheUtils.put(k.getKeywordName().hashCode()+"", k.getKeywordId()); } }
	 * 
	 * System.out.println("-------------------------注入完成------------------");
	 * return new Object(); }
	 */
}
