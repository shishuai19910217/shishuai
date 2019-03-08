package com.ido85.seo.keywordrank.application.impl;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import com.ido85.seo.keywordrank.application.DashboardKeywordApplication;
import com.ido85.seo.keywordrank.domain.DashboardKeyword;
import com.ido85.seo.keywordrank.resources.DashboardKeywordResources;

@Named
public class DashboardKeywordApplicationImpl implements DashboardKeywordApplication{
	@Inject
	private DashboardKeywordResources dashKeywordRes;
	
	@Override
	public DashboardKeyword getDashKeywordInfo(Date crawlDate, Integer projectId, Integer keywordId, Integer engineType) {
		return dashKeywordRes.getDashKeywordInfo(crawlDate, projectId, keywordId, engineType);
	}
	
}