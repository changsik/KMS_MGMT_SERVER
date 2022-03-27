package com.tmonet.kms.mgmt.operation.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SignResponse extends BaseResponse {

	public SignResponse(BaseRequest request) {
		super(request);
	}

	private Object signature;

	public Object getSignature() {
		return signature;
	}

	public void setSignature(Object signature) {
		this.signature = signature;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tsignature=");
		builder.append(signature);
		builder.append("\n]");
		return builder.toString();
	}

}
