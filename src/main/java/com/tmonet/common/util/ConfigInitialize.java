package com.tmonet.common.util;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigInitialize {

	private static final Logger logger = LoggerFactory.getLogger(ConfigInitialize.class);
	
	@Value("${serverConfigFilePath}")
	private String serverConfigFilePath;

	@PostConstruct
	@Bean
	ConfigLoadData configLoadData() {
	
		logger.debug("=====================================================");
		logger.debug("serverConfigFilePath : {}", serverConfigFilePath);
		logger.debug("=====================================================");
		return new ConfigLoadData(serverConfigFilePath);
		
	}

}
