package com.tmonet.common.object;

import javax.validation.constraints.NotNull;

public class Code {
	
	@NotNull
	private String code;
	
	@NotNull
	private String name;

	public Code() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Code(@NotNull String code, @NotNull String name) {
		super();
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tcode=");
		builder.append(code);
		builder.append("\n\tname=");
		builder.append(name);
		builder.append("\n]");
		return builder.toString();
	}
}
