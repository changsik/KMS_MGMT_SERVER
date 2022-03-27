package com.tmonet.kms.mgmt.client.model;

import com.tmonet.kms.mgmt.common.model.BaseRequest;

public class SelectClientKeyListRequest extends BaseRequest {

	private String keyType;
	private String usageStatus;

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public String getUsageStatus() {
		return usageStatus;
	}

	public void setUsageStatus(String usageStatus) {
		this.usageStatus = usageStatus;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tkeyType=");
		builder.append(keyType);
		builder.append("\n\tusageStatus=");
		builder.append(usageStatus);
		builder.append("\n]");
		return builder.toString();
	}

}
