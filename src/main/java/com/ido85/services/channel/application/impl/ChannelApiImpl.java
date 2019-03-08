package com.ido85.services.channel.application.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import com.ido85.frame.common.utils.ListUntils;
import com.ido85.frame.common.utils.StringUtils;
import com.ido85.frame.web.rest.security.Constants;
import com.ido85.frame.web.rest.utils.CacheUtils;
import com.ido85.frame.web.rest.utils.RestUserUtils;
import com.ido85.master.channel.application.ChannelApplication;
import com.ido85.master.channel.domain.ChannelDict;
import com.ido85.master.packages.domain.TenantPackage;
import com.ido85.services.channel.application.ChannelApi;
import com.ido85.services.channel.dto.ChannelDto;
import com.ido85.services.packages.PackageApi;

@Named
public class ChannelApiImpl implements ChannelApi {
	@Inject
	private ChannelApplication channelApp;
	@Inject
	private PackageApi packageApi;

	@Override
	public List<ChannelDict> getChannelDictsInfo(List<Integer> channelIds)
			throws Exception {
		return channelApp.getChannelDictsInfo(channelIds);
	}
	@Override
	public List<ChannelDict> getChannelDictsInfo() throws Exception {
		return channelApp.getChannelDictsInfo();
	}
	@Override
	public List<ChannelDto> getChannel() throws Exception {
		List<ChannelDto> dtos = new ArrayList<ChannelDto>();
		List<TenantPackage> packages = packageApi.getPackageExampleListByTenantId(RestUserUtils.getTencentId() + "", "0");
		if(ListUntils.isNull(packages)){
			return null;
		}
		List<Map<String,Object>> temp = packageApi.getScope(packages.get(0).getTenantPackageElementsRelList());
		String engine = packageApi.getEngine(temp);
		if(StringUtils.isNotBlank(engine)){
			for(String str : engine.split("\\,")){
				ChannelDto d = new ChannelDto(str,((ChannelDict)CacheUtils.get(Constants.CHANNELDICT_CACHE, str)).getEngineName());
				dtos.add(d);
			}
		}
		return dtos;
	}
	@Override
	public List<ChannelDto> getChannelByIds(List<Integer> engines) throws Exception {
		List<ChannelDto> dtos = new ArrayList<ChannelDto>();
		List<TenantPackage> packages = packageApi.getPackageExampleListByTenantId(RestUserUtils.getTencentId() + "", "0");
		if(ListUntils.isNull(packages)){
			return dtos;
		}
		List<Map<String,Object>> temp = packageApi.getScope(packages.get(0).getTenantPackageElementsRelList());
		String engine = packageApi.getEngine(temp);
		if(StringUtils.isNotBlank(engine)){
			for(String str : engine.split("\\,")){
				for (int i = 0; i < engines.size(); i++) {
					if(engines.get(i).equals(StringUtils.toInteger(str))){
						ChannelDto dto = new ChannelDto(str,((ChannelDict)CacheUtils.get(Constants.CHANNELDICT_CACHE, str)).getEngineName());
						dtos.add(dto);
					}
				}
			}
		}
		
		return dtos;
	}
}
