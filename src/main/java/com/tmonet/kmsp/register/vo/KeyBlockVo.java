package com.tmonet.kmsp.register.vo;

import javax.validation.Valid;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tmonet.kmsp.common.vo.TypeValueVo;

public class KeyBlockVo {
	@Valid
	@Nullable
	@JsonProperty(value="KeyFormatType")
	private TypeValueVo keyFormatType;
	
	@Valid
	@Nullable
	@JsonProperty(value="KeyValue")
	private KeyValueVo  keyValue;
	
	@Valid
	@Nullable
	@JsonProperty(value="CryptographicAlgorithm")
	private TypeValueVo cryptographicAlgorithm;
	
	@Valid
	@Nullable
	@JsonProperty(value="CryptographicLength")
	private TypeValueVo cryptographicLength;
	
	public TypeValueVo getKeyFormatType() {
		return keyFormatType;
	}
	
	public void setKeyFormatType(TypeValueVo keyFormatType) {
		this.keyFormatType = keyFormatType;
	}
	
	public KeyValueVo getKeyValue() {
		return keyValue;
	}
	
	public void setKeyValue(KeyValueVo keyValue) {
		this.keyValue = keyValue;
	}
	
	public TypeValueVo getCryptographicAlgorithm() {
		return cryptographicAlgorithm;
	}
	
	public void setCryptographicAlgorithm(TypeValueVo cryptographicAlgorithm) {
		this.cryptographicAlgorithm = cryptographicAlgorithm;
	}
	
	public TypeValueVo getCryptographicLength() {
		return cryptographicLength;
	}
	
	public void setCryptographicLength(TypeValueVo cryptographicLength) {
		this.cryptographicLength = cryptographicLength;
	}
		
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		//builder.append(super.toString());
		builder.append("\n\tkeyFormatType=");
		builder.append(keyFormatType);
		builder.append("\n\tkeyValue=");
		builder.append(keyValue);
		builder.append("\n\tcryptographicAlgorithm=");
		builder.append(cryptographicAlgorithm);
		builder.append("\n\tcryptographicLength=");
		builder.append(cryptographicLength);
		builder.append("\n]");
		return builder.toString();
	}
}
