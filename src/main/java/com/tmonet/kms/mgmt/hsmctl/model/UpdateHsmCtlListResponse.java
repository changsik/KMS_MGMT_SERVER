package com.tmonet.kms.mgmt.hsmctl.model;

import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;

public class UpdateHsmCtlListResponse extends BaseResponse {

	private String hsmCtlSvrId;

	public UpdateHsmCtlListResponse(BaseRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	public String getHsmCtlSvrId() {
		return hsmCtlSvrId;
	}

	public void setHsmCtlSvrId(String hsmCtlSvrId) {
		this.hsmCtlSvrId = hsmCtlSvrId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\thsmCtlSvrId=");
		builder.append(hsmCtlSvrId);
		builder.append("\n]");
		return builder.toString();
	}

}
