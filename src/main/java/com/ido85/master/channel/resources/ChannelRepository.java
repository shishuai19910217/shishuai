package com.ido85.master.channel.resources;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ido85.master.channel.domain.ChannelDict;

public interface ChannelRepository extends JpaRepository<ChannelDict, Integer>{
	
}
