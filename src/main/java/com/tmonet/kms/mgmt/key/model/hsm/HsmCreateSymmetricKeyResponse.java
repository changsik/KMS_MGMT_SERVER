package com.tmonet.kms.mgmt.key.model.hsm;

import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;

public class HsmCreateSymmetricKeyResponse extends BaseResponse {
	
	public HsmCreateSymmetricKeyResponse() {
		
	}

	public HsmCreateSymmetricKeyResponse(BaseRequest request) {
		super(request);
	}

	private String keyId;

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tkeyId=");
		builder.append(keyId);
		builder.append("\n]");
		return builder.toString();
	}

}
