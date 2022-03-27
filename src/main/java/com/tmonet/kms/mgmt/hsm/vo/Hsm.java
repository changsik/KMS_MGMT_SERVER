package com.tmonet.kms.mgmt.hsm.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.common.object.Code;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Hsm {
	private String hsmId;
	private String hsmGroupId;
	private Code hsmVendor;
	private String hsmSerialNumber;
	private Code hsmInstallType;
	private String hsmIpAddress;
	private Long regDatetime;
	private Long lastUpdateDatetime;

	public String getHsmId() {
		return hsmId;
	}

	public void setHsmId(String hsmId) {
		this.hsmId = hsmId;
	}

	public String getHsmGroupId() {
		return hsmGroupId;
	}

	public void setHsmGroupId(String hsmGroupId) {
		this.hsmGroupId = hsmGroupId;
	}

	public Code getHsmVendor() {
		return hsmVendor;
	}

	public void setHsmVendor(Code hsmVendor) {
		this.hsmVendor = hsmVendor;
	}

	public String getHsmSerialNumber() {
		return hsmSerialNumber;
	}

	public void setHsmSerialNumber(String hsmSerialNumber) {
		this.hsmSerialNumber = hsmSerialNumber;
	}

	public Code getHsmInstallType() {
		return hsmInstallType;
	}

	public void setHsmInstallType(Code hsmInstallType) {
		this.hsmInstallType = hsmInstallType;
	}

	public String getHsmIpAddress() {
		return hsmIpAddress;
	}

	public void setHsmIpAddress(String hsmIpAddress) {
		this.hsmIpAddress = hsmIpAddress;
	}

	public Long getRegDatetime() {
		return regDatetime;
	}

	public void setRegDatetime(Long regDatetime) {
		this.regDatetime = regDatetime;
	}

	public Long getLastUpdateDatetime() {
		return lastUpdateDatetime;
	}

	public void setLastUpdateDatetime(Long lastUpdateDatetime) {
		this.lastUpdateDatetime = lastUpdateDatetime;
	}

}
