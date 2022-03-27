package com.tmonet.kms.mgmt.hsmgroup.model;

public class HsmGroup {
	private String groupId;
	private String vIpAddr;
	private int vPort;
	private String description;
	private String regDttm;
	private String regUser;
	private String uptDttm;
	private String uptUser;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getvIpAddr() {
		return vIpAddr;
	}

	public void setvIpAddr(String vIpAddr) {
		this.vIpAddr = vIpAddr;
	}

	public int getvPort() {
		return vPort;
	}

	public void setvPort(int vPort) {
		this.vPort = vPort;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getUptDttm() {
		return uptDttm;
	}

	public void setUptDttm(String uptDttm) {
		this.uptDttm = uptDttm;
	}

	public String getUptUser() {
		return uptUser;
	}

	public void setUptUser(String uptUser) {
		this.uptUser = uptUser;
	}

}
