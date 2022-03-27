package com.tmonet.kms.mgmt.key.model;

import javax.validation.constraints.NotNull;

import com.tmonet.kms.mgmt.common.model.BaseRequest;

public class ImportAsymmetricKeyRequest extends BaseRequest {

	@NotNull
	private String serviceId;

	@NotNull
	private String keyProfileId;

	private String keyLabel;

	@NotNull
	private String keyFormatType;

	private Object asymmetricKeyData;

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getKeyProfileId() {
		return keyProfileId;
	}

	public void setKeyProfileId(String keyProfileId) {
		this.keyProfileId = keyProfileId;
	}

	public String getKeyLabel() {
		return keyLabel;
	}

	public void setKeyLabel(String keyLabel) {
		this.keyLabel = keyLabel;
	}

	public String getKeyFormatType() {
		return keyFormatType;
	}

	public void setKeyFormatType(String keyFormatType) {
		this.keyFormatType = keyFormatType;
	}

	public Object getAsymmetricKeyData() {
		return asymmetricKeyData;
	}

	public void setAsymmetricKeyData(Object asymmetricKeyData) {
		this.asymmetricKeyData = asymmetricKeyData;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tserviceId=");
		builder.append(serviceId);
		builder.append(", \n\tkeyProfileId=");
		builder.append(keyProfileId);
		builder.append(", \n\tkeyLabel=");
		builder.append(keyLabel);
		builder.append(", \n\tkeyFormatType=");
		builder.append(keyFormatType);
		builder.append(", \n\tasymmetricKeyData=");
		builder.append(asymmetricKeyData);
		builder.append("\n]");
		return builder.toString();
	}

}
