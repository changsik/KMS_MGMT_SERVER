package com.tmonet.kms.mgmt.common.kmsenum;

//처리 결과
public enum EnumResultStatus{
	
	RESULT_STATUS_SUCCESS		("00000000", "Success"),
	RESULT_STATUS_OP_FAILED		("00000001", "Operation Failed"),
	RESULT_STATUS_OP_PENDING	("00000002", "Operation Pending"),
	RESULT_STATUS_OP_UNDONE		("00000003", "Operation Undone");
	
	private final String key;
	private final String value;
	
	EnumResultStatus(String key, String value) {
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
