package com.tmonet.kms.mgmt.common.kmsenum;

//해시 알고리즘
public enum EnumHashingAlgorithm{
	
	HASH_ALG_MD2			("00000001", "MD2"),
	HASH_ALG_MD4			("00000002", "MD4"),
	HASH_ALG_MD5			("00000003", "MD5"),
	HASH_ALG_SHA_1			("00000004", "SHA-1"),
	HASH_ALG_SHA_224		("00000005", "SHA-224"),
	HASH_ALG_SHA_256		("00000006", "SHA-256"),
	HASH_ALG_SHA_384		("00000007", "SHA-384"),
	HASH_ALG_SHA_512		("00000008", "SHA-512"),
	HASH_ALG_RIPEMD_160		("00000009", "RIPEMD-160"),
	HASH_ALG_TIGER			("0000000A", "Tiger"),
	HASH_ALG_WHIRLPOOL		("0000000B", "Whirlpool"),
	HASH_ALG_SHA_512_224	("0000000C", "SHA-512/224"),
	HASH_ALG_SHA_512_256	("0000000D", "SHA-512/256"),
	HASH_ALG_SHA3_224		("0000000E", "SHA3-224"),
	HASH_ALG_SHA3_256		("0000000F", "SHA3-256"),
	HASH_ALG_SHA3_384		("00000010", "SHA3-384"),
	HASH_ALG_SHA3_512		("00000011", "SHA3-512");
	
	private final String key;
	private final String value;
	
	EnumHashingAlgorithm(String key, String value) {
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
