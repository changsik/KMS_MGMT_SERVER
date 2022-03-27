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

import com.tmonet.kms.mgmt.client.model.ClientKeyListResponse;
import com.tmonet.kms.mgmt.client.service.DeleteClientKeyService;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.model.BaseRequest;

@RestController
@RequestMapping("/kms")
public class DeleteClientKeyController {

	private static final Logger logger = LoggerFactory.getLogger(DeleteClientKeyController.class);
	
	@Autowired
	private DeleteClientKeyService service;
	
	@DeleteMapping("/client/key/{clientKeyId}")
	public @ResponseBody ClientKeyListResponse deleteClientKey(@PathVariable String clientKeyId, @RequestBody @Validated BaseRequest request, Errors errors) {
		
		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}
		
		// 2. 클라이언트 키 삭제
		service.deleteClientKeyList(clientKeyId);
		
		// 3. 삭제 결과 전송
		ClientKeyListResponse response = new ClientKeyListResponse(request);
		response.setClientKeyId(clientKeyId);
		
		return response;
		
	}
	
}
