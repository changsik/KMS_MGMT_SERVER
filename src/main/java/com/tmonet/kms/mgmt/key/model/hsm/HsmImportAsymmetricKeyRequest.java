package com.tmonet.kms.mgmt.key.model.hsm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HsmImportAsymmetricKeyRequest extends BaseRequest {

	private String partitionId;
	private String cryptographicAlgorithm;
	private int keyLength;
	private String keyFormatType;
	private Object asymmetricKeyData;

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

	public int getKeyLength() {
		return keyLength;
	}

	public void setKeyLength(int keyLength) {
		this.keyLength = keyLength;
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
		builder.append("\n\tpartitionId=");
		builder.append(partitionId);
		builder.append(", \n\tcryptographicAlgorithm=");
		builder.append(cryptographicAlgorithm);
		builder.append(", \n\tkeyLength=");
		builder.append(keyLength);
		builder.append(", \n\tkeyFormatType=");
		builder.append(keyFormatType);
		builder.append(", \n\tasymmetricKeyData=");
		builder.append(asymmetricKeyData);
		builder.append("\n]");
		return builder.toString();
	}

}
