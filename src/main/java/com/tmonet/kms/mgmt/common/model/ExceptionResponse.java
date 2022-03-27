package com.tmonet.kms.mgmt.common.model;

import com.tmonet.kmip.kmipenum.EnumResultReason;
import com.tmonet.kmip.kmipenum.EnumResultStatus;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;

public class ExceptionResponse extends BaseResponse {

	public ExceptionResponse(BaseRequest request) {
		super(request);
		this.getResult().setResultStatus(EnumResultStatus.OperationFailed);
		this.getResult().setErrorCode(EnumResultReason.InternalServerError);
		this.getResult().setErrorMessage("Internal Server Error");
	}

	public ExceptionResponse(BaseRequest request, Throwable e) {
		super(request);
		this.getResult().setResultStatus(EnumResultStatus.OperationFailed);
		this.getResult().setErrorCode(EnumResultReason.InternalServerError);
		this.getResult().setErrorMessage("Internal Server Error: " + e.getMessage());
	}

	public ExceptionResponse(BaseRequest request, int code, String message) {
		super(request);
		this.getResult().setResultStatus(EnumResultStatus.OperationFailed);
		this.getResult().setErrorCode(code);
		this.getResult().setErrorMessage(message);
	}

	public ExceptionResponse(BaseRequest request, int code, Throwable e) {
		super(request);
		this.getResult().setResultStatus(EnumResultStatus.OperationFailed);
		this.getResult().setErrorCode(code);
		this.getResult().setErrorMessage(e.getMessage() + " [" + e.getClass().getSimpleName() + "]");
	}
	
	public ExceptionResponse(BaseRequest request, KMSErrorCode error) {
		super(request);
		this.getResult().setResultStatus(EnumResultStatus.OperationFailed);
		this.getResult().setErrorCode(error.getCode());
		this.getResult().setErrorMessage(error.getMessage());
	}
}
