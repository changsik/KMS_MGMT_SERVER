package com.tmonet.kms.mgmt.operation.model.hsm;

import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;

public class HsmEncryptResponse extends BaseResponse {

	public HsmEncryptResponse() {

	}

	public HsmEncryptResponse(BaseRequest request) {
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
