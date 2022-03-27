package com.tmonet.kmsp.register.vo;

import javax.validation.Valid;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SymmetricKeyVo {
	@Valid
	@Nullable
	@JsonProperty(value="KeyBlock")
	private KeyBlockVo keyBlock;
	
	public KeyBlockVo getKeyBlock() {
		return keyBlock;
	}
	
	public void setKeyBlock(KeyBlockVo keyBlock) {
		this.keyBlock = keyBlock;
	}
		
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		//builder.append(super.toString());
		builder.append("\n\tkeyBlock=");
		builder.append(keyBlock);
		builder.append("\n]");
		return builder.toString();
	}
}
