package com.tmonet.kms.mgmt.common.exception;

public enum KMSErrorCode {
	// http status error
	BAD_REQUEST("400", "Bad request"),
	UNAUTHORIZED("401", "Unauthorized"),
	METHOD_NOT_ALLOWED("405", "Method not allowed"),
	INTERNAL_SERVER_ERROR("500", "Internal server error"),
	// kms error
	AUTHENTICATION_NOT_SUCCESSFUL("00000003", "Authentication not successful"),
	OPERATION_NOT_SUPPORTED("00000005", "Operation not supported"),
	UNKNOWN_ERROR("80000000", "Unknown Error"),
	INVALID_PARAMETER("80000001", "Invalid parameter"),
	UNAVAILABLE_STATUS("80000002", "Unavailable status"),
	HSM_SERVER_ERROR("80000003", "HSM server error"),
	PUBLIC_KEY_DATA_PARSING_FAILED("80000004", "Public key data parsing failed"),
	DATABASE_CONNECTION_FAILURE("81000001", "Database connection failure"),
	DATABASE_SELECT_FAILURE("81000002", "Database select failure"),
	DATABASE_INSERT_FAILURE("81000003", "Database insert failure"),
	DATABASE_UPDATE_FAILURE("81000004", "Database update failure"),
	DATABASE_DELETE_FAILURE("81000005", "Database delete failure");

	private String code = null;
	private String message = null;

	private KMSErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}

}
