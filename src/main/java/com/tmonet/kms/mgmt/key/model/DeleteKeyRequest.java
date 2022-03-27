package com.tmonet.kms.mgmt.key.model;

import javax.validation.constraints.NotNull;

import com.tmonet.kms.mgmt.common.model.BaseRequest;

public class DeleteKeyRequest extends BaseRequest {

	@NotNull
	private String serviceId;

	@NotNull
	private String keyId;

	private String keyLabel;

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
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
		builder.append(", \n\tkeyId=");
		builder.append(keyId);
		builder.append(", \n\tkeyLabel=");
		builder.append(keyLabel);
		builder.append("\n]");
		return builder.toString();
	}

}
