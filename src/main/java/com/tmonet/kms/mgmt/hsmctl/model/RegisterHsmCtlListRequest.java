package com.tmonet.kms.mgmt.hsmctl.model;

import javax.validation.constraints.NotNull;

import com.tmonet.kms.mgmt.common.model.BaseRequest;

public class RegisterHsmCtlListRequest extends BaseRequest {

	@NotNull
	private String hsmCtlSvrId;
	
	@NotNull
	private String hsmGroupId;
	
	@NotNull
	private String hsmId;
	
	@NotNull
	private int hsmCtlOrder;
	
	@NotNull
	private String hsmCtlMode;

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

	public String getHsmCtlMode() {
		return hsmCtlMode;
	}

	public void setHsmCtlMode(String hsmCtlMode) {
		this.hsmCtlMode = hsmCtlMode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\thsmCtlSvrId=");
		builder.append(hsmCtlSvrId);
		builder.append("\n\thsmGroupId=");
		builder.append(hsmGroupId);
		builder.append("\n\thsmId=");
		builder.append(hsmId);
		builder.append("\n\thsmCtlOrder=");
		builder.append(hsmCtlOrder);
		builder.append("\n\thsmCtlMode=");
		builder.append(hsmCtlMode);
		builder.append("\n]");
		return builder.toString();
	}

}
