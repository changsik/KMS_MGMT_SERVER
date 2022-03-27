package com.tmonet.kms.mgmt.common.kmsenum;

public enum EnumHistoryOperationCode {

	HIST_OP_CODE_CREATE_HSM					("00000001", "Create HSM"),
	HIST_OP_CODE_UPDATE_HSM					("00000002", "Update HSM"),
	HIST_OP_CODE_DELETE_HSM					("00000003", "Delete HSM"),
	HIST_OP_CODE_CREATE_HSM_GROUP			("00000004", "Create HSM Group"),
	HIST_OP_CODE_UPDATE_HSM_GROUP			("00000005", "Update HSM Group"),
	HIST_OP_CODE_DELETE_HSM_GROUP			("00000006", "Delete HSM Group"),
	HIST_OP_CODE_CREATE_PARTITION			("00000007", "Create Partition"),
	HIST_OP_CODE_UPDATE_PARTITION			("00000008", "Update Partition"),
	HIST_OP_CODE_DELETE_PARTITION			("00000009", "Delete Partition"),
	HIST_OP_CODE_CREATE_HSM_CTL_SVR			("0000000A", "Create HSM Control Server"),
	HIST_OP_CODE_UPDATE_HSM_CTL_SVR			("0000000B", "Update HSM Control Server"),
	HIST_OP_CODE_DELETE_HSM_CTL_SVR			("0000000C", "Delete HSM Control Server"),
	HIST_OP_CODE_CREATE_HSM_CTL_LIST		("0000000D", "Create HSM Control List"),
	HIST_OP_CODE_UPDATE_HSM_CTL_LIST		("0000000E", "Update HSM Control List"),
	HIST_OP_CODE_DELETE_HSM_CTL_LIST		("0000000F", "Delete HSM Control List"),
	HIST_OP_CODE_CREATE_KEY_PROFILE			("00000010", "Create KeyProfile"),
	HIST_OP_CODE_UPDATE_KEY_PROFILE			("00000011", "Update KeyProfile"),
	HIST_OP_CODE_DELETE_KEY_PROFILE			("00000012", "Delete KeyProfile"),
	HIST_OP_CODE_CREATE_CLIENT				("00000013", "Create Client"),
	HIST_OP_CODE_AUTH_CLIENT				("00000014", "Authenticate Client"),
	HIST_OP_CODE_UPDATE_CLIENT				("00000015", "Update Client"),
	HIST_OP_CODE_DELETE_CLIENT				("00000016", "Delete Client"),
	HIST_OP_CODE_CREATE_MANAGER				("00000017", "Create Manager"),
	HIST_OP_CODE_LOGIN_MANAGER				("00000018", "Login Manager"),
	HIST_OP_CODE_CHANGE_PASSWORD			("00000019", "Change Password"),
	HIST_OP_CODE_UPDATE_MANAGER				("0000001A", "Update Manager"),
	HIST_OP_CODE_DELETE_MANAGER				("0000001B", "Delete Manager"),
	HIST_OP_CODE_CREATE_KEY_PROFILE_ATTR	("0000001C", "Create KeyProfile Attribute List"),
	HIST_OP_CODE_UPDATE_KEY_PROFILE_ATTR	("0000001D", "Update KeyProfile Attribute List"),
	HIST_OP_CODE_DELETE_KEY_PROFILE_ATTR	("0000001E", "Delete KeyProfile Attribute List"),
	HIST_OP_CODE_CREATE_MNG_AUTH			("0000001F", "Create Manager Auth"),
	HIST_OP_CODE_UPDATE_MNG_AUTH			("00000020", "Update Manager Auth"),
	HIST_OP_CODE_DELETE_MNG_AUTH			("00000021", "Delete Manager Auth"),
	HIST_OP_CODE_CREATE_CLI_AUTH			("00000022", "Create Client Auth"),
	HIST_OP_CODE_UPDATE_CLI_AUTH			("00000023", "Update Client Auth"),
	HIST_OP_CODE_DELETE_CLI_AUTH			("00000024", "Delete Client Auth");
	
	private final String key;
	private final String value;
	
	EnumHistoryOperationCode(String key, String value) {
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
