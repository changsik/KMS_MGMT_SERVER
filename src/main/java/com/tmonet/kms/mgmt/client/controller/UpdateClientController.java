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

import com.tmonet.kms.mgmt.client.model.ClientResponse;
import com.tmonet.kms.mgmt.client.model.UpdateClientRequest;
import com.tmonet.kms.mgmt.client.service.UpdateClientService;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;

@RestController
@RequestMapping("/kms")
public class UpdateClientController {

	private static final Logger logger = LoggerFactory.getLogger(UpdateClientController.class);

	@Autowired
	private UpdateClientService service;

	@PatchMapping("/client/{clientIp}")
	public @ResponseBody ClientResponse updateClient(@PathVariable String clientIp,
			@RequestBody @Validated UpdateClientRequest request, Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 요청 파라미터 검증
		service.checkParams(clientIp, request);

		// 3. 클라이언트 정보 수정, 수정 이력 등록
		service.updateClient(clientIp, request);

		// 4. 수정 결과 전송
		ClientResponse response = new ClientResponse(request);
		response.setClientIp(clientIp);

		return response;
	}

}
