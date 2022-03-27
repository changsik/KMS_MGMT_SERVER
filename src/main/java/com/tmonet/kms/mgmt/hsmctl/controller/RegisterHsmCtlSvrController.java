package com.tmonet.kms.mgmt.hsmctl.controller;

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
import com.tmonet.kms.mgmt.hsmctl.model.RegisterHsmCtlSvrRequest;
import com.tmonet.kms.mgmt.hsmctl.model.RegisterHsmCtlSvrResponse;
import com.tmonet.kms.mgmt.hsmctl.service.RegisterHsmCtlSvrService;

@RestController
@RequestMapping("/kms")
public class RegisterHsmCtlSvrController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterHsmCtlSvrController.class);

	@Autowired
	private RegisterHsmCtlSvrService service;

	@PostMapping("/hsmctl/server")
	public @ResponseBody RegisterHsmCtlSvrResponse registerHsmCtlSvr(
			@RequestBody @Validated RegisterHsmCtlSvrRequest request, Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 요청 파라미터 검증
		service.checkParams(request);

		// 3. HSM 제어서버 등록, 이력 등록
		service.registerHsmCtlSvr(request);

		// 4. 등록 결과 전송
		RegisterHsmCtlSvrResponse response = new RegisterHsmCtlSvrResponse(request);
		response.setHsmCtlSvrId(request.getHsmCtlSvrId());
		response.setHsmIpAddress(request.getHsmIpAddress());
		response.setHsmPort(request.getHsmPort());

		return response;

	}

}
