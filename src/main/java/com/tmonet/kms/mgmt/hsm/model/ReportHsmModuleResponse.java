package com.tmonet.kms.mgmt.hsm.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;
import com.tmonet.kms.mgmt.hsm.vo.HsmModuleStatus;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReportHsmModuleResponse extends BaseResponse {

	private List<HsmModuleStatus> listHsmStatus;

	public ReportHsmModuleResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReportHsmModuleResponse(BaseRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	public ReportHsmModuleResponse(BaseRequest request, List<HsmModuleStatus> listHsmStatus) {
		super(request);
		this.listHsmStatus = listHsmStatus;
		// TODO Auto-generated constructor stub
	}

	public List<HsmModuleStatus> getListHsmStatus() {
		return listHsmStatus;
	}

	public void setListHsmStatus(List<HsmModuleStatus> listHsmStatus) {
		this.listHsmStatus = listHsmStatus;
	}

}
