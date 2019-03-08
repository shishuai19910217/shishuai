package com.ido85.master.project.dto;

/****
 * 项目管理 中的项目基本信息
 * @author shishuai
 *
 */
public class ProjectPageDto {
	private String projectId;
	private int   competitorNum;
	private int   keywordNum;
	private int keywordRank ;
	private int keywordChange ;
	private String  projectName;
	private int crawlIssues;
	private int issuesChange;
	private String receiveEmail;
	private String createDate;
	private String filingDate;
	private String projectUrl;
	public String getProjectUrl() {
		return projectUrl;
	}
	public void setProjectUrl(String projectUrl) {
		this.projectUrl = projectUrl;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getFilingDate() {
		return filingDate;
	}
	public void setFilingDate(String filingDate) {
		this.filingDate = filingDate;
	}
	public ProjectPageDto(){
		
	}
	public ProjectPageDto(Integer  projectId ,long competitorNum, long keywordNum){
		this.projectId = projectId.toString();
		this.competitorNum = (int)competitorNum;
		this.keywordNum = (int)keywordNum;
		
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public int getCompetitorNum() {
		return competitorNum;
	}
	public void setCompetitorNum(int competitorNum) {
		this.competitorNum = competitorNum;
	}
	public int getKeywordNum() {
		return keywordNum;
	}
	public void setKeywordNum(int keywordNum) {
		this.keywordNum = keywordNum;
	}
	public int getKeywordRank() {
		return keywordRank;
	}
	public void setKeywordRank(int keywordRank) {
		this.keywordRank = keywordRank;
	}
	public int getKeywordChange() {
		return keywordChange;
	}
	public void setKeywordChange(int keywordChange) {
		this.keywordChange = keywordChange;
	}
	public int getCrawlIssues() {
		return crawlIssues;
	}
	public void setCrawlIssues(int crawlIssues) {
		this.crawlIssues = crawlIssues;
	}
	public int getIssuesChange() {
		return issuesChange;
	}
	public void setIssuesChange(int issuesChange) {
		this.issuesChange = issuesChange;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getReceiveEmail() {
		return receiveEmail;
	}
	public void setReceiveEmail(String receiveEmail) {
		this.receiveEmail = receiveEmail;
	}
	
	

}
