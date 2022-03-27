package com.tmonet.kms.mgmt.keyprofile.vo;

import javax.validation.constraints.NotNull;

public class KeyAttribute {

	@NotNull
	private String keyAttributeId;

	@NotNull
	private String keyAttributeValue;

	public String getKeyAttributeId() {
		return keyAttributeId;
	}

	public void setKeyAttributeId(String keyAttributeId) {
		this.keyAttributeId = keyAttributeId;
	}

	public String getKeyAttributeValue() {
		return keyAttributeValue;
	}

	public void setKeyAttributeValue(String keyAttributeValue) {
		this.keyAttributeValue = keyAttributeValue;
	}

}
