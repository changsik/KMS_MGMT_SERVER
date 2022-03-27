package com.tmonet.kms.mgmt.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tmonet.kms.mgmt.client.model.ClientResponse;
import com.tmonet.kms.mgmt.client.service.DeleteClientService;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.model.BaseRequest;

@RestController
@RequestMapping("/kms")
public class DeleteClientController {

	private static final Logger logger = LoggerFactory.getLogger(DeleteClientController.class);

	@Autowired
	private DeleteClientService service;

	@DeleteMapping("/client/{clientIp}")
	public @ResponseBody ClientResponse deleteClient(@PathVariable String clientIp,
			@RequestBody @Validated BaseRequest request, Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 요청 파라미터 검증
		service.checkParams(clientIp);

		// 3. 클라이언트 정보 삭제, 삭제 이력 등록
		service.deleteClientInfo(clientIp);

		// 4. 삭제 결과 전송
		ClientResponse response = new ClientResponse(request);
		response.setClientIp(clientIp);

		return response;
	}

}
