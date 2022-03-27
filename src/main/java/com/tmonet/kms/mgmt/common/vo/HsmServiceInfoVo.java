package com.tmonet.kms.mgmt.common.vo;

public class HsmServiceInfoVo {
	private String PARTITION_ID;
	private String GROUP_ID;
	private String SLB_IP_ADDR;
	private int SLB_PORT;

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

}
