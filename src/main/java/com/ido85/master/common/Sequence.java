package com.ido85.master.common;

import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.ido85.frame.common.utils.StringUtils;

public class Sequence {
	private AtomicLong startSeq = new AtomicLong(0l);
	private AtomicLong endSeq = new AtomicLong(0l);
	private long step = 100;
	private SequnceType type;
	private EntityManager em;
	
	public Sequence(SequnceType type, EntityManager em){
		this.type = type;
		this.em = em;
		init();
	}
	
	private void init(){
		//这里初始化sequence
		queryFromDb();
		startSeq.set(endSeq.get() - step);
	}
	
	public long nextSeq(){
		long res = startSeq.incrementAndGet();
		if(res >= endSeq.get()){
			//这里再次获取sequnce
			this.queryFromDb();
			startSeq.set(endSeq.get() - step);
		}
		return res;
	}
	
	private void queryFromDb(){
		String sql = null;
		if(type == SequnceType.KEYWORD_SEQUENCE){
			sql = "select keyword_nextval('keyword_seq')";
		}else{
			sql = "select pronextval('proseq')";

		}
		Query query = em.createNativeQuery(sql);
		Object seq = query.getSingleResult();
		long res = StringUtils.toLong(seq);
		if (res <= 0) {
			throw new RuntimeException();
		}
		endSeq.set(res);
	}
	
	public enum SequnceType{
		PROJECT_SEQUENCE,
		KEYWORD_SEQUENCE
	}
}
