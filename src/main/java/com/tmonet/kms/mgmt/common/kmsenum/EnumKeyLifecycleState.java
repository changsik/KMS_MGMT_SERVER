package com.tmonet.kms.mgmt.common.kmsenum;

public enum EnumKeyLifecycleState {

	KEY_LC_PRE_ACTIVE				("00000001", "Pre-Active"),
	KEY_LC_ACTIVE					("00000002", "Active"),
	KEY_LC_DEACTIVATED				("00000003", "Deactivated"),
	KEY_LC_COMPROMISED				("00000004", "Compromised"),
	KEY_LC_DESTROYED				("00000005", "Destroyed"),
	KEY_LC_DESTROYED_COMPROMISED	("00000006", "Destroyed Compromised");
	
	private final String key;
	private final String value;
	
	EnumKeyLifecycleState(String key, String value) {
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

