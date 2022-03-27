package com.tmonet.kms.mgmt.client.vo;

public class ClientHistoryVo {

	private int HIST_NO;
	private String IP_ADDR;
	private String TYPE;
	private String PARAM;
	private String HMAC;
	private String REG_DTTM;
	private String REG_USER;

	public int getHIST_NO() {
		return HIST_NO;
	}

	public void setHIST_NO(int hIST_NO) {
		HIST_NO = hIST_NO;
	}

	public String getIP_ADDR() {
		return IP_ADDR;
	}

	public void setIP_ADDR(String iP_ADDR) {
		IP_ADDR = iP_ADDR;
	}

	public String getTYPE() {
		return TYPE;
	}

	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}

	public String getPARAM() {
		return PARAM;
	}

	public void setPARAM(String pARAM) {
		PARAM = pARAM;
	}

	public String getHMAC() {
		return HMAC;
	}

	public void setHMAC(String hMAC) {
		HMAC = hMAC;
	}

	public String getREG_DTTM() {
		return REG_DTTM;
	}

	public void setREG_DTTM(String rEG_DTTM) {
		REG_DTTM = rEG_DTTM;
	}

	public String getREG_USER() {
		return REG_USER;
	}

	public void setREG_USER(String rEG_USER) {
		REG_USER = rEG_USER;
	}

}
