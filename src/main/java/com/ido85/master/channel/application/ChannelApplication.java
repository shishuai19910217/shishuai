package com.ido85.master.channel.application;

import java.util.List;

import com.ido85.master.channel.domain.ChannelDict;

public interface ChannelApplication {
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
}
