package com.tmonet.kms.mgmt.common.model;

import com.tmonet.kms.mgmt.common.object.ApiInfo;
import com.tmonet.kms.mgmt.common.object.ApiResult;

public class BaseResponse {

	/**
	 * API 정보
	 */
	private ApiInfo apiInfo;

	/**
	 * 처리 결과
	 */
	private ApiResult result;

	public BaseResponse() {
		
	}

	public BaseResponse(BaseRequest request) {
		this.apiInfo = request.getApiInfo();
		this.result = new ApiResult();
	}

	public BaseResponse(BaseRequest request, ApiResult result) {
		this.apiInfo = request.getApiInfo();
		this.result = result;
	}

	public ApiInfo getApiInfo() {
		return apiInfo;
	}

	public void setApiInfo(ApiInfo apiInfo) {
		this.apiInfo = apiInfo;
	}

	public ApiResult getResult() {
		return result;
	}

	public void setResult(ApiResult result) {
		this.result = result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\tapiInfo=");
		builder.append(apiInfo);
		builder.append(", \n\tresult=");
		builder.append(result);
		return builder.toString();
	}

}
