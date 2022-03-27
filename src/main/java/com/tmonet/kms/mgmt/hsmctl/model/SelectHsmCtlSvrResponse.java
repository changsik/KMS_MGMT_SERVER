package com.tmonet.kms.mgmt.hsmctl.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlSvr;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SelectHsmCtlSvrResponse extends BaseResponse{

	private List<HsmCtlSvr> listHsmCtlSvr;

	public SelectHsmCtlSvrResponse(BaseRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	public List<HsmCtlSvr> getListHsmCtlSvr() {
		return listHsmCtlSvr;
	}

	public void setListHsmCtlSvr(List<HsmCtlSvr> listHsmCtlSvr) {
		this.listHsmCtlSvr = listHsmCtlSvr;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tlistHsmCtlSvr=");
		builder.append(listHsmCtlSvr);
		builder.append("\n]");
		return builder.toString();
	}
	
}
