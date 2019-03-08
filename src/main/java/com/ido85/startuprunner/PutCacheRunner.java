package com.ido85.startuprunner;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.ido85.frame.web.rest.security.Constants;
import com.ido85.master.channel.domain.ChannelDict;
import com.ido85.master.keyword.domain.Keyword;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * 服务启动执行 将数据放入缓存中
 */
@Component
@AutoConfigureOrder
@DependsOn("cacheManager")
public class PutCacheRunner implements CommandLineRunner {
	@PersistenceContext(unitName = "system")
	private EntityManager entityManager;
	@Inject
	private CacheManager cacheManager;

	private Cache getCache(String cacheName) {
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null) {
			cacheManager.addCache(cacheName);
			cache = cacheManager.getCache(cacheName);
			cache.getCacheConfiguration().setEternal(true);
		}
		return cache;
	}
	/**
	 * 写入缓存
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public void put(String cacheName, String key, Object value) {
		Element element = new Element(key, value);
		getCache(cacheName).put(element);
	}
	/**
	 * 获取缓存
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public Object get(String cacheName, String key) {
		Element element = getCache(cacheName).get(key);
		return element == null ? null : element.getObjectValue();
	}
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作<<<<<<<<<<<<<");
		putKeyword();
		putFocussiteDict();
		putChannel();
		System.out.println(">>>>>>>>>>>>>>>服务启动执行，加载数据等操作完成<<<<<<<<<<<<<");
	}
	private void putKeyword() throws Exception {
		Query query = (Query) entityManager.createNamedQuery("Keyword.findAll");
		List<Keyword> list = query.getResultList();
		for (Keyword keyword : list) {
			put(Constants.KEYWORD_CACHE, keyword.getKeywordName(), keyword);
		}
		put(Constants.DICT_CACHE, Constants.KEYWORD_CACHE_LIST, list);
	}
	private void putFocussiteDict() throws Exception {
//		Query query = (Query) entityManager.createNamedQuery("FocussiteDict.findAll");
//		List<FocussiteDict> list = query.getResultList();
//		for (FocussiteDict focussiteDict : list) {
//			put(Constants.FOCUSSITEDICT_CACHE, focussiteDict.getFocussiteId(), focussiteDict);
//		}
//		put(Constants.DICT_CACHE, Constants.FOCUSSITEDICT_CACHE_LIST, list);
	}
	private void putChannel() throws Exception {
		Query query = (Query) entityManager.createNamedQuery("ChannelDict.findAll");
		List<ChannelDict> list = query.getResultList();
		for (ChannelDict channelDict : list) {
			put(Constants.CHANNELDICT_CACHE, channelDict.getEngineType().toString(), channelDict);
		}
		put(Constants.DICT_CACHE, Constants.CHANNELDICT_CACHE_LIST, list);
	}
}
