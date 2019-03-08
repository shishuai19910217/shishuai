package com.ido85.seo.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ido85.config.properties.BussinessMsgCodeProperties;
import com.ido85.frame.common.restful.Resource;
import com.ido85.frame.common.utils.CompareUtil;
import com.ido85.frame.common.utils.MathUtil;
import com.ido85.frame.common.utils.StringUtils;
import com.ido85.seo.dashboard.application.DashboardVisibilityApplication;
import com.ido85.seo.dashboard.dto.InExtLinkDto;
import com.ido85.seo.dashboard.dto.InKeywordRankDto;
import com.ido85.seo.dashboard.dto.InKeywordTopDto;
import com.ido85.seo.dashboard.dto.InSearchRankDto;
import com.ido85.seo.dashboard.dto.InVisibilityDto;
import com.ido85.seo.dashboard.dto.OutExtLinkDomainsDto;
import com.ido85.seo.dashboard.dto.OutExtLinkDto;
import com.ido85.seo.dashboard.dto.OutKeywordRankDto;
import com.ido85.seo.dashboard.dto.OutKeywordTopDto;
import com.ido85.seo.dashboard.dto.OutSearchRankDto;
import com.ido85.seo.dashboard.dto.OutVisibilityDto;

@RestController
public class DashboardController{
	@Inject
	private BussinessMsgCodeProperties msg;
	@Inject
	private DashboardVisibilityApplication dashboardApp;

	/**
	 * projectId 项目id engineType 搜索引擎 搜索引擎默认是百度PC ：0 百度 PC 1百度 移动 2搜狗 PC 3搜狗 移动
	 * 4 好搜 PC 5好搜 移动 6 神马 移动 isHave 是否竞品 0 不包含 竞品 1包含竞品 默认0 isWeek 按周还是按月 0：按周
	 * 1 按月 按周还是按月 0：按周 1 按月 默认0
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping("/seo/getVisibilityList")
	public Resource<OutVisibilityDto> getVisibilityList(@Valid @RequestBody InVisibilityDto in) throws Exception {
		OutVisibilityDto dto = new OutVisibilityDto();
		if (StringUtils.isNull(in.getEngineType())) {
			in.setEngineType("0");
		}
		if (StringUtils.isNull(in.getIsHave())) {
			in.setIsHave("0");
		}
		if (StringUtils.isNull(in.getIsWeek())) {
			in.setIsWeek("0");
		}
		dto = dashboardApp.getVisibilityListByEngineType(in);
		
		if (null == dto || dto.getVisibilityList().size() <= 0) {// 项目是空的
			return new Resource<OutVisibilityDto>(msg.getProcessStatus("COMMON_SUCCESS"));
		}
		Collections.sort(dto.getVisibilityList(), CompareUtil.createComparator(1, "crawlDate"));
		for(Map<String, Object> map : dto.getVisibilityList()){
			map.put("visibility", MathUtil.rounding(StringUtils.toDouble(map.get("visibility")), 2));
			map.put("upOrDown", MathUtil.rounding(StringUtils.toDouble(map.get("upOrDown")), 2));
		}

				return new Resource<OutVisibilityDto>(dto, msg.getProcessStatus("COMMON_SUCCESS"));

	}

	/***
	 * 仅项目  搜索可见性 多个搜索引擎
	 * 
	 * @param param
	 *            ProjectId 项目id string engineType 搜索引擎 string 搜索引擎：多选用，分割 ：0 百度
	 *            PC 1百度 移动 2搜狗 PC 3搜狗 移动 4 好搜 PC 5好搜 移动 6 神马 移动 isHave 是否竞品
	 *            string 0 不包含 竞品 1包含竞品 默认0 isWeek
	 * @return
	 */

	@RequestMapping("/seo/getVisibilityListByMoreEngine")
	public Resource<OutVisibilityDto> getVisibilityListByMoreEngine(@Valid @RequestBody InVisibilityDto in)
			throws Exception {
		OutVisibilityDto dto = new OutVisibilityDto();
		if (StringUtils.isNull(in.getEngineType())) {
			in.setEngineType("0");
		}
		if (StringUtils.isNull(in.getIsHave())) {
			in.setIsHave("0");
		}
		if (StringUtils.isNull(in.getIsWeek())) {
			in.setIsWeek("0");
		}
		dto = dashboardApp.getVisibilityListByMoreEngine(in);
		if(null==dto||dto.getVisibilityList().size()<=0){//项目是空的
			 return new Resource<OutVisibilityDto>(msg.getProcessStatus("COMMON_SUCCESS"));
		}
		//按时间排序
		 Collections.sort(dto.getVisibilityList(),
					CompareUtil.createComparator(1, "crawlDate")); 
		 for(Map<String, Object> map : dto.getVisibilityList()){
				map.put("visibility", MathUtil.rounding(StringUtils.toDouble(map.get("visibility")), 2));
				map.put("upOrDown", MathUtil.rounding(StringUtils.toDouble(map.get("upOrDown")), 2));
			}
		 
		return new Resource<OutVisibilityDto>(dto,msg.getProcessStatus("COMMON_SUCCESS"));
	}
	/***
	 * 排名最高的前5个关键词
	 * @param param
	 * @return
	 */
	@RequestMapping("/seo/getKeywordTop")
	public Resource<OutKeywordTopDto> getKeywordTop(@Valid @RequestBody InKeywordTopDto in) throws Exception{
		OutKeywordTopDto dto = new OutKeywordTopDto();
		if (StringUtils.isNull(in.getEngineType())) {
			in.setEngineType("0");
		}
		if (StringUtils.isNull(in.getIsHave())) {
			in.setIsHave("0");
		}
		if (StringUtils.isNull(in.getIsWeek())) {
			in.setIsWeek("0");
		}
		dto = dashboardApp.getKeywordTop(in);
		if(null==dto||dto.getKeywordList().size()<=0){
			return new Resource<OutKeywordTopDto>(msg.getProcessStatus("COMMON_SUCCESS"));
		}
		if(dto.getKeywordList().size()>0){
			Collections.sort(dto.getKeywordList(), CompareUtil.createComparator(1, "rank"));
			if(dto.getKeywordList().size()>in.getNum()){
				List<Map<String, Object>> list = new ArrayList<>();
				for(int i =0 ;i<in.getNum();i++ ){
					list.add(dto.getKeywordList().get(i));
				}
				dto.setKeywordList(list);
			}
		}
		return new Resource<OutKeywordTopDto>(dto,msg.getProcessStatus("COMMON_SUCCESS"));
		
	}
	/***
	 * 排名1-10位的关键词个数
	 * @param param
	 * @return
	 */
	@RequestMapping("/seo/getkeywordDistributeRank")
	public Resource<OutKeywordRankDto> getkeywordRankNum(@Valid @RequestBody InKeywordRankDto in) throws Exception{
		OutKeywordRankDto dto = new OutKeywordRankDto();
		if (StringUtils.isNull(in.getEngineType())) {
			in.setEngineType("0");
		}
		if (StringUtils.isNull(in.getIsHave())) {
			in.setIsHave("0");
		}
		if (StringUtils.isNull(in.getIsWeek())) {
			in.setIsWeek("0");
		}
		dto = dashboardApp.getkeywordRankNum(in);
		if(null==dto||null==dto.getProject()){
			return new Resource<OutKeywordRankDto>(msg.getProcessStatus("COMMON_SUCCESS"));
		}
		
		return new Resource<OutKeywordRankDto>(dto,msg.getProcessStatus("COMMON_SUCCESS"));
		
	}
	/***
	 * 排名情况
	 * @param param
	 * @return
	 */
	@RequestMapping("/seo/getSearchRank")
	public Resource<OutSearchRankDto> getSearchRank(@Valid @RequestBody InSearchRankDto in) throws Exception{
		OutSearchRankDto dto = new OutSearchRankDto();
		dto = dashboardApp.getSearchRank(in);
		if(null==dto||dto.getRangeList().size()<=0){
			return new Resource<OutSearchRankDto>(msg.getProcessStatus("COMMON_SUCCESS"));
		}
		return new Resource<OutSearchRankDto>(dto,msg.getProcessStatus("COMMON_SUCCESS"));
		
	}
	
	/***
	 * 
	 * 整站爬取
	 * @param param
	 * @return
	 */
//	@RequestMapping("/seo/getQuestionByWeekOrM")
//	public Resource<OutSearchQuestionDto> getQuestionByWeekOrM(@Valid @RequestBody InSearchQuestionDto in) throws Exception{
//		OutSearchQuestionDto dto = new OutSearchQuestionDto();
//		dto = dashboardApp.getQuestionByWeekOrM(in);
//		return new Resource<OutSearchQuestionDto>(dto,msg.getProcessStatus("COMMON_SUCCESS"));
//		
//	}
	
	/***
	 * 外部链接域名个数
	 * @param param
	 * @return
	 */
	@RequestMapping("/seo/getExtLinkDomains")
	public Resource<OutExtLinkDomainsDto> getExtLinkDomains(@Valid @RequestBody InExtLinkDto in )  throws Exception{
		OutExtLinkDomainsDto dto = new OutExtLinkDomainsDto();
		if(StringUtils.isBlank(in.getIsWeek())){
			in.setIsWeek("0");
		}
		dto = dashboardApp.getExtLinkDomains(in);
		return new Resource<OutExtLinkDomainsDto>(dto,msg.getProcessStatus("COMMON_SUCCESS"));
		
	}
	/***
	 * 外部链接个数
	 * @param param
	 * @return
	 */
	@RequestMapping("/seo/getExtLink")
	public Resource<OutExtLinkDto> getExtLink(@Valid @RequestBody InExtLinkDto in) throws Exception{
		OutExtLinkDto dto = new OutExtLinkDto();
		dto = dashboardApp.getExtLink(in);
		if(null==dto){
			return new Resource<OutExtLinkDto>(msg.getProcessStatus("COMMON_SUCCESS"));
		}
		return new Resource<OutExtLinkDto>(dto,msg.getProcessStatus("COMMON_SUCCESS"));
		
	}
}
