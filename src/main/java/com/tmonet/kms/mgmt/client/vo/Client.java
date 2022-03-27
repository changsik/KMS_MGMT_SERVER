package com.tmonet.kms.mgmt.client.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.common.object.Code;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Client {

	private String clientIp;
	private Code authType;
	private String clientName;
	private String description;
	private Code usageStatus;
	private Long regDatetime;
	private Long lastUpdateDatetime;

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public Code getAuthType() {
		return authType;
	}

	public void setAuthType(Code authType) {
		this.authType = authType;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Code getUsageStatus() {
		return usageStatus;
	}

	public void setUsageStatus(Code usageStatus) {
		this.usageStatus = usageStatus;
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
