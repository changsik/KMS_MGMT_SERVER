package com.tmonet.kmsp.common.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tmonet.kms.mgmt.common.model.BaseRequest;

import lombok.Getter;
import lombok.Setter;

public class ResponseHeaderVo {

	@Valid
	@Nullable
	@JsonProperty(value="ProtocolVersion")
	private ProtocolVersionVo protocolVersion;
	
	@Valid
	@Nullable
	@JsonProperty(value="TimeStamp")
	private TypeValueVo timeStamp;
	
	@Valid
	@Nullable
	@JsonProperty(value="BatchCount")
	private TypeValueVo batchCount;
	
	public ProtocolVersionVo getProtocolVersion() {
		return protocolVersion;
	}
	
	public void setProtocolVersion(ProtocolVersionVo protocolVersion) {
		this.protocolVersion = protocolVersion;
	}
	
	public TypeValueVo getTimeStamp() {
		return timeStamp;
	}
	
	public void setTimeStamp(TypeValueVo timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public TypeValueVo getBatchCount() {
		return batchCount;
	}
	
	public void setBatchCount(TypeValueVo batchCount) {
		this.batchCount = batchCount;
	}
		
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		//builder.append(super.toString());
		builder.append("\n\tprotocolVersion=");
		builder.append(protocolVersion);
		builder.append("\n\ttimeStamp=");
		builder.append(timeStamp);
		builder.append("\n\tbatchCount=");
		builder.append(batchCount);
		builder.append("\n]");
		return builder.toString();
	}
}