package com.tmonet.kmsp.register.vo;

import javax.validation.Valid;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tmonet.kmsp.common.vo.TypeValueVo;

public class RegisterSymmKeyRequestPayloadVo {
	@Valid
	@Nullable
	@JsonProperty(value="ObjectType")
	private TypeValueVo objectType;

	@Valid
	@Nullable
	@JsonProperty(value="Attributes")
	private AttributesVo attributes;

	@Valid
	@Nullable
	@JsonProperty(value="SymmetricKey")
	private SymmetricKeyVo symmetricKey;
	
	public TypeValueVo getObjectType() {
		return objectType;
	}
	
	public void setObjectType(TypeValueVo objectType) {
		this.objectType = objectType;
	}
	
	public AttributesVo getAttributes() {
		return attributes;
	}
	
	public void setAttributes(AttributesVo attributes) {
		this.attributes = attributes;
	}
	
	public SymmetricKeyVo getSymmetricKey() {
		return symmetricKey;
	}
	
	public void setSymmetricKey(SymmetricKeyVo symmetricKey) {
		this.symmetricKey = symmetricKey;
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
		builder.append("\n\tsymmetricKey=");
		builder.append(symmetricKey);
		builder.append("\n]");
		return builder.toString();
	}
}
