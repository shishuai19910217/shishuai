package com.ido85.seo.extlinks.dto;

import java.util.List;

public class OutDomainDistriDto {
	private List<OutDomainDto> domains;
	private long refDomains;
	public List<OutDomainDto> getDomains() {
		return domains;
	}
	public void setDomains(List<OutDomainDto> domains) {
		this.domains = domains;
	}
	public long getRefDomains() {
		return refDomains;
	}
	public void setRefDomains(long refDomains) {
		this.refDomains = refDomains;
	}
	
}
