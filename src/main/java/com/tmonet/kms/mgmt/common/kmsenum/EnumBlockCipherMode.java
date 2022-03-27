package com.tmonet.kms.mgmt.common.kmsenum;

//블록 암호 운영 모드
public enum EnumBlockCipherMode{
	
	BLOCK_CIPHER_MODE_CBC			("00000001", "CBC"),
	BLOCK_CIPHER_MODE_ECB			("00000002", "ECB"),
	BLOCK_CIPHER_MODE_PCBC			("00000003", "PCBC"),
	BLOCK_CIPHER_MODE_CFB			("00000004", "CFB"),
	BLOCK_CIPHER_MODE_OFB			("00000005", "OFB"),
	BLOCK_CIPHER_MODE_CTR			("00000006", "CTR"),
	BLOCK_CIPHER_MODE_CMAC			("00000007", "CMAC"),
	BLOCK_CIPHER_MODE_CCM			("00000008", "CCM"),
	BLOCK_CIPHER_MODE_GCM			("00000009", "GCM"),
	BLOCK_CIPHER_MODE_CBC_MAC		("0000000A", "CBC_MAC"),
	BLOCK_CIPHER_MODE_XTS			("0000000B", "XTS"),
	BLOCK_CIPHER_MODE_AES_WRAP_PAD	("0000000C", "AESKeyWrapPadding"),
	BLOCK_CIPHER_MODE_NISTKey_WRAP	("0000000D", "NISTKeyWrap"),
	BLOCK_CIPHER_MODE_X9_102_AESKW	("0000000E", "X9.102 AESKW"),
	BLOCK_CIPHER_MODE_X9_102_TDKW	("0000000F", "X9.102 TDKW"),
	BLOCK_CIPHER_MODE_X9_102_AKW1	("00000010", "X9.102 AKW1"),
	BLOCK_CIPHER_MODE_X9_102_AKW2	("00000011", "X9.102 AKW2"),
	BLOCK_CIPHER_MODE_AEAD			("00000012", "AEAD");
	
	private final String key;
	private final String value;
	
	EnumBlockCipherMode(String key, String value) {
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
