package com.tmonet.kms.mgmt.common.object;

public class TransparentECPrivateKey {

	private String curve;
	private String D;

	public String getCurve() {
		return curve;
	}

	public void setCurve(String curve) {
		this.curve = curve;
	}

	public String getD() {
		return D;
	}

	public void setD(String d) {
		D = d;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\n\t\tcurve=");
		builder.append(curve);
		builder.append(", \n\t\tD=");
		builder.append(D);
		builder.append("\n\t}");
		return builder.toString();
	}

}
