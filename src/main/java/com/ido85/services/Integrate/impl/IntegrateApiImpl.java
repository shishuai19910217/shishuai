package com.ido85.services.Integrate.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.ido85.config.properties.WiConstantsProperties;
import com.ido85.master.keyword.application.KeywordApplication;
import com.ido85.master.packages.application.TenantPackageApplication;
import com.ido85.master.packages.domain.TenantPackage;
import com.ido85.master.packages.domain.TenantPackageElementsRel;
import com.ido85.master.project.application.ProjectApplication;
import com.ido85.master.project.domain.ProjectTenantExampleRel;
import com.ido85.services.Integrate.IntegrateApi;

/**
 * 集成服务层
 * @author fire
 *
 */
@Named
public class IntegrateApiImpl implements IntegrateApi{
	@Inject
	private TenantPackageApplication packageApp;
	@Inject
	private KeywordApplication keywordApp;
	@Inject
	private ProjectApplication projectApplication;
	@Inject
	private WiConstantsProperties wiConstants;
	
	/**
	 * 判断项目是否还可以添加关键词，
	 * 并且返回项目添加完此次关键词之后还能够添加多少个关键词
	 * @param projectId
	 * @param keywordNum 
	 * @param leftNum  添加完此次关键词之后还能够添加多少个关键词
	 * @return 
	 */
	public boolean checkAddKeyword(String projectId, int keywordNum, int leftNum) {
		boolean res = false;
		int allNum = 0;
		int allKeyNum = keywordApp.getProjectKeywordNum(projectId);
		ProjectTenantExampleRel proTenRel = projectApplication.getProTenRelById(projectId);
		TenantPackage tenantPackage = packageApp.getTenantPackageInfo(proTenRel.getTenantId()+"");
		if(null != tenantPackage && !"".equals(tenantPackage)){
			List<TenantPackageElementsRel> rel = tenantPackage.getTenantPackageElementsRelList();
			for (int i = 0; i < rel.size(); i++) {
				TenantPackageElementsRel tenantRel = rel.get(i);
				if(null != tenantRel && !"".equals(tenantRel) 
						&& tenantRel.getElements().getElementId().toString().equals(wiConstants.getValue("keywordNum")+"")){
					allNum = tenantRel.getUsedMax();
					break;
				}
			}
		}
		leftNum = allNum - allKeyNum - keywordNum;
		if(leftNum >= 0)
			res = true;
		return res;
	}
}
