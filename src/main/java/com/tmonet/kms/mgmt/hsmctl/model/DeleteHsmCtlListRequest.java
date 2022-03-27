package com.tmonet.kms.mgmt.hsmctl.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmModule;

public class DeleteHsmCtlListRequest extends BaseRequest {

	@NotNull
	private String hsmCtlSvrId;
	
	@Valid
	@NotNull
	private List<HsmModule> deleteHsmModuleList;

	public String getHsmCtlSvrId() {
		return hsmCtlSvrId;
	}

	public void setHsmCtlSvrId(String hsmCtlSvrId) {
		this.hsmCtlSvrId = hsmCtlSvrId;
	}

	public List<HsmModule> getDeleteHsmModuleList() {
		return deleteHsmModuleList;
	}

	public void setDeleteHsmModuleList(List<HsmModule> deleteHsmModuleList) {
		this.deleteHsmModuleList = deleteHsmModuleList;
	}

}
