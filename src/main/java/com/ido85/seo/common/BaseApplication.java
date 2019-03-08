package com.ido85.seo.common;

import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * application 基类
 * @author fire
 *
 */
public abstract class BaseApplication {
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@PersistenceContext(unitName="system")
	protected EntityManager entity;
	@PersistenceContext(unitName="seo")
	protected EntityManager seoEntityManager;
	
	@Transactional
	public boolean updateEntitySys(String sql, Map<String, Object> params) throws Exception {
		Query query = entity.createQuery(sql);
		
		if(null != params && params.size() > 0){
			Set<String> set = params.keySet();
				for (String str : set) {
					query.setParameter(str, params.get(str));
				}
		}
		return query.executeUpdate() >= 0?true:false;
	}
	@Transactional
	public boolean updateEntityNodes(String sql, Map<String, Object> params) throws Exception {
		Query query = seoEntityManager.createQuery(sql);
		
		if(null != params && params.size() > 0){
			Set<String> set = params.keySet();
			for (String str : set) {
				query.setParameter(str, params.get(str));
			}
		}
		return query.executeUpdate() >= 0?true:false;
	}

}
