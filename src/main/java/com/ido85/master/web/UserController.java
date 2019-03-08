package com.ido85.master.web;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ido85.frame.common.restful.Resource;
import com.ido85.frame.common.utils.CopyUtil;
import com.ido85.frame.web.UserInfo;
import com.ido85.frame.web.rest.utils.RestUserUtils;
import com.ido85.master.packages.dto.TenantPackageDto;
import com.ido85.master.user.dto.OutUserInfoDto;
import com.ido85.master.user.util.UserUtils;
import com.ido85.services.packages.PackageApi;
import com.ido85.services.project.ProjectApi;

@RestController
public class UserController {
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(UserController.class);
	@Inject
	private PackageApi packageApi;
	@Inject
	private ProjectApi projectApi;
	

	@RequestMapping("/ws/getUserInfo")
	public Resource<OutUserInfoDto> getUserInfo() throws Exception{
		//TODO 此处可以优化，一次性查出用户所有的行业和职业信息
		UserInfo user = RestUserUtils.getUserInfo();
		OutUserInfoDto out = new OutUserInfoDto();
		if(null == user){
			return new Resource<OutUserInfoDto>(out);
		}
		
		System.out.println("llz====user======"+UserUtils.getTenantID());
		
		List<TenantPackageDto> packageDtos = null;
		int activeProjectNum = 0;
		int projectNum = 0;
		
		packageDtos = packageApi.getPackageExampleListByTenantId(user.getTenantID() + "");
		if(null != packageDtos && packageDtos.size() > 0){
			for (int j = 0,lenth = packageDtos.size(); j < lenth; j++) {
				if("0".equals(packageDtos.get(j).getAppType())){
					activeProjectNum = projectApi.getActiveProjectNum(packageDtos.get(j).getTenantPackageId());
					projectNum = projectApi.getProjectNum(packageDtos.get(j).getTenantPackageId());
					break;
				}
			}
		}
		CopyUtil.copyProperties(out, user);
		out.setProjectNum(projectNum);
		out.setActiveNum(activeProjectNum);
		
		out.setPackageInfos(packageDtos);
		if(null != out.getPackageInfos() && out.getPackageInfos().size() > 0){
			logger.info("llz==packages.size="+out.getPackageInfos().size());
			logger.info("llz==packages=1=getTenantPackageId="+out.getPackageInfos().get(0).getTenantPackageId());
		}
		return new Resource<OutUserInfoDto>(out);
	}
}
