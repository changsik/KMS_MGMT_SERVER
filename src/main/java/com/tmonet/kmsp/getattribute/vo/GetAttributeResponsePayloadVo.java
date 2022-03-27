package com.tmonet.kmsp.getattribute.vo;

import javax.validation.Valid;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tmonet.kmsp.common.vo.TypeValueVo;

public class GetAttributeResponsePayloadVo {

	@Valid
	@Nullable
	@JsonProperty(value="UniqueIdentifier")
	private TypeValueVo uniqueIdentifier;
	
	@Valid
	@Nullable
	@JsonProperty(value="Attributes")
	private AttributeVo attributes;
	
	public TypeValueVo getUniqueIdentifier() {
		return uniqueIdentifier;
	}
	
	public void setUniqueIdentifier(TypeValueVo uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}
	
	public AttributeVo getAttributes() {
		return attributes;
	}
	
	public void setAttributes(AttributeVo attributes) {
		this.attributes = attributes;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		//builder.append(super.toString());
		builder.append("\n\tuniqueIdentifier=");
		builder.append(uniqueIdentifier);
		builder.append("\n\tattributes=");
		builder.append(attributes);
		builder.append("\n]");
		return builder.toString();
	}
}