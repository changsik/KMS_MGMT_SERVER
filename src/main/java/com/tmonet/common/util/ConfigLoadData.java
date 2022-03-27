package com.tmonet.common.util;

import java.io.FileReader;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class ConfigLoadData {

	private static final Logger logger = LoggerFactory.getLogger(ConfigLoadData.class);
	private static String kmsServerUrl;
	private static String kmsManagerId;
	
	public ConfigLoadData(String configPath) {
/*
		try {
			Gson gson = new Gson();
			
			JsonReader reader = new JsonReader(new FileReader(configPath));
			
			HashMap<String, String> config = gson.fromJson(reader, HashMap.class);
			
			kmsServerUrl = config.get("kmsServerUrl");
			kmsManagerId = config.get("kmsManagerId");
			
			logger.debug("======================================================");
			logger.debug("==                  CONFIG LOAD                     ==");
			logger.debug("======================================================");
			logger.debug("== configPath : {}", configPath);
			logger.debug("== kmsServerUrl : {}", kmsServerUrl);
			logger.debug("== kmsManagerId : {}", kmsManagerId);
			
		} catch(Exception e) {
			logger.error("일부 설정 로드 실패로 인한 프로세스 종료!!!");
			System.exit(-1);
		}
		*/
	}

	public static String getKmsServerUrl() {
		return kmsServerUrl;
	}

	public static String getKmsManagerId() {
		return kmsManagerId;
	}
	
}
