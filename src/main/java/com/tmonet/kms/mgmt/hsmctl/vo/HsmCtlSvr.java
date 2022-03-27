package com.tmonet.kms.mgmt.hsmctl.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HsmCtlSvr {

	private String hsmCtlSvrId;
	private String hsmIpAddress;
	private int hsmPort;
	private String description;
	private Long regDatetime;
	private Long lastUpdateDatetime;

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
