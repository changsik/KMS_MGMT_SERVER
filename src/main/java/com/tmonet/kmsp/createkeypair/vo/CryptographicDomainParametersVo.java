package com.tmonet.kmsp.createkeypair.vo;

import javax.validation.Valid;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tmonet.kmsp.common.vo.TypeValueVo;

public class CryptographicDomainParametersVo {
	@Valid
	@Nullable
	@JsonProperty(value="Qlength")
	private TypeValueVo qlength;
	
	public TypeValueVo getQlength() {
		return qlength;
	}
	
	public void setQlength(TypeValueVo qlength) {
		this.qlength = qlength;
	}
		
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		//builder.append(super.toString());
		builder.append("\n\tqlength=");
		builder.append(qlength);
		builder.append("\n]");
		return builder.toString();
	}
}
