package com.tmonet.kms.mgmt.common.object;

public class TransparentRSAPublicKey {

	private String modulus;
	private String publicExponent;

	public String getModulus() {
		return modulus;
	}

	public void setModulus(String modulus) {
		this.modulus = modulus;
	}

	public String getPublicExponent() {
		return publicExponent;
	}

	public void setPublicExponent(String publicExponent) {
		this.publicExponent = publicExponent;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\n\t\tmodulus=");
		builder.append(modulus);
		builder.append(", \n\t\tpublicExponent=");
		builder.append(publicExponent);
		builder.append("\n\t}");
		return builder.toString();
	}

}
