package com.tmonet.kms.mgmt.common.object;

public class CryptographicParameter {

	private String cryptographicAlgorithm;
	private String blockCipherMode;
	private String paddingRule;
	private String iv;
	private String hashingAlgorithm;
	private String tag;

	public String getCryptographicAlgorithm() {
		return cryptographicAlgorithm;
	}

	public void setCryptographicAlgorithm(String cryptographicAlgorithm) {
		this.cryptographicAlgorithm = cryptographicAlgorithm;
	}

	public String getBlockCipherMode() {
		return blockCipherMode;
	}

	public void setBlockCipherMode(String blockCipherMode) {
		this.blockCipherMode = blockCipherMode;
	}

	public String getPaddingRule() {
		return paddingRule;
	}

	public void setPaddingRule(String paddingRule) {
		this.paddingRule = paddingRule;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	public String getHashingAlgorithm() {
		return hashingAlgorithm;
	}

	public void setHashingAlgorithm(String hashingAlgorithm) {
		this.hashingAlgorithm = hashingAlgorithm;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "CryptographicParameter [cryptographicAlgorithm=" + cryptographicAlgorithm + ", blockCipherMode="
				+ blockCipherMode + ", paddingRule=" + paddingRule + ", iv=" + iv + ", hashingAlgorithm="
				+ hashingAlgorithm + ", tag=" + tag + "]";
	}

}
