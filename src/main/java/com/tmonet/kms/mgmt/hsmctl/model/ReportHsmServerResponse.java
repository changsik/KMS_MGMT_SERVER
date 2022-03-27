package com.tmonet.kms.mgmt.hsmctl.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmServerStatus;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReportHsmServerResponse extends BaseResponse {

	private boolean usable;
	private HsmServerStatus detailReport;

	public ReportHsmServerResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReportHsmServerResponse(BaseRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	public boolean isUsable() {
		return usable;
	}

	public void setUsable(boolean usable) {
		this.usable = usable;
	}

	public HsmServerStatus getDetailReport() {
		return detailReport;
	}

	public void setDetailReport(HsmServerStatus detailReport) {
		this.detailReport = detailReport;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tusable=");
		builder.append(usable);
		builder.append("\n]");
		return builder.toString();
	}
	
}
