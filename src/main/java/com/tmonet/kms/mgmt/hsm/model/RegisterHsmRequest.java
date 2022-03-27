package com.tmonet.kms.mgmt.hsm.model;

import javax.validation.constraints.NotNull;

import com.tmonet.kms.mgmt.common.model.BaseRequest;

public class RegisterHsmRequest extends BaseRequest {

	@NotNull
	private String hsmGroupId;

	@NotNull
	private String hsmVendor;

	@NotNull
	private String hsmSerialNumber;

	@NotNull
	private String hsmInstallType;

	private String hsmIpAddress;

	private String description;

	public String getHsmGroupId() {
		return hsmGroupId;
	}

	public void setHsmGroupId(String hsmGroupId) {
		this.hsmGroupId = hsmGroupId;
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

	public String getHsmIpAddress() {
		return hsmIpAddress;
	}

	public void setHsmIpAddress(String hsmIpAddress) {
		this.hsmIpAddress = hsmIpAddress;
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
		builder.append("\n\thsmGroupId=");
		builder.append(hsmGroupId);
		builder.append("\n\thsmVendor=");
		builder.append(hsmVendor);
		builder.append("\n\thsmSerialNumber=");
		builder.append(hsmSerialNumber);
		builder.append("\n\thsmInstallType=");
		builder.append(hsmInstallType);
		builder.append("\n\thsmIpAddress=");
		builder.append(hsmIpAddress);
		builder.append("\n\tdescription=");
		builder.append(description);
		builder.append("\n]");
		return builder.toString();
	}

}
