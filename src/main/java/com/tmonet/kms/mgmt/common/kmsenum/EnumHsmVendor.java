package com.tmonet.kms.mgmt.common.kmsenum;

//HSM 공급사
public enum EnumHsmVendor{
	
	HSM_VENDOR_ENTRUST	("VD.HSM.01", "Entrust"),
	HSM_VENDOR_SAFENET	("VD.HSM.02", "SafeNet");
	
	private final String key;
	private final String value;
	
	EnumHsmVendor(String key, String value) {
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

