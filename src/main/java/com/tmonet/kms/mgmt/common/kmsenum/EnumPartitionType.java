package com.tmonet.kms.mgmt.common.kmsenum;

//파티션 유형
public enum EnumPartitionType{
	
	PARTITION_TYPE_SOFTCARD	("TP.PAT.01", "nShield SoftCard"),
	PARTITION_TYPE_CARDSET	("TP.PAT.02", "nShield CardSet");
	
	private final String key;
	private final String value;
	
	EnumPartitionType(String key, String value) {
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
