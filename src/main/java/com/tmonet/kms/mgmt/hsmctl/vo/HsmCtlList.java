package com.tmonet.kms.mgmt.hsmctl.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.common.object.Code;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HsmCtlList {

	private String hsmCtlSvrId;
	private String hsmGroupId;
	private String hsmId;
	private int hsmCtlOrder;
	private Code hsmCtlMode;
	private Long regDatetime;
	private Long lastUpdateDatetime;

	public String getHsmCtlSvrId() {
		return hsmCtlSvrId;
	}

	public void setHsmCtlSvrId(String hsmCtlSvrId) {
		this.hsmCtlSvrId = hsmCtlSvrId;
	}

	public String getHsmGroupId() {
		return hsmGroupId;
	}

	public void setHsmGroupId(String hsmGroupId) {
		this.hsmGroupId = hsmGroupId;
	}

	public String getHsmId() {
		return hsmId;
	}

	public void setHsmId(String hsmId) {
		this.hsmId = hsmId;
	}

	public int getHsmCtlOrder() {
		return hsmCtlOrder;
	}

	public void setHsmCtlOrder(int hsmCtlOrder) {
		this.hsmCtlOrder = hsmCtlOrder;
	}

	public Code getHsmCtlMode() {
		return hsmCtlMode;
	}

	public void setHsmCtlMode(Code hsmCtlMode) {
		this.hsmCtlMode = hsmCtlMode;
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
