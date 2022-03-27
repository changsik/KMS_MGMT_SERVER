package com.tmonet.kms.mgmt.manager.model;

import javax.validation.constraints.NotNull;

import com.tmonet.kms.mgmt.common.model.BaseRequest;

public class RegisterMngAuthRequest extends BaseRequest {

	@NotNull
	private String managerId;

	@NotNull
	private String serviceId;

	private String createYn;
	private String readYn;
	private String updateYn;
	private String deleteYn;

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getCreateYn() {
		return createYn;
	}

	public void setCreateYn(String createYn) {
		this.createYn = createYn;
	}

	public String getReadYn() {
		return readYn;
	}

	public void setReadYn(String readYn) {
		this.readYn = readYn;
	}

	public String getUpdateYn() {
		return updateYn;
	}

	public void setUpdateYn(String updateYn) {
		this.updateYn = updateYn;
	}

	public String getDeleteYn() {
		return deleteYn;
	}

	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tmanagerId=");
		builder.append(managerId);
		builder.append("\n\t=serviceId");
		builder.append(serviceId);
		builder.append("\n\t=createYn");
		builder.append(createYn);
		builder.append("\n\t=readYn");
		builder.append(readYn);
		builder.append("\n\t=updateYn");
		builder.append(updateYn);
		builder.append("\n\t=deleteYn");
		builder.append(deleteYn);
		builder.append("\n]");
		return builder.toString();
	}

}
