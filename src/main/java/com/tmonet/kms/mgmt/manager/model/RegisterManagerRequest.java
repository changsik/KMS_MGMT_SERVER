package com.tmonet.kms.mgmt.manager.model;

import javax.validation.constraints.NotNull;

import com.tmonet.kms.mgmt.common.model.BaseRequest;

public class RegisterManagerRequest extends BaseRequest {

	@NotNull
	private String managerId;

	@NotNull
	private String managerPassword;

	private String managerEmail;
	private String managerName;
	private String description;

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tmanagerId=");
		builder.append(managerId);
		builder.append("\n\tmanagerPassword=");
		builder.append(managerPassword);
		builder.append("\n\tmanagerEmail=");
		builder.append(managerEmail);
		builder.append("\n\tmanagerName=");
		builder.append(managerName);
		builder.append("\n\tdescription=");
		builder.append(description);
		builder.append("\n]");
		return builder.toString();
	}

}
