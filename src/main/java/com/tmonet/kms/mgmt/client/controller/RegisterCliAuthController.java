package com.tmonet.kms.mgmt.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tmonet.kms.mgmt.client.model.CliAuthResponse;
import com.tmonet.kms.mgmt.client.model.RegisterCliAuthRequest;
import com.tmonet.kms.mgmt.client.service.RegisterCliAuthService;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;

@RestController
@RequestMapping("/kms")
public class RegisterCliAuthController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterCliAuthController.class);

	@Autowired
	private RegisterCliAuthService service;

	@PostMapping("/client/auth")
	public @ResponseBody CliAuthResponse registerCliAuth(@RequestBody @Validated RegisterCliAuthRequest request,
			Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 요청 파라미터 검증
		service.checkParams(request);

		// 3. 클라이언트 접근권한정보 등록, 관리 이력 등록
		service.insertCliAuthInfo(request);

		// 4. 등록 결과 전송
		CliAuthResponse response = new CliAuthResponse(request);
		response.setClientIp(request.getClientIp());
		response.setServiceId(request.getServiceId());
		return response;

	}

}
