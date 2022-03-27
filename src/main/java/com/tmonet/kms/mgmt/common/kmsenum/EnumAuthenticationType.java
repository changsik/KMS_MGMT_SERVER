package com.tmonet.kms.mgmt.common.kmsenum;

public enum EnumAuthenticationType {

	AUTH_TYPE_NONE		("TP.AUTH.01", "None"),
	AUTH_TYPE_API_KEY	("TP.AUTH.02", "API 키"),
	AUTH_TYPE_PASSWORD	("TP.AUTH.03", "Password"),
	AUTH_TYPE_HMAC		("TP.AUTH.04", "HMAC");
	
	private final String key;
	private final String value;
	
	EnumAuthenticationType(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}
	
	public String getValue() {
		return value;
	}
	
}
