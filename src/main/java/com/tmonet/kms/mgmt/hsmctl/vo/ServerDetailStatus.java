package com.tmonet.kms.mgmt.hsmctl.vo;

public class ServerDetailStatus {

	private String hsmId;
	private boolean usable;
	private Long countConnectionFailed;
	private Long lastConnectionTime;

	public String getHsmId() {
		return hsmId;
	}

	public void setHsmId(String hsmId) {
		this.hsmId = hsmId;
	}

	public boolean isUsable() {
		return usable;
	}

	public void setUsable(boolean usable) {
		this.usable = usable;
	}

	public Long getCountConnectionFailed() {
		return countConnectionFailed;
	}

	public void setCountConnectionFailed(Long countConnectionFailed) {
		this.countConnectionFailed = countConnectionFailed;
	}

	public Long getLastConnectionTime() {
		return lastConnectionTime;
	}

	public void setLastConnectionTime(Long lastConnectionTime) {
		this.lastConnectionTime = lastConnectionTime;
	}

}
