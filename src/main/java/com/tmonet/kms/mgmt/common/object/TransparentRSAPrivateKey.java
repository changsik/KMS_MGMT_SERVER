package com.tmonet.kms.mgmt.common.object;

public class TransparentRSAPrivateKey {

	private String modulus;
	private String privateExponent;
	private String publicExponent;
	private String primeP;
	private String primeQ;
	private String primeExponentP;
	private String primeExponentQ;
	private String crtCoefficient;

	public String getModulus() {
		return modulus;
	}

	public void setModulus(String modulus) {
		this.modulus = modulus;
	}

	public String getPrivateExponent() {
		return privateExponent;
	}

	public void setPrivateExponent(String privateExponent) {
		this.privateExponent = privateExponent;
	}

	public String getPublicExponent() {
		return publicExponent;
	}

	public void setPublicExponent(String publicExponent) {
		this.publicExponent = publicExponent;
	}

	public String getPrimeP() {
		return primeP;
	}

	public void setPrimeP(String primeP) {
		this.primeP = primeP;
	}

	public String getPrimeQ() {
		return primeQ;
	}

	public void setPrimeQ(String primeQ) {
		this.primeQ = primeQ;
	}

	public String getPrimeExponentP() {
		return primeExponentP;
	}

	public void setPrimeExponentP(String primeExponentP) {
		this.primeExponentP = primeExponentP;
	}

	public String getPrimeExponentQ() {
		return primeExponentQ;
	}

	public void setPrimeExponentQ(String primeExponentQ) {
		this.primeExponentQ = primeExponentQ;
	}

	public String getCrtCoefficient() {
		return crtCoefficient;
	}

	public void setCrtCoefficient(String crtCoefficient) {
		this.crtCoefficient = crtCoefficient;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\n\t\tmodulus=");
		builder.append(modulus);
		builder.append(", \n\t\tprivateExponent=");
		builder.append(privateExponent);
		builder.append(", \n\t\tpublicExponent=");
		builder.append(publicExponent);
		builder.append(", \n\t\tprimeP=");
		builder.append(primeP);
		builder.append(", \n\t\tprimeQ=");
		builder.append(primeQ);
		builder.append(", \n\t\tprimeExponentP=");
		builder.append(primeExponentP);
		builder.append(", \n\t\tprimeExponentQ=");
		builder.append(primeExponentQ);
		builder.append(", \n\t\tcrtCoefficient=");
		builder.append(crtCoefficient);
		builder.append("\n\t}");
		return builder.toString();
	}

}
