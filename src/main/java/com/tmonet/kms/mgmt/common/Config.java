package com.tmonet.kms.mgmt.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "env")
public class Config {

	private String projectDesc;
	private String key;

	public String getProjectDesc() {
		return projectDesc;
	}

	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
