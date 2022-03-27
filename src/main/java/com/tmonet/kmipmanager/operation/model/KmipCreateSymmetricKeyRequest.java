package com.tmonet.kmipmanager.operation.model;

import java.util.List;

import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyAttribute;

public class KmipCreateSymmetricKeyRequest extends BaseRequest {

	private String clientIp = "";
	private String serviceId = "";
	private List<KeyAttribute> listKeyAttribute;

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public List<KeyAttribute> getListKeyAttribute() {
		return listKeyAttribute;
	}

	public void setListKeyAttribute(List<KeyAttribute> listKeyAttribute) {
		this.listKeyAttribute = listKeyAttribute;
	}

	@Override
	public String toString() {
		return "CreateSymmetricKeyRequest [clientIp=" + clientIp + ", serviceId=" + serviceId + ", listKeyAttribute="
				+ listKeyAttribute + "]";
	}
	
	
	
}
