package com.ido85.seo.allSite.dto;

/**
 * wi_site _detail实体
 * @author fire
 *
 */
public class OutIssuesInfoDto {
	private String anchorText;   //锚文本                                     
	private int descriptionNum;  //description字节数                          
	private int domains;         //链接到这个页面的域名数/Linking Root Domains
	private String duplicateUrl; //重复url                                    
	private String engine;       //未被收录的搜索引擎                         
	private String externalLinks;//External link count                        
	private String flashTag;     //存在flash框架                              
	private int h1Num;           //H1标签数量                                 
	private int h2Num;           //H2标签数量                                 
	private int h3Num;           //H3标签数量                                 
	private String iframeTag;    //存在iframe框架                             
	private String issueCode;    //问题编码                                   
	private String linkPage;     //链接网页                                   
	private String pageAuthority;//Page authority/页面权重                    
	private String pageTitle;    //标题                                       
	private String pageUrl;      //页面url                                    
	private String riskEngine;   //被标记为风险网站的搜索引擎                 
	private String statusCode;   //状态吗                                     
	private int titleNum;        //title字符数                                
	private int totalIssues;     //问题总数                                   
	private int urlNum;          //URL字节数                                  
	private String robots;       //网站无Robots文件
	private String gzip;         //网站未启用gzip压缩
	public String getAnchorText() {
		return anchorText;
	}
	public void setAnchorText(String anchorText) {
		this.anchorText = anchorText;
	}
	public int getDescriptionNum() {
		return descriptionNum;
	}
	public void setDescriptionNum(int descriptionNum) {
		this.descriptionNum = descriptionNum;
	}
	public int getDomains() {
		return domains;
	}
	public void setDomains(int domains) {
		this.domains = domains;
	}
	public String getDuplicateUrl() {
		return duplicateUrl;
	}
	public void setDuplicateUrl(String duplicateUrl) {
		this.duplicateUrl = duplicateUrl;
	}
	public String getEngine() {
		return engine;
	}
	public void setEngine(String engine) {
		this.engine = engine;
	}
	public String getExternalLinks() {
		return externalLinks;
	}
	public void setExternalLinks(String externalLinks) {
		this.externalLinks = externalLinks;
	}
	public String getFlashTag() {
		return flashTag;
	}
	public void setFlashTag(String flashTag) {
		this.flashTag = flashTag;
	}
	public int getH1Num() {
		return h1Num;
	}
	public void setH1Num(int h1Num) {
		this.h1Num = h1Num;
	}
	public int getH2Num() {
		return h2Num;
	}
	public void setH2Num(int h2Num) {
		this.h2Num = h2Num;
	}
	public int getH3Num() {
		return h3Num;
	}
	public void setH3Num(int h3Num) {
		this.h3Num = h3Num;
	}
	public String getIframeTag() {
		return iframeTag;
	}
	public void setIframeTag(String iframeTag) {
		this.iframeTag = iframeTag;
	}
	public String getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(String issueCode) {
		this.issueCode = issueCode;
	}
	public String getLinkPage() {
		return linkPage;
	}
	public void setLinkPage(String linkPage) {
		this.linkPage = linkPage;
	}
	public String getPageAuthority() {
		return pageAuthority;
	}
	public void setPageAuthority(String pageAuthority) {
		this.pageAuthority = pageAuthority;
	}
	public String getPageTitle() {
		return pageTitle;
	}
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public String getRiskEngine() {
		return riskEngine;
	}
	public void setRiskEngine(String riskEngine) {
		this.riskEngine = riskEngine;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public int getTitleNum() {
		return titleNum;
	}
	public void setTitleNum(int titleNum) {
		this.titleNum = titleNum;
	}
	public int getTotalIssues() {
		return totalIssues;
	}
	public void setTotalIssues(int totalIssues) {
		this.totalIssues = totalIssues;
	}
	public int getUrlNum() {
		return urlNum;
	}
	public void setUrlNum(int urlNum) {
		this.urlNum = urlNum;
	}
	public String getRobots() {
		return robots;
	}
	public void setRobots(String robots) {
		this.robots = robots;
	}
	public String getGzip() {
		return gzip;
	}
	public void setGzip(String gzip) {
		this.gzip = gzip;
	}
	
}
