package com.tmonet.kms.mgmt.common.kmsenum;
	
//HSM 설치유형
public enum EnumHsmInstallType{
		
	HSM_INSTALL_TYPE_NETWORK	("TP.HSM.01", "Network"),
	HSM_INSTALL_TYPE_PCI		("TP.HSM.11", "PCI"),
	HSM_INSTALL_TYPE_USB		("TP.HSM.12", "USB");
	
	private final String key;
	private final String value;
	
	EnumHsmInstallType(String key, String value) {
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
