package com.tmonet.kmsp.createkeypair.model;

import javax.validation.Valid;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kmsp.common.vo.TypeValueVo;
import com.tmonet.kmsp.createkeypair.vo.CreateKeyPairRequestPayloadVo;

public class CreateKeyPairRequest extends BaseRequest {
	@Valid
	@Nullable
	@JsonProperty(value="Operation")
	private TypeValueVo operation;
	
	@Valid
	@Nullable
	@JsonProperty(value="RequestPayload")
	private CreateKeyPairRequestPayloadVo requestPayload;
	
	public TypeValueVo getOperation() {
		return operation;
	}
	
	public void setOperation(TypeValueVo operation) {
		this.operation = operation;
	}
	
	public CreateKeyPairRequestPayloadVo getRequestPayload() {
		return requestPayload;
	}
	
	public void setRequestPayload(CreateKeyPairRequestPayloadVo requestPayload) {
		this.requestPayload = requestPayload;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\toperation=");
		builder.append(operation);
		builder.append("\n\trequestPayload=");
		builder.append(requestPayload);
		builder.append("\n]");
		return builder.toString();
	}
}
