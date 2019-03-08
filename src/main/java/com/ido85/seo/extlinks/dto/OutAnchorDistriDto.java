package com.ido85.seo.extlinks.dto;

import java.util.List;

public class OutAnchorDistriDto {
	private List<OutAnchorDto> anchors;
	private long extBackLinks;
	public List<OutAnchorDto> getAnchors() {
		return anchors;
	}
	public void setAnchors(List<OutAnchorDto> anchors) {
		this.anchors = anchors;
	}
	public long getExtBackLinks() {
		return extBackLinks;
	}
	public void setExtBackLinks(long extBackLinks) {
		this.extBackLinks = extBackLinks;
	}
	
}
