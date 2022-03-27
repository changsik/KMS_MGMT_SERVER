package com.tmonet.kms.mgmt.hsmctl.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlList;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SelectHsmCtlListResponse extends BaseResponse {

	private List<HsmCtlList> listHsmCtlList;

	public SelectHsmCtlListResponse(BaseRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	public List<HsmCtlList> getListHsmCtlList() {
		return listHsmCtlList;
	}

	public void setListHsmCtlList(List<HsmCtlList> listHsmCtlList) {
		this.listHsmCtlList = listHsmCtlList;
	}

}
