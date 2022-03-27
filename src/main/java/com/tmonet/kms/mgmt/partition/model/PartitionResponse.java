package com.tmonet.kms.mgmt.partition.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;
import com.tmonet.kms.mgmt.partition.vo.Partition;
import com.tmonet.kms.mgmt.partition.vo.PartitionAuth;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PartitionResponse extends BaseResponse {

	private String partitionId;
	private String hsmGroupId;
	private List<Partition> listPartition;
	private List<PartitionAuth> listPartitionAuth;

	public PartitionResponse(BaseRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

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

	public List<Partition> getListPartition() {
		return listPartition;
	}

	public void setListPartition(List<Partition> listPartition) {
		this.listPartition = listPartition;
	}

	public List<PartitionAuth> getListPartitionAuth() {
		return listPartitionAuth;
	}

	public void setListPartitionAuth(List<PartitionAuth> listPartitionAuth) {
		this.listPartitionAuth = listPartitionAuth;
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
		builder.append("\n]");
		return builder.toString();
	}

}
