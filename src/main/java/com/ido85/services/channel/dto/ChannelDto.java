package com.ido85.services.channel.dto;

public class ChannelDto {
	private String engineType;
	private String engineName;
	
	public ChannelDto(){}
	
	public ChannelDto(String engineType, String engineName) {
		super();
		this.engineType = engineType;
		this.engineName = engineName;
	}

	public String getEngineType() {
		return engineType;
	}
	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}
	public String getEngineName() {
		return engineName;
	}
	public void setEngineName(String engineName) {
		this.engineName = engineName;
	}
	
	
}
