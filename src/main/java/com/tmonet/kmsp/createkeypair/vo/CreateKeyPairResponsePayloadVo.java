package com.tmonet.kmsp.createkeypair.vo;

import javax.validation.Valid;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tmonet.kmsp.common.vo.TypeValueVo;

public class CreateKeyPairResponsePayloadVo {
	@Valid
	@Nullable
	@JsonProperty(value="PrivateKeyUniqueIdentifier")
	private TypeValueVo privateKeyUniqueIdentifier;
	
	@Valid
	@Nullable
	@JsonProperty(value="PublicKeyUniqueIdentifier")
	private TypeValueVo publicKeyUniqueIdentifier;
	
	public TypeValueVo getPrivateKeyUniqueIdentifier() {
		return privateKeyUniqueIdentifier;
	}
	
	public void setPrivateKeyUniqueIdentifier(TypeValueVo privateKeyUniqueIdentifier) {
		this.privateKeyUniqueIdentifier = privateKeyUniqueIdentifier;
	}
	
	public TypeValueVo getPublicKeyUniqueIdentifier() {
		return publicKeyUniqueIdentifier;
	}
	
	public void setPublicKeyUniqueIdentifier(TypeValueVo publicKeyUniqueIdentifier) {
		this.publicKeyUniqueIdentifier = publicKeyUniqueIdentifier;
	}
		
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		//builder.append(super.toString());
		builder.append("\n\tprivateKeyUniqueIdentifier=");
		builder.append(publicKeyUniqueIdentifier);
		builder.append("\n\tpublicKeyUniqueIdentifier=");
		builder.append(publicKeyUniqueIdentifier);
		builder.append("\n]");
		return builder.toString();
	}
}
