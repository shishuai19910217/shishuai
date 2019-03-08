package com.ido85.seo.allSite.application.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;

import com.ido85.config.properties.BussinessMsgCodeProperties;
import com.ido85.frame.common.exceptions.BusinessException;
import com.ido85.frame.common.restful.Resource;
import com.ido85.frame.common.utils.ComparatorDate;
import com.ido85.frame.common.utils.CompareUtil;
import com.ido85.frame.common.utils.DateUtils;
import com.ido85.frame.common.utils.ListUntils;
import com.ido85.frame.web.rest.security.Constants;
import com.ido85.seo.allSite.application.AllSiteApplication;
import com.ido85.seo.allSite.domain.PageDetail;
import com.ido85.seo.allSite.domain.PageMain;
import com.ido85.seo.allSite.domain.PageRealate;
import com.ido85.seo.allSite.dto.InIssuesInfoDto;
import com.ido85.seo.allSite.dto.OutIssuesInfoDto;
import com.ido85.seo.allSite.dto.OutIssuesItemDto;
import com.ido85.seo.allSite.dto.OutSearchQuestionDto;
import com.ido85.seo.allSite.dto.SiteQuestionDto;
import com.ido85.seo.allSite.resources.AllSiteResources;
import com.ido85.seo.common.BaseApplication;
import com.ido85.seo.dashboard.domain.ProLinksView;
import com.ido85.services.project.ProjectApi;
import com.ido85.services.time.TimeInsideApi;
@Named
public class AllSiteApplicationImpl extends BaseApplication implements AllSiteApplication{
	
	@PersistenceContext(unitName = "seo")
	private EntityManager entityManager;
	@Inject
	private AllSiteResources allSiteResources;
	@Inject
	private TimeInsideApi timeApi;
	
	@Inject
	private ProjectApi projectApi;
	
	@Inject
	private BussinessMsgCodeProperties msg;
	@Override
	public List<OutIssuesItemDto> getChartIssuesInfo(
			InIssuesInfoDto in) throws Exception {
		List<OutIssuesItemDto> dtoList = new ArrayList<OutIssuesItemDto>();
		
		Pageable pageable = new PageRequest(0, Constants.SHOW_NUM, Direction.DESC, "createDate");
		List<Date> dList = timeApi.getTimesByIsWeek(in.getProjectId(), "0", Constants.SHOW_NUM);
		if(dList != null){
			for (Date date : dList) {
				List<OutIssuesItemDto> dtoTmpList = new ArrayList<OutIssuesItemDto>();
				//问题编码不为空
				if(StringUtils.isNotEmpty(in.getIssueCode())){
					dtoTmpList = allSiteResources.getHistoryIssuesInfo(in.getProjectId(), in.getIssueType(),in.getIssueCode(),date);
				}else{
					dtoTmpList = allSiteResources.getHistoryIssuesInfo(in.getProjectId(), in.getIssueType(),date);
				}
				if(!ListUntils.isNull(dtoTmpList) && dtoTmpList.get(0).getIssuesDate() != null){
					dtoList.addAll(dtoTmpList);
				}
				
			}
		}
		
		return dtoList;
	}

	@Override
	public SiteQuestionDto getQuestion(int projectId) throws Exception {
		SiteQuestionDto dto = new SiteQuestionDto();
		List<OutIssuesItemDto> dtoList = new ArrayList<OutIssuesItemDto>();
		List<Map<String, Object>> highIssuesInfo  =  new ArrayList<>();//严重问题
		List<Map<String, Object>> lowIssuesInfo=  new ArrayList<>();//轻微问题
		List<Map<String, Object>> mediumIssuesInfo=  new ArrayList<>();//一般问题
		List<Map<String, Object>> issuesShow=  new ArrayList<>();//
		
		highIssuesInfo = getIssuesCodeNumByLevel(projectId, Constants.ISSUES_LEVEL_HIGH);
		lowIssuesInfo = getIssuesCodeNumByLevel(projectId, Constants.ISSUES_LEVEL_LOW);
		mediumIssuesInfo = getIssuesCodeNumByLevel(projectId, Constants.ISSUES_LEVEL_MEDIUM);
		
		List<Date> dList = timeApi.getTimesByIsWeek(projectId, "0", Constants.SHOW_NUM);
		if(dList != null){
			for (Date date : dList) {
				 List<Map<String, Object>> dtoTmpList = new ArrayList<Map<String, Object>>();
				 Map<String, Object> map = new HashMap<String, Object>();
				dtoTmpList = getHistoryLevelNumMap(projectId, date);
				for (Map<String, Object> m : dtoTmpList) {
					int level = Integer.parseInt(m.get("issues_level").toString());
					if( level== Constants.ISSUES_LEVEL_HIGH){//严重问题
						map.put("highIssuesNum", m.get("sum"));
					}
					if(level == Constants.ISSUES_LEVEL_MEDIUM){//一般问题
						map.put("mediumIssuesNum", m.get("sum"));
					}
					if(level == Constants.ISSUES_LEVEL_LOW){//轻微问题
						map.put("lowIssuesNum", m.get("sum"));
					}
				}
				int num = getPageCrawled(projectId,date);
				map.put("issuesDate", DateUtils.formatDateTime(date));
				map.put("pageCrawled", num);
				issuesShow.add(map);
			}
		}
		dto.setHighIssuesInfo(highIssuesInfo);
		dto.setIssuesShow(issuesShow);
		dto.setLowIssuesInfo(lowIssuesInfo);
		dto.setMediumIssuesInfo(mediumIssuesInfo);
		return dto;
	}

	@Override
	public List<OutIssuesInfoDto> getIssuesInfoDetail(
			InIssuesInfoDto in) throws Exception {
		/**
		 * 查询所有页面问题列表  另外
		 * 严重问题  是否收录和是否风险网站
		 * 轻微问题  是否有robots文件
		 * 轻微问题是否有gzip压缩
		 * 这几种问题不在关系表中 要到统计表中查询
		 */
		List<PageDetail> pageList = new ArrayList<PageDetail>();
		List<OutIssuesInfoDto> dtoList = new ArrayList<OutIssuesInfoDto>();
		
		List<Date> dList = timeApi.getTimesByIsWeek(in.getProjectId(), "0", Constants.SHOW_NUM);
		
		if (ListUntils.isNull(dList)) {
			throw new BusinessException(msg.getProcessStatus("NOT_CRAWL"));
		}
	
		Collections.sort(dList, CompareUtil.createComparator(-1));// 倒叙
		
		PageMain m = allSiteResources.getPageMainByProject(in.getProjectId(),dList.get(0));
		
		
		if(StringUtils.isNotEmpty(in.getIssueCode())){
			//如果错误码是整站信息 返回一条记录，其它返回列表
			if(in.getIssueCode().equals("30101") || in.getIssueCode().equals("30102") 
					|| in.getIssueCode().equals("20103") || in.getIssueCode().equals("10102") ){
				OutIssuesInfoDto dto = new OutIssuesInfoDto();
				List riskEngine = new ArrayList();
				if(m.getIsSafeBaidu() == 0){
					riskEngine.add("百度");
				}
				if(m.getIsSafeHaosou() == 0){
					riskEngine.add("好搜");
				}
				if(m.getIsSafeSogou() == 0){
					riskEngine.add("搜狗");
				}
				dto.setRiskEngine(riskEngine.toString());
				
				
				List engine = new ArrayList();
				if(m.getIsEnteringBaidu() == 0){
					engine.add("百度");
				}
				if(m.getIsEnteringHaoso() == 0){
					engine.add("好搜");
				}
				if(m.getIsEnteringSogou() == 0){
					engine.add("搜狗");
				}
				dto.setEngine(engine.toString());
				
				dto.setGzip(m.getGzip()+"");
				dto.setRobots(m.getIsRobots()+"");
				dtoList.add(dto);
			}else{
				pageList = this.getIssuesInfoDetail(in.getProjectId(),in.getIssueCode(), in.getIssueType(),dList.get(0));
				dtoList = init(pageList,m);
			}
			
		}else{
			pageList = this.getIssuesInfoDetail(in.getProjectId(),in.getIssueCode(), in.getIssueType(),dList.get(0));
			dtoList = init(pageList,m);
		}
		return dtoList;
	}

	private List<OutIssuesInfoDto> init(List<PageDetail> pageList,PageMain m){
		List<OutIssuesInfoDto> dtoList = new ArrayList<OutIssuesInfoDto>();
		for (PageDetail p : pageList) {
			if(p == null){
				continue;
			}
			OutIssuesInfoDto dto = new OutIssuesInfoDto();
			dto.setAnchorText("");
			int res = 0;
			if(p.getDes() != null){
				res = p.getDes().length();
			}
			dto.setDescriptionNum(res);
			if(p.getDomains() != null){
				dto.setDomains(p.getDomains());
			}else{
				dto.setDomains(0);
			}
			
			dto.setDuplicateUrl("");
			dto.setExternalLinks("");
			dto.setFlashTag(p.getIsFlash()+"");
			dto.setGzip(m.getGzip()+"");
			if(p.getH1Sum() != null){
				dto.setH1Num(p.getH1Sum());
			}else{
				dto.setH1Num(0);
			}
			if(p.getH2Sum() != null){
				dto.setH2Num(p.getH2Sum());
			}else{
				dto.setH2Num(0);
			}
			if(p.getH3Sum() != null){
				dto.setH1Num(p.getH3Sum());
			}else{
				dto.setH3Num(0);
			}
			dto.setIframeTag(p.getIsFlash()+"");
			dto.setIssueCode("");
			dto.setLinkPage("");
			dto.setPageAuthority("");
			dto.setPageTitle(p.getTitle());
			dto.setPageUrl(p.getPageUrl());
			dto.setStatusCode(p.getStatusCode());
			if(p.getTitle() != null){
				dto.setTitleNum(p.getTitle().length());
			}else{
				dto.setTitleNum(0);
			}
			
			dto.setUrlNum(p.getPageUrl().length());
			dtoList.add(dto);
		}
		return dtoList;
	}
	@Override
	public Map<String, Object> getPageQuestion(int projectId,
			String url) throws Exception {
		Map map = new HashMap();
		int highIssue = 0;
		int lowIssue = 0;
		int mediumIssue = 0;
		List issueInfo = new ArrayList();
		List<Date> dList = timeApi.getTimesByIsWeek(projectId, "0", Constants.SHOW_NUM);
		if (ListUntils.isNull(dList)) {
			throw new BusinessException(msg.getProcessStatus("NOT_CRAWL"));
		}
	
		Collections.sort(dList, CompareUtil.createComparator(-1));// 倒叙
		
		List<PageDetail> detailList = allSiteResources.getDetailByUrl(projectId, url,dList.get(0));
		if(detailList != null && detailList.size() > 0){
			PageDetail d = detailList.get(0);
			List<PageRealate> realList = allSiteResources.getPageRealateByUrl(d.getDetailId(),dList.get(0));
			for (PageRealate r : realList) {
				Map issueMap = new HashMap();
				int level = Integer.parseInt(r.getIssuesLevel());
				String code = r.getIssuesCode();
				if( level== Constants.ISSUES_LEVEL_HIGH){//严重问题
					highIssue = highIssue+1;
				}
				if(level == Constants.ISSUES_LEVEL_MEDIUM){//一般问题
					mediumIssue = mediumIssue+1;
				}
				if(level == Constants.ISSUES_LEVEL_LOW){//轻微问题
					lowIssue = lowIssue+1;
				}
				if(code.equals("30100")){
					List<PageDetail> list = allSiteResources.getRepeatTitle(projectId, d.getTitle(),dList.get(0));
					List l = new ArrayList();
					for (PageDetail p : list) {
						Map m = new HashMap();
						m.put("domains", p.getDomains());
						m.put("externalLinks", p.getLinkNum());
						m.put("statusCode", p.getStatusCode());
						m.put("url", p.getPageUrl());
						l.add(m);
						issueMap.put("existData", l);
					}
				}
				
				issueMap.put("issueCode", r.getIssuesCode());
				issueMap.put("issueLevel", r.getIssuesLevel());
				issueInfo.add(issueMap);
			}
		}
		map.put("highIssue", highIssue);
		map.put("lowIssue", lowIssue);
		map.put("mediumIssue", mediumIssue);
		map.put("issueInfo", issueInfo);
		return map;
	}
	/**
	* @Description: 统计项目在某天各个等级所有问题数量
	* @param @param project_id
	* @param @return    设定文件 
	* @return Map    返回类型 
	* @throws
	 */
	public List<Map<String, Object>> getHistoryLevelNumMap(int projectId,Date createDate){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "SELECT s.issues_level,sum(s.num) sum FROM tf_b_site_statistic s WHERE s.project_id = :projectId and s.create_date = :createDate and s.issues_code != -1 and s.issues_level != -1 group by s.issues_level";
		Query query =  entityManager.createNativeQuery(sql);
		query.setParameter("projectId", projectId);
		query.setParameter("createDate", createDate); 
	    
	    query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
	    List rows = query.getResultList();  
	    for (Object obj : rows) {  
	        Map row = (Map) obj;
	        list.add(row);
	    }  
	    return list;
	}
	/**
	* @Description: 统计项目在某天各个错误码下所有问题数量
	* @param @param project_id
	* @param @return    设定文件 
	* @return Map    返回类型 
	* @throws
	 */
	public List<Map<String, Object>> getHistoryCodeNumMap(int projectId,Date createDate){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "SELECT s.issues_code code,sum(s.num) num FROM tf_b_site_statistic s WHERE s.project_id = :projectId and s.create_date = :createDate and s.issues_code != -1 and s.issues_level != -1  group by s.issues_code";
		Query query =  entityManager.createNativeQuery(sql);
		query.setParameter("projectId", projectId);
		query.setParameter("createDate", createDate); 
	    
	    query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
	    List rows = query.getResultList();  
	    for (Object obj : rows) {  
	        Map row = (Map) obj;
	        list.add(row);
	    }  
	    return list;
	}
	/**
	 * @throws Exception 
	* @Description: 查询不同等级下各个错误码对应的问题数量
	* @param @param project_id 项目id
	* @param @param issuesLevel 问题级别/问题类型
	* @param @return    设定文件 
	* @return Map    返回类型 
	* @throws
	 */
	public List<Map<String, Object>> getIssuesCodeNumByLevel(int projectId,int issuesLevel) throws Exception{
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		 List<Date> dList = timeApi.getTimesByIsWeek(projectId, "0", Constants.SHOW_NUM);
			
		if (ListUntils.isNull(dList)) {
			throw new BusinessException(msg.getProcessStatus("NOT_CRAWL"));
		}
	
		Collections.sort(dList, CompareUtil.createComparator(-1));// 倒叙
		
			
		String sql = "select r.issues_code issueCode,count(*) num from tf_b_page_realate r,tf_b_page_detail d "
				+ " where d.project_id = :projectId and d.detail_id = r.detail_id "
				+ " and r.issues_level = :issuesLevel and d.crawl_date = :crawlDate "
				+ " group by r.issues_code";
		Query query =  entityManager.createNativeQuery(sql);
		query.setParameter("projectId", projectId);
		query.setParameter("issuesLevel", issuesLevel); 
		query.setParameter("crawlDate", dList.get(0)); 
		
	    query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
	    List rows = query.getResultList();  
	    for (Object obj : rows) {  
	        Map row = (Map) obj;
	        list.add(row);
	    }  
	    
		
	  //统计完页面的数量后 要加上作用于全站的几个属性 gzip 是否收录 是否安全   Robots
	    PageMain wiPageMain =  allSiteResources.getPageMainByProject(projectId,dList.get(0));
			if( issuesLevel== Constants.ISSUES_LEVEL_HIGH){//严重问题
				int is_entering_baidu = wiPageMain.getIsEnteringBaidu();
				int is_entering_haoso = wiPageMain.getIsEnteringHaoso();
				int is_entering_sogou = wiPageMain.getIsEnteringSogou();
				if(is_entering_baidu == 0 && is_entering_haoso == 0 && is_entering_sogou == 0){
					Map m = new HashMap();
					m.put("issueCode", 30101);
					m.put("num", 1);
					list.add(m);
				}
				
				int is_safe_baidu = wiPageMain.getIsSafeBaidu();
				int is_safe_haosou = wiPageMain.getIsSafeHaosou();
				int is_safe_sogou = wiPageMain.getIsSafeSogou();
				if(is_safe_baidu == 0 && is_safe_haosou == 0 && is_safe_sogou == 0){
					Map m = new HashMap();
					m.put("issueCode", 30102);
					m.put("num", 1);
					list.add(m);
				}
			}
			if( issuesLevel== Constants.ISSUES_LEVEL_MEDIUM){//一般
				if(wiPageMain.getIsRobots() == 0){
					Map m = new HashMap();
					m.put("issueCode", 20103);
					m.put("num", 1);
					list.add(m);
				}
			}
			if( issuesLevel== Constants.ISSUES_LEVEL_LOW){//轻微问题
				if(wiPageMain.getGzip() == 0){
					Map m = new HashMap();
					m.put("issueCode", 10102);
					m.put("num", 1);
					list.add(m);
				}
			}
	    
	    return list;
	}
	
	public int getPageCrawled(int projectId,Date date){
		StringBuffer count = new StringBuffer("SELECT num FROM SiteStatistic s where s.issuesCode = -1 and s.issuesLevel =-1 and s.projectId = :projectId and s.createDate = :createDate");
		Query countQuery = entityManager.createQuery(count.toString());
		countQuery.setParameter("projectId", projectId);
		countQuery.setParameter("createDate", date);
		int num = 0;
		if(countQuery.getResultList().size() > 0){
			num = Integer.parseInt(countQuery.getSingleResult().toString());
		}
		return num;
	} 
	@SuppressWarnings("unchecked")
	@Override
	public OutSearchQuestionDto getQuestionByWeekOrM(int projectId,int isWeek) throws Exception{
		OutSearchQuestionDto dto = new OutSearchQuestionDto();
		List<Map<String, Object>> questionList  = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> commonQuestionList = new ArrayList<Map<String, Object>>();

		
		
		
		List<Date> tempDates = new ArrayList<>();// 需要补齐的时间点
		int highQuestionNum = 0;
		int pageNum = 0;
		
		//最近爬去日期列表
		List<Date> crawlDates = timeApi.getTimesByIsWeek((Integer)projectId,isWeek+"", 8);
		if (ListUntils.isNull(crawlDates)) {
			throw new BusinessException(msg.getProcessStatus("NOT_CRAWL"));
		}
		
		ComparatorDate c = new ComparatorDate();
        Collections.sort(crawlDates, c);
		
		Date date = crawlDates.get(0);// 最新一次 按周或者按月
		
		//最近一次的严重问题数量和爬去页面数量
		List<Map<String, Object>> dtoLastList = new ArrayList<Map<String, Object>>();
		dtoLastList = getHistoryLevelNumMap(projectId, date);
		for (Map<String, Object> m : dtoLastList) {
			int level = Integer.parseInt(m.get("issues_level").toString());
			if( level== Constants.ISSUES_LEVEL_HIGH){//严重问题
				highQuestionNum=  Integer.parseInt(m.get("sum").toString());
			}
			
		}
		commonQuestionList = getHistoryCodeNumMap(projectId, date);
		
		//某天所有爬去页面数量
		pageNum = getPageCrawled(projectId,date);
		
		//所有节点问题数量
		for (Date d : crawlDates) {
			List<Map<String, Object>> dtoLevelList = new ArrayList<Map<String, Object>>();
			 Map<String, Object> map = new HashMap<String, Object>();
			 dtoLevelList = getHistoryLevelNumMap(projectId, date);
			for (Map<String, Object> m : dtoLevelList) {
				int level = Integer.parseInt(m.get("issues_level").toString());
				if( level== Constants.ISSUES_LEVEL_HIGH){//严重问题
					map.put("highIssuesNum", m.get("sum"));
				}
				if(level == Constants.ISSUES_LEVEL_MEDIUM){//一般问题
					map.put("mediumIssuesNum", m.get("sum"));
				}
				if(level == Constants.ISSUES_LEVEL_LOW){//轻微问题
					map.put("lowIssuesNum", m.get("sum"));
				}
			}
			map.put("crawlDate", date);
			questionList.add(map);
		}
		
		
		dto.setCommonQuestionList(commonQuestionList);
		dto.setQuestionList(questionList);
		dto.setHighQuestionNum(highQuestionNum);
		dto.setPageNum(pageNum);
		return dto;
	}
	
	/**
	* @Description: 查询不同等级的问题列表
	* @param @param project_id 项目id
	* @param @param issuesCode 问题编码
	* @param @param issuesLevel 问题级别/问题类型
	* @param @return    设定文件 
	* @return List<PageDetail>    返回类型 
	* @throws
	 */
	private List<PageDetail> getIssuesInfoDetail(int projectId,String issuesCode,String issuesLevel,Date crawlDate){
		StringBuffer bf = new StringBuffer();
		bf.append(" select d from PageDetail d,PageRealate r ");
		bf.append(" where r.detailId = d.detailId and d.projectId = :projectId ");
		
		if(StringUtils.isNotBlank(issuesCode)){
			bf.append(" and r.issuesCode = :issuesCode ");
		}
		bf.append(" and r.issuesLevel = :issuesLevel ");
		bf.append(" and r.issuesLevel = :issuesLevel ");
		bf.append(" and r.crawlDate = :crawlDate ");
		bf.append(" group by d.detailId ");
		
		
		Query query = seoEntityManager.createQuery(bf.toString(), PageDetail.class);
		query.setParameter("projectId", projectId);
		if(StringUtils.isNotBlank(issuesCode)){
			query.setParameter("issuesCode", issuesCode);
		}
		
		query.setParameter("issuesLevel", issuesLevel);
		query.setParameter("crawlDate", crawlDate);
		query.setFirstResult(0);
		query.setMaxResults(300);
		List<PageDetail> detailList = query.getResultList();
		return detailList;
	}
	public PageMain getIssuesInfo(Map<String, Object> param) throws Exception {
		int projectId = Integer.parseInt(param.get("projectId").toString());
		List<Date> dList = timeApi.getTimesByIsWeek(projectId, "0", Constants.SHOW_NUM);
		
		if (ListUntils.isNull(dList)) {
			throw new BusinessException(msg.getProcessStatus("NOT_CRAWL"));
		}
	
		Collections.sort(dList, CompareUtil.createComparator(-1));// 倒叙
		 PageMain wiPageMain =  allSiteResources.getPageMainByProject(projectId,dList.get(0));
		return wiPageMain;

	}
	
}
