package com.tmonet.kms.mgmt.keyprofile.vo;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.common.object.Code;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class KeyProfile {

	private String keyProfileId;

	@Valid
	@NotNull
	private List<KeyAttribute> listKeyAttribute;

	private Code usageStatus;
	private Long regDatetime;
	private Long laseUpdateDatetime;

	public String getKeyProfileId() {
		return keyProfileId;
	}

	public void setKeyProfileId(String keyProfileId) {
		this.keyProfileId = keyProfileId;
	}

	public List<KeyAttribute> getListKeyAttribute() {
		return listKeyAttribute;
	}

	public void setListKeyAttribute(List<KeyAttribute> listKeyAttribute) {
		this.listKeyAttribute = listKeyAttribute;
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

	public Long getLaseUpdateDatetime() {
		return laseUpdateDatetime;
	}

	public void setLaseUpdateDatetime(Long laseUpdateDatetime) {
		this.laseUpdateDatetime = laseUpdateDatetime;
	}

}
