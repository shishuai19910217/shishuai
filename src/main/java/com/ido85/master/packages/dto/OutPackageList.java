package com.ido85.master.packages.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ido85.master.packages.domain.Packages;

/***
 * 订购页面的最终返回信息
 * @author shishuai
 *
 */
public class OutPackageList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Packages> packageList = new ArrayList<Packages>();

	public List<Packages> getPackageList() {
		return packageList;
	}

	public void setPackageList(List<Packages> packageList) {
		this.packageList = packageList;
	}
}
