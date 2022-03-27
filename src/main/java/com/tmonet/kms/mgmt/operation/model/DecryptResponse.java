package com.tmonet.kms.mgmt.operation.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DecryptResponse extends BaseResponse {

	public DecryptResponse(BaseRequest request) {
		super(request);
	}

	private String plaintext;

	public String getPlaintext() {
		return plaintext;
	}

	public void setPlaintext(String plaintext) {
		this.plaintext = plaintext;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tplaintext=");
		builder.append(plaintext);
		builder.append("\n]");
		return builder.toString();
	}

}
