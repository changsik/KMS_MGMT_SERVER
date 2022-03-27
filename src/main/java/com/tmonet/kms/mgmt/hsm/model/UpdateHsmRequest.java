package com.tmonet.kms.mgmt.hsm.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UpdateHsmRequest extends BaseRequest {

	private String hsmIpAddress;
	private String hsmVendor;
	private String hsmSerialNumber;
	private String hsmInstallType;
	private String description;

	public String getHsmIpAddress() {
		return hsmIpAddress;
	}

	public void setHsmIpAddress(String hsmIpAddress) {
		this.hsmIpAddress = hsmIpAddress;
	}

	public String getHsmVendor() {
		return hsmVendor;
	}

	public void setHsmVendor(String hsmVendor) {
		this.hsmVendor = hsmVendor;
	}

	public String getHsmSerialNumber() {
		return hsmSerialNumber;
	}

	public void setHsmSerialNumber(String hsmSerialNumber) {
		this.hsmSerialNumber = hsmSerialNumber;
	}

	public String getHsmInstallType() {
		return hsmInstallType;
	}

	public void setHsmInstallType(String hsmInstallType) {
		this.hsmInstallType = hsmInstallType;
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
		builder.append(", \n\thsmIpAddress=");
		builder.append(hsmIpAddress);
		builder.append("\n\thsmVendor=");
		builder.append(hsmVendor);
		builder.append(", \n\thsmSerialNumber=");
		builder.append(hsmSerialNumber);
		builder.append(", \n\thsmInstallType=");
		builder.append(hsmInstallType);
		builder.append(", \n\tdescription=");
		builder.append(description);
		builder.append("\n]");
		return builder.toString();
	}

}
