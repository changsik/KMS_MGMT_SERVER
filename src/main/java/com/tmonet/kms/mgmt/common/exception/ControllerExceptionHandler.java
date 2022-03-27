package com.tmonet.kms.mgmt.common.exception;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import com.ncipher.nfast.NFException;
import com.tmonet.kmip.kmipenum.EnumResultReason;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.model.ExceptionResponse;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerExceptionHandler {

	/**
	 * HSM 이용 암호 처리 관련 예외오류에 대한 후처리
	 * 
	 * @param ex
	 * @param httpServletRequest
	 * @param webRequest         'body' attribute에 클라이언트로 부터 전달받은 RequestBody 정보가
	 *                           저장되어 있다
	 * @return
	 */
	@ExceptionHandler({ NFException.class })
	protected ResponseEntity<ExceptionResponse> handleHsmException(Exception ex, WebRequest webRequest) {
		BaseRequest baseRequest = BaseRequest.class
				.cast(webRequest.getAttribute("body", RequestAttributes.SCOPE_REQUEST));
		ExceptionResponse response = new ExceptionResponse(baseRequest, EnumResultReason.CryptographicFailure, ex);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 암호 처리 관련 예외오류에 대한 후처리
	 * 
	 * @param ex
	 * @param httpServletRequest
	 * @param webRequest         'body' attribute에 클라이언트로 부터 전달받은 RequestBody 정보가
	 *                           저장되어 있다
	 * @return
	 */
	@ExceptionHandler({ InvalidKeyException.class, NoSuchAlgorithmException.class,
			InvalidParameterSpecException.class })
	protected ResponseEntity<ExceptionResponse> handleCryptoException(Exception ex, WebRequest webRequest) {
		BaseRequest baseRequest = BaseRequest.class
				.cast(webRequest.getAttribute("body", RequestAttributes.SCOPE_REQUEST));
		ExceptionResponse response = new ExceptionResponse(baseRequest,
				EnumResultReason.UnsupportedCryptographicParameters, ex);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 별도 지정되지 않은 일반적인(알수없는) 예외오류에 대한 후처리
	 * 
	 * @param ex
	 * @param httpServletRequest 'baseRequest' attribute에 클라이언트로 부터 전달받은 RequestBody 정보가
	 *                           저장되어 있다
	 * @return
	 */
//    @ExceptionHandler(Exception.class)
//    protected ResponseEntity<ExceptionResponse> handleException(Exception ex, WebRequest webRequest) {
//        BaseRequest baseRequest = BaseRequest.class.cast(webRequest.getAttribute("body", RequestAttributes.SCOPE_REQUEST));
//        ExceptionResponse response = new ExceptionResponse(baseRequest, ex);
//        
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ExceptionResponse> handleException(Exception ex, HttpServletRequest httpRequest) {
		BaseRequest baseRequest = BaseRequest.class.cast(httpRequest.getAttribute("baseRequest"));
		// Controller 진입 전 model 생성 시 에러 발생한 경우 baseRequest값 셋팅 전이므로 null point exception 발생
		// baseRequest null 인 경우 빈 객체 생성
		if (baseRequest == null) {
			baseRequest = new BaseRequest();
		}
		ExceptionResponse response = new ExceptionResponse(baseRequest, ex);

		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Runtime 커스터마이징 예외처리(KMSException)로 발생하는 오류에 대한 후처리
	 * 
	 * @param ex
	 * @param httpServletRequest 'baseRequest' attribute에 클라이언트로 부터 전달받은 RequestBody 정보가
	 *                           저장되어 있다
	 * @return
	 */
	@ExceptionHandler(KMSException.class)
	protected ResponseEntity<ExceptionResponse> handleKMSException(KMSException ex, HttpServletRequest httpRequest) {
		BaseRequest baseRequest = BaseRequest.class.cast(httpRequest.getAttribute("baseRequest"));
		ExceptionResponse response = new ExceptionResponse(baseRequest, ex.getCode());

		HttpStatus status;
		switch (ex.getCode()) {
		case BAD_REQUEST:
			status = HttpStatus.BAD_REQUEST;
			break;
		case UNAUTHORIZED:
			status = HttpStatus.UNAUTHORIZED;
			break;
		case METHOD_NOT_ALLOWED:
			status = HttpStatus.METHOD_NOT_ALLOWED;
			break;
		case INTERNAL_SERVER_ERROR:
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			break;
		default:
			status = HttpStatus.OK;
			break;
		}

		return new ResponseEntity<>(response, status);

	}

}
