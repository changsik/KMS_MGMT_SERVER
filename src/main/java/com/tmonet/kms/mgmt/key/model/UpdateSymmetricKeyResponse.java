package com.tmonet.kms.mgmt.key.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UpdateSymmetricKeyResponse extends BaseResponse {

	public UpdateSymmetricKeyResponse(BaseRequest request) {
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
