package com.tmonet.kmsp.create.model;

import javax.validation.Valid;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kmsp.common.vo.TypeValueVo;
import com.tmonet.kmsp.create.vo.CreateSymmKeyRequestPayloadVo;

public class CreateSymmetricKeyRequest extends BaseRequest{

	@Valid
	@Nullable
	@JsonProperty(value="Operation")
	private TypeValueVo operation;
	
	@Valid
	@Nullable
	@JsonProperty(value="RequestPayload")
	private CreateSymmKeyRequestPayloadVo requestPayload;
	
	public TypeValueVo getOperation() {
		return operation;
	}
	
	public void setOperation(TypeValueVo operation) {
		this.operation = operation;
	}
	
	public CreateSymmKeyRequestPayloadVo getRequestPayload() {
		return requestPayload;
	}
	
	public void setRequestPayload(CreateSymmKeyRequestPayloadVo requestPayload) {
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