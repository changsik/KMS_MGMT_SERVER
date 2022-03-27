package com.tmonet.kms.mgmt.keyprofile.vo;

public class KeyProfileHistoryVo {

	private int HIST_NO;
	private String PROFILE_ID;
	private String ATTR_ID;
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

	public String getPROFILE_ID() {
		return PROFILE_ID;
	}

	public void setPROFILE_ID(String pROFILE_ID) {
		PROFILE_ID = pROFILE_ID;
	}

	public String getATTR_ID() {
		return ATTR_ID;
	}

	public void setATTR_ID(String aTTR_ID) {
		ATTR_ID = aTTR_ID;
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
