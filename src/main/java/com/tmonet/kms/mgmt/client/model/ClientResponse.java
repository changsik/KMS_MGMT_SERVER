package com.tmonet.kms.mgmt.client.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.client.vo.Client;
import com.tmonet.kms.mgmt.client.vo.ClientInfoVo;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ClientResponse extends BaseResponse {

	private String clientIp;

	private List<Client> listClient;

	public ClientResponse(BaseRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public List<Client> getListClient() {
		return listClient;
	}

	public void setListClient(List<Client> listClient) {
		this.listClient = listClient;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tclientIp=");
		builder.append(clientIp);
		builder.append("\n]");
		return builder.toString();
	}

}
