package com.tmonet.kms.mgmt.partition.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RegisterPartitionRequest extends BaseRequest {

	@NotNull
	private String partitionId;

	@NotNull
	private String hsmGroupId;

	@NotNull
	private String partitionType;

	private String partitionPassphrase;
	private String partitionIdent;
	private String description;

	public String getPartitionId() {
		return partitionId;
	}

	public void setPartitionId(String partitionId) {
		this.partitionId = partitionId;
	}

	public String getHsmGroupId() {
		return hsmGroupId;
	}

	public void setHsmGroupId(String hsmGroupId) {
		this.hsmGroupId = hsmGroupId;
	}

	public String getPartitionType() {
		return partitionType;
	}

	public void setPartitionType(String partitionType) {
		this.partitionType = partitionType;
	}

	public String getPartitionPassphrase() {
		return partitionPassphrase;
	}

	public void setPartitionPassphrase(String partitionPassphrase) {
		this.partitionPassphrase = partitionPassphrase;
	}

	public String getPartitionIdent() {
		return partitionIdent;
	}

	public void setPartitionIdent(String partitionIdent) {
		this.partitionIdent = partitionIdent;
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
		builder.append("\n\tpartitionId=");
		builder.append(partitionId);
		builder.append("\n\tpartitionType=");
		builder.append(partitionType);
		builder.append("\n\tpartitionPassphrase=");
		builder.append(partitionPassphrase);
		builder.append("\n\tpartitionIdent=");
		builder.append(partitionIdent);
		builder.append("\n\tdescription=");
		builder.append(description);
		builder.append("\n]");
		return builder.toString();
	}

}
