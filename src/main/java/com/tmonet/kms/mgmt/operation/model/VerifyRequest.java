package com.tmonet.kms.mgmt.operation.model;

import javax.validation.constraints.NotNull;

import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.object.CryptographicParameter;

public class VerifyRequest extends BaseRequest {

	@NotNull
	private String serviceId;

	@NotNull
	private String keyId;

	@NotNull
	private String plaintext;

	@NotNull
	private Object signature;

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

	public String getPlaintext() {
		return plaintext;
	}

	public void setPlaintext(String plaintext) {
		this.plaintext = plaintext;
	}

	public Object getSignature() {
		return signature;
	}

	public void setSignature(Object signature) {
		this.signature = signature;
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
		builder.append(", \n\tplaintext=");
		builder.append(plaintext);
		builder.append(", \n\tsignature=");
		builder.append(signature);
		builder.append(", \n\tcryptographicParameter=");
		builder.append(cryptographicParameter);
		builder.append("\n]");
		return builder.toString();
	}

}
