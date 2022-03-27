package com.tmonet.kms.mgmt.hsm.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;
import com.tmonet.kms.mgmt.hsm.vo.Hsm;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HsmResponse extends BaseResponse {

	private String hsmId;

	private List<Hsm> listHsm;

	public HsmResponse(BaseRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	public String getHsmId() {
		return hsmId;
	}

	public void setHsmId(String hsmId) {
		this.hsmId = hsmId;
	}

	public List<Hsm> getListHsm() {
		return listHsm;
	}

	public void setListHsm(List<Hsm> listHsm) {
		this.listHsm = listHsm;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\thsmId=");
		builder.append(hsmId);
		builder.append("\n]");
		return builder.toString();
	}

}
