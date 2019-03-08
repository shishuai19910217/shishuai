package com.ido85.master.channel.application.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.ido85.master.channel.application.ChannelApplication;
import com.ido85.master.channel.domain.ChannelDict;
import com.ido85.master.channel.resources.ChannelRepository;

@Named
public class ChannelApplicationImpl implements ChannelApplication {
	@Inject
	private ChannelRepository channelRes;

	@Override
	public List<ChannelDict> getChannelDictsInfo(List<Integer> channelIds) throws Exception {
		return channelRes.findAll(channelIds);
	}

	@Override
	public List<ChannelDict> getChannelDictsInfo() throws Exception {
		return channelRes.findAll();
	}

}
