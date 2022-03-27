package com.tmonet.kms.mgmt.common.kmsenum;

//키 관리 코드
public enum EnumOperationCode {

	OP_CODE_CREATE					("00000001", "Create"),
	OP_CODE_CREATE_KEY_PAIR			("00000002", "Create Key Pair"),
	OP_CODE_REGISTER				("00000003", "Register"),
	OP_CODE_RE_KEY					("00000004", "Re-key"),
	OP_CODE_DERIVE_KEY				("00000005", "Derive Key"),
	OP_CODE_CERTIFY					("00000006", "Certify"),
	OP_CODE_RE_CERTIFY				("00000007", "Re-certify"),
	OP_CODE_LOCATE					("00000008", "Locate"),
	OP_CODE_CHECK					("00000009", "Check"),
	OP_CODE_GET						("0000000A", "Get"),
	OP_CODE_GET_ATTR				("0000000B", "Get Attributes"),
	OP_CODE_GET_ATTR_LIST			("0000000C", "Get Attribute List"),
	OP_CODE_ADD_ATTR				("0000000D", "Add Attribute"),
	OP_CODE_MODIFY_ATTR				("0000000E", "Modify Attribute"),
	OP_CODE_DELETE_ATTR				("0000000F", "Delete Attribute"),
	OP_CODE_OBTAIN_LEASE			("00000010", "Obtain Lease"),
	OP_CODE_GET_USAGE_ALLOCATION	("00000011", "Get Usage Allocation"),
	OP_CODE_ACTIVATE				("00000012", "Activate"),
	OP_CODE_REVOKE					("00000013", "Revoke"),
	OP_CODE_DESTROY					("00000014", "Destroy"),
	OP_CODE_ARCHIVE					("00000015", "Archive"),
	OP_CODE_RECOVER					("00000016", "Recover"),
	OP_CODE_VALIDATE				("00000017", "Validate"),
	OP_CODE_QUERY					("00000018", "Query"),
	OP_CODE_CANCEL					("00000019", "Cancel"),
	OP_CODE_POLL					("0000001A", "Poll"),
	OP_CODE_NOTIFY					("0000001B", "Notify"),
	OP_CODE_PUT						("0000001C", "Put"),
	OP_CODE_RE_KEY_KEY_PAIR			("0000001D", "Re-key Key Pair"),
	OP_CODE_DISCOVER_VERSIONS		("0000001E", "Discover Versions"),
	OP_CODE_ENCRYPT					("0000001F", "Encrypt"),
	OP_CODE_DECRYPT					("00000020", "Decrypt"),
	OP_CODE_SIGN					("00000021", "Sign"),
	OP_CODE_SIGNATURE_VERIFY		("00000022", "Signature Verify"),
	OP_CODE_MAC						("00000023", "MAC"),
	OP_CODE_MAC_VERIFY				("00000024", "MAC Verify"),
	OP_CODE_RNG_RETRIEVE			("00000025", "RNG_RETRIEVE"),
	OP_CODE_RNG_SEED				("00000026", "RNG Seed"),
	OP_CODE_HASH					("00000027", "Hash"),
	OP_CODE_CREATE_SPLIT_KEY		("00000028", "Create Split Key"),
	OP_CODE_JOIN_SPLIT_KEY			("00000029", "Join Split Key"),
	OP_CODE_IMPORT					("0000002A", "Import"),
	OP_CODE_EXPORT					("0000002B", "Export"),
	OP_CODE_LOG						("0000002C", "Log"),
	OP_CODE_LOGIN					("0000002D", "Login"),
	OP_CODE_LOGOUT					("0000002E", "Logout"),
	OP_CODE_DELEGATED_LOGIN			("0000002F", "Delegated Login"),
	OP_CODE_ADJUST_ATTR				("00000030", "Adjust Attribute"),
	OP_CODE_SET_ATTR				("00000031", "Set Attribute"),
	OP_CODE_SET_ENDPOINT_ROLE		("00000032", "Set Endpoint Role"),
	OP_CODE_PKCS_11					("00000033", "PKCS#11"),
	OP_CODE_INTEROP					("00000034", "Interop"),
	OP_CODE_RE_PROVISION			("00000035", "Re-Provision"),
	OP_CODE_PRE_ACTIVE				("80000001", "Pre-Active"),
	OP_CODE_ACTIVE					("80000002", "Active"),
	OP_CODE_DEACTIVATED				("80000003", "Deactivated"),
	OP_CODE_COMPROMISED				("80000004", "Compromised"),
	OP_CODE_DESTROYED				("80000005", "Destroyed"),
	OP_CODE_DESTROYED_COMPROMISED	("80000006", "Destroyed Compromised");
	
	private final String key;
	private final String value;
	
	EnumOperationCode(String key, String value) {
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
