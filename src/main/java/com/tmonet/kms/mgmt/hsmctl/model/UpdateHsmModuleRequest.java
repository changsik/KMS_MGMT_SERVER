package com.tmonet.kms.mgmt.hsmctl.model;

import java.util.List;

import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmModuleInfo;

public class UpdateHsmModuleRequest extends BaseRequest {

	private List<HsmModuleInfo> updateHsmModuleList;

	public List<HsmModuleInfo> getUpdateHsmModuleList() {
		return updateHsmModuleList;
	}

	public void setUpdateHsmModuleList(List<HsmModuleInfo> updateHsmModuleList) {
		this.updateHsmModuleList = updateHsmModuleList;
	}

}
