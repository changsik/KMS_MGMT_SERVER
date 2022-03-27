package com.tmonet.kms.mgmt.key.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;
import com.tmonet.kms.mgmt.common.object.ApiResult;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreateSymmetricKeyResponse extends BaseResponse {
	
	public CreateSymmetricKeyResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CreateSymmetricKeyResponse(BaseRequest request) {
		super(request);
	}

	private String keyId;
	private Long regDatetime;

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public Long getRegDatetime() {
		return regDatetime;
	}

	public void setRegDatetime(Long regDatetime) {
		this.regDatetime = regDatetime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tkeyId=");
		builder.append(keyId);
		builder.append(", \n\tregDatetime=");
		builder.append(regDatetime);
		builder.append("\n]");
		return builder.toString();
	}

}
