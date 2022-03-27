package com.tmonet.kms.mgmt.client.model;

import com.tmonet.kms.mgmt.common.model.BaseRequest;

public class UpdateCliAuthRequest extends BaseRequest {

	private String createYn;
	private String readYn;
	private String updateYn;
	private String deleteYn;

	public String getCreateYn() {
		return createYn;
	}

	public void setCreateYn(String createYn) {
		this.createYn = createYn;
	}

	public String getReadYn() {
		return readYn;
	}

	public void setReadYn(String readYn) {
		this.readYn = readYn;
	}

	public String getUpdateYn() {
		return updateYn;
	}

	public void setUpdateYn(String updateYn) {
		this.updateYn = updateYn;
	}

	public String getDeleteYn() {
		return deleteYn;
	}

	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tcreateYn=");
		builder.append(createYn);
		builder.append("\n\treadYn=");
		builder.append(readYn);
		builder.append("\n\tupdateYn=");
		builder.append(updateYn);
		builder.append("\n\tdeleteYn=");
		builder.append(deleteYn);
		builder.append("\n]");
		return builder.toString();
	}

}
