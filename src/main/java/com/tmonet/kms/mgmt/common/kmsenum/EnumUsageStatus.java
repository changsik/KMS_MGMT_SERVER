package com.tmonet.kms.mgmt.common.kmsenum;

//사용 상태
public enum EnumUsageStatus{
	
	USAGE_STATUS_READY		("UST.01", "Ready"),
	USAGE_STATUS_ACTIVE		("UST.02", "Active"),
	USAGE_STATUS_SUSPEND	("UST.03", "Suspend"),
	USAGE_STATUS_DISCARD	("UST.04", "Discard");
	
	private final String key;
	private final String value;
	
	EnumUsageStatus(String key, String value) {
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
