package com.ido85.seo.keywordrank.application;

import java.util.Date;
import java.util.List;

import com.ido85.seo.keywordrank.domain.ProDetail;
import com.ido85.seo.keywordrank.dto.OutDiffEngineRankDto;
import com.ido85.seo.keywordrank.dto.OutKeyRankDto;
import com.ido85.seo.keywordrank.dto.OutRankDto;
import com.ido85.seo.keywordrank.vo.KeywordRankCompareVo;
import com.ido85.seo.keywordrank.vo.ProRankVo;
import com.ido85.seo.keywordrank.vo.RankSearchVisibilityVo;

public interface KeywordRankApplication {
	/**
	 * 获取项目下的关键词在某一渠道的排名信息
	 * @param crawlDate
	 * @param projectId
	 * @param keywordId
	 * @param engineType
	 * @return
	 */
	ProDetail getKeywordRankInfo(Date crawlDate, Integer projectId, Integer keywordId, Integer engineType) throws Exception;
	/**
	 * 获取项目下的某些关键词在某些渠道的某些日期的排名信息
	 * @param crawlDates
	 * @param projectId
	 * @param keywordIds
	 * @param engineTypes
	 * @return
	 */
	List<ProDetail> getProDetailInfos(List<Date> crawlDates, Integer projectId, List<Integer> keywordIds, List<Integer> engineTypes) throws Exception;
	/**
	 * 获取项目维度搜索可见性/排名升降情况
	 * @param crawlDates
	 * @param projectId
	 * @param keywordIds
	 * @param engineTypes
	 * @return
	 */
	OutKeyRankDto getKeywordRankBaseInfo(List<Date> crawlDates, Integer projectId, List<Integer> keywordIds, Integer engineType) throws Exception;
	/**
	 * 查询项目和竞品维度所有的关键词的搜索可见性
	 * @param crawlDates
	 * @param projectId
	 * @param competitorIds
	 * @param keywordIds
	 * @param engineType
	 * @return
	 * @throws Exception
	 */
	List<RankSearchVisibilityVo> getRankSearchVisibility(List<Date> crawlDates, Integer projectId, List<Integer> competitorIds, List<Integer> keywordIds, Integer engineType) throws Exception;
	/**
	 * 查询项目维度所有的关键词在某一渠道的排名
	 * @param crawlDates
	 * @param projectId
	 * @param keywordIds
	 * @param engineType
	 * @return
	 * @throws Exception
	 */
	List<OutRankDto> getProRanks(List<Date> crawlDates, Integer projectId, List<Integer> keywordIds, Integer engineType) throws Exception;
	/**
	 * 查询项目某些的关键词在某一渠道的排名详细信息
	 * @param crawlDates
	 * @param projectId
	 * @param keywordIds
	 * @param engineType
	 * @return
	 * @throws Exception
	 */
	List<KeywordRankCompareVo> getAllKeywordsRanks(List<Date> crawlDates, Integer projectId, List<Integer> keywordIds, Integer engineType) throws Exception;
	/**
	 * 查询项目所有的关键词在所有渠道的搜索可见性
	 * @param crawlDates
	 * @param projectId
	 * @param keywordIds
	 * @param engineTypes
	 * @return
	 * @throws Exception
	 */
	List<RankSearchVisibilityVo> getAllEngineVisibility(List<Date> crawlDates, Integer projectId, List<Integer> keywordIds, List<Integer> engineTypes) throws Exception;
	/**
	 * 查询项目维度所有的关键词在某些渠道的排名和升迁情况
	 * @param crawlDates
	 * @param projectId
	 * @param keywordIds
	 * @param engineType
	 * @return
	 * @throws Exception
	 */
	List<OutDiffEngineRankDto> getDiffEngineRanks(List<Date> crawlDates, Integer projectId, List<Integer> keywordIds, List<Integer> engineTypes) throws Exception;
	/**
	 * 查询项目和竞品维度的某些关键词的排名前30个数
	 * @param crawlDates
	 * @param projectId
	 * @param competitorIds
	 * @param keywordIds
	 * @param engineType
	 * @return
	 * @throws Exception
	 */
	List<ProRankVo> getAllSearchResult(List<Date> crawlDates, Integer projectId, List<Integer> competitorIds, List<Integer> keywordIds, Integer engineType) throws Exception;
	/**
	 * 查询项目和竞品维度的某些关键词的排名信息
	 * @param crawlDates
	 * @param projectId
	 * @param competitorIds
	 * @param keywordIds
	 * @param engineType
	 * @return
	 * @throws Exception
	 */
	List<ProRankVo> getRankingAnalysis(List<Date> crawlDates, Integer projectId, List<Integer> competitorIds, List<Integer> keywordIds, Integer engineType) throws Exception;
}
