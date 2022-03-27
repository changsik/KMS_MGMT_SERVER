package com.tmonet.kms.mgmt.hsmgroup.model;

import javax.validation.constraints.NotNull;

import com.tmonet.kms.mgmt.common.model.BaseRequest;

public class HsmGroupRequest extends BaseRequest {

	@NotNull
	private String vIpAddr;

	@NotNull
	private int vPort;

	private String description;

	public String getvIpAddr() {
		return vIpAddr;
	}

	public void setvIpAddr(String vIpAddr) {
		this.vIpAddr = vIpAddr;
	}

	public int getvPort() {
		return vPort;
	}

	public void setvPort(int vPort) {
		this.vPort = vPort;
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
		builder.append("\n\tvIpAddr=");
		builder.append(vIpAddr);
		builder.append(", \n\tvPort=");
		builder.append(vPort);
		builder.append(", \n\tdescription=");
		builder.append(description);
		builder.append("\n]");
		return builder.toString();
	}

}
