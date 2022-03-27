package com.tmonet.kmsp.create.vo;

import javax.validation.Valid;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tmonet.kmsp.common.vo.TypeValueVo;

@Service
public class CreateSymmKeyAttributeVo {
	
	@Valid
	@Nullable
	@JsonProperty(value="CryptographicAlgorithm")
	private TypeValueVo cryptographicAlgorithm;
	
	@Valid
	@Nullable
	@JsonProperty(value="CryptographicLength")
	private TypeValueVo cryptographicLength;
	
	public CreateSymmKeyAttributeVo() {}
	
	public CreateSymmKeyAttributeVo(TypeValueVo cryptographicAlgorithm, TypeValueVo cryptographicLength) {
		this.cryptographicAlgorithm = cryptographicAlgorithm;
		this.cryptographicLength = cryptographicLength;
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
		builder.append("\n\tcryptographicAlgorithm=");
		builder.append(cryptographicAlgorithm);
		builder.append("\n\tcryptographicLength=");
		builder.append(cryptographicLength);
		builder.append("\n]");
		return builder.toString();
	}
}