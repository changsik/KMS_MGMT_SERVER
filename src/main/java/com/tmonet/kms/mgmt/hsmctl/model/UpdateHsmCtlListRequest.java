package com.tmonet.kms.mgmt.hsmctl.model;

import java.util.List;

import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmModule;

public class UpdateHsmCtlListRequest extends BaseRequest {

	private String hsmCtlSvrId;
	private List<HsmModule> updateHsmModuleList;

	public String getHsmCtlSvrId() {
		return hsmCtlSvrId;
	}

	public void setHsmCtlSvrId(String hsmCtlSvrId) {
		this.hsmCtlSvrId = hsmCtlSvrId;
	}

	public List<HsmModule> getUpdateHsmModuleList() {
		return updateHsmModuleList;
	}

	public void setUpdateHsmModuleList(List<HsmModule> updateHsmModuleList) {
		this.updateHsmModuleList = updateHsmModuleList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\thsmCtlSvrId=");
		builder.append(hsmCtlSvrId);
		builder.append("\n\tupdateHsmModuleList=");
		builder.append(updateHsmModuleList);
		builder.append("\n]");
		return builder.toString();
	}

}
