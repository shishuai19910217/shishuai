package com.ido85.seo.common;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ido85.frame.common.utils.StringUtils;

@Named
public class SequenceUtil {
	private static transient final Logger log = LoggerFactory.getLogger(SequenceUtil.class);
	@PersistenceContext(unitName="system")
	private EntityManager entity;
	
	/**
	 * 业务数据模块通用主键序列获取
	 * @return
	 * @throws Exception
	 */
	@Transactional(value = TxType.REQUIRES_NEW)
	public synchronized int getCommonSeq()throws Exception {
		int res = 0;
		String sql = "select pronextval('proseq')";
		
		Query query = entity.createNativeQuery(sql);
		Object seq = query.getSingleResult();
		res = StringUtils.toInteger(seq);
		if(res <= 0){
			log.info("通用序列生成失败！");
			throw new RuntimeException();
		}
		return res;
	}

	/**
	 * 业务数据关键词模块通用主键序列获取
	 * @return
	 * @throws Exception
	 */
	@Transactional(value = TxType.REQUIRES_NEW)
	public synchronized int getKeywordSeq()throws Exception {
		int res = 0;
		String sql = "select keyword_nextval('keyword_seq')";
		
		Query query = entity.createNativeQuery(sql);
		Object seq = query.getSingleResult();
		res = StringUtils.toInteger(seq);
		if(res <= 0){
			log.info("通用序列生成失败！");
			throw new RuntimeException();
		}
		return res;
	}
	
}
