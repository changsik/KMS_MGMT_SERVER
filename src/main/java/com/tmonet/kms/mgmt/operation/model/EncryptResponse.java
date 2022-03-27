package com.tmonet.kms.mgmt.operation.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EncryptResponse extends BaseResponse {

	public EncryptResponse(BaseRequest request) {
		super(request);
	}

	private String ciphertext;

	public String getCiphertext() {
		return ciphertext;
	}

	public void setCiphertext(String ciphertext) {
		this.ciphertext = ciphertext;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tciphertext=");
		builder.append(ciphertext);
		builder.append("\n]");
		return builder.toString();
	}

}
