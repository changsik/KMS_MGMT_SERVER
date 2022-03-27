package com.tmonet.kms.mgmt.hsmctl.model;

import javax.validation.constraints.NotNull;

import com.tmonet.kms.mgmt.common.model.BaseRequest;

public class RegisterHsmCtlSvrRequest extends BaseRequest {

	@NotNull
	private String hsmCtlSvrId;

	@NotNull
	private String hsmIpAddress;

	@NotNull
	private int hsmPort;

	private String description;

	public String getHsmCtlSvrId() {
		return hsmCtlSvrId;
	}

	public void setHsmCtlSvrId(String hsmCtlSvrId) {
		this.hsmCtlSvrId = hsmCtlSvrId;
	}

	public String getHsmIpAddress() {
		return hsmIpAddress;
	}

	public void setHsmIpAddress(String hsmIpAddress) {
		this.hsmIpAddress = hsmIpAddress;
	}

	public int getHsmPort() {
		return hsmPort;
	}

	public void setHsmPort(int hsmPort) {
		this.hsmPort = hsmPort;
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
		builder.append("\n\thsmCtlSvrId=");
		builder.append(hsmCtlSvrId);
		builder.append("\n\thsmIpAddress=");
		builder.append(hsmIpAddress);
		builder.append("\n\thsmPort=");
		builder.append(hsmPort);
		builder.append("\n\tdescription=");
		builder.append(description);
		builder.append("\n]");
		return builder.toString();
	}

}
