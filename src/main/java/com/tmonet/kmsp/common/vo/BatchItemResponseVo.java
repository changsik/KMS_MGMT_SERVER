package com.tmonet.kmsp.common.vo;

import javax.validation.Valid;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BatchItemResponseVo {

	@Valid
	@Nullable
	@JsonProperty(value="Operation")
	private TypeValueVo operation;
	
	@Valid
	@Nullable
	@JsonProperty(value="ResultStatus")
	private TypeValueVo resultStatus;
	
	@Valid
	@Nullable
	@JsonProperty(value="ResponsePayload")
	private Object responsePayload;
	
	public TypeValueVo getOperation() {
		return operation;
	}
	
	public void setOperation(TypeValueVo operation) {
		this.operation = operation;
	}
	
	public TypeValueVo getResultStatus() {
		return resultStatus;
	}
	
	public void setResultStatus(TypeValueVo resultStatus) {
		this.resultStatus = resultStatus;
	}
	
	public Object getResponsePayload() {
		return responsePayload;
	}
	
	public void setResponsePayload(Object responsePayload) {
		this.responsePayload = responsePayload;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		//builder.append(super.toString());
		builder.append("\n\toperation=");
		builder.append(operation);
		builder.append("\n\tresultStatus=");
		builder.append(resultStatus);
		builder.append("\n\tresponsePayload=");
		builder.append(responsePayload);
		builder.append("\n]");
		return builder.toString();
	}
}