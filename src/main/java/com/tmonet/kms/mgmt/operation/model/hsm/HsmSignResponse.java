package com.tmonet.kms.mgmt.operation.model.hsm;

import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;

public class HsmSignResponse extends BaseResponse {

	public HsmSignResponse() {

	}

	public HsmSignResponse(BaseRequest request) {
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
