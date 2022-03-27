package com.tmonet.kms.mgmt.key.model;

import javax.validation.constraints.NotNull;

import com.tmonet.kms.mgmt.common.model.BaseRequest;

public class CreateAsymmetricKeyRequest extends BaseRequest {

	@NotNull
	private String serviceId;

	@NotNull
	private String publicKeyProfileId;

	@NotNull
	private String privateKeyProfileId;

	private String keyLabel;

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getPublicKeyProfileId() {
		return publicKeyProfileId;
	}

	public void setPublicKeyProfileId(String publicKeyProfileId) {
		this.publicKeyProfileId = publicKeyProfileId;
	}

	public String getPrivateKeyProfileId() {
		return privateKeyProfileId;
	}

	public void setPrivateKeyProfileId(String privateKeyProfileId) {
		this.privateKeyProfileId = privateKeyProfileId;
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
		builder.append(", \n\tpublicKeyProfileId=");
		builder.append(publicKeyProfileId);
		builder.append(", \n\tprivateKeyProfileId=");
		builder.append(privateKeyProfileId);
		builder.append(", \n\tkeyLabel=");
		builder.append(keyLabel);
		builder.append("\n]");
		return builder.toString();
	}

}
