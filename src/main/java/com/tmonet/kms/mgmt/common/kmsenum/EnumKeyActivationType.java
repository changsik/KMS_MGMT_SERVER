package com.tmonet.kms.mgmt.common.kmsenum;

//키 활성화 유형
public enum EnumKeyActivationType{
	
	KEY_ACT_TYPE_AUTO	("TP.KEY.ACT.01", "Auto"),
	KEY_ACT_TYPE_MANUAL	("TP.KEY.ACT.02", "Manual");
	
	private final String key;
	private final String value;
	
	EnumKeyActivationType(String key, String value) {
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
