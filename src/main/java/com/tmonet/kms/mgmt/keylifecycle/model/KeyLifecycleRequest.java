package com.tmonet.kms.mgmt.keylifecycle.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class KeyLifecycleRequest extends BaseRequest {

	private String clientKeyId;
	private String keyId;
	private String partitionId;
	private String hsmGroupId;

	public String getClientKeyId() {
		return clientKeyId;
	}

	public void setClientKeyId(String clientKeyId) {
		this.clientKeyId = clientKeyId;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getPartitionId() {
		return partitionId;
	}

	public void setPartitionId(String partitionId) {
		this.partitionId = partitionId;
	}

	public String getHsmGroupId() {
		return hsmGroupId;
	}

	public void setHsmGroupId(String hsmGroupId) {
		this.hsmGroupId = hsmGroupId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tclientKeyId=");
		builder.append(clientKeyId);
		builder.append("\n\tkeyId=");
		builder.append(keyId);
		builder.append("\n\tpartitionId=");
		builder.append(partitionId);
		builder.append("\n\thsmGroupId=");
		builder.append(hsmGroupId);
		builder.append("\n]");
		return builder.toString();
	}

}
