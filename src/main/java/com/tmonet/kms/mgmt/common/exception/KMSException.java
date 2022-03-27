package com.tmonet.kms.mgmt.common.exception;

public class KMSException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private KMSErrorCode code;

	public KMSException(KMSErrorCode code) {
		super(code.getMessage());
		this.code = code;
	}

	public KMSException(String message, KMSErrorCode code) {
		super(message);
		this.code = code;
	}

	public KMSException(String message, Throwable cause, KMSErrorCode code) {
		super(message, cause);
		this.code = code;
	}

	public KMSException(Throwable cause, KMSErrorCode code) {
		super(cause);
		this.code = code;
	}

	public KMSErrorCode getCode() {
		return this.code;
	}

}
