package com.tmonet.kms.mgmt.hsm.vo;

import java.util.Date;

public class HsmInfoVo {
	private String HSM_ID;
	private String GROUP_ID;
	private String IP_ADDR;
	private String VENDOR;
	private String SERIAL;
	private String INSTALL_TYPE;
	private String DESCRIPTION;
	private Date REG_DTTM;
	private String REG_USER;
	private Date UPT_DTTM;
	private String UPT_USER;

	public String getHSM_ID() {
		return HSM_ID;
	}

	public void setHSM_ID(String hSM_ID) {
		HSM_ID = hSM_ID;
	}

	public String getGROUP_ID() {
		return GROUP_ID;
	}

	public void setGROUP_ID(String gROUP_ID) {
		GROUP_ID = gROUP_ID;
	}

	public String getIP_ADDR() {
		return IP_ADDR;
	}

	public void setIP_ADDR(String iP_ADDR) {
		IP_ADDR = iP_ADDR;
	}

	public String getVENDOR() {
		return VENDOR;
	}

	public void setVENDOR(String vENDOR) {
		VENDOR = vENDOR;
	}

	public String getSERIAL() {
		return SERIAL;
	}

	public void setSERIAL(String sERIAL) {
		SERIAL = sERIAL;
	}

	public String getINSTALL_TYPE() {
		return INSTALL_TYPE;
	}

	public void setINSTALL_TYPE(String iNSTALL_TYPE) {
		INSTALL_TYPE = iNSTALL_TYPE;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
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
