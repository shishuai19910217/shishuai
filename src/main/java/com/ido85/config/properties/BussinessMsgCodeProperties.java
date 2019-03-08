package com.ido85.config.properties;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.ido85.frame.common.restful.ProcessStatus;

@Named(value = "bussinessMsgCodeProperties")
@ConfigurationProperties(locations = "classpath:conf/businessMsgCode.yml", merge=true)
public class BussinessMsgCodeProperties{
	
	
	private Map<String, ProcessStatus> maps = new HashMap<String, ProcessStatus>();

	public Map<String, ProcessStatus> getMaps() {
		return maps;
	}
	public void setMaps(Map<String, ProcessStatus> maps) {
		this.maps = maps;
	}
	 
	public ProcessStatus getProcessStatus(String name){
		return maps.get(name);
	}
	
	public String getBussinessMsg(String key){
		return maps.get(key).getRetMsg();
	}
	
	public int getBussinessCode(String key){
		return maps.get(key).getRetCode();
	}
}
