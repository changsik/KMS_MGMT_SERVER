package com.tmonet.kms.mgmt.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tmonet.kms.mgmt.client.model.CliAuthResponse;
import com.tmonet.kms.mgmt.client.model.UpdateCliAuthRequest;
import com.tmonet.kms.mgmt.client.service.UpdateCliAuthService;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;

@RestController
@RequestMapping("/kms")
public class UpdateCliAuthController {

	private static final Logger logger = LoggerFactory.getLogger(UpdateCliAuthController.class);

	@Autowired
	private UpdateCliAuthService service;

	@PatchMapping("/client/auth/{clientIp}/{serviceId}")
	public @ResponseBody CliAuthResponse updateCliAuth(@PathVariable String clientIp, @PathVariable String serviceId,
			@RequestBody @Validated UpdateCliAuthRequest request, Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 요청 파라미터 검증
		service.checkParams(clientIp, serviceId);

		// 3. 클라이언트 접근권한정보 수정, 수정 이력 등록
		service.updateCliAuthInfo(clientIp, serviceId, request);

		// 4. 수정 결과 전송
		CliAuthResponse response = new CliAuthResponse(request);
		response.setClientIp(clientIp);
		response.setServiceId(serviceId);
		return response;

	}

}
