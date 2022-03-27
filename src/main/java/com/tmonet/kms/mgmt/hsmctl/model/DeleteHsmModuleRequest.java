package com.tmonet.kms.mgmt.hsmctl.model;

import java.util.List;

import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmModuleInfo;

public class DeleteHsmModuleRequest extends BaseRequest {

	private List<HsmModuleInfo> deleteHsmModuleList;

	public List<HsmModuleInfo> getDeleteHsmModuleList() {
		return deleteHsmModuleList;
	}

	public void setDeleteHsmModuleList(List<HsmModuleInfo> deleteHsmModuleList) {
		this.deleteHsmModuleList = deleteHsmModuleList;
	}

}
