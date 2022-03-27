package com.tmonet.kmipmanager.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tmonet.kmipmanager.common.service.KmipService;
import com.tmonet.kmipmanager.operation.model.KmipCreateSymmetricKeyRequest;
import com.tmonet.kmipmanager.operation.model.KmipCreateSymmetricKeyResponse;
import com.tmonet.kmipmanager.operation.service.KmipCreateSymmetricKeyService;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;

@RestController
@RequestMapping("/kms/kmip")
public class KmipCreateSymmetricKeyController {

	private static final Logger logger = LoggerFactory.getLogger(KmipCreateSymmetricKeyController.class);

	@Autowired
	private KmipService kmipService;

	@Autowired
	private KmipCreateSymmetricKeyService kmipCreateSymmetricKeyService;

	@PostMapping("/symmetrickey/create")
	public @ResponseBody KmipCreateSymmetricKeyResponse createSymmetricKey(
			@RequestBody KmipCreateSymmetricKeyRequest request) {

		logger.debug("##################### createSymmetricKey Controller Start #########################");
		
		String keyId = "";
		
		try {
			// 1. 클라이언트 키 생성 권한 조회
			kmipService.selectClientAuth(request.getClientIp(), request.getServiceId());
	
			// 2. 키 프로파일 생성
			String keyProfileId = kmipCreateSymmetricKeyService.registerSymmetricKeyProfile(request.getListKeyAttribute());
	
			// 3. 대칭키 생성
			keyId = kmipCreateSymmetricKeyService.createSymmetricKey(keyProfileId, request.getServiceId());

		} catch(KMSException e) {
			logger.debug("KmsException : {}", e);
		} catch(Exception ex) {
			logger.error(logErrorString(getClass().getName().toString(), ex.getMessage(), "createSymmetricKey 에러 "), ex);
			throw new KMSException(KMSErrorCode.UNKNOWN_ERROR);
		}
		
		KmipCreateSymmetricKeyResponse response = new KmipCreateSymmetricKeyResponse();
		response.setKeyId(keyId);

		logger.debug("##################### createSymmetricKey Controller End #########################");
		
		return response;

	}
	
	private String logErrorString(String className, String errCode, String msg) {

		StackTraceElement[] list = Thread.currentThread().getStackTrace();

		int classFind = 0;

		for (int i = 0; i < list.length; i++) {
			if (-1 < list[i].getClassName().toString().indexOf(className)) {
				classFind = i;
				break;
			}
		}

		StackTraceElement lastState = list[classFind + 1];

		return "[" + lastState.getClassName() + " - " + lastState.getMethodName() + "](" + lastState.getLineNumber()
				+ ") [" + errCode + "] > " + msg;
	}

}
