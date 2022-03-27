package com.tmonet.kms.mgmt.operation.model.hsm;

import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;

public class HsmVerifyResponse extends BaseResponse {

	public HsmVerifyResponse() {

	}

	public HsmVerifyResponse(BaseRequest request) {
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
