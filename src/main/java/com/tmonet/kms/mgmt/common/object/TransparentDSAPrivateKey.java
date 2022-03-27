package com.tmonet.kms.mgmt.common.object;

public class TransparentDSAPrivateKey {

	private String P;
	private String Q;
	private String G;
	private String X;

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

	public String getX() {
		return X;
	}

	public void setX(String x) {
		X = x;
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
		builder.append(", \n\t\tX=");
		builder.append(X);
		builder.append("\n\t}");
		return builder.toString();
	}

}
