package com.ido85.services.dashboard.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import com.ido85.seo.dashboard.application.DashboardVisibilityApplication;
import com.ido85.seo.dashboard.dto.InKeywordRankDto;
import com.ido85.seo.dashboard.dto.OutKeywordRankDto;
import com.ido85.services.dashboard.DashboardApi;
@Named
public class DashboardApiImpl implements DashboardApi{
	@Inject
	private DashboardVisibilityApplication dashboardApp;
	@Override
	public OutKeywordRankDto getProManagerKeywordRankNum( Integer projectId) throws Exception{
		InKeywordRankDto in = new InKeywordRankDto();
		in.setEngineType("0");
		in.setIsHave("0");
		in.setIsWeek("0");
		in.setProjectId(projectId.toString());
		OutKeywordRankDto  outDto = dashboardApp.getkeywordRankNum(in);
		 
		 return outDto;
	}

}
