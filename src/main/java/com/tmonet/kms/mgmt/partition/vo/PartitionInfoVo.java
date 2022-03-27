package com.tmonet.kms.mgmt.partition.vo;

import java.util.Date;

public class PartitionInfoVo {

	private String PARTITION_ID;
	private String GROUP_ID;
	private String SERVICE_ID;
	private String PASSWORD;
	private String PROTECT_MODE;
	private String IDENT;
	private String DESCRIPTION;
	private Date REG_DTTM;
	private String REG_USER;
	private Date UPT_DTTM;
	private String UPT_USER;

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

	public String getSERVICE_ID() {
		return SERVICE_ID;
	}

	public void setSERVICE_ID(String sERVICE_ID) {
		SERVICE_ID = sERVICE_ID;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public String getPROTECT_MODE() {
		return PROTECT_MODE;
	}

	public void setPROTECT_MODE(String pROTECT_MODE) {
		PROTECT_MODE = pROTECT_MODE;
	}

	public String getIDENT() {
		return IDENT;
	}

	public void setIDENT(String iDENT) {
		IDENT = iDENT;
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
