package com.tmonet.kms.mgmt.hsmgroup.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;

@JsonInclude(JsonInclude.Include.NON_DEFAULT) // NON_EMPTY 포함 int : 0 인 경우 제외
public class HsmGroupResponse extends BaseResponse {

	public HsmGroupResponse(BaseRequest request) {
		super(request);
	}

	private List<HsmGroup> listHsmGroup;

	private String groupId;

	public List<HsmGroup> getListHsmGroup() {
		return listHsmGroup;
	}

	public void setListHsmGroup(List<HsmGroup> listHsmGroup) {
		this.listHsmGroup = listHsmGroup;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tgroupId=");
		builder.append(groupId);
		builder.append("\n]");
		return builder.toString();
	}

}
