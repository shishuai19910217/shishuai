package com.ido85.services.channel.application;

import java.util.List;

import com.ido85.master.channel.domain.ChannelDict;
import com.ido85.services.channel.dto.ChannelDto;

public interface ChannelApi {
	/**
	 * 根据渠道id获取渠道信息
	 * @param channelIds
	 * @return
	 */
	List<ChannelDict> getChannelDictsInfo(List<Integer> channelIds) throws Exception;
	/**
	 * 获取所有渠道信息
	 * @return
	 */
	List<ChannelDict> getChannelDictsInfo() throws Exception;
	/**
	 * 获取当前租户的当前套餐的所有渠道信息
	 * @return
	 */
	List<ChannelDto> getChannel() throws Exception;
	/**
	 * 根据搜索引擎id判断当前租户的当前套餐的渠道是否存在
	 * @param engines
	 * @return
	 * @throws Exception engines 为空时抛出空指针异常
	 */
	List<ChannelDto> getChannelByIds(List<Integer> engines) throws Exception;
	
	
}
