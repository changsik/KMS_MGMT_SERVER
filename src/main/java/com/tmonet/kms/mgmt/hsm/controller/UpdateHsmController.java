package com.tmonet.kms.mgmt.hsm.controller;

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
import com.tmonet.kms.mgmt.hsm.model.HsmResponse;
import com.tmonet.kms.mgmt.hsm.model.UpdateHsmRequest;
import com.tmonet.kms.mgmt.hsm.service.UpdateHsmService;

@RestController
@RequestMapping("/kms")
public class UpdateHsmController {

	private static final Logger logger = LoggerFactory.getLogger(UpdateHsmController.class);

	@Autowired
	private UpdateHsmService service;

	@PatchMapping("/hsm/{hsmId}")
	public @ResponseBody HsmResponse updateHsm(@PathVariable String hsmId,
			@RequestBody @Validated UpdateHsmRequest request, Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 제어서버를 통해 수정 가능한 HSM인지 검증
		service.checkParams(hsmId, request);

		// 3. HSM 수정, 이력 등록
		service.updateHsmInfo(hsmId, request);

		// 4. 수정 결과 전송
		HsmResponse response = new HsmResponse(request);

		response.setHsmId(hsmId);
		return response;

	}

}
