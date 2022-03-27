package com.tmonet.kms.mgmt.manager.controller;

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

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.manager.model.MngAuthResponse;
import com.tmonet.kms.mgmt.manager.model.RegisterMngAuthRequest;
import com.tmonet.kms.mgmt.manager.service.RegisterMngAuthService;

@RestController
@RequestMapping("/kms")
public class RegisterMngAuthController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterMngAuthController.class);

	@Autowired
	private RegisterMngAuthService service;

	@PostMapping("/manager/auth")
	public @ResponseBody MngAuthResponse registerMngAuth(@RequestBody @Validated RegisterMngAuthRequest request,
			Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2.요청 파라미터 검증
		service.checkParams(request);

		// 3. 관리자 접근권한정보 등록, 관리 이력 등록
		service.insertMngAuthInfo(request);

		// 4. 등록 결과 전송
		MngAuthResponse response = new MngAuthResponse(request);
		response.setManagerId(request.getManagerId());
		response.setServiceId(request.getServiceId());

		return response;

	}

}
