package com.tmonet.kms.mgmt.common.vo;

public class HsmHistoryVo {
	private int HIST_NO;
	private String HSM_ID;
	private String PARTITION_ID;
	private String GROUP_ID;
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

	public String getHSM_ID() {
		return HSM_ID;
	}

	public void setHSM_ID(String hSM_ID) {
		HSM_ID = hSM_ID;
	}

	public String getPARTITION_ID() {
		return PARTITION_ID;
	}

	public void setPARTITION_ID(String pARTITION_ID) {
		PARTITION_ID = pARTITION_ID;
	}

	public String getGROUP_ID() {
		return GROUP_ID;
	}

	public void setGROUP_ID(String gROUP_ID) {
		GROUP_ID = gROUP_ID;
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
