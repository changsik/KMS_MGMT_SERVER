package com.tmonet.kms.mgmt.client.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.client.vo.CliAuth;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;
import com.tmonet.kms.mgmt.common.object.ApiResult;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CliAuthResponse extends BaseResponse {

	private String clientIp;
	private String serviceId;
	private List<CliAuth> listCliAuth;

	public CliAuthResponse() {
		super();
	}

	public CliAuthResponse(BaseRequest request) {
		super(request);
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public List<CliAuth> getListCliAuth() {
		return listCliAuth;
	}

	public void setListCliAuth(List<CliAuth> listCliAuth) {
		this.listCliAuth = listCliAuth;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tlistCliAuth=");
		builder.append(listCliAuth.toString());
		builder.append("\n]");
		return builder.toString();
	}

}
