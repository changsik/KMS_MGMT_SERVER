package com.tmonet.kmsp.common.vo;

import javax.validation.Valid;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;

public class KMIPResponseVo extends BaseResponse{

	@Valid
	@Nullable
	@JsonProperty(value="ResponseHeader")
	private ResponseHeaderVo responseHeader;
	
	@Valid
	@Nullable
	@JsonProperty(value="BatchItem")
	private BatchItemResponseVo batchItem;
	
	public KMIPResponseVo() {}
	
	public KMIPResponseVo(BaseRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}
	
	public BatchItemResponseVo getBatchItem() {
		return batchItem;
	}
	
	public void setBatchItem(BatchItemResponseVo batchItem) {
		this.batchItem = batchItem;
	}
	
	public ResponseHeaderVo getResponseHeader() {
		return responseHeader;
	}
	
	public void setResponseHeader(ResponseHeaderVo responseHeader) {
		this.responseHeader = responseHeader;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tbatchItem=");
		builder.append(batchItem);
		builder.append("\n\tresponseHeader=");
		builder.append(responseHeader);
		builder.append("\n]");
		return builder.toString();
	}
}