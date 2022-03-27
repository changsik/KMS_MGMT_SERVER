package com.tmonet.kms.mgmt.common.kmsenum;

//키 객체 유형
public enum EnumKeyObjectType{
	
	KEY_OBJ_TYPE_PRI_KEY		("TP.KEY.01", "Private Key"),
	KEY_OBJ_TYPE_PUB_KEY		("TP.KEY.02", "Public Key"),
	KEY_OBJ_TYPE_SYMMETRIC_KEY	("TP.KEY.03", "Symmetric Key"),
	KEY_OBJ_TYPE_SPLIT_KEY		("TP.KEY.04", "Split Key");
	
	private final String key;
	private final String value;
	
	EnumKeyObjectType(String key, String value) {
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
