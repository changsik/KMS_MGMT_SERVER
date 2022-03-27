package com.tmonet.kmsp.createkeypair.vo;

import javax.validation.Valid;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tmonet.kmsp.common.vo.TypeValueVo;

public class PublicKeyAttributesVo {
	@Valid
	@Nullable
	@JsonProperty(value="CryptographicUsageMask")
	private TypeValueVo cryptographicUsageMask;
	
	public TypeValueVo getCryptographicUsageMask() {
		return cryptographicUsageMask;
	}
	
	public void setCryptographicUsageMask(TypeValueVo cryptographicUsageMask) {
		this.cryptographicUsageMask = cryptographicUsageMask;
	}
		
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		//builder.append(super.toString());
		builder.append("\n\tcryptographicUsageMask=");
		builder.append(cryptographicUsageMask);
		builder.append("\n]");
		return builder.toString();
	}
}
