package com.ido85.master.project.dto;


/**
 * 竞品入参，修改项目信息时使用此dto
 * @author fire
 *
 */
public class InCompetitorDto {
	private String id;
	private String url;
	private String name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}