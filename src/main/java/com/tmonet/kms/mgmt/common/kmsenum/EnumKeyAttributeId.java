package com.tmonet.kms.mgmt.common.kmsenum;

//키 속성 고유식별번호
public enum EnumKeyAttributeId{
	
	KEY_ATTR_ID_KEY_OBJ_TYPE	("keyObjectType", "키 객체 유형"),
	KEY_ATTR_ID_CRYPTO_ALG		("cryptographicAlgorithm", "암호 알고리즘"),
	KEY_ATTR_ID_KEY_LENGTH		("keyLength", "키의 길이 (단위: bit)"),
	KEY_ATTR_ID_KEY_USAGE_MASK	("keyUsageMask", "키의 용도"),
	KEY_ATTR_ID_KEY_LIFETIME	("keyLifetime", "키의 수명 (단위: Day)"),
	KEY_ATTR_ID_KEY_ACT_TYPE	("keyActivationType", "키 활성화 유형"),
	KEY_ATTR_ID_EXPORT_KEY		("canExportKey", "키 내보내기 가능여부"),
	KEY_ATTR_ID_IMPORT_KEY		("canImportKey", "외부에서 생성된 키 주입 가능여부"),
	KEY_ATTR_ID_CURVE			("curve", "EC 알고리즘에서 사용되는 Recommended Curve"),
	KEY_ATTR_ID_KEY_LABEL		("keyLabel", "키 라벨"),
	KEY_ATTR_ID_KEY_PAIR_ID		("keyPairId", "키 쌍 ID"),
	KEY_ATTR_ID_PUB_EXPONENT	("publicExponent", "Public Exponent");
	
	private final String key;
	private final String value;
	
	EnumKeyAttributeId(String key, String value) {
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
