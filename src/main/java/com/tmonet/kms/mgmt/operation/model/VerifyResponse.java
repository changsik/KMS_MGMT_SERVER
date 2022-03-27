package com.tmonet.kms.mgmt.operation.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VerifyResponse extends BaseResponse {

	public VerifyResponse(BaseRequest request) {
		super(request);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n]");
		return builder.toString();
	}

}
