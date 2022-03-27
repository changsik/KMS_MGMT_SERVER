package com.tmonet.kms.mgmt.keyprofile.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UpdateKeyProfileResponse extends BaseResponse {

	private String keyProfileId;

	public UpdateKeyProfileResponse(BaseRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	public String getKeyProfileId() {
		return keyProfileId;
	}

	public void setKeyProfileId(String keyProfileId) {
		this.keyProfileId = keyProfileId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tkeyProfileId=");
		builder.append(keyProfileId);
		builder.append("\n]");
		return builder.toString();
	}

}
