package com.tmonet.kmsp.common.vo;

import javax.validation.Valid;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProtocolVersionVo {

	@Valid
	@Nullable
	@JsonProperty(value="ProtocolVersionMajor")
	private TypeValueVo protocolVersionMajor;
	
	@Valid
	@Nullable
	@JsonProperty(value="ProtocolVersionMinor")
	private TypeValueVo protocolVersionMinor;
	
	public TypeValueVo getProtocolVersionMajor() {
		return protocolVersionMajor;
	}
	
	public void setProtocolVersionMajor(TypeValueVo protocolVersionMajor) {
		this.protocolVersionMajor = protocolVersionMajor;
	}
	
	public TypeValueVo getProtocolVersionMinor() {
		return protocolVersionMinor;
	}
	
	public void setProtocolVersionMinor(TypeValueVo protocolVersionMinor) {
		this.protocolVersionMinor = protocolVersionMinor;
	}
		
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		//builder.append(super.toString());
		builder.append("\n\tprotocolVersionMajor=");
		builder.append(protocolVersionMajor);
		builder.append("\n\tprotocolVersionMinor=");
		builder.append(protocolVersionMinor);
		builder.append("\n]");
		return builder.toString();
	}
}