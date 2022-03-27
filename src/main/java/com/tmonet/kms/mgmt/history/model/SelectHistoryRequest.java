package com.tmonet.kms.mgmt.history.model;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;

import com.tmonet.kms.mgmt.common.model.BaseRequest;

public class SelectHistoryRequest extends BaseRequest {

	@NotNull
	private String resource;

	private ArrayList<String> id;

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public ArrayList<String> getId() {
		return id;
	}

	public void setId(ArrayList<String> id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tresource=");
		builder.append(resource);
		builder.append(", \n\tid=");
		builder.append(id);
		builder.append("\n]");
		return builder.toString();
	}

}
