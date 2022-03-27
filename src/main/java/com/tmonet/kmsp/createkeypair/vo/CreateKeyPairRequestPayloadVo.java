package com.tmonet.kmsp.createkeypair.vo;

import javax.validation.Valid;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateKeyPairRequestPayloadVo {
	
	@Valid
	@Nullable
	@JsonProperty(value="CommonAttributes")
	private CommonAttributesVo commonAttributes;
	
	@Valid
	@Nullable
	@JsonProperty(value="PrivateKeyAttributes")
	private PrivateKeyAttributesVo privateKeyAttributes;
	
	@Valid
	@Nullable
	@JsonProperty(value="PublicKeyAttributes")
	private PublicKeyAttributesVo publicKeyAttributes;
	
	public CommonAttributesVo getCommonAttributes() {
		return commonAttributes;
	}
	
	public void setCommonAttributes(CommonAttributesVo commonAttributes) {
		this.commonAttributes = commonAttributes;
	}
	
	public PrivateKeyAttributesVo getPrivateKeyAttributes() {
		return privateKeyAttributes;
	}
	
	public void setPrivateKeyAttributes(PrivateKeyAttributesVo privateKeyAttributes) {
		this.privateKeyAttributes = privateKeyAttributes;
	}
	

	public PublicKeyAttributesVo getPublicKeyAttributes() {
		return publicKeyAttributes;
	}
	
	public void setPublicKeyAttributes(PublicKeyAttributesVo publicKeyAttributes) {
		this.publicKeyAttributes = publicKeyAttributes;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		//builder.append(super.toString());
		builder.append("\n\tcommonAttributes=");
		builder.append(commonAttributes);
		builder.append("\n\tprivateKeyAttributes=");
		builder.append(privateKeyAttributes);
		builder.append("\n\tpublicKeyAttributes=");
		builder.append(publicKeyAttributes);
		builder.append("\n]");
		return builder.toString();
	}
}
