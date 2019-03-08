package com.ido85.seo.extlinks.dto;

import java.util.List;


public class OutTrendDto {
	private List<OutTrendItemDto> trend;
	private List<OutLinkItemDto> linksInfos;
	private List<OutLinkItemDto> domainInfos;
	private String crawlDate;
	public String getCrawlDate() {
		return crawlDate;
	}
	public void setCrawlDate(String crawlDate) {
		this.crawlDate = crawlDate;
	}
	public List<OutTrendItemDto> getTrend() {
		return trend;
	}
	public void setTrend(List<OutTrendItemDto> trend) {
		this.trend = trend;
	}
	public List<OutLinkItemDto> getLinksInfos() {
		return linksInfos;
	}
	public void setLinksInfos(List<OutLinkItemDto> linksInfos) {
		this.linksInfos = linksInfos;
	}
	public List<OutLinkItemDto> getDomainInfos() {
		return domainInfos;
	}
	public void setDomainInfos(List<OutLinkItemDto> domainInfos) {
		this.domainInfos = domainInfos;
	}
	
	
}
