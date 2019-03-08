package com.ido85.config.properties;

import org.springframework.beans.factory.annotation.Value;

//@Named(value = "httpUrlProperties")
//@ConfigurationProperties(prefix = "url", locations = "application.yml", merge = true)
public class HttpUrlProperties {
	@Value("${url.keywordRanks}")
	private String keywordRanks;
	@Value("${url.keywordRankTrend}")
	private String keywordRankTrend;
	@Value("${url.keywordSerpReport}")
	private String keywordSerpReport;
	@Value("${url.keywordRankBaseInfo}")
	private String keywordRankBaseInfo;
	@Value("${url.rankSearchVisibility}")
	private String rankSearchVisibility;
	@Value("${url.ranks}")
	private String ranks;
	@Value("${url.allKeywordsRanks}")
	private String allKeywordsRanks;
	@Value("${url.allEngineVisibility}")
	private String allEngineVisibility;
	@Value("${url.diffEngineRanks}")
	private String diffEngineRanks;
	@Value("${url.rankingAnalysis}")
	private String rankingAnalysis;
	@Value("${url.siteIssuesInfo}")
	private String siteIssuesInfo;
	@Value("${url.getPageQuestion}")
	private String PageQuestion;
	@Value("${url.allSearchResult}")
	private String allSearchResult;
	@Value("${url.upOrDownRankNum}")
	private String upOrDownRankNum;

	public String getAllSearchResult() {
		return allSearchResult;
	}

	public void setAllSearchResult(String allSearchResult) {
		this.allSearchResult = allSearchResult;
	}

	public String getSiteIssuesInfo() {
		return siteIssuesInfo;
	}

	public void setSiteIssuesInfo(String siteIssuesInfo) {
		this.siteIssuesInfo = siteIssuesInfo;
	}

	public String getRankingAnalysis() {
		return rankingAnalysis;
	}

	public void setRankingAnalysis(String rankingAnalysis) {
		this.rankingAnalysis = rankingAnalysis;
	}

	public String getDiffEngineRanks() {
		return diffEngineRanks;
	}

	public void setDiffEngineRanks(String diffEngineRanks) {
		this.diffEngineRanks = diffEngineRanks;
	}

	public String getAllEngineVisibility() {
		return allEngineVisibility;
	}

	public void setAllEngineVisibility(String allEngineVisibility) {
		this.allEngineVisibility = allEngineVisibility;
	}

	public String getAllKeywordsRanks() {
		return allKeywordsRanks;
	}

	public void setAllKeywordsRanks(String allKeywordsRanks) {
		this.allKeywordsRanks = allKeywordsRanks;
	}

	public String getRanks() {
		return ranks;
	}

	public void setRanks(String ranks) {
		this.ranks = ranks;
	}

	public String getRankSearchVisibility() {
		return rankSearchVisibility;
	}

	public void setRankSearchVisibility(String rankSearchVisibility) {
		this.rankSearchVisibility = rankSearchVisibility;
	}

	public String getKeywordRankBaseInfo() {
		return keywordRankBaseInfo;
	}

	public void setKeywordRankBaseInfo(String keywordRankBaseInfo) {
		this.keywordRankBaseInfo = keywordRankBaseInfo;
	}

	public String getKeywordRankTrend() {
		return keywordRankTrend;
	}

	public void setKeywordRankTrend(String keywordRankTrend) {
		this.keywordRankTrend = keywordRankTrend;
	}

	public String getKeywordSerpReport() {
		return keywordSerpReport;
	}

	public void setKeywordSerpReport(String keywordSerpReport) {
		this.keywordSerpReport = keywordSerpReport;
	}

	public String getKeywordRanks() {
		return keywordRanks;
	}

	public void setKeywordRanks(String keywordRanks) {
		this.keywordRanks = keywordRanks;
	}

	public String getPageQuestion() {
		return PageQuestion;
	}

	public void setPageQuestion(String pageQuestion) {
		PageQuestion = pageQuestion;
	}

	public String getUpOrDownRankNum() {
		return upOrDownRankNum;
	}

	public void setUpOrDownRankNum(String upOrDownRankNum) {
		this.upOrDownRankNum = upOrDownRankNum;
	}


}
