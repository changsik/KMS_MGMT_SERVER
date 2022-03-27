package com.tmonet.kms.mgmt.common.kmsenum;

//키의 용도
public enum EnumKeyUsageMask{
	
	KEY_USAGE_MASK_SIGN				("00000001", "Sign"),
	KEY_USAGE_MASK_VERIFY			("00000002", "Verify"),
	KEY_USAGE_MASK_ENCRYPT			("00000004", "Encrypt"),
	KEY_USAGE_MASK_DECRYPT			("00000008", "Decrypt"),
	KEY_USAGE_MASK_WRAP_KEY			("00000010", "Wrap Key"),
	KEY_USAGE_MASK_UNWRAP_KEY		("00000020", "Unwrap Key"),
	KEY_USAGE_MASK_MAC_GENERATE		("00000080", "MAC Generate"),
	KEY_USAGE_MASK_MAC_VERIFY		("00000100", "MAC Verify"),
	KEY_USAGE_MASK_DERIVE_KEY		("00000200", "Derive Key"),
	KEY_USAGE_MASK_KEY_AGREEMENT	("00000800", "Key Agreement"),
	KEY_USAGE_MASK_CERT_SIGN		("00001000", "Certificate Sign"),
	KEY_USAGE_MASK_CRL_SIGN			("00002000", "CRL Sign"),
	KEY_USAGE_MASK_AUTHENTICATE		("00100000", "Authenticate"),
	KEY_USAGE_MASK_UNRESTRICTED		("00200000", "Unrestricted"),
	KEY_USAGE_MASK_FPE_ENCRYPT		("00400000", "FPE Encrypt"),
	KEY_USAGE_MASK_FPE_DECRYPT		("00800000", "FPE Decrypt");
	
	private final String key;
	private final String value;
	
	EnumKeyUsageMask(String key, String value) {
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
