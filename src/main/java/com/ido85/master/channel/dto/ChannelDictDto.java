package com.ido85.master.channel.dto;

import java.io.Serializable;

public class ChannelDictDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer engineType;
	private String engineName;
	
	public Integer getEngineType() {
		return engineType;
	}
	public void setEngineType(Integer engineType) {
		this.engineType = engineType;
	}
	public String getEngineName() {
		return engineName;
	}
	public void setEngineName(String engineName) {
		this.engineName = engineName;
	}

}
