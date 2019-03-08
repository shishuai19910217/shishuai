package com.ido85.master.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ido85.config.properties.BussinessMsgCodeProperties;
import com.ido85.frame.common.restful.PagedResources;
import com.ido85.frame.common.restful.PagedResources.PageMetadata;
import com.ido85.frame.common.restful.Resource;
import com.ido85.frame.common.utils.StringUtils;
import com.ido85.master.keyword.dto.KeywordRankDto;
import com.ido85.master.spider.ArticlesOriginalCheck;
import com.ido85.master.spider.KeWordExtendThreadMain;
import com.ido85.master.spider.KeWordRanksThreadMain;
import com.ido85.master.spider.PageCheck;
import com.ido85.master.spider.vo.KeyWordScore;
import com.ido85.master.spider.vo.Page;

@RestController
public class ShortcutToolsController{
	@Inject
	private BussinessMsgCodeProperties msg;
	private static String splitString = "€_€";
	private static String[] issuesCode = {"10100","10106","10101","20102","30103","30104","30105","10104","10105","20104",};

	@SuppressWarnings("unchecked")
	@RequestMapping("/tools/keywordRank")
	public PagedResources<KeywordRankDto> keywordRank(@RequestBody Map<String, Object> param) {
		try {
			if (null == param || param.isEmpty()) {
				new Resource<Object>(msg.getProcessStatus("PARAM_IS_NULL"));
			}
			if (param.get("keywords") == null || "".equals(param.get("keywords"))) {
				new Resource<Object>(msg.getProcessStatus("PARAM_IS_NULL"));
			}
			if (param.get("url") == null || "".equals(param.get("url"))) {
				new Resource<Object>(msg.getProcessStatus("PARAM_IS_NULL"));
			}
			if (param.get("type") == null || "".equals(param.get("type"))) {
				new Resource<Object>(msg.getProcessStatus("PARAM_IS_NULL"));
			}
			List<String> keywords = (List<String>) param.get("keywords");

			String url = StringUtils.toString(param.get("url"));
			String type = StringUtils.toString(param.get("type"));

			Map<String, String> map = new HashMap<>();
			if ("0".equals(type)) {
				if (keywords.size() > 50 || keywords.size() <= 0) {
					new Resource<Object>(msg.getProcessStatus("KEYWORD_NUM_TOLONGERROR"));
				}
				map = KeWordRanksThreadMain.Threadmain(keywords, url, 0);
			} else if ("1".equals(type)) {
				if (keywords.size() > 3 || keywords.size() <= 0) {
					new Resource<Object>(msg.getProcessStatus("KEYWORD_NUM_TOLONGERROR"));
				}
				map = KeWordRanksThreadMain.Threadmain(keywords, url, 0);
			}

			List<KeywordRankDto> list = null;
			if (map.isEmpty()) {
				return new PagedResources<KeywordRankDto>(msg.getProcessStatus("RETURN_IS_NULL"));
			} else {
				list = new ArrayList<KeywordRankDto>();
				KeywordRankDto keywordRankDto = null;
				for (Map.Entry<String, String> entry : map.entrySet()) {
					keywordRankDto = new KeywordRankDto();
					keywordRankDto.setKeywordName(entry.getKey());
					if (null != entry.getValue()) {
						if ("".equals(entry.getValue())) {
							keywordRankDto.setRanking(-1);
							keywordRankDto.setTargetUrl("");
						} else {
							String[] sourceStrArray = entry.getValue().split(splitString);
							keywordRankDto.setRanking(Integer.parseInt(sourceStrArray[0]));
							keywordRankDto.setTargetUrl(sourceStrArray[1]);

						}
						list.add(keywordRankDto);
					}
				}
				PageMetadata pageMetadata = new PageMetadata();
				pageMetadata.setCount(list.size());
				return new PagedResources<KeywordRankDto>(list, pageMetadata);
			}

		} catch (InterruptedException e) {
			return new PagedResources<KeywordRankDto>(msg.getProcessStatus("SPIDER_ERROR"));
		}

	}

	/**
	 * 关键词扩展
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping("/tools/keywordExtend")
	public PagedResources<String> keywordExtend(@RequestBody Map<String, Object> param) {
		try {
			if (null == param || param.isEmpty()) {
				new Resource<String>(msg.getProcessStatus("PARAM_IS_NULL"));
			}
			if (param.get("keywords") == null || "".equals(param.get("keywords"))) {
				new Resource<String>(msg.getProcessStatus("PARAM_IS_NULL"));
			}
			String keywords = StringUtils.toString(param.get("keywords"));
			List<String> keywordlist = KeWordExtendThreadMain.Threadmain(keywords);
			PageMetadata pageMetadata = new PageMetadata();
			pageMetadata.setCount(keywordlist.size());
			return new PagedResources<>(keywordlist, pageMetadata);

		} catch (InterruptedException e) {
			return new PagedResources<String>(msg.getProcessStatus("SPIDER_ERROR"));
		}
	}
	/**
	 * 页面问题分析建议
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping("/tools/getPageAnalysis")
	public Resource<Map<String, Object>> getPageAnalysis(@RequestBody Map<String, String> param) {
		try {
			if (null == param || param.isEmpty()) {
				return new Resource<Map<String, Object>>(msg.getProcessStatus("PARAM_IS_NULL"));
			}
			if(!param.containsKey("keyword") || param.get("keyword") == null || 
					"".equals(param.get("keyword")) || !param.containsKey("url") ||
					param.get("url") == null || "".equals(param.get("url"))) {
				return new Resource<Map<String, Object>>(msg.getProcessStatus("PARAM_ERROR"));
			}
			
			Page page = PageCheck.getPageAnaly(param.get("keyword"), param.get("url"));
			Map<String, Object> res = new HashMap<String, Object>();
			if(null != page && !"".equals(page)){
				List<String> issueCode = page.getErrorlist();
				Map<String, Integer> map = page.getKeywords();
				//获取页面分析详细问题
				if(null != issueCode && issueCode.size() > 0){
					List<Map<String, String>> analyList = new ArrayList<Map<String,String>>();
					Map<String, String> temp = null;
					int flag = 0;
					if(issueCode.size() == 1 && ("30103".equals(issueCode.get(0)) || "30105".equals(issueCode.get(0)))){
						temp = new HashMap<String, String>();
						temp.put("issueCode", issueCode.get(0));
						temp.put("flag", "1");
						analyList.add(temp);
					}else {
						for (int j = 0; j < issuesCode.length; j++) {
							for (int i = 0,len = issueCode.size(); i < len; i++) {
								temp = new HashMap<String, String>();
								temp.put("issueCode", issuesCode[j]);
								if(issuesCode[j].equals(issueCode.get(i))){
									temp.put("flag", "1");
									analyList.add(temp);
									flag = 1;
									break;
								}
							}
							if(flag <= 0){
								temp = new HashMap<String, String>();
								temp.put("issueCode", issuesCode[j]);
								temp.put("flag", "0");
								analyList.add(temp);
							}
							flag = 0;
						}
					}
					res.put("analysis", analyList);
				}
				
				if(null != map && map.size() > 0){
					Set<String> set = map.keySet();
					
					if(null != set && set.size() > 0){
						int num = 0;
						for (String key : set) {
							if(null != map.get(key) && !"".equals(map.get(key)) && map.get(key) > 0){
								num = num + map.get(key);
							}
							res.put(key, map.get(key));
						}
						res.put("frequency", num);
					}
				}
			}
			return new Resource<Map<String, Object>>(res);
			
		} catch (Exception e) {
			return new Resource<Map<String, Object>>(msg.getProcessStatus("SPIDER_ERROR"));
		}
	}
	/**
	 * 文章原创度
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping("/tools/getPageOriginal")
	public Resource<Map<String, Object>> getPageOriginal(@RequestBody Map<String, String> param) {
		try {
			if (null == param || param.isEmpty()) {
				return new Resource<Map<String, Object>>(msg.getProcessStatus("PARAM_IS_NULL"));
			}
			if(!param.containsKey("text") || param.get("text") == null || 
					"".equals(param.get("text")) || param.get("text").length() > 1200) {
				return new Resource<Map<String, Object>>(msg.getProcessStatus("PARAM_ERROR"));
			}
			
			Map<String, Object> res = new HashMap<String, Object>();
			KeyWordScore keyWordScore = ArticlesOriginalCheck.originalcheck(param.get("text"));
			if(null != keyWordScore){
				res.put("original", new BigDecimal(keyWordScore.getOriginal()).setScale(2, BigDecimal.ROUND_HALF_UP));
				
				Map<String, Integer> map = keyWordScore.getOriginalList();
				if(null != map && map.size() > 0){
					List<Map<String, Object>> originalList = new ArrayList<Map<String,Object>>();
					Map<String, Object> temp = null;
					Set<String> keySet = map.keySet();
					for (String key : keySet) {
						temp = new HashMap<String, Object>();
						temp.put("breakText", key);
						temp.put("similarResult", map.get(key));
						originalList.add(temp);
					}
					res.put("originalList", originalList);
				}
			}
			
			return new Resource<Map<String, Object>>(res);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Resource<Map<String, Object>>(msg.getProcessStatus("SPIDER_ERROR"));
		}
	}
	/**
	 * 百度指数
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping("/tools/getBaiduIndex")
	public Resource<Map<String, Object>> getBaiduIndex(@RequestBody List<String> param) {
		try {
			if (null == param || param.isEmpty()) {
				return new Resource<Map<String, Object>>(msg.getProcessStatus("PARAM_IS_NULL"));
			}
			if(param.size() > 50) {
				return new Resource<Map<String, Object>>(msg.getProcessStatus("TOO_MANY_ARGUMENT"));
			}
			
			return new Resource<Map<String, Object>>(msg.getProcessStatus("ERROR"));
			
		} catch (Exception e) {
			return new Resource<Map<String, Object>>(msg.getProcessStatus("SPIDER_ERROR"));
		}
	}
}
