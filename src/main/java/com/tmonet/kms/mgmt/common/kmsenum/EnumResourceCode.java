package com.tmonet.kms.mgmt.common.kmsenum;

public enum EnumResourceCode{
	
	RESOURCE_CODE_HSM					("RC.01", "HSM"),
	RESOURCE_CODE_HSM_CONTROL_SERVER	("RC.02", "HSM Control Server"),
	RESOURCE_CODE_KEY					("RC.03", "Key"),
	RESOURCE_CODE_KEY_PROFILE			("RC.04", "Key Profile"),
	RESOURCE_CODE_CLIENT				("RC.05", "Client"),
	RESOURCE_CODE_MANAGER				("RC.06", "Manager"),
	RESOURCE_CODE_CLIENT_AUTH			("RC.07", "Client Auth"),
	RESOURCE_CODE_MANAGER_AUTH			("RC.08", "Manager Auth");
	
	private final String key;
	private final String value;
	
	EnumResourceCode(String key, String value) {
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
