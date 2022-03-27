package com.tmonet.kms.mgmt.hsm.controller;

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
import com.tmonet.kms.mgmt.hsm.model.RegisterHsmRequest;
import com.tmonet.kms.mgmt.hsm.model.HsmResponse;
import com.tmonet.kms.mgmt.hsm.service.RegisterHsmService;

@RestController
@RequestMapping("/kms")
public class RegisterHsmController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterHsmController.class);

	@Autowired
	private RegisterHsmService service;

	@PostMapping("/hsm")
	public @ResponseBody HsmResponse registerHsm(@RequestBody @Validated RegisterHsmRequest request, Errors errors) {

		// 1.요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 제어서버를 통해 사용가능한 HSM인지 검증
		service.checkParams(request);

		// 3. HSM 등록, 이력 등록
		String regHsmId = service.registerHsmInfo(request);

		// 4. 등록 결과 전송
		HsmResponse response = new HsmResponse(request);
		response.setHsmId(regHsmId);

		return response;
	}

}
