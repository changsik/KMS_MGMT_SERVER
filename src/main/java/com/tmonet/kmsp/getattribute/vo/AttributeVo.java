package com.tmonet.kmsp.getattribute.vo;

import javax.validation.Valid;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tmonet.kmsp.common.vo.TypeValueVo;

@Service
public class AttributeVo {

	@Valid
	@Nullable
	@JsonProperty(value="Extractable")
	private TypeValueVo extractable;
	
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
	@JsonProperty(value="ObjectType")
	private TypeValueVo objectType;
	
	@Valid
	@Nullable
	@JsonProperty(value="CryptographicUsageMask")
	private TypeValueVo cryptographicUsageMask;
	
	public AttributeVo() {
	}
	
	public AttributeVo(TypeValueVo extractable, TypeValueVo cryptographicAlgorithm, TypeValueVo cryptographicLength, TypeValueVo objectType, TypeValueVo cryptographicUsageMask) {
		this.extractable = extractable;
		this.cryptographicAlgorithm = cryptographicAlgorithm;
		this.cryptographicLength = cryptographicLength;
		this.objectType = objectType;
		this.cryptographicUsageMask = cryptographicUsageMask;
	}
	
	public TypeValueVo getExtractable() {
		return extractable;
	}
	
	public void setExtractable(TypeValueVo extractable) {
		this.extractable = extractable;
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
	
	public TypeValueVo getobjectType() {
		return objectType;
	}
	
	public void setObjectType(TypeValueVo objectType) {
		this.objectType = objectType;
	}
	
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
		builder.append("\n\textractable=");
		builder.append(extractable);
		builder.append("\n\tcryptographicAlgorithm=");
		builder.append(cryptographicAlgorithm);
		builder.append("\n\tcryptographicLength=");
		builder.append(cryptographicLength);
		builder.append("\n\tobjectType=");
		builder.append(objectType);
		builder.append("\n\tcryptographicUsageMask=");
		builder.append(cryptographicUsageMask);
		builder.append("\n]");
		return builder.toString();
	}
}