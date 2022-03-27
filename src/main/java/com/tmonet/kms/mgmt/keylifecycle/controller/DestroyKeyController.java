package com.tmonet.kms.mgmt.keylifecycle.controller;

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
import com.tmonet.kms.mgmt.keylifecycle.model.KeyLifecycleRequest;
import com.tmonet.kms.mgmt.keylifecycle.model.KeyLifecycleResponse;
import com.tmonet.kms.mgmt.keylifecycle.service.DestroyKeyService;

@RestController
@RequestMapping("/kms")
public class DestroyKeyController {

	private static final Logger logger = LoggerFactory.getLogger(DestroyKeyController.class);

	@Autowired
	private DestroyKeyService service;

	@PostMapping("/keylifecycle/destroy")
	public @ResponseBody KeyLifecycleResponse destroyKey(@RequestBody @Validated KeyLifecycleRequest request,
			Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 요청 파라미터 검증
		service.checkParams(request);

		// 3. 폐기상태로 상태 변경, 변경 이력 추가
		String keyLifecycleState = service.destroyKey(request);

		// 4. 변경 결과 전송
		KeyLifecycleResponse response = new KeyLifecycleResponse(request);
		response.setKeyLifecycleState(keyLifecycleState);

		return response;

	}
}
