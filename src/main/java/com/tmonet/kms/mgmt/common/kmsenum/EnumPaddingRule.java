package com.tmonet.kms.mgmt.common.kmsenum;

//패딩 규칙
public enum EnumPaddingRule{
	
	PADDING_RULE_NONE		("00000001", "None"),
	PADDING_RULE_OAEP		("00000002", "OAEP"),
	PADDING_RULE_PKCS5		("00000003", "PKCS5"),
	PADDING_RULE_SSL3		("00000004", "SSL3"),
	PADDING_RULE_ZEROS		("00000005", "Zeros"),
	PADDING_RULE_ANSI_X9_23	("00000006", "ANSI X9.23"),
	PADDING_RULE_ISO_10126	("00000007", "ISO 10126"),
	PADDING_RULE_PKCS1_V1_5	("00000008", "PKCS1 v1.5"),
	PADDING_RULE_X9_31		("00000009", "X9.31"),
	PADDING_RULE_PSS		("0000000A", "PSS"),
	PADDING_RULE_ISO7816	("0000000B", "ISO7816");
	
	private final String key;
	private final String value;
	
	EnumPaddingRule(String key, String value) {
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
