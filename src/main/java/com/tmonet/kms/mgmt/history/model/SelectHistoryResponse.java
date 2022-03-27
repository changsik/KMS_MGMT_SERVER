package com.tmonet.kms.mgmt.history.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SelectHistoryResponse extends BaseResponse {

	public SelectHistoryResponse(BaseRequest request) {
		super(request);
	}

	private List<History> listHistory;

	public List<History> getListHistory() {
		return listHistory;
	}

	public void setListHistory(List<History> listHistory) {
		this.listHistory = listHistory;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tlistHistory=");
		builder.append(listHistory);
		builder.append("\n]");
		return builder.toString();
	}

}
