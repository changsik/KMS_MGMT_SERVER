package com.tmonet.kms.mgmt.hsmctl.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RegisterHsmCtlSvrResponse extends BaseResponse {

	private String hsmCtlSvrId;
	private String hsmIpAddress;
	private int hsmPort;
	public RegisterHsmCtlSvrResponse(BaseRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}
	public String getHsmCtlSvrId() {
		return hsmCtlSvrId;
	}
	public void setHsmCtlSvrId(String hsmCtlSvrId) {
		this.hsmCtlSvrId = hsmCtlSvrId;
	}
	public String getHsmIpAddress() {
		return hsmIpAddress;
	}
	public void setHsmIpAddress(String hsmIpAddress) {
		this.hsmIpAddress = hsmIpAddress;
	}
	public int getHsmPort() {
		return hsmPort;
	}
	public void setHsmPort(int hsmPort) {
		this.hsmPort = hsmPort;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\thsmCtlSvrId=");
		builder.append(hsmCtlSvrId);
		builder.append("\n\thsmIpAddress=");
		builder.append(hsmIpAddress);
		builder.append("\n\thsmPort=");
		builder.append(hsmPort);
		builder.append("\n]");
		return builder.toString();
	}
	
}
