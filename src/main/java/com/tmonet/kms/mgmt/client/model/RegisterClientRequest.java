package com.tmonet.kms.mgmt.client.model;

import javax.validation.constraints.NotNull;

import com.tmonet.kms.mgmt.common.model.BaseRequest;

public class RegisterClientRequest extends BaseRequest {

	@NotNull
	private String clientIp;

	private String authType;
	private String clientName;
	private String description;
	private String usageStatus;

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		builder.append("\n\tclientIp=");
		builder.append(clientIp);
		builder.append("\n\tauthType=");
		builder.append(authType);
		builder.append("\n\tclientName=");
		builder.append(clientName);
		builder.append("\n\tdescription=");
		builder.append(description);
		builder.append("\n\tusageStatus=");
		builder.append(usageStatus);
		builder.append("\n]");
		return builder.toString();
	}

}
