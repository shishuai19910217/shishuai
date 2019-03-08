package com.ido85.seo.keywordrank.resources;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.seo.keywordrank.domain.ProDetail;
import com.ido85.seo.keywordrank.vo.KeywordRankCompareVo;
import com.ido85.seo.keywordrank.vo.ProRankVo;
import com.ido85.seo.keywordrank.vo.RankSearchVisibilityVo;

public interface KeywordRankResources extends JpaRepository<ProDetail, Integer> {
	/**
	 * 获取项目下的关键词在某一渠道的排名信息
	 * @param crawlDate
	 * @param projectId
	 * @param keywordId
	 * @param engineType
	 * @return
	 */
	@Query("select t from ProDetail t where t.crawlDate = :crawlDate and t.projectId = :projectId and t.competitorId = :projectId and t.keywordId = :keywordId and t.engineType = :engineType and t.delFlag = '0' ")
	ProDetail getDashKeywordInfo(@Param("crawlDate")Date crawlDate, @Param("projectId")Integer projectId, @Param("keywordId")Integer keywordId, @Param("engineType")Integer engineType) throws Exception;
	/**
	 * 获取项目下的某些关键词在某些渠道的某些日期的排名信息
	 * @param crawlDate
	 * @param projectId
	 * @param keywordId
	 * @param engineType
	 * @return
	 */
	@Query("select t from ProDetail t where t.crawlDate in :crawlDates and t.projectId = :projectId and t.competitorId = :projectId and t.keywordId in :keywordIds and t.engineType in :engineTypes and t.delFlag = '0' ")
	List<ProDetail> getProDetailInfos(@Param("crawlDates")List<Date> crawlDates, @Param("projectId")Integer projectId, @Param("keywordIds")List<Integer> keywordIds, @Param("engineTypes")List<Integer> engineTypes) throws Exception;
	/**
	 * 查询项目和竞品维度某些的关键词在某一渠道的搜索可见性
	 * @param crawlDates
	 * @param projectId
	 * @param competitorIds
	 * @param keywordIds
	 * @param engineTypes
	 * @return
	 * @throws Exception
	 */
	@Query("SELECT new com.ido85.seo.keywordrank.vo.RankSearchVisibilityVo(t.crawlDate, t.competitorId, SUM(t.visibility) / :keywordNum, t.engineType) \n" +
			"FROM\n" +
			"	ProDetail t\n" +
			"WHERE\n" +
			"	t.crawlDate IN :crawlDates\n" +
			"AND t.projectId = :projectId\n" +
			"AND t.competitorId in :competitorIds\n" +
			"AND t.keywordId IN :keywordIds\n" +
			"AND t.engineType in :engineTypes\n" +
			"AND t.delFlag = '0'\n" +
			"GROUP BY\n" +
			"	t.crawlDate,\n" +
			"	t.competitorId,\n" +
			"	t.engineType")
	List<RankSearchVisibilityVo> getSearchVisibility(@Param("crawlDates")List<Date> crawlDates, @Param("projectId")Integer projectId, @Param("competitorIds")List<Integer> competitorIds, @Param("keywordIds")List<Integer> keywordIds, @Param("engineTypes")List<Integer> engineTypes, @Param("keywordNum")Double keywordNum) throws Exception;
	/**
	 * 查询项目某些的关键词在某些渠道的排名
	 * @param crawlDates
	 * @param projectId
	 * @param competitorIds
	 * @param keywordIds
	 * @param engineTypes
	 * @return
	 * @throws Exception
	 */
	@Query("SELECT\n" +
			"	new com.ido85.seo.keywordrank.vo.ProRankVo (" +
			"		t.competitorId," +
			"		t.crawlDate," +
			"		t.rank," +
			"		t.keywordId," +
			"		t.engineType" +
			"	)\n" +
			"FROM\n" +
			"	ProDetail t\n" +
			"WHERE\n" +
			"	t.crawlDate IN :crawlDates\n" +
			"AND t.projectId = :projectId\n" +
			"AND t.competitorId IN :competitorIds\n" +
			"AND t.keywordId IN :keywordIds\n" +
			"AND t.engineType in :engineTypes\n" +
			"AND t.delFlag = '0'" +
			"GROUP BY\n" +
			"	t.crawlDate,\n" +
			"	t.competitorId,\n" +
			"	t.engineType,\n" +
			"	t.keywordId")
	List<ProRankVo> getProRanks(@Param("crawlDates")List<Date> crawlDates, @Param("projectId")Integer projectId, @Param("competitorIds")List<Integer> competitorIds, @Param("keywordIds")List<Integer> keywordIds, @Param("engineTypes")List<Integer> engineTypes) throws Exception;
	/**
	 * 查询项目某些的关键词在某一渠道的排名详细信息
	 * @param crawlDate
	 * @param projectId
	 * @param keywordIds
	 * @param engineType
	 * @return
	 * @throws Exception
	 */
	@Query("SELECT new com.ido85.seo.keywordrank.vo.KeywordRankCompareVo(sd.url,pd.rank,pd.keywordId,pd.crawlDate,sd.linkUrl)\n" +
			"FROM\n" +
			"	ProDetail pd  LEFT JOIN SerpDetail sd ON pd.detailId = sd.detailId AND sd.delFlag = '0' \n" +
			"AND sd.crawlDate in :crawlDates\n" +
			"AND sd.keywordId IN :keywordIds\n" +
			"WHERE\n" +
			"	pd.crawlDate in :crawlDates\n" +
			"AND pd.projectId = :projectId\n" +
			"AND pd.competitorId = :projectId\n" +
			"AND pd.keywordId IN :keywordIds\n" +
			"AND pd.engineType = :engineType\n" +
			"AND pd.delFlag = '0'")
	List<KeywordRankCompareVo> getAllKeywordsRanks(@Param("crawlDates")List<Date> crawlDates, @Param("projectId")Integer projectId, @Param("keywordIds")List<Integer> keywordIds, @Param("engineType")Integer engineType) throws Exception;
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
	@Query("SELECT\n" +
			"	new com.ido85.seo.keywordrank.vo.ProRankVo (\n" +
			"		t.competitorId,\n" +
			"		t.crawlDate,\n" +
			"		COUNT(t.rank)\n" +
			"	)\n" +
			"FROM\n" +
			"	ProDetail t\n" +
			"WHERE\n" +
			"	t.crawlDate IN :crawlDates\n" +
			"AND t.projectId = :projectId\n" +
			"AND t.competitorId IN :competitorIds\n" +
			"AND t.keywordId IN :keywordIds\n" +
			"AND t.engineType = :engineType\n" +
			"AND t.rank < 31\n" +
			"AND t.delFlag = '0'" + 
			"GROUP BY\n" +
			"	t.crawlDate,\n" +
			"	t.competitorId\n")
	List<ProRankVo> getNumKeywordTopThirty(@Param("crawlDates")List<Date> crawlDates, @Param("projectId")Integer projectId, @Param("competitorIds")List<Integer> competitorIds, @Param("keywordIds")List<Integer> keywordIds, @Param("engineType")Integer engineType) throws Exception;
}
