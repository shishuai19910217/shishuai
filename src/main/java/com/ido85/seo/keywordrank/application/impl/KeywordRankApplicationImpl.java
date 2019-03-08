package com.ido85.seo.keywordrank.application.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import com.ido85.frame.common.utils.CompareUtil;
import com.ido85.frame.common.utils.DateUtils;
import com.ido85.frame.common.utils.ListUntils;
import com.ido85.frame.common.utils.MathUtil;
import com.ido85.frame.common.utils.StringUtils;
import com.ido85.seo.keywordrank.application.KeywordRankApplication;
import com.ido85.seo.keywordrank.domain.ProDetail;
import com.ido85.seo.keywordrank.dto.OutDiffEngineRankDto;
import com.ido85.seo.keywordrank.dto.OutKeyRankDto;
import com.ido85.seo.keywordrank.dto.OutRankDto;
import com.ido85.seo.keywordrank.resources.KeywordRankResources;
import com.ido85.seo.keywordrank.vo.KeywordRankCompareVo;
import com.ido85.seo.keywordrank.vo.ProRankVo;
import com.ido85.seo.keywordrank.vo.RankSearchVisibilityVo;

@Named
public class KeywordRankApplicationImpl implements KeywordRankApplication {
	@Inject
	private KeywordRankResources rankRes;

	@Override
	public ProDetail getKeywordRankInfo(Date crawlDate, Integer projectId, Integer keywordId, Integer engineType) throws Exception {
		return rankRes.getDashKeywordInfo(crawlDate, projectId, keywordId, engineType);
	}

	@Override
	public List<ProDetail> getProDetailInfos(List<Date> crawlDates, Integer projectId, List<Integer> keywordIds,
			List<Integer> engineTypes) throws Exception {
		return rankRes.getProDetailInfos(crawlDates, projectId, keywordIds, engineTypes);
	}

	@Override
	public OutKeyRankDto getKeywordRankBaseInfo(List<Date> crawlDates, Integer projectId, List<Integer> keywordIds,
			Integer engineType) throws Exception {
		DateUtils.getFirstAndLastDate(crawlDates);
		List<Integer> engineTypes = new ArrayList<Integer>();
		engineTypes.add(engineType);
		List<ProDetail> proDetails = this.getProDetailInfos(crawlDates, projectId, keywordIds, engineTypes);
		
		OutKeyRankDto out = null;
		if(!ListUntils.isNull(proDetails)){
			out = new OutKeyRankDto();
			//搜索引擎前30位排名的关键词数量 
			int[] lastStage = new int[]{0,0,0,0};
			int[] firstStage = new int[]{0,0,0,0};
			int outRange = 0;
			double visibility = 0d;
			int downNum = 0, upNum = 0;
//			List<ProDetail> firstList = splitListByDate(proDetails, crawlDates).get(crawlDates.get(0));
			for (ProDetail proDetail : proDetails) {
				//计算所有的关键词的平均搜索可见性
				if(DateUtils.isSameDate(proDetail.getCrawlDate(), crawlDates.get(crawlDates.size() - 1))){
					visibility = visibility + proDetail.getVisibility();
					
					if(crawlDates.size() >= 2){//前一次抓取的数据
						ProDetail first = null;
						//计算关键词排名升迁变化
						for (int i = 0,len = proDetails.size(); i < len; i++) {
							first = proDetails.get(i);
							//计算百度自然搜索排名上升下降个数
							if(proDetail.getKeywordId().equals(first.getKeywordId()) && DateUtils.isSameDate(crawlDates.get(0), first.getCrawlDate())){
								//上一次的搜索引擎前30位排名的关键词数量详细排名个数
								if (first.getRank() > 0 && first.getRank() <= 3) {
									firstStage[0]++;
								}else if (first.getRank() > 3 && first.getRank() <= 10) {
									firstStage[1]++;
								}else if (first.getRank() > 10 && first.getRank() <= 20) {
									firstStage[2]++;
								}else if (first.getRank() > 20 && first.getRank() <= 30) {
									firstStage[3]++;
								}
								
								if(proDetail.getRank().equals(first.getRank())){
									break;
								}else if(proDetail.getRank() > first.getRank()){
									downNum++;
									break;
								}else {
									upNum++;
									break;
								}
							}
						}
					}
					
					//最近一次的搜索引擎前30位排名的关键词数量详细排名个数
					if (proDetail.getRank() > 0 && proDetail.getRank() <= 3) {
						lastStage[0]++;
					}else if (proDetail.getRank() > 3 && proDetail.getRank() <= 10) {
						lastStage[1]++;
					}else if (proDetail.getRank() > 10 && proDetail.getRank() <= 20) {
						lastStage[2]++;
					}else if (proDetail.getRank() > 20 && proDetail.getRank() <= 30) {
						lastStage[3]++;
					}else if (proDetail.getRank() > 30) {
						outRange++;
					}
				}
			}
			
			out.setVisibility(MathUtil.div(visibility, StringUtils.toDouble(keywordIds.size()), 2) + "");
			out.setDownNum(downNum + "");
			out.setUpNum(upNum + "");
			out.setOutRange(outRange + "");
			
			//位于搜索引擎前30位排名的关键词数量 最终计算返回
			Map<String, String> rankInfos = null;
			List<Map<String, String>> stage = new ArrayList<Map<String,String>>();
			for (int i = 0; i < lastStage.length; i++) {
				rankInfos = new HashMap<String, String>();
				rankInfos.put("num", lastStage[i] + "");
				if(crawlDates.size() >= 2){
					rankInfos.put("upOrDown", (lastStage[i] - firstStage[i]) + "");
				}else
					rankInfos.put("upOrDown", "0");
				
				if(i == 0){
					rankInfos.put("stageBegin", "1");
					rankInfos.put("stageEnd", "3");
				}else if(i == 1){
					rankInfos.put("stageBegin", "4");
					rankInfos.put("stageEnd", "10");
				}else if(i == 2){
					rankInfos.put("stageBegin", "11");
					rankInfos.put("stageEnd", "20");
				}else if(i == 3){
					rankInfos.put("stageBegin", "21");
					rankInfos.put("stageEnd", "30");
				}
				stage.add(rankInfos);
			}
			out.setStage(stage);
		}
		return out;
	}

	@Override
	public List<RankSearchVisibilityVo> getRankSearchVisibility(List<Date> crawlDates, Integer projectId, List<Integer> competitorIds,
			List<Integer> keywordIds, Integer engineType) throws Exception {
		List<Integer> engineTypes = new ArrayList<Integer>();
		engineTypes.add(engineType);
		return rankRes.getSearchVisibility(crawlDates, projectId, competitorIds, keywordIds, engineTypes, StringUtils.toDouble(keywordIds.size()));
	}

	@Override
	public List<OutRankDto> getProRanks(List<Date> crawlDates, Integer projectId, List<Integer> keywordIds,
			Integer engineType) throws Exception {
		List<Integer> competitorIds = new ArrayList<Integer>();
		competitorIds.add(projectId);
		List<Integer> engineTypes = new ArrayList<Integer>();
		engineTypes.add(engineType);
		List<ProRankVo> proRankVos = rankRes.getProRanks(crawlDates, projectId, competitorIds, keywordIds, engineTypes);
		
		List<OutRankDto> res = new ArrayList<OutRankDto>();
		OutRankDto outRankDto = null;
		if(!ListUntils.isNull(proRankVos)){
			int[] stage = null;
			for (Date date : crawlDates) {
				stage = new int[]{0,0,0,0};
				outRankDto = new OutRankDto();
				for (ProRankVo proRankVo : proRankVos) {
					if(DateUtils.isSameDate(date, proRankVo.getCrawlDate())){
						if (proRankVo.getRank() > 0 && proRankVo.getRank() <= 3) {
							stage[0]++;
						}else if (proRankVo.getRank() > 3 && proRankVo.getRank() <= 10) {
							stage[1]++;
						}else if (proRankVo.getRank() > 10 && proRankVo.getRank() <= 20) {
							stage[2]++;
						}else if (proRankVo.getRank() > 20 && proRankVo.getRank() <= 30) {
							stage[3]++;
						}
					}			
				}
				outRankDto.setCrawlDate(DateUtils.formatDate(date, "yyyy-MM-dd"));
				outRankDto.setTopThreeNum(stage[0] + "");
				outRankDto.setFourToTenNum(stage[1] + "");
				outRankDto.setElevenToTwenty(stage[2] + "");
				outRankDto.setTwentyOneTothirty(stage[3] + "");
				res.add(outRankDto);
			}
		}
		return res;
	}

	@Override
	public List<KeywordRankCompareVo> getAllKeywordsRanks(List<Date> crawlDates, Integer projectId,
			List<Integer> keywordIds, Integer engineType) throws Exception {
		DateUtils.getFirstAndLastDate(crawlDates);
		
		List<KeywordRankCompareVo> temp = rankRes.getAllKeywordsRanks(crawlDates, projectId, keywordIds, engineType);
		List<KeywordRankCompareVo> res = null;
		if(!ListUntils.isNull(temp)){
			res = new ArrayList<KeywordRankCompareVo>();
			for (KeywordRankCompareVo keywordRankCompareVo : temp) {
				if(DateUtils.isSameDate(keywordRankCompareVo.getCrawlDate(), crawlDates.get(crawlDates.size() - 1))){//后一次抓取
					if(crawlDates.size() >= 2){
						for (KeywordRankCompareVo first : temp) {
							if(keywordRankCompareVo.getKeywordId().equals(first.getKeywordId()) && DateUtils.isSameDate(first.getCrawlDate(), crawlDates.get(0))){//前一次抓取
								keywordRankCompareVo.setUpOrDown((first.getRank() - keywordRankCompareVo.getRank()) + "");
								break;
							}
						}
					}
					res.add(keywordRankCompareVo);
				}
			}
		}
		return res;
	}

	@Override
	public List<RankSearchVisibilityVo> getAllEngineVisibility(List<Date> crawlDates, Integer projectId,
			List<Integer> keywordIds, List<Integer> engineTypes) throws Exception {
		List<Integer> competitorIds = new ArrayList<Integer>();
		competitorIds.add(projectId);
		List<RankSearchVisibilityVo> res = rankRes.getSearchVisibility(crawlDates, projectId, competitorIds, keywordIds, engineTypes, StringUtils.toDouble(keywordIds.size()));
		if(!ListUntils.isNull(res)){
			for (RankSearchVisibilityVo rankSearchVisibilityVo : res) {
				rankSearchVisibilityVo.setVisibility(MathUtil.rounding(rankSearchVisibilityVo.getVisibility(), 2));
			}
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OutDiffEngineRankDto> getDiffEngineRanks(List<Date> crawlDates, Integer projectId,
			List<Integer> keywordIds, List<Integer> engineTypes) throws Exception {
		DateUtils.getFirstAndLastDate(crawlDates);
		List<Integer> competitorIds = new ArrayList<Integer>();
		competitorIds.add(projectId);
		List<ProRankVo> proRankVos = rankRes.getProRanks(crawlDates, projectId, competitorIds, keywordIds, engineTypes);
		List<OutDiffEngineRankDto> res = new ArrayList<OutDiffEngineRankDto>();
		if(!ListUntils.isNull(proRankVos)){
			OutDiffEngineRankDto item = null;
			//获取第一个搜索引擎的排名和升迁情况
			for (ProRankVo proRankVo : proRankVos) {
				if(DateUtils.isSameDate(proRankVo.getCrawlDate(), crawlDates.get(crawlDates.size() - 1)) && proRankVo.getEngineType().equals(engineTypes.get(0))){
					item = new OutDiffEngineRankDto();
					item.setKeywordId(proRankVo.getKeywordId() + "");
					item.setRanking1(proRankVo.getRank() + "");
					if(crawlDates.size() >= 2){
						for (ProRankVo first : proRankVos) {
							if(first.getKeywordId().equals(proRankVo.getKeywordId()) && DateUtils.isSameDate(proRankVo.getCrawlDate(), crawlDates.get(0)) 
									&& proRankVo.getEngineType().equals(engineTypes.get(0))){
								item.setRankingpromotion1((first.getRank() - proRankVo.getRank()) + "");
								break;
							}
						}
					}
					res.add(item);
				}
			}
			//第二个搜索引擎的排名和升迁情况
			if(!ListUntils.isNull(res)){
				for (OutDiffEngineRankDto outDiffEngineRankDto : res) {
					Collections.sort(proRankVos, CompareUtil.createComparator(-1, "crawlDate"));
					for (ProRankVo second : proRankVos) {
						if(outDiffEngineRankDto.getKeywordId().equals(second.getKeywordId() + "") && DateUtils.isSameDate(second.getCrawlDate(), crawlDates.get(crawlDates.size() - 1))
								&& second.getEngineType().equals(engineTypes.get(engineTypes.size() - 1))){
							outDiffEngineRankDto.setRanking2(second.getRank() + "");
						}
						if(crawlDates.size() >= 2){
							if(outDiffEngineRankDto.getKeywordId().equals(second.getKeywordId() + "") && DateUtils.isSameDate(second.getCrawlDate(), crawlDates.get(crawlDates.size() - 1)) 
									&& second.getEngineType().equals(engineTypes.get(1))){
								item.setRankingpromotion2((second.getRank() - StringUtils.toInteger(outDiffEngineRankDto.getRanking2())) + "");
								break;
							}
						}
					}
				}
			}
		}
		return res;
	}

	@Override
	public List<ProRankVo> getAllSearchResult(List<Date> crawlDates, Integer projectId, List<Integer> competitorIds,
			List<Integer> keywordIds, Integer engineType) throws Exception {
		return rankRes.getNumKeywordTopThirty(crawlDates, projectId, competitorIds, keywordIds, engineType);
	}

	@Override
	public List<ProRankVo> getRankingAnalysis(List<Date> crawlDates, Integer projectId, List<Integer> competitorIds,
			List<Integer> keywordIds, Integer engineType) throws Exception {
		DateUtils.getFirstAndLastDate(crawlDates);
		List<Integer> engineTypes = new ArrayList<Integer>();
		engineTypes.add(engineType);
		List<ProRankVo> proRankVos = rankRes.getProRanks(crawlDates, projectId, competitorIds, keywordIds, engineTypes);
		
		List<ProRankVo> res = new ArrayList<ProRankVo>();
		if(!ListUntils.isNull(proRankVos)){
			for (ProRankVo proRankVo : proRankVos) {//最新一次抓取数据
				if(DateUtils.isSameDate(proRankVo.getCrawlDate(), crawlDates.get(crawlDates.size() - 1))){
					if(crawlDates.size() >= 2){
						//前一次抓取数据
						for (ProRankVo first : proRankVos) {
							if(first.getKeywordId().equals(proRankVo.getKeywordId()) && DateUtils.isSameDate(first.getCrawlDate(), crawlDates.get(0))
									&& first.getCompetitorId().equals(proRankVo.getCompetitorId())){
								proRankVo.setRankNum(StringUtils.toLong(first.getRank() - proRankVo.getRank()));
							}
						}
					}else {
						proRankVo.setRankNum(0l);
					}
					res.add(proRankVo);
				}
			}
		}
		return res;
	}

//	private Map<Date, List<ProDetail>> splitListByDate(List<ProDetail> proDetails, List<Date> crawlDates){
//		if(ListUntils.isNull(proDetails) || ListUntils.isNull(crawlDates)){
//			return null;
//		}
//		Map<Date, List<ProDetail>> res = new HashMap<Date, List<ProDetail>>();
//		List<ProDetail> item = null;
//		for (int i = 0; i < crawlDates.size(); i++) {
//			item = new ArrayList<ProDetail>();
//			for (int j = 0, len = proDetails.size(); j < len; j++) {
//				if(DateUtils.isSameDate(crawlDates.get(i), proDetails.get(j).getCrawlDate())){
//					item.add(proDetails.get(j));
//				}
//			}
//			res.put(crawlDates.get(i), item);
//		}
//		return res;
//	}
}
