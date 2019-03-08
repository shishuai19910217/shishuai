package com.ido85.seo.dashboard.application;

import com.ido85.seo.dashboard.dto.InExtLinkDto;
import com.ido85.seo.dashboard.dto.InKeywordRankDto;
import com.ido85.seo.dashboard.dto.InKeywordTopDto;
import com.ido85.seo.dashboard.dto.InSearchQuestionDto;
import com.ido85.seo.dashboard.dto.InSearchRankDto;
import com.ido85.seo.dashboard.dto.InVisibilityDto;
import com.ido85.seo.dashboard.dto.OutExtLinkDomainsDto;
import com.ido85.seo.dashboard.dto.OutExtLinkDto;
import com.ido85.seo.dashboard.dto.OutKeywordRankDto;
import com.ido85.seo.dashboard.dto.OutKeywordTopDto;
import com.ido85.seo.dashboard.dto.OutSearchQuestionDto;
import com.ido85.seo.dashboard.dto.OutSearchRankDto;
import com.ido85.seo.dashboard.dto.OutVisibilityDto;

public interface DashboardVisibilityApplication {
	/*****
	 * 搜索品牌可见性 单搜索引擎 
	 * @param in
	 * @return
	 */
	public OutVisibilityDto getVisibilityListByEngineType(InVisibilityDto in) throws Exception;
	/*****
	 * 搜索仅项目可见性 多搜索引擎 
	 * @param in
	 * @return
	 */
	public OutVisibilityDto getVisibilityListByMoreEngine(InVisibilityDto in) throws Exception;
	/***
	 * 项目本身排名最高的前5个关键词
	 * @param in
	 * @return
	 */
	public OutKeywordTopDto getKeywordTop(InKeywordTopDto in) throws Exception;
	/***
	 * 排名在1-10位的关键词个数
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public OutKeywordRankDto getkeywordRankNum(InKeywordRankDto in)throws Exception;
	/***
	 * 
	 *	搜索情况概览   排名情况
	 * @param in
	 * @return
	 */
	public OutSearchRankDto getSearchRank(InSearchRankDto in)throws Exception;
	/***
	 * 搜索情况   整站爬取
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public OutSearchQuestionDto getQuestionByWeekOrM(InSearchQuestionDto in)throws Exception;
	
	/**
	 * 外部链接域名个数
	 * @param in
	 * @return
	 */
	public OutExtLinkDomainsDto getExtLinkDomains(InExtLinkDto in) throws Exception;
	/***
	 * 
	 * 外部链接个数
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public OutExtLinkDto getExtLink(InExtLinkDto in) throws Exception;

}
