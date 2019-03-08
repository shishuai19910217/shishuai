package com.ido85.services.dashboard;

import java.util.Date;
import java.util.List;

import com.ido85.seo.dashboard.dto.OutKeywordRankDto;

public interface DashboardApi {
	/***
	 * 项目管理   关键词变化
	 * @param projectId
	 * @param i
	 * @param crawlTimes
	 * @return
	 * @throws Exception
	 */
	public OutKeywordRankDto getProManagerKeywordRankNum(Integer projectId
			) throws Exception;
}
