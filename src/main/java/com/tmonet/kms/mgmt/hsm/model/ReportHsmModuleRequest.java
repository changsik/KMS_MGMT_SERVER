package com.tmonet.kms.mgmt.hsm.model;

import com.tmonet.kms.mgmt.common.model.BaseRequest;

public class ReportHsmModuleRequest extends BaseRequest {

	private String slbIpAddress;
	private String slbPort;

	public String getSlbIpAddress() {
		return slbIpAddress;
	}

	public void setSlbIpAddress(String slbIpAddress) {
		this.slbIpAddress = slbIpAddress;
	}

	public String getSlbPort() {
		return slbPort;
	}

	public void setSlbPort(String slbPort) {
		this.slbPort = slbPort;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tslbIpAddress=");
		builder.append(slbIpAddress);
		builder.append("\n\tslbPort=");
		builder.append(slbPort);
		builder.append("\n]");
		return builder.toString();
	}

}
