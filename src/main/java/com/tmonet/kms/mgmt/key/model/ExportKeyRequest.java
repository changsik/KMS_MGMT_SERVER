package com.tmonet.kms.mgmt.key.model;

import javax.validation.constraints.NotNull;

import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.object.CryptographicParameter;

public class ExportKeyRequest extends BaseRequest {

	@NotNull
	private String serviceId;

	@NotNull
	private String keyId;

	private String wrappingMethod;

	@NotNull
	private String keyEncryptionKeyId;

	@NotNull
	private CryptographicParameter cryptographicParameter;

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

	public String getWrappingMethod() {
		return wrappingMethod;
	}

	public void setWrappingMethod(String wrappingMethod) {
		this.wrappingMethod = wrappingMethod;
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
		builder.append(", \n\tkeyId=");
		builder.append(keyId);
		builder.append(", \n\twrappingMethod=");
		builder.append(wrappingMethod);
		builder.append(", \n\tkeyEncryptionKeyId=");
		builder.append(keyEncryptionKeyId);
		builder.append(", \n\tcryptographicParameter=");
		builder.append(cryptographicParameter);
		builder.append("\n]");
		return builder.toString();
	}

}
