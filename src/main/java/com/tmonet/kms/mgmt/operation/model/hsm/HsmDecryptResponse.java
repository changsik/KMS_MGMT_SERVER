package com.tmonet.kms.mgmt.operation.model.hsm;

import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;

public class HsmDecryptResponse extends BaseResponse {

	public HsmDecryptResponse() {

	}

	public HsmDecryptResponse(BaseRequest request) {
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
