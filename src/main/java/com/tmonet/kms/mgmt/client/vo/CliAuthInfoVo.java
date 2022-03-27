package com.tmonet.kms.mgmt.client.vo;

import java.util.Date;

public class CliAuthInfoVo {

	private String IP_ADDR;
	private String SERVICE_ID;
	private String CREATE_YN;
	private String READ_YN;
	private String UPDATE_YN;
	private String DELETE_YN;
	private Date REG_DTTM;
	private String REG_USER;
	private Date UPT_DTTM;
	private String UPT_USER;

	public String getIP_ADDR() {
		return IP_ADDR;
	}

	public void setIP_ADDR(String iP_ADDR) {
		IP_ADDR = iP_ADDR;
	}

	public String getSERVICE_ID() {
		return SERVICE_ID;
	}

	public void setSERVICE_ID(String sERVICE_ID) {
		SERVICE_ID = sERVICE_ID;
	}

	public String getCREATE_YN() {
		return CREATE_YN;
	}

	public void setCREATE_YN(String cREATE_YN) {
		CREATE_YN = cREATE_YN;
	}

	public String getREAD_YN() {
		return READ_YN;
	}

	public void setREAD_YN(String rEAD_YN) {
		READ_YN = rEAD_YN;
	}

	public String getUPDATE_YN() {
		return UPDATE_YN;
	}

	public void setUPDATE_YN(String uPDATE_YN) {
		UPDATE_YN = uPDATE_YN;
	}

	public String getDELETE_YN() {
		return DELETE_YN;
	}

	public void setDELETE_YN(String dELETE_YN) {
		DELETE_YN = dELETE_YN;
	}

	public Date getREG_DTTM() {
		return REG_DTTM;
	}

	public void setREG_DTTM(Date rEG_DTTM) {
		REG_DTTM = rEG_DTTM;
	}

	public String getREG_USER() {
		return REG_USER;
	}

	public void setREG_USER(String rEG_USER) {
		REG_USER = rEG_USER;
	}

	public Date getUPT_DTTM() {
		return UPT_DTTM;
	}

	public void setUPT_DTTM(Date uPT_DTTM) {
		UPT_DTTM = uPT_DTTM;
	}

	public String getUPT_USER() {
		return UPT_USER;
	}

	public void setUPT_USER(String uPT_USER) {
		UPT_USER = uPT_USER;
	}

}
