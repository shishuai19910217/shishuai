package com.ido85.config.properties;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Named(value = "fileMapingProperties")
@ConfigurationProperties(prefix = "fileMaping", locations = "application.properties", merge = true)
public class FileMapingProperties {
	@Value("${fileMaping.path}")
	private String path;
	@Value("${fileMaping.sqlPath}")
	private String sqlPath;

	public String getSqlPath() {
		return sqlPath;
	}

	public void setSqlPath(String sqlPath) {
		this.sqlPath = sqlPath;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	

}
