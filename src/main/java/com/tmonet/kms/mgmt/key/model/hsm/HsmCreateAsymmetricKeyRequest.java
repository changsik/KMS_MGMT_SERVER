package com.tmonet.kms.mgmt.key.model.hsm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HsmCreateAsymmetricKeyRequest extends BaseRequest {

	private String partitionId;
	private String cryptographicAlgorithm;
	private int keyLength;
	private String publicExponent;
	private String curve;

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

	public String getPublicExponent() {
		return publicExponent;
	}

	public void setPublicExponent(String publicExponent) {
		this.publicExponent = publicExponent;
	}

	public String getCurve() {
		return curve;
	}

	public void setCurve(String curve) {
		this.curve = curve;
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
		builder.append(", \n\tpublicExponent=");
		builder.append(publicExponent);
		builder.append(", \n\tcurve=");
		builder.append(curve);
		builder.append("\n]");
		return builder.toString();
	}

}
