package com.tmonet.kms.mgmt.hsmctl.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RegisterHsmCtlListResponse extends BaseResponse {

	private String hsmCtlSvrId;
	private String hsmGroupId;
	private String hsmId;

	public RegisterHsmCtlListResponse(BaseRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

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
		builder.append("\n]");
		return builder.toString();
	}

}
