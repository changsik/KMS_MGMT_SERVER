package com.tmonet.kms.mgmt.partition.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.common.object.Code;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Partition {

	private String partitionId;
	private String hsmGroupId;
	private String serviceId;
	private Code partitionType;
	private String partitionPassphrase;
	private String partitionIdent;
	private String description;
	private Long regDatetime;
	private Long lastUpdateDatetime;

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

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
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

	public Long getRegDatetime() {
		return regDatetime;
	}

	public void setRegDatetime(Long regDatetime) {
		this.regDatetime = regDatetime;
	}

	public Long getLastUpdateDatetime() {
		return lastUpdateDatetime;
	}

	public void setLastUpdateDatetime(Long lastUpdateDatetime) {
		this.lastUpdateDatetime = lastUpdateDatetime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tpartitionId=");
		builder.append(partitionId);
		builder.append("\n\thsmGroupId=");
		builder.append(hsmGroupId);
		builder.append("\n\tserviceId=");
		builder.append(serviceId);
		builder.append("\n\tpartitionTypeCode=");
		builder.append(partitionType.getCode());
		builder.append("\n\tpartitionPassphrase=");
		builder.append(partitionPassphrase);
		builder.append("\n\tpartitionIdent=");
		builder.append(partitionIdent);
		builder.append("\n\tdescription=");
		builder.append(description);
		builder.append("\n\tregDatetime=");
		builder.append(regDatetime);
		builder.append("\n]");
		return builder.toString();
	}

}
