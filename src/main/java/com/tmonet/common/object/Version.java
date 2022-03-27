package com.tmonet.common.object;

import javax.validation.constraints.NotNull;

/**
 * Semantic Versioning Specification
 * 
 * @see <a href="https://semver.org/lang/ko/"> Semantic Versioning
 *      Specification</a><br>
 */
public class Version {

	/**
	 * Major 버전
	 */
	@NotNull
	private int major;

	/**
	 * Minor 버전
	 */
	@NotNull	
	private int minor;

	/**
	 * Patch 버전
	 */
	@NotNull
	private int patch;

	/**
	 * Version 객체 생성
	 * 
	 * @param major Major 버전
	 * @param minor Minor 버전
	 * @param patch Patch 버전
	 */
	
	public Version() {
		
	}
	
    public Version(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

	public int getPatch() {
		return patch;
	}

	public void setPatch(int patch) {
		this.patch = patch;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(major);
		builder.append(".");
		builder.append(minor);
		builder.append(".");
		builder.append(patch);
		return builder.toString();
	}

}
