package com.tmonet.kms.mgmt.key.vo;

public class KeyProfileInfoVo {
	private String PROFILE_ID;
	private String NAME;
	private String DESCRIPTION;
	private String STATUS;
	private String REG_DTTM;
	private String REG_USER;

	public String getPROFILE_ID() {
		return PROFILE_ID;
	}

	public void setPROFILE_ID(String pROFILE_ID) {
		PROFILE_ID = pROFILE_ID;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
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
