package com.ido85.master.common;


import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.ido85.master.common.Sequence;
import com.ido85.master.common.Sequence.SequnceType;

@Named
public class MasterSequenceUtil {
	@PersistenceContext(unitName = "system")
	private EntityManager entityManager;
	private Sequence seqKey;
	private Sequence seqPro;

	@PostConstruct
	private void init(){
		seqKey = new Sequence(SequnceType.KEYWORD_SEQUENCE, entityManager);
		seqPro = new Sequence(SequnceType.PROJECT_SEQUENCE, entityManager);
	}
	
	/**
	 * 业务数据模块通用主键序列获取
	 * 
	 * @return
	 * @throws Exception
	 */
	@Transactional(value = TxType.REQUIRES_NEW)
	public synchronized int getCommonSeq() throws Exception {
		return (int)seqPro.nextSeq();
	}

	/**
	 * 业务数据关键词模块通用主键序列获取
	 * 
	 * @return
	 * @throws Exception
	 */
	@Transactional(value = TxType.REQUIRES_NEW)
	public synchronized int getKeywordSeq() throws Exception {
		return (int)seqKey.nextSeq();
	}
	

}
