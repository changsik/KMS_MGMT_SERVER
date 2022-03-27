package com.tmonet.kms.mgmt.key.model.hsm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.object.CryptographicParameter;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HsmImportSymmetricKeyRequest extends BaseRequest {

	private String partitionId;
	private String cryptographicAlgorithm;
	private String symmetricKeyData;
	private String keyEncryptionKeyId;
	private CryptographicParameter cryptographicParameter;

	public String getPartitionId() {
		return partitionId;
	}

	public void setPartitionId(String partitionId) {
		this.partitionId = partitionId;
	}

	public String getCryptographicAlgorithm() {
		return cryptographicAlgorithm;
	}

	public void setCryptographicAlgorithm(String cryptographicAlgorithm) {
		this.cryptographicAlgorithm = cryptographicAlgorithm;
	}

	public String getSymmetricKeyData() {
		return symmetricKeyData;
	}

	public void setSymmetricKeyData(String symmetricKeyData) {
		this.symmetricKeyData = symmetricKeyData;
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
		builder.append(", \n\tcryptographicAlgorithm=");
		builder.append(cryptographicAlgorithm);
		builder.append(", \n\tsymmetricKeyData=");
		builder.append(symmetricKeyData);
		builder.append(", \n\tkeyEncryptionKeyId=");
		builder.append(keyEncryptionKeyId);
		builder.append(", \n\tcryptographicParameter=");
		builder.append(cryptographicParameter);
		builder.append("\n]");
		return builder.toString();
	}

}
