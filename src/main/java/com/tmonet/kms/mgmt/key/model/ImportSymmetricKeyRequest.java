package com.tmonet.kms.mgmt.key.model;

import javax.validation.constraints.NotNull;

import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.object.CryptographicParameter;

public class ImportSymmetricKeyRequest extends BaseRequest {

	@NotNull
	private String serviceId;

	@NotNull
	private String keyProfileId;

	@NotNull
	private String symmetricKeyData;

	private String keyLabel;

	private String keyEncryptionKeyId;

	private CryptographicParameter cryptographicParameter;

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

	public String getSymmetricKeyData() {
		return symmetricKeyData;
	}

	public void setSymmetricKeyData(String symmetricKeyData) {
		this.symmetricKeyData = symmetricKeyData;
	}

	public String getKeyLabel() {
		return keyLabel;
	}

	public void setKeyLabel(String keyLabel) {
		this.keyLabel = keyLabel;
	}

	public String getKeyEncryptionKeyId() {
		return keyEncryptionKeyId;
	}

	public void setKeyEncryptionKeyId(String keyEncryptionKeyId) {
		this.keyEncryptionKeyId = keyEncryptionKeyId;
	}

	public CryptographicParameter getCryptographicParameter() {
		return cryptographicParameter;
	}

	public void setCryptographicParameter(CryptographicParameter cryptographicParameter) {
		this.cryptographicParameter = cryptographicParameter;
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
		builder.append(", \n\tsymmetricKeyData=");
		builder.append(symmetricKeyData);
		builder.append(", \n\tkeyLabel=");
		builder.append(keyLabel);
		builder.append(", \n\tkeyEncryptionKeyId=");
		builder.append(keyEncryptionKeyId);
		builder.append(", \n\tcryptographicParameter=");
		builder.append(cryptographicParameter);
		builder.append("\n]");
		return builder.toString();
	}

}
