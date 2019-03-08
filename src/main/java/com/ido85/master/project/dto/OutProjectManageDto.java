package com.ido85.master.project.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/***
 * 项目管理 最终要返回前段的出参
 * @author shishuai
 *
 */
public class OutProjectManageDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int activeProNum;
	private int  count;
	private String  noProject;
	private int  pageNo;
	private int  pageSize;
	private List<ProjectPageDto>  projectInfos = new ArrayList<ProjectPageDto>();
	public int getActiveProNum() {
		return activeProNum;
	}
	public void setActiveProNum(int activeProNum) {
		this.activeProNum = activeProNum;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getNoProject() {
		return noProject;
	}
	public void setNoProject(String noProject) {
		this.noProject = noProject;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List<ProjectPageDto> getProjectInfos() {
		return projectInfos;
	}
	public void setProjectInfos(List<ProjectPageDto> projectInfos) {
		this.projectInfos = projectInfos;
	}
	
	
	

}
