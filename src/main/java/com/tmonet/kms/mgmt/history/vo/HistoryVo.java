package com.tmonet.kms.mgmt.history.vo;

public class HistoryVo {
	private int HIST_NO;

	// HSM
	private String HSM_ID;

	// HSM 제어서버
	private String CTLSVR_ID;

	// 키
	private String KEY_ID;

	// 키 프로파일
	private String PROFILE_ID;

	// 관리자, 관리자 권한
	private String ID;

	// 클라이언트, 클라이언트 권한
	private String IP_ADDR;

	// 공통
	private String PARTITION_ID;
	private String GROUP_ID;
	private String ATTR_ID;
	private String SERVICE_ID;
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

	public String getCTLSVR_ID() {
		return CTLSVR_ID;
	}

	public void setCTLSVR_ID(String cTLSVR_ID) {
		CTLSVR_ID = cTLSVR_ID;
	}

	public String getKEY_ID() {
		return KEY_ID;
	}

	public void setKEY_ID(String kEY_ID) {
		KEY_ID = kEY_ID;
	}

	public String getPROFILE_ID() {
		return PROFILE_ID;
	}

	public void setPROFILE_ID(String pROFILE_ID) {
		PROFILE_ID = pROFILE_ID;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getIP_ADDR() {
		return IP_ADDR;
	}

	public void setIP_ADDR(String iP_ADDR) {
		IP_ADDR = iP_ADDR;
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

	public String getATTR_ID() {
		return ATTR_ID;
	}

	public void setATTR_ID(String aTTR_ID) {
		ATTR_ID = aTTR_ID;
	}

	public String getSERVICE_ID() {
		return SERVICE_ID;
	}

	public void setSERVICE_ID(String sERVICE_ID) {
		SERVICE_ID = sERVICE_ID;
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
