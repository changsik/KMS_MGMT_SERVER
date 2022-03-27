package com.tmonet.kms.mgmt.keylifecycle.model;

import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;

public class KeyLifecycleResponse extends BaseResponse {

	private String keyLifecycleState;

	public KeyLifecycleResponse(BaseRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	public KeyLifecycleResponse(BaseRequest request, String keyLifecycleState) {
		super(request);
		this.keyLifecycleState = keyLifecycleState;
	}

	public String getKeyLifecycleState() {
		return keyLifecycleState;
	}

	public void setKeyLifecycleState(String keyLifecycleState) {
		this.keyLifecycleState = keyLifecycleState;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tkeyLifecycleState=");
		builder.append(keyLifecycleState);
		builder.append("\n]");
		return builder.toString();
	}

}
