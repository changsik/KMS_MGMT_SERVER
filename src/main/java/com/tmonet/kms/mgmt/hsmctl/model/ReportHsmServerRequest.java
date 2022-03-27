package com.tmonet.kms.mgmt.hsmctl.model;

import javax.validation.constraints.NotNull;

import com.tmonet.kms.mgmt.common.model.BaseRequest;

public class ReportHsmServerRequest extends BaseRequest {

	@NotNull
	private String hsmCtlSvrId;

	private boolean requestDetailReport;

	public String getHsmCtlSvrId() {
		return hsmCtlSvrId;
	}

	public void setHsmCtlSvrId(String hsmCtlSvrId) {
		this.hsmCtlSvrId = hsmCtlSvrId;
	}

	public boolean isRequestDetailReport() {
		return requestDetailReport;
	}

	public void setRequestDetailReport(boolean requestDetailReport) {
		this.requestDetailReport = requestDetailReport;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\thsmCtlSvrId=");
		builder.append(hsmCtlSvrId);
		builder.append("\n\trequestDetailReport=");
		builder.append(requestDetailReport);
		builder.append("\n]");
		return builder.toString();
	}

}
