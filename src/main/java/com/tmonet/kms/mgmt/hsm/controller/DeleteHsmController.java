package com.tmonet.kms.mgmt.hsm.controller;

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

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.hsm.model.HsmResponse;
import com.tmonet.kms.mgmt.hsm.service.DeleteHsmService;

@RestController
@RequestMapping("/kms")
public class DeleteHsmController {

	private static final Logger logger = LoggerFactory.getLogger(DeleteHsmController.class);

	@Autowired
	private DeleteHsmService service;

	@DeleteMapping("/hsm/{hsmId}")
	public @ResponseBody HsmResponse deleteHsm(@PathVariable String hsmId, @RequestBody @Validated BaseRequest request,
			Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. HSM_ID 검증
		service.checkParams(hsmId);

		// 3. HSM 삭제, 이력 등록
		service.deleteHsmInfo(hsmId);

		// 4. 삭제 결과 전송
		HsmResponse response = new HsmResponse(request);
		response.setHsmId(hsmId);

		return response;
	}

}
