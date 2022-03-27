package com.tmonet.kms.mgmt.keyprofile.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;
import com.tmonet.kms.mgmt.common.object.ApiResult;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyAttribute;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RegisterKeyProfileResponse extends BaseResponse {
	private List<KeyAttribute> listKeyAttribute;
	private String keyProfileId;
	private String keyProfileName;
	private String usageStatus;
	private Long regDatetime;
	
	public RegisterKeyProfileResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RegisterKeyProfileResponse(BaseRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	public List<KeyAttribute> getListKeyAttribute() {
		return listKeyAttribute;
	}

	public void setListKeyAttribute(List<KeyAttribute> listKeyAttribute) {
		this.listKeyAttribute = listKeyAttribute;
	}

	public String getKeyProfileId() {
		return keyProfileId;
	}

	public void setKeyProfileId(String keyProfileId) {
		this.keyProfileId = keyProfileId;
	}

	public String getKeyProfileName() {
		return keyProfileName;
	}

	public void setKeyProfileName(String keyProfileName) {
		this.keyProfileName = keyProfileName;
	}

	public String getUsageStatus() {
		return usageStatus;
	}

	public void setUsageStatus(String usageStatus) {
		this.usageStatus = usageStatus;
	}

	public Long getRegDatetime() {
		return regDatetime;
	}

	public void setRegDatetime(Long regDatetime) {
		this.regDatetime = regDatetime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tlistKeyAttribute=");
		builder.append(listKeyAttribute);
		builder.append(", \n\tkeyProfileId=");
		builder.append(keyProfileId);
		builder.append(", \n\tkeyProfileName=");
		builder.append(keyProfileName);
		builder.append(", \n\tusageStatus=");
		builder.append(usageStatus);
		builder.append(", \n\tregDatetime=");
		builder.append(regDatetime);
		builder.append("\n]");
		return builder.toString();
	}

}
