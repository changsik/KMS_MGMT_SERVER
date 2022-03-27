package com.tmonet.kmsp.create.vo;

import javax.validation.Valid;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tmonet.kmsp.common.vo.TypeValueVo;

public class CreateSymmKeyRequestPayloadVo {

	@Valid
	@Nullable
	@JsonProperty(value="ObjectType")
	private TypeValueVo objectType;
	
	@Valid
	@Nullable
	@JsonProperty(value="Attributes")
	private CreateSymmKeyAttributeVo attributes;
	
	public TypeValueVo getObjectType() {
		return objectType;
	}

	public void setObjectType(TypeValueVo objectType) {
		this.objectType = objectType;
	}

	public CreateSymmKeyAttributeVo getAttributes() {
		return attributes;
	}

	public void setAttributes(CreateSymmKeyAttributeVo attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		//builder.append(super.toString());
		builder.append("\n\tobjectType=");
		builder.append(objectType);
		builder.append("\n\tattributes=");
		builder.append(attributes);
		builder.append("\n]");
		return builder.toString();
	}
}