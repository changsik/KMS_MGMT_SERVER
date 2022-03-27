package com.tmonet.kms.mgmt.client.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.client.vo.ClientKeyList;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ClientKeyListResponse extends BaseResponse {

	private String clientKeyId;
	private String clientKeyValue;
	private List<ClientKeyList> listClientKey;

	public ClientKeyListResponse(BaseRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	public String getClientKeyId() {
		return clientKeyId;
	}

	public void setClientKeyId(String clientKeyId) {
		this.clientKeyId = clientKeyId;
	}

	public String getClientKeyValue() {
		return clientKeyValue;
	}

	public void setClientKeyValue(String clientKeyValue) {
		this.clientKeyValue = clientKeyValue;
	}

	public List<ClientKeyList> getListClientKey() {
		return listClientKey;
	}

	public void setListClientKey(List<ClientKeyList> listClientKey) {
		this.listClientKey = listClientKey;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tclientKeyId=");
		builder.append(clientKeyId);
		builder.append("\n\tclientKeyValue=");
		builder.append(clientKeyValue);
		builder.append("\n]");
		return builder.toString();
	}

}
