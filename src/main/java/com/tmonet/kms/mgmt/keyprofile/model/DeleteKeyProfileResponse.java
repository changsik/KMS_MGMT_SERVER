package com.tmonet.kms.mgmt.keyprofile.model;

import javax.validation.constraints.NotNull;

import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;

public class DeleteKeyProfileResponse extends BaseResponse {

	@NotNull
	private String keyProfileId;

	public DeleteKeyProfileResponse(BaseRequest request) {
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
		builder.append(", \n\tkeyProfileId=");
		builder.append(keyProfileId);
		builder.append("\n]");
		return builder.toString();
	}

}
