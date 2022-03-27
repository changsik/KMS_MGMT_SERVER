package com.tmonet.kms.mgmt.keyprofile.vo;

import java.util.Date;

public class KeyProfileInfoVo {
	private String PROFILE_ID;
	private String NAME;
	private String DESCRIPTION;
	private String STATUS;
	private Date REG_DTTM;
	private String REG_USER;
	private Date UPT_DTTM;
	private String UPT_USER;

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
