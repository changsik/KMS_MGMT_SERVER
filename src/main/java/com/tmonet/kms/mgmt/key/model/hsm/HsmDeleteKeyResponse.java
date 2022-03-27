package com.tmonet.kms.mgmt.key.model.hsm;

import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;

public class HsmDeleteKeyResponse extends BaseResponse {

	public HsmDeleteKeyResponse() {

	}

	public HsmDeleteKeyResponse(BaseRequest request) {
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
