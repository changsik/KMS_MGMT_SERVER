package com.tmonet.kms.mgmt.operation.model.hsm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.object.CryptographicParameter;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HsmVerifyRequest extends BaseRequest {

	private String partitionId;
	private String keyId;
	private String plaintext;
	private Object signature;
	private CryptographicParameter cryptographicParameter;

	public String getPartitionId() {
		return partitionId;
	}

	public void setPartitionId(String partitionId) {
		this.partitionId = partitionId;
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
		builder.append("\n\tpartitionId=");
		builder.append(partitionId);
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
