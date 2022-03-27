package com.tmonet.kms.mgmt.common.object;

public class TransparentECPublicKey {

	private String curve;
	private String affineCoordinateX;
	private String affineCoordinateY;

	public String getCurve() {
		return curve;
	}

	public void setCurve(String curve) {
		this.curve = curve;
	}

	public String getAffineCoordinateX() {
		return affineCoordinateX;
	}

	public void setAffineCoordinateX(String affineCoordinateX) {
		this.affineCoordinateX = affineCoordinateX;
	}

	public String getAffineCoordinateY() {
		return affineCoordinateY;
	}

	public void setAffineCoordinateY(String affineCoordinateY) {
		this.affineCoordinateY = affineCoordinateY;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\n\t\tcurve=");
		builder.append(curve);
		builder.append(", \n\t\taffineCoordinateX=");
		builder.append(affineCoordinateX);
		builder.append(", \n\t\taffineCoordinateY=");
		builder.append(affineCoordinateY);
		builder.append("\n\t}");
		return builder.toString();
	}

}
