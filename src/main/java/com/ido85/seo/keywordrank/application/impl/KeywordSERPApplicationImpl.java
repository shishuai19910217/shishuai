package com.ido85.seo.keywordrank.application.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.ido85.frame.common.utils.CompareUtil;
import com.ido85.frame.common.utils.DateUtils;
import com.ido85.frame.common.utils.ListUntils;
import com.ido85.master.project.dto.ProjectDto;
import com.ido85.seo.keywordrank.application.KeywordSERPApplication;
import com.ido85.seo.keywordrank.domain.SerpDetail;
import com.ido85.seo.keywordrank.resources.KeywordSERPResources;

@Named
public class KeywordSERPApplicationImpl implements KeywordSERPApplication {
	@Inject
	private KeywordSERPResources serpRes;

	@Override
	public List<SerpDetail> getSerpDetailInfos(List<Date> crawlDates, Integer engineType, Integer keywordId) throws Exception {
		return serpRes.getSerpDetailInfos(crawlDates, engineType, keywordId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SerpDetail> getSerpRankTrend(List<Date> crawlDates, Integer engineType, Integer keywordId, ProjectDto projectDto) throws Exception {
		Collections.sort(crawlDates);
		List<SerpDetail> res = new ArrayList<SerpDetail>();
		//取最近的一次抓取时间
		List<SerpDetail> newestSerp = this.getSerpDetailInfos(crawlDates.subList(crawlDates.size() - 1, crawlDates.size()), engineType, keywordId);
		List<SerpDetail> serps = new ArrayList<SerpDetail>();
		if(!ListUntils.isNull(newestSerp)){
			//判断前三名，后查询8周的数据
			Collections.sort(newestSerp, CompareUtil.createComparator(1, "rank"));
			List<String> urls = new ArrayList<String>();
			SerpDetail serpDetail = null;
			projectDto.setProjectUrl(projectDto.getProjectUrl().replaceFirst("http://", "").replaceFirst("https://", ""));
			for (int i = 0; i < newestSerp.size(); i++) {
				serpDetail = newestSerp.get(i);
				String url = serpDetail.getUrl();
				if(null == url || "".equals(url))
					continue;
				// 排除子域名
				if("0".equals(projectDto.getIsSubdomain())){//值为0时,不排除子域名
					url = url.replaceFirst("http://", "");
					url = url.replaceFirst("https://", "");
					String subdomain = url.split("\\.")[0];
					if(url.startsWith(subdomain + "." + projectDto.getProjectUrl().replaceFirst("www", ""))){//子域名情况
						if(serps.size() <= 0){
							serps.add(serpDetail);
							urls.add(serpDetail.getUrl());
						}else if(serps.size() >= 3)
							break;
						else if(!urls.contains(serpDetail.getUrl())){
							urls.add(serpDetail.getUrl());
							serps.add(serpDetail);
						}
					}else if(url.startsWith(projectDto.getProjectUrl()) || url.startsWith(projectDto.getProjectUrl().replaceFirst("www", ""))){//与项目域名情况
						if(serps.size() <= 0){
							serps.add(serpDetail);
							urls.add(serpDetail.getUrl());
						}else if(serps.size() >= 3)
							break;
						else if(!urls.contains(serpDetail.getUrl())){
							urls.add(serpDetail.getUrl());
							serps.add(serpDetail);
						}
					}
				}else if(url.startsWith(projectDto.getProjectUrl()) || url.startsWith(projectDto.getProjectUrl().replaceFirst("www", ""))){//排除子域名
					if(serps.size() <= 0){
						serps.add(serpDetail);
						urls.add(serpDetail.getUrl());
					}else if(serps.size() >= 3)
						break;
					else if(!urls.contains(serpDetail.getUrl())){
						urls.add(serpDetail.getUrl());
						serps.add(serpDetail);
					}
				}
				if(serps.size() >= 3)
					break;
			}
		}
		res.addAll(serps);
		if(ListUntils.isNull(serps)){
			return res;
		}
		newestSerp = null;
		
		//取剩下的所有的关键词serp信息
		if(crawlDates.size() > 1){
			List<SerpDetail> allSerps = this.getSerpDetailInfos(crawlDates.subList(0, crawlDates.size() - 1), engineType, keywordId);
			if(!ListUntils.isNull(allSerps)){
				List<SerpDetail> timeSerps = null;
				for (int i = 0; i < serps.size(); i++) {
					timeSerps = new ArrayList<SerpDetail>();
					SerpDetail temp = null;
					if(null != allSerps && allSerps.size() > 0){//获取到的数据有重复
						SerpDetail tem = null;
						for (int k = 0; k < crawlDates.size(); k++) {
							for (int j = 0; j < allSerps.size(); j++) {
								tem = allSerps.get(j);
								if(!serps.get(i).getUrl().equals(tem.getUrl())){
									allSerps.remove(j);
									j--;
									continue;
								}
								//根据排名先后来判断去重
								if(null != tem && DateUtils.isSameDate(tem.getCrawlDate(), crawlDates.get(k)) && null == temp){
									temp = tem;
								}else if(null != tem && DateUtils.isSameDate(tem.getCrawlDate(), crawlDates.get(k)) && null != temp){
									if(tem.getRank() <= temp.getRank()){
										temp = tem;
									}
								}
							}
							if(null != temp)
								timeSerps.add(temp);
							temp = null;
						}
					}
					
					res.addAll(timeSerps);
				}
			}
		}
		
		return res;
	}
	
	
}
