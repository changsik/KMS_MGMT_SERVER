package com.tmonet.kms.mgmt.hsmctl.vo;

import java.util.Date;

public class HsmCtlSvrInfoVo {

	private String CTLSVR_ID;
	private String IP_ADDR;
	private int PORT;
	private String DESCRIPTION;
	private Date REG_DTTM;
	private String REG_USER;
	private Date UPT_DTTM;
	private String UPT_USER;

	public String getCTLSVR_ID() {
		return CTLSVR_ID;
	}

	public void setCTLSVR_ID(String cTLSVR_ID) {
		CTLSVR_ID = cTLSVR_ID;
	}

	public String getIP_ADDR() {
		return IP_ADDR;
	}

	public void setIP_ADDR(String iP_ADDR) {
		IP_ADDR = iP_ADDR;
	}

	public int getPORT() {
		return PORT;
	}

	public void setPORT(int pORT) {
		PORT = pORT;
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
