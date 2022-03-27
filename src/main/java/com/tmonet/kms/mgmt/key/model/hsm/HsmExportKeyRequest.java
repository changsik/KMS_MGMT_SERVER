package com.tmonet.kms.mgmt.key.model.hsm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.object.CryptographicParameter;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HsmExportKeyRequest extends BaseRequest {

	private String partitionId;
	private String keyId;
	private String wrappingMethod;
	private String keyEncryptionKeyId;
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
		builder.append("\n\tpartitionId=");
		builder.append(partitionId);
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
