package com.tmonet.kms.mgmt.common.kmsenum;

//관리자 유형
public enum EnumManagerType {

	MNG_TYPE_SUPER("TP.MNG.01", "Super"), 
	MNG_TYPE_NORMAL("TP.MNG.02", "Normal");

	private final String key;
	private final String value;

	EnumManagerType(String key, String value) {
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