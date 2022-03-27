package com.tmonet.kms.mgmt.history.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class History {
	private int histNo;

	// HSM
	private String hsmId;

	// HSM 제어서버
	private String ctlsvrId;

	// 키
	private String keyId;

	// 키 프로파일
	private String profileId;

	// 관리자
	private String id;

	// 클라이언트
	private String ipAddr;

	// 공통
	private String partitionId;
	private String groupId;
	private String attrId;
	private String type;
	private String param;
	private String hmac;
	private String regDttm;
	private String regUser;

	public int getHistNo() {
		return histNo;
	}

	public void setHistNo(int histNo) {
		this.histNo = histNo;
	}

	public String getHsmId() {
		return hsmId;
	}

	public void setHsmId(String hsmId) {
		this.hsmId = hsmId;
	}

	public String getCtlsvrId() {
		return ctlsvrId;
	}

	public void setCtlsvrId(String ctlsvrId) {
		this.ctlsvrId = ctlsvrId;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getPartitionId() {
		return partitionId;
	}

	public void setPartitionId(String partitionId) {
		this.partitionId = partitionId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getAttrId() {
		return attrId;
	}

	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getHmac() {
		return hmac;
	}

	public void setHmac(String hmac) {
		this.hmac = hmac;
	}

	public String getRegDttm() {
		return regDttm;
	}

	public void setRegDttm(String regDttm) {
		this.regDttm = regDttm;
	}

	public String getRegUser() {
		return regUser;
	}

	public void setRegUser(String regUser) {
		this.regUser = regUser;
	}

}
