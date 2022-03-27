package com.tmonet.kmsp.register.vo;

import javax.validation.Valid;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tmonet.kmsp.common.vo.TypeValueVo;

public class KeyValueVo {
	@Valid
	@Nullable
	@JsonProperty(value="KeyMaterial")
	private TypeValueVo keyMaterial;
	
	public TypeValueVo getKeyMaterial() {
		return keyMaterial;
	}
	
	public void setKeyMaterial(TypeValueVo keyMaterial) {
		this.keyMaterial = keyMaterial;
	}
		
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		//builder.append(super.toString());
		builder.append("\n\tkeyMaterial=");
		builder.append(keyMaterial);
		builder.append("\n]");
		return builder.toString();
	}
}
