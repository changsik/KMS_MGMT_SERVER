package com.tmonet.kms.mgmt.client.vo;

public class CliAuth {

	private String clientIp;
	private String serviceId;
	private String createYn;
	private String readYn;
	private String updateYn;
	private String deleteYn;
	private Long regDatetime;
	private Long lastUpdateDatetime;

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

	@Override
	public String toString() {
		return "CliAuth [clientIp=" + clientIp + ", serviceId=" + serviceId + ", createYn=" + createYn + ", readYn="
				+ readYn + ", updateYn=" + updateYn + ", deleteYn=" + deleteYn + ", regDatetime=" + regDatetime
				+ ", lastUpdateDatetime=" + lastUpdateDatetime + "]";
	}

}
