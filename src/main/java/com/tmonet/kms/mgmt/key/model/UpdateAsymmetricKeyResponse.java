package com.tmonet.kms.mgmt.key.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.BaseResponse;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UpdateAsymmetricKeyResponse extends BaseResponse {

	public UpdateAsymmetricKeyResponse(BaseRequest request) {
		super(request);
	}

	private String privateKeyId;
	private String publicKeyId;
	private Object publicKeyData;
	private Long regDatetime;

	public String getPrivateKeyId() {
		return privateKeyId;
	}

	public void setPrivateKeyId(String privateKeyId) {
		this.privateKeyId = privateKeyId;
	}

	public String getPublicKeyId() {
		return publicKeyId;
	}

	public void setPublicKeyId(String publicKeyId) {
		this.publicKeyId = publicKeyId;
	}

	public Object getPublicKeyData() {
		return publicKeyData;
	}

	public void setPublicKeyData(Object publicKeyData) {
		this.publicKeyData = publicKeyData;
	}

	public Long getRegDatetime() {
		return regDatetime;
	}

	public void setRegDatetime(Long regDatetime) {
		this.regDatetime = regDatetime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[\n");
		builder.append(super.toString());
		builder.append("\n\tprivateKeyId=");
		builder.append(privateKeyId);
		builder.append("\n\tpublicKeyId=");
		builder.append(publicKeyId);
		builder.append("\n\tpublicKeyData=");
		builder.append(publicKeyData);
		builder.append(", \n\tregDatetime=");
		builder.append(regDatetime);
		builder.append("\n]");
		return builder.toString();
	}

}
