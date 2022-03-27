package com.tmonet.kms.mgmt.client.model;

import javax.validation.constraints.NotNull;

import com.tmonet.kms.mgmt.common.model.BaseRequest;

public class RegisterCliAuthRequest extends BaseRequest {

	@NotNull
	private String clientIp;

	@NotNull
	private String serviceId;

	private String createYn;
	private String readYn;
	private String updateYn;
	private String deleteYn;

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
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

}
