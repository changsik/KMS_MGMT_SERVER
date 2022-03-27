package com.tmonet.kms.mgmt.hsmctl.model;

import com.tmonet.kms.mgmt.common.kmsenum.EnumHsmControlMode;
import com.tmonet.kms.mgmt.common.model.BaseRequest;

public class RegisterHsmModuleRequest extends BaseRequest {
	private String hsmId;
	private String hsmSerialNumber;
	private int hsmControlOrder;
	private EnumHsmControlMode hsmControlMode;

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

	public EnumHsmControlMode getHsmControlMode() {
		return hsmControlMode;
	}

	public void setHsmControlMode(EnumHsmControlMode hsmControlMode) {
		this.hsmControlMode = hsmControlMode;
	}
}
