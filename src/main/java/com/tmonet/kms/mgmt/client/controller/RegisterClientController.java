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

import com.tmonet.kms.mgmt.client.model.ClientResponse;
import com.tmonet.kms.mgmt.client.model.RegisterClientRequest;
import com.tmonet.kms.mgmt.client.service.RegisterClientService;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;

@RestController
@RequestMapping("/kms")
public class RegisterClientController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterClientController.class);

	@Autowired
	private RegisterClientService service;

	@PostMapping("/client")
	public @ResponseBody ClientResponse registerClient(@RequestBody @Validated RegisterClientRequest request,
			Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 요청 파라미터 검증
		service.checkParams(request);

		// 3. 클라이언트 정보 등록, 관리 이력 등록
		service.insertClientInfo(request);

		// 4. 등록 결과 전송
		ClientResponse response = new ClientResponse(request);
		response.setClientIp(request.getClientIp());
		return response;
	}

}
