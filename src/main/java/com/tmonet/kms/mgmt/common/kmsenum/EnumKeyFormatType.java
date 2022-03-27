package com.tmonet.kms.mgmt.common.kmsenum;

//키 데이터의 포맷 방식
public enum EnumKeyFormatType{
	
	KEY_FORMAT_TYPE_RAW				("00000001", "Raw"),
	KEY_FORMAT_TYPE_OPAQUE			("00000002", "Opaque"),
	KEY_FORMAT_TYPE_PKCS_1			("00000003", "PKCS#1"),
	KEY_FORMAT_TYPE_PKCS_8			("00000004", "PKCS#8"),
	KEY_FORMAT_TYPE_X_509			("00000005", "X.509"),
	KEY_FORMAT_TYPE_ECPRIVATEKEY	("00000006", "ECPrivateKey"),
	KEY_FORMAT_TYPE_SMTR			("00000007", "TransParent Symmetric Key"),
	KEY_FORMAT_TYPE_DSA_PRI			("00000008", "Transparent DSA Private Key"),
	KEY_FORMAT_TYPE_DSA_PUB			("00000009", "Transparent DSA Public Key"),
	KEY_FORMAT_TYPE_RSA_PRI			("0000000A", "Transparent RSA Private Key"),
	KEY_FORMAT_TYPE_RSA_PUB			("0000000B", "Transparent RSA Public Key"),
	KEY_FORMAT_TYPE_DH_PRI			("0000000C", "Transparent DH Private Key"),
	KEY_FORMAT_TYPE_DH_PUB			("0000000D", "Transparent DH Public Key"),
	KEY_FORMAT_TYPE_EC_PRI			("00000014", "Transparent EC Private Key"),
	KEY_FORMAT_TYPE_EC_PUB			("00000015", "Transparent EC Public Key"),
	KEY_FORMAT_TYPE_PKCS_12			("00000016", "PKCS#12"),
	KEY_FORMAT_TYPE_PKCS_10			("00000017", "PKCS#10");
	
	private final String key;
	private final String value;
	
	EnumKeyFormatType(String key, String value) {
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
