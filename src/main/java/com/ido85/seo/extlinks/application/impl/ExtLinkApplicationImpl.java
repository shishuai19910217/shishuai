package com.ido85.seo.extlinks.application.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ido85.frame.common.Page;
import com.ido85.frame.common.utils.CompareUtil;
import com.ido85.frame.common.utils.StringUtils;
import com.ido85.seo.dashboard.domain.ProDomain;
import com.ido85.seo.dashboard.domain.ProLinksView;
import com.ido85.seo.extlinks.application.ExtLinkApplication;
import com.ido85.seo.extlinks.domain.ProAnchor;
import com.ido85.seo.extlinks.domain.ProDownUrl;
import com.ido85.seo.extlinks.domain.ProLinks;
import com.ido85.seo.extlinks.dto.ExtLinkDomainsDto;
import com.ido85.seo.extlinks.dto.ExtLinkDto;
import com.ido85.seo.extlinks.dto.OutLinksViewDto;

@Named
public class ExtLinkApplicationImpl implements ExtLinkApplication {
	@PersistenceContext(unitName = "system")
	protected EntityManager sysentity;
	@PersistenceContext(unitName = "seo")
	protected EntityManager entity;

	public OutLinksViewDto getLinkInfoByProjectId(String projectId,
			int limitNum, Date crawlDate) {
		OutLinksViewDto out = null;
		StringBuffer sql = new StringBuffer("select t from ProLinksView t where t.projectId = :projectId "
				+ "and t.competitorId = :projectId ");
		if(null != crawlDate){
			sql.append(" and t.crawlDate <= :crawlDate");
		}
		sql.append(" order by t.crawlDate desc ");
		
		Query query = entity.createQuery(sql.toString(), ProLinksView.class);
		query.setParameter("projectId", StringUtils.toInteger(projectId));
		query.setFirstResult(0);
		query.setMaxResults(limitNum);
		if(null != crawlDate){
			query.setParameter("crawlDate", crawlDate);
		}
		
		List<ProLinksView> proLinksViews = query.getResultList();
		if(null != proLinksViews && proLinksViews.size() > 0){
			out = new OutLinksViewDto();
			out.setExtBackLinks(proLinksViews.get(0).getExtBackLinks());
			out.setRefDomains(proLinksViews.get(0).getRefDomains());
			out.setCrawlDate(proLinksViews.get(0).getCrawlDate());
			if(limitNum == proLinksViews.size() && (null == proLinksViews.get(0).getCompetitorId() || "".equals(proLinksViews.get(0).getCompetitorId()))){
				out.setDomainVariety(proLinksViews.get(0).getRefDomains() - proLinksViews.get(limitNum - 1).getRefDomains());
				out.setLinksVariety(proLinksViews.get(0).getExtBackLinks() - proLinksViews.get(limitNum - 1).getExtBackLinks());
			}
		}
		return out;
	}

	@Override
	public List<ProLinksView> getLinkInfo(String projectId, Date startDate, Date endDate) {
		
		StringBuffer sql = new StringBuffer("select t from ProLinksView t where t.projectId = :projectId ");
		if(null != endDate){
			sql.append(" and t.crawlDate <= :endDate");
		}
		if(null != startDate){
			sql.append(" and t.crawlDate >= :startDate");
		}
		sql.append(" order by t.crawlDate asc ");
		
		Query query = entity.createQuery(sql.toString(), ProLinksView.class);
		query.setParameter("projectId", StringUtils.toInteger(projectId));
		if(null != endDate){
			query.setParameter("endDate", endDate);
		}
		if(null != startDate){
			query.setParameter("startDate", startDate);
		}
		
		return query.getResultList();
	}

	@Override
	public void getPageProLinks(String projectId, Page<ProLinksView> page, String flag, String queryTag,
			Date crawlDate) {
		StringBuffer sql = new StringBuffer("select t from ProLinksView t where t.projectId = :projectId ");

		if ("0".equals(queryTag)) {
			sql.append(" and t.competitorId = :projectId ");
		}
		if (null != crawlDate) {
			sql.append(" and t.crawlDate = :crawlDate ");
		}
		sql.append(" order by t.crawlDate desc");
		Query query = entity.createQuery(sql.toString(), ProLinksView.class);
		query.setParameter("projectId", StringUtils.toInteger(projectId));
		query.setFirstResult(page.getPageNo() <= 0 ? 0 : (page.getPageNo() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		if (null != crawlDate) {
			query.setParameter("crawlDate", crawlDate);
		}

		List<ProLinksView> proLinksViews = query.getResultList();
		if (null != proLinksViews && proLinksViews.size() > 0) {
			Collections.sort(proLinksViews, CompareUtil.createComparator(1, "crawlDate"));
			page.setList(proLinksViews);
		} else {
			page.setCount(0);
			return;
		}

		// 查询分页总数
		if ("1".equals(flag)) {
			sql = new StringBuffer(" select count(t) from ProLinksView t where t.projectId = :projectId ");

			if ("0".equals(queryTag)) {
				sql.append(" and t.competitorId = :projectId ");
			}
			if (null != crawlDate) {
				sql.append(" and t.crawlDate = :crawlDate ");
			}
			Query countQuery = entity.createQuery(sql.toString());
			countQuery.setParameter("projectId", StringUtils.toInteger(projectId));
			if (null != crawlDate) {
				query.setParameter("crawlDate", crawlDate);
			}
			Object count = countQuery.getSingleResult();

			page.setCount(StringUtils.toLong(count));
		}

	}

	@Override
	public void getPageDomain(String projectId, Page<ProDomain> page, String flag) {
		String sql = "select t from ProDomain t where t.projectId = :projectId order by t.extLinks desc ";

		Query query = entity.createQuery(sql, ProDomain.class);
		query.setParameter("projectId", StringUtils.toInteger(projectId));
		query.setFirstResult(page.getPageNo() <= 0 ? 0 : (page.getPageNo() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());

		List<ProDomain> proDomains = query.getResultList();
		if (null != proDomains && proDomains.size() > 0) {
			Collections.sort(proDomains, CompareUtil.createComparator(1, "crawlDate"));
			page.setList(proDomains);
		} else {
			page.setCount(0);
			return;
		}
		// 查询分页总数
		if ("1".equals(flag)) {
			sql = "select count(t) from ProDomain t where t.projectId = :projectId";

			Query countQuery = entity.createQuery(sql);
			countQuery.setParameter("projectId", StringUtils.toInteger(projectId));
			Object count = countQuery.getSingleResult();

			page.setCount(StringUtils.toLong(count));
		}
	}

	@Override
	public void getPageAnchor(String projectId, Page<ProAnchor> page,
			String flag) {
		StringBuffer sql= new StringBuffer("select t from ProAnchor t where t.projectId = :projectId ");
		
		if(null != page.getFuncParam() && !"".equals(page.getFuncParam())){
			sql.append(" and t.anchor like :anchor ");
		}
		sql.append(" order by t.refDomains desc ");
		Query query = entity.createQuery(sql.toString(), ProAnchor.class);
		query.setParameter("projectId", StringUtils.toInteger(projectId));
		query.setFirstResult(page.getPageNo() <= 0?0:(page.getPageNo() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		
		if(null != page.getFuncParam() && !"".equals(page.getFuncParam())){
			query.setParameter("anchor", "%" + page.getFuncParam() + "%");
		}
		
		List<ProAnchor> proAnchor = query.getResultList();
		if(null != proAnchor && proAnchor.size() > 0){
			Collections.sort(proAnchor, CompareUtil.createComparator(1, "crawlDate"));
			page.setList(proAnchor);
		}else {
			page.setCount(0);
			return;
		}
		
		//查询分页总数
		if("1".equals(flag)){
			sql.delete(0, sql.length()); 
			sql.append("select count(t) from ProAnchor t where t.projectId = :projectId");
			
			if(null != page.getFuncParam() && !"".equals(page.getFuncParam())){
				sql.append(" and t.anchor like :anchor ");
			}
			
			Query countQuery = entity.createQuery(sql.toString());
			countQuery.setParameter("projectId", StringUtils.toInteger(projectId));
			
			if(null != page.getFuncParam() && !"".equals(page.getFuncParam())){
				countQuery.setParameter("anchor", "%" + page.getFuncParam() + "%");
			}
			
			Object count = countQuery.getSingleResult();
			
			page.setCount(StringUtils.toLong(count));
		}
	}
	@Override
	public void getPageExtLinks(String projectId, Page<ProLinks> page, String flag) {
		String sql= "select t from ProLinks t where t.projectId = :projectId ";
		
		Query query = entity.createQuery(sql, ProLinks.class);
		StringUtils.toInteger(projectId);
		query.setParameter("projectId", StringUtils.toInteger(projectId));
		query.setFirstResult(page.getPageNo() <= 0?0:(page.getPageNo() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		
		List<ProLinks> proLinks = query.getResultList();
		if(null != proLinks && proLinks.size() > 0){
			page.setList(proLinks);
		}else {
			page.setCount(0);
			return;
		}
		
		//查询分页总数
		if("1".equals(flag)){
			sql = "select count(t) from ProLinks t where t.projectId = :projectId";
			
			Query countQuery = entity.createQuery(sql);
			countQuery.setParameter("projectId", StringUtils.toInteger(projectId));
			Object count = countQuery.getSingleResult();
			
			page.setCount(StringUtils.toLong(count));
		}
	}
	

	@Override
	public void getPageDownUrl(String projectId, Page<ProDownUrl> page, String flag) {
		String sql= "select t from ProDownUrl t where t.projectId = :projectId ";
		
		Query query = entity.createQuery(sql, ProDownUrl.class);
		query.setParameter("projectId", StringUtils.toInteger(projectId));
		query.setFirstResult(page.getPageNo() <= 0?0:(page.getPageNo() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		
		List<ProDownUrl> proDownUrls = query.getResultList();
		if(null != proDownUrls && proDownUrls.size() > 0){
			page.setList(proDownUrls);
		}else {
			page.setCount(0);
			return;
		}
		
		//查询分页总数
		if("1".equals(flag)){
			sql = "select count(t) from ProDownUrl t where t.projectId = :projectId";
			
			Query countQuery = entity.createQuery(sql);
			countQuery.setParameter("projectId", StringUtils.toInteger(projectId));
			Object count = countQuery.getSingleResult();
			
			page.setCount(StringUtils.toLong(count));
		}
	}

	@Override
	public ExtLinkDomainsDto getExtLinkDomains(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExtLinkDto getExtLink(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long getSumPageAnchor(Integer projectId) {
		StringBuffer sql= new StringBuffer("select sum(t.totalLinks) from ProAnchor t where t.projectId = :projectId ");
		Query query = entity.createQuery(sql.toString());
		query.setParameter("projectId", projectId);
		
		Object res = query.getSingleResult();
		if(null != res){
			return StringUtils.toLong(res);
		}else {
			return 0;
		}
	}
	
	@Override
	public long getSumPageDomain(Integer projectId) {
		StringBuffer sql= new StringBuffer("select sum(t.extLinks) from ProDomain t where t.projectId = :projectId ");
		Query query = entity.createQuery(sql.toString());
		query.setParameter("projectId", projectId);
		
		Object res = query.getSingleResult();
		if(null != res){
			return StringUtils.toLong(res);
		}else {
			return 0;
		}
	}

}
