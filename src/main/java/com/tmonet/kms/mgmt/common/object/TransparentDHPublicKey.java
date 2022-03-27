package com.tmonet.kms.mgmt.common.object;

public class TransparentDHPublicKey {

	private String P;
	private String Q;
	private String G;
	private String J;
	private String Y;

	public String getP() {
		return P;
	}

	public void setP(String p) {
		P = p;
	}

	public String getQ() {
		return Q;
	}

	public void setQ(String q) {
		Q = q;
	}

	public String getG() {
		return G;
	}

	public void setG(String g) {
		G = g;
	}

	public String getJ() {
		return J;
	}

	public void setJ(String j) {
		J = j;
	}

	public String getY() {
		return Y;
	}

	public void setY(String y) {
		Y = y;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\n\t\tP=");
		builder.append(P);
		builder.append(", \n\t\tQ=");
		builder.append(Q);
		builder.append(", \n\t\tG=");
		builder.append(G);
		builder.append(", \n\t\tJ=");
		builder.append(J);
		builder.append(", \n\t\tY=");
		builder.append(Y);
		builder.append("\n\t}");
		return builder.toString();
	}

}
