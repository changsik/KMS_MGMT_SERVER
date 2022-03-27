package com.tmonet.kms.mgmt.keyprofile.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyAttribute;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UpdateKeyProfileRequest extends BaseRequest {

	@NotNull
	@Valid
	private List<KeyAttribute> listKeyAttribute;

	private String usageStatus;
	private String keyProfileName;
	private String description;

	public List<KeyAttribute> getListKeyAttribute() {
		return listKeyAttribute;
	}

	public void setListKeyAttribute(List<KeyAttribute> listKeyAttribute) {
		this.listKeyAttribute = listKeyAttribute;
	}

	public String getUsageStatus() {
		return usageStatus;
	}

	public void setUsageStatus(String usageStatus) {
		this.usageStatus = usageStatus;
	}

	public String getKeyProfileName() {
		return keyProfileName;
	}

	public void setKeyProfileName(String keyProfileName) {
		this.keyProfileName = keyProfileName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tlistKeyAttribute=");
		builder.append(listKeyAttribute);
		builder.append(", \n\tkeyProfileName=");
		builder.append(keyProfileName);
		builder.append(", \n\tdescription=");
		builder.append(description);
		builder.append(", \n\tusageStatus=");
		builder.append(usageStatus);
		builder.append("\n]");
		return builder.toString();
	}

}
