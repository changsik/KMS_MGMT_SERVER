package com.tmonet.kms.mgmt.common.object;

public class ApiResult {

	/**
	 * 처리 결과
	 */
	private String resultStatus;
	/**
	 * 에러 코드
	 */
	private String errorCode;

	/**
	 * 에러 메시지
	 */
	private String errorMessage;

	/**
	 * 기본값(처리결과 정상)으로 API 처리결과 객체 생성
	 */
	public ApiResult() {
		super();
		setResultStatus(0);
		setErrorCode(0);
		setErrorMessage("");
	}

	/**
	 * 설정된 파라메타 값으로 API 처리결과 객체 생성
	 * 
	 * @param resultStatus 처리 결과
	 * @param errorCode    에러 코드
	 * @param errorMessage 에러 메시지
	 */
	public ApiResult(String resultStatus, String errorCode, String errorMessage) {
		super();
		this.resultStatus = resultStatus;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}

	public void setResultStatus(int resultStatus) {
		this.resultStatus = String.format("%08X", resultStatus);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = String.format("%08X", errorCode);
		;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\n\t\tresultStatus=");
		builder.append(resultStatus);
		builder.append(", \n\t\terrorCode=");
		builder.append(errorCode);
		builder.append(", \n\t\terrorMessage=");
		builder.append(errorMessage);
		builder.append("\n\t}");
		return builder.toString();
	}

}
