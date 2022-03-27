package com.tmonet.kms.mgmt.key.model;

import javax.validation.constraints.NotNull;

import com.tmonet.kms.mgmt.common.model.BaseRequest;

public class UpdateAsymmetricKeyRequest extends BaseRequest {

	@NotNull
	private String serviceId;

	@NotNull
	private String privateKeyId;

	@NotNull
	private String publicKeyId;

	private String keyLabel;

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

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

	public String getKeyLabel() {
		return keyLabel;
	}

	public void setKeyLabel(String keyLabel) {
		this.keyLabel = keyLabel;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tserviceId=");
		builder.append(serviceId);
		builder.append(", \n\tprivateKeyId=");
		builder.append(privateKeyId);
		builder.append(", \n\tpublicKeyId=");
		builder.append(publicKeyId);
		builder.append(", \n\tkeyLabel=");
		builder.append(keyLabel);
		builder.append("\n]");
		return builder.toString();
	}

}
