package com.tmonet.kms.mgmt.hsmgroup.vo;

public class HsmGroupInfoVo {
	private String GROUP_ID;
	private String SLB_IP_ADDR;
	private int SLB_PORT;
	private String DESCRIPTION;
	private String REG_DTTM;
	private String REG_USER;
	private String UPT_DTTM;
	private String UPT_USER;

	public String getGROUP_ID() {
		return GROUP_ID;
	}

	public void setGROUP_ID(String gROUP_ID) {
		GROUP_ID = gROUP_ID;
	}

	public String getSLB_IP_ADDR() {
		return SLB_IP_ADDR;
	}

	public void setSLB_IP_ADDR(String sLB_IP_ADDR) {
		SLB_IP_ADDR = sLB_IP_ADDR;
	}

	public int getSLB_PORT() {
		return SLB_PORT;
	}

	public void setSLB_PORT(int sLB_PORT) {
		SLB_PORT = sLB_PORT;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
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

	public String getUPT_DTTM() {
		return UPT_DTTM;
	}

	public void setUPT_DTTM(String uPT_DTTM) {
		UPT_DTTM = uPT_DTTM;
	}

	public String getUPT_USER() {
		return UPT_USER;
	}

	public void setUPT_USER(String uPT_USER) {
		UPT_USER = uPT_USER;
	}

}
