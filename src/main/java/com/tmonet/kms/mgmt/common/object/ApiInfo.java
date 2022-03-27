package com.tmonet.kms.mgmt.common.object;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.tmonet.common.object.Version;

public class ApiInfo {

	/**
	 * API 고유식별번호
	 */
	@NotNull
	private String apiId;

	/**
	 * API 버전
	 */
	@Valid
	@NotNull
	private Version apiVersion;

	public String getApiId() {
		return apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	public Version getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(Version apiVersion) {
		this.apiVersion = apiVersion;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\n\t\tapiId=");
		builder.append(apiId);
		builder.append(", \n\t\tapiVersion=");
		builder.append(apiVersion);
		builder.append("\n\t}");
		return builder.toString();
	}

}
