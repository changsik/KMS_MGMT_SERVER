package com.tmonet.kms.mgmt.manager.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;
import com.tmonet.kms.mgmt.manager.vo.MngAuth;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MngAuthResponse extends BaseResponse {

	private String managerId;
	private String serviceId;

	private List<MngAuth> listMngAuth;

	public MngAuthResponse(BaseRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public List<MngAuth> getListMngAuth() {
		return listMngAuth;
	}

	public void setListMngAuth(List<MngAuth> listMngAuth) {
		this.listMngAuth = listMngAuth;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tmanagerId=");
		builder.append(managerId);
		builder.append("\n\t=serviceId");
		builder.append(serviceId);
		builder.append("\n]");
		return builder.toString();
	}
}
