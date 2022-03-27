package com.tmonet.kms.mgmt.manager.model;

import com.tmonet.kms.mgmt.common.model.BaseRequest;

public class UpdateManagerRequest extends BaseRequest {

	private String managerPassword;
	private String managerEmail;
	private String managerName;
	private String description;
	private String usageStatus;

	public String getManagerPassword() {
		return managerPassword;
	}

	public void setManagerPassword(String managerPassword) {
		this.managerPassword = managerPassword;
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
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
		builder.append("\n\tmanagerPassword=");
		builder.append(managerPassword);
		builder.append("\n\tmanagerEmail=");
		builder.append(managerEmail);
		builder.append("\n\tmanagerName=");
		builder.append(managerName);
		builder.append("\n\tdescription=");
		builder.append(description);
		builder.append("\n\tusageStatus=");
		builder.append(usageStatus);
		builder.append("\n]");
		return builder.toString();
	}

}
