package com.tmonet.kms.mgmt.partition.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.common.object.Code;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PartitionAuth {

	private String partitionId;
	private String hsmGroupId;
	private Code partitionType;
	private String partitionPassphrase;

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

	public Code getPartitionType() {
		return partitionType;
	}

	public void setPartitionType(Code partitionType) {
		this.partitionType = partitionType;
	}

	public String getPartitionPassphrase() {
		return partitionPassphrase;
	}

	public void setPartitionPassphrase(String partitionPassphrase) {
		this.partitionPassphrase = partitionPassphrase;
	}

}
