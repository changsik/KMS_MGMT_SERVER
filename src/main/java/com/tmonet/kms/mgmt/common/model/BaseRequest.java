package com.tmonet.kms.mgmt.common.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.tmonet.kms.mgmt.common.object.ApiInfo;

public class BaseRequest {

	/**
	 * API 정보
	 */
	@Valid
	@NotNull
	private ApiInfo apiInfo;

	
	
	public BaseRequest() {
		super();
	}

	public BaseRequest(@Valid @NotNull ApiInfo apiInfo) {
		super();
		this.apiInfo = apiInfo;
	}

	public ApiInfo getApiInfo() {
		return apiInfo;
	}

	public void setApiInfo(ApiInfo apiInfo) {
		this.apiInfo = apiInfo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\tapiInfo=");
		builder.append(apiInfo);
		return builder.toString();
	}

}
