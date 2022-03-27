package com.tmonet.kms.mgmt.manager.controller;

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

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.manager.model.MngAuthResponse;
import com.tmonet.kms.mgmt.manager.model.UpdateMngAuthRequest;
import com.tmonet.kms.mgmt.manager.service.UpdateMngAuthService;

@RestController
@RequestMapping("/kms")
public class UpdateMngAuthController {

	private static final Logger logger = LoggerFactory.getLogger(UpdateMngAuthController.class);

	@Autowired
	private UpdateMngAuthService service;

	@PatchMapping("/manager/auth/{managerId}/{serviceId}")
	public @ResponseBody MngAuthResponse updateMngAuth(@PathVariable String managerId, @PathVariable String serviceId,
			@RequestBody @Validated UpdateMngAuthRequest request, Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 요청 파라미터 검증
		service.checkParams(managerId, serviceId);

		// 3. 관리자 접근권한정보 수정, 관리 이력 등록
		service.updateMngAuth(managerId, serviceId, request);

		// 4. 수정 결과 전송
		MngAuthResponse response = new MngAuthResponse(request);
		response.setManagerId(managerId);
		response.setServiceId(serviceId);

		return response;

	}

}
