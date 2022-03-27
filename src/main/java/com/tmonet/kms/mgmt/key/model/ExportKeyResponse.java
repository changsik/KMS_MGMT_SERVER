package com.tmonet.kms.mgmt.key.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExportKeyResponse extends BaseResponse {

	public ExportKeyResponse(BaseRequest request) {
		super(request);
	}

	private Object exportedKeyData;

	public Object getExportedKeyData() {
		return exportedKeyData;
	}

	public void setExportedKeyData(Object exportedKeyData) {
		this.exportedKeyData = exportedKeyData;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\texportedKeyData=");
		builder.append(exportedKeyData);
		builder.append("\n]");
		return builder.toString();
	}

}
