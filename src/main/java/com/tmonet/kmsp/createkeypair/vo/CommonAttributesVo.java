package com.tmonet.kmsp.createkeypair.vo;

import javax.validation.Valid;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tmonet.kmsp.common.vo.TypeValueVo;

public class CommonAttributesVo {

	@Valid
	@Nullable
	@JsonProperty(value="CryptographicAlgorithm")
	private TypeValueVo cryptographicAlgorithm;
	
	@Valid
	@Nullable
	@JsonProperty(value="CryptographicDomainParameters")
	private CryptographicDomainParametersVo cryptographicDomainParameters;
	
	@Valid
	@Nullable
	@JsonProperty(value="ActivationDate")
	private TypeValueVo activationDate;
	
	public TypeValueVo getCryptographicAlgorithm() {
		return cryptographicAlgorithm;
	}
	
	public void setCryptographicAlgorithm(TypeValueVo cryptographicAlgorithm) {
		this.cryptographicAlgorithm = cryptographicAlgorithm;
	}
	
	public CryptographicDomainParametersVo getCryptographicDomainParameters() {
		return cryptographicDomainParameters;
	}
	
	public void setCryptographicDomainParameters(CryptographicDomainParametersVo cryptographicDomainParameters) {
		this.cryptographicDomainParameters = cryptographicDomainParameters;
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
		builder.append("\n\tcryptographicAlgorithm=");
		builder.append(cryptographicAlgorithm);
		builder.append("\n\tcryptographicDomainParameters=");
		builder.append(cryptographicDomainParameters);
		builder.append("\n\tactivationDate=");
		builder.append(activationDate);
		builder.append("\n]");
		return builder.toString();
	}
}
