package com.tmonet.kms.mgmt.hsmctl.vo;

import java.util.List;

import com.tmonet.kms.mgmt.hsm.vo.HsmModuleStatus;

public class HsmServerStatus {

	private int hsmModuleCount;
	private List<HsmModuleStatus> listHsmStatus;

	public int getHsmModuleCount() {
		return hsmModuleCount;
	}

	public void setHsmModuleCount(int hsmModuleCount) {
		this.hsmModuleCount = hsmModuleCount;
	}

	public List<HsmModuleStatus> getListHsmStatus() {
		return listHsmStatus;
	}

	public void setListHsmStatus(List<HsmModuleStatus> listHsmStatus) {
		this.listHsmStatus = listHsmStatus;
	}

}
