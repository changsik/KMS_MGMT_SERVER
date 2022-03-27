package com.tmonet.kms.mgmt.common.kmsenum;

//전자서명 알고리즘
public enum EnumDigitalSignatureAlgorithm{
	
	DGT_SGN_ALG_MD2_RSA			("00000001", "MD2 with RSA Encryption"),
	DGT_SGN_ALG_MD5_RSA			("00000002", "MD5 with RSA Encryption"),
	DGT_SGN_ALG_SHA_1_RSA		("00000003", "SHA-1 with RSA Encryption"),
	DGT_SGN_ALG_SHA_224_RSA		("00000004", "SHA-224 with RSA Encryption"),
	DGT_SGN_ALG_SHA_256_RSA		("00000005", "SHA-256 with RSA Encryption"),
	DGT_SGN_ALG_SHA_384_RSA		("00000006", "SHA-384 with RSA Encryption"),
	DGT_SGN_ALG_SHA_512_RSA		("00000007", "SHA-512 with RSA Encryption"),
	DGT_SGN_ALG_RSASSA_PSS		("00000008", "RSASSA_PSS"),
	DGT_SGN_ALG_DSA_SHA_1		("00000009", "DSA with SHA-1"),
	DGT_SGN_ALG_DSA_SHA224		("0000000A", "DSA with SHA224"),
	DGT_SGN_ALG_DSA_SHA256		("0000000B", "DSA with SHA256"),
	DGT_SGN_ALG_ECDSA_SHA_1		("0000000C", "ECDSA with SHA-1"),
	DGT_SGN_ALG_ECDSA_SHA224	("0000000D", "ECDSA with SHA224"),
	DGT_SGN_ALG_ECDSA_SHA256	("0000000E", "ECDSA with SHA256"),
	DGT_SGN_ALG_ECDSA_SHA384	("0000000F", "ECDSA with SHA384"),
	DGT_SGN_ALG_ECDSA_SHA512	("00000010", "ECDSA with SHA512"),
	DGT_SGN_ALG_SHA3_256_RSA	("00000011", "SHA3-256 with RSA Encryption"),
	DGT_SGN_ALG_SHA3_384_RSA	("00000012", "SHA3-384 with RSA Encryption"),
	DGT_SGN_ALG_SHA3_512_RSA	("00000013", "SHA3-512 with RSA Encryption");
	
	private final String key;
	private final String value;
	
	EnumDigitalSignatureAlgorithm(String key, String value) {
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
