package com.tmonet.kms.mgmt.manager.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tmonet.common.object.Code;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Manager {

	private String managerId;
	private String managerPassword;
	private String managerEmail;
	private String managerName;
	private Code managerType;
	private String description;
	private Code usageStatus;
	private Long regDatetime;
	private Long lastUpdateDate;

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getManagerPassword() {
		return managerPassword;
	}

	public void setManagerPassword(String managerPassword) {
		this.managerPassword = managerPassword;
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public Code getManagerType() {
		return managerType;
	}

	public void setManagerType(Code managerType) {
		this.managerType = managerType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Code getUsageStatus() {
		return usageStatus;
	}

	public void setUsageStatus(Code usageStatus) {
		this.usageStatus = usageStatus;
	}

	public Long getRegDatetime() {
		return regDatetime;
	}

	public void setRegDatetime(Long regDatetime) {
		this.regDatetime = regDatetime;
	}

	public Long getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Long lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

}
