package com.tmonet.kms.mgmt.keyprofile.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyProfile;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SelectKeyProfileResponse extends BaseResponse {

	@NotNull
	private List<KeyProfile> listKeyProfile;

	public SelectKeyProfileResponse(BaseRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	public List<KeyProfile> getListKeyProfile() {
		return listKeyProfile;
	}

	public void setListKeyProfile(List<KeyProfile> listKeyProfile) {
		this.listKeyProfile = listKeyProfile;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tlistKeyProfile=");
		builder.append(listKeyProfile);
		builder.append("\n]");
		return builder.toString();
	}
}
