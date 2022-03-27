package com.tmonet.kms.mgmt.hsmctl.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DeleteHsmCtlListResponse extends BaseResponse {

	private String hsmCtlSvrId;
	private String hsmGroupId;
	private List<String> hsmIdList;

	public DeleteHsmCtlListResponse(BaseRequest request) {
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

	public List<String> getHsmIdList() {
		return hsmIdList;
	}

	public void setHsmIdList(List<String> hsmIdList) {
		this.hsmIdList = hsmIdList;
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
		builder.append(hsmCtlSvrId);
		builder.append("\n]");
		return builder.toString();
	}

}
