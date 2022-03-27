package com.tmonet.kms.mgmt.hsmctl.vo;

import javax.validation.constraints.NotNull;

public class HsmModule {

	@NotNull
	private String hsmId;
	
	@NotNull
	private String hsmSerialNumber;
	
	@NotNull
	private int hsmControlOrder;
	
	@NotNull
	private String hsmControlMode;

	public String getHsmId() {
		return hsmId;
	}

	public void setHsmId(String hsmId) {
		this.hsmId = hsmId;
	}

	public String getHsmSerialNumber() {
		return hsmSerialNumber;
	}

	public void setHsmSerialNumber(String hsmSerialNumber) {
		this.hsmSerialNumber = hsmSerialNumber;
	}

	public int getHsmControlOrder() {
		return hsmControlOrder;
	}

	public void setHsmControlOrder(int hsmControlOrder) {
		this.hsmControlOrder = hsmControlOrder;
	}

	public String getHsmControlMode() {
		return hsmControlMode;
	}

	public void setHsmControlMode(String hsmControlMode) {
		this.hsmControlMode = hsmControlMode;
	}

}
