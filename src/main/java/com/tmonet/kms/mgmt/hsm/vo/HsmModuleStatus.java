package com.tmonet.kms.mgmt.hsm.vo;

public class HsmModuleStatus {

	private String hsmId;
	private boolean usable;
	private long countConnectionFailed;
	private long lastConnectionTime;

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

	public long getCountConnectionFailed() {
		return countConnectionFailed;
	}

	public void setCountConnectionFailed(long countConnectionFailed) {
		this.countConnectionFailed = countConnectionFailed;
	}

	public long getLastConnectionTime() {
		return lastConnectionTime;
	}

	public void setLastConnectionTime(long lastConnectionTime) {
		this.lastConnectionTime = lastConnectionTime;
	}

}
