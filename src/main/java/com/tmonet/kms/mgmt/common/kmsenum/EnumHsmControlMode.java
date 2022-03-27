package com.tmonet.kms.mgmt.common.kmsenum;

//HSM 제어 모드
public enum EnumHsmControlMode{

	HCM_01	("HCM.01", "Active"),
	HCM_02	("HCM.02", "Standby"),
	HCM_03	("HCM.03", "Inactive");
	
	private final String key;
	private final String value;
	
	EnumHsmControlMode(String key, String value) {
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
