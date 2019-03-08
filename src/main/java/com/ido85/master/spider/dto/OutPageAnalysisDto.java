package com.ido85.master.spider.dto;

import java.util.Map;

public class OutPageAnalysisDto {
	private Map<String, Object> analysis;
	private int bold;
	private int description;
	private int frequency;
	private int h1;
	private int h2;
	private int imageAlt;
	private int text;
	private int title;
	private int url;
	public Map<String, Object> getAnalysis() {
		return analysis;
	}
	public void setAnalysis(Map<String, Object> analysis) {
		this.analysis = analysis;
	}
	public int getBold() {
		return bold;
	}
	public void setBold(int bold) {
		this.bold = bold;
	}
	public int getDescription() {
		return description;
	}
	public void setDescription(int description) {
		this.description = description;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public int getH1() {
		return h1;
	}
	public void setH1(int h1) {
		this.h1 = h1;
	}
	public int getH2() {
		return h2;
	}
	public void setH2(int h2) {
		this.h2 = h2;
	}
	public int getImageAlt() {
		return imageAlt;
	}
	public void setImageAlt(int imageAlt) {
		this.imageAlt = imageAlt;
	}
	public int getText() {
		return text;
	}
	public void setText(int text) {
		this.text = text;
	}
	public int getTitle() {
		return title;
	}
	public void setTitle(int title) {
		this.title = title;
	}
	public int getUrl() {
		return url;
	}
	public void setUrl(int url) {
		this.url = url;
	}
	
}
