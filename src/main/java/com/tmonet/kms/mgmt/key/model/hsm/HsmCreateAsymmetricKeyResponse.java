package com.tmonet.kms.mgmt.key.model.hsm;

import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;

public class HsmCreateAsymmetricKeyResponse extends BaseResponse {

	public HsmCreateAsymmetricKeyResponse() {

	}

	public HsmCreateAsymmetricKeyResponse(BaseRequest request) {
		super(request);
	}

	private String privateKeyId;
	private String publicKeyId;
	private Object publicKeyData;

	public String getPrivateKeyId() {
		return privateKeyId;
	}

	public void setPrivateKeyId(String privateKeyId) {
		this.privateKeyId = privateKeyId;
	}

	public String getPublicKeyId() {
		return publicKeyId;
	}

	public void setPublicKeyId(String publicKeyId) {
		this.publicKeyId = publicKeyId;
	}

	public Object getPublicKeyData() {
		return publicKeyData;
	}

	public void setPublicKeyData(Object publicKeyData) {
		this.publicKeyData = publicKeyData;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tprivateKeyId=");
		builder.append(privateKeyId);
		builder.append("\n\tpublicKeyId=");
		builder.append(publicKeyId);
		builder.append("\n]");
		return builder.toString();
	}

}
