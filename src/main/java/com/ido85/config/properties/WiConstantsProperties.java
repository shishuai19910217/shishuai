package com.ido85.config.properties;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @author shishuai
 *
 */
@Named
@ConfigurationProperties(locations="classpath:conf/wiConstants.yml", merge=true)
public class WiConstantsProperties {
	private Map<String, Object> maps = new HashMap<String, Object>();

	public Map<String, Object> getMaps() {
		return maps;
	}
	public void setMaps(Map<String, Object> maps) {
		this.maps = maps;
	}
	 
	public Object getValue(String key){
		return maps.get(key);
	}
	public void setValue(String key,String value){
		maps.put(key, value);
	}
}
