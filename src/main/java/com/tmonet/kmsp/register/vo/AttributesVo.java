package com.tmonet.kmsp.register.vo;

import javax.validation.Valid;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tmonet.kmsp.common.vo.TypeValueVo;

public class AttributesVo {
	@Valid
	@Nullable
	@JsonProperty(value="CryptographicUsageMask")
	private TypeValueVo cryptographicUsageMask;

	@Valid
	@Nullable
	@JsonProperty(value="CryptographicAlgorithm")
	private TypeValueVo cryptographicAlgorithm;

	@Valid
	@Nullable
	@JsonProperty(value="CryptographicLength")
	private TypeValueVo cryptographicLength;

	@Valid
	@Nullable
	@JsonProperty(value="ActivationDate")
	private TypeValueVo activationDate;

	public TypeValueVo getCryptographicUsageMask() {
		return cryptographicUsageMask;
	}
	
	public void setCryptographicUsageMask(TypeValueVo cryptographicUsageMask) {
		this.cryptographicUsageMask = cryptographicUsageMask;
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
	
	public TypeValueVo getActivationDate() {
		return activationDate;
	}
	
	public void setActivationDate(TypeValueVo activationDate) {
		this.activationDate = activationDate;
	}
		
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		//builder.append(super.toString());
		builder.append("\n\tcryptographicUsageMask=");
		builder.append(cryptographicUsageMask);
		builder.append("\n\tcryptographicAlgorithm=");
		builder.append(cryptographicAlgorithm);
		builder.append("\n\tcryptographicLength=");
		builder.append(cryptographicLength);
		builder.append("\n\tactivationDate=");
		builder.append(activationDate);
		builder.append("\n]");
		return builder.toString();
	}
}
