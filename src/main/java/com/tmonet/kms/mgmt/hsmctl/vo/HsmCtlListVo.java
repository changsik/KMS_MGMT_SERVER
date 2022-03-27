package com.tmonet.kms.mgmt.hsmctl.vo;

import java.util.Date;

public class HsmCtlListVo {

	private String CTLSVR_ID;
	private String GROUP_ID;
	private String HSM_ID;
	private int CTL_ORDER;
	private String MODE;
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

	public String getGROUP_ID() {
		return GROUP_ID;
	}

	public void setGROUP_ID(String gROUP_ID) {
		GROUP_ID = gROUP_ID;
	}

	public String getHSM_ID() {
		return HSM_ID;
	}

	public void setHSM_ID(String hSM_ID) {
		HSM_ID = hSM_ID;
	}

	public int getCTL_ORDER() {
		return CTL_ORDER;
	}

	public void setCTL_ORDER(int cTL_ORDER) {
		CTL_ORDER = cTL_ORDER;
	}

	public String getMODE() {
		return MODE;
	}

	public void setMODE(String mODE) {
		MODE = mODE;
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
