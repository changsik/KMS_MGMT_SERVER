package com.tmonet.kmsp.common.vo;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TypeValueVo {
	@NotNull
	private String type;
	
	@NotNull
	private String value;
	
	public TypeValueVo() {
	}
	
	public TypeValueVo(String type, String value) {
		this.type = type;
		this.value = value;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		//builder.append(super.toString());
		builder.append("\n\ttype=");
		builder.append(type);
		builder.append("\n\tvalue=");
		builder.append(value);
		builder.append("\n]");
		return builder.toString();
	}
}