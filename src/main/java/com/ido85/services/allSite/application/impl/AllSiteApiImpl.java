package com.ido85.services.allSite.application.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.ido85.frame.common.utils.ComparatorDate;
import com.ido85.frame.web.rest.security.Constants;
import com.ido85.seo.allSite.application.AllSiteApplication;
import com.ido85.seo.allSite.dto.OutIssuesItemDto;
import com.ido85.seo.allSite.resources.AllSiteResources;
import com.ido85.services.allSite.application.AllSiteApi;
import com.ido85.services.time.TimeInsideApi;

@Named
public class AllSiteApiImpl implements AllSiteApi {
	@Inject
	private AllSiteApplication allSiteApplication;
	@Inject
	private AllSiteResources allSiteResources;
	@Inject
	private TimeInsideApi timeApi;
	@Override
	public Map getCrawInfo(int projectId) throws Exception {
		Map map = new HashMap();
//		Pageable pageable = new PageRequest(0, 2, Direction.DESC, "createDate");
//		List<Date> dList = allSiteResources.getLastDate(pageable);
		List<Date> dList = timeApi.getTimesByIsWeek(projectId, "0", Constants.SHOW_NUM);
		ComparatorDate c = new ComparatorDate();
        Collections.sort(dList, c);
		int size = dList.size();
		if( size == 0){
			return map;
		}
		if(size == 1){
			OutIssuesItemDto dtoLast = allSiteResources.getHistoryIssuesInfo(projectId,dList.get(0)).get(0);
			map.put("crawlIssues", dtoLast.getIssuesNum() == null ? 0 : dtoLast.getIssuesNum());
			map.put("issuesChange", 0);
		}
		
		if(size == 2){
			OutIssuesItemDto dtoLast = allSiteResources.getHistoryIssuesInfo(projectId,dList.get(0)).get(0);
			OutIssuesItemDto dtoBefore = allSiteResources.getHistoryIssuesInfo(projectId,dList.get(1)).get(0);
			if(dtoLast != null && dtoBefore!= null && dtoLast.getIssuesNum() != null && dtoBefore.getIssuesNum()!= null) {
				Long change = dtoLast.getIssuesNum() - dtoBefore.getIssuesNum();
				map.put("crawlIssues", dtoLast.getIssuesNum());
				map.put("issuesChange", change);
			}else if(dtoLast != null){
				Long change = dtoLast.getIssuesNum();
				map.put("crawlIssues", dtoLast.getIssuesNum());
				map.put("issuesChange", 0);
			}
					
			
		}
		
		return map;
	}

	
}
