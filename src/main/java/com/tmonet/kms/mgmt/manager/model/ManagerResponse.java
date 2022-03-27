package com.tmonet.kms.mgmt.manager.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;
import com.tmonet.kms.mgmt.manager.vo.Manager;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ManagerResponse extends BaseResponse {

	private List<Manager> listManager;

	private String managerId;

	public ManagerResponse(BaseRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	public List<Manager> getListManager() {
		return listManager;
	}

	public void setListManager(List<Manager> listManager) {
		this.listManager = listManager;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tmanagerId=");
		builder.append(managerId);
		builder.append("\n]");
		return builder.toString();
	}

}
