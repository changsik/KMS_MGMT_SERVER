package com.tmonet.kms.mgmt.client.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.common.object.Code;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ClientKeyList {

	private String clientKeyId;
	private String clientIp;
	private String clientKeyValue;
	private Code usageStatus;
	private Long regDatetime;
	private Long lastUpdateDatetime;

	public String getClientKeyId() {
		return clientKeyId;
	}

	public void setClientKeyId(String clientKeyId) {
		this.clientKeyId = clientKeyId;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getClientKeyValue() {
		return clientKeyValue;
	}

	public void setClientKeyValue(String clientKeyValue) {
		this.clientKeyValue = clientKeyValue;
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
