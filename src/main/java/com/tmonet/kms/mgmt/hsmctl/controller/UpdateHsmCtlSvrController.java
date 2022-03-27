package com.tmonet.kms.mgmt.hsmctl.controller;

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
import com.tmonet.kms.mgmt.hsmctl.model.UpdateHsmCtlSvrRequest;
import com.tmonet.kms.mgmt.hsmctl.model.UpdateHsmCtlSvrResponse;
import com.tmonet.kms.mgmt.hsmctl.service.UpdateHsmCtlSvrService;

@RestController
@RequestMapping("/kms")
public class UpdateHsmCtlSvrController {

	private static final Logger logger = LoggerFactory.getLogger(UpdateHsmCtlSvrController.class);

	@Autowired
	private UpdateHsmCtlSvrService service;

	@PatchMapping("/hsmctl/server/{hsmCtlSvrId}")
	public @ResponseBody UpdateHsmCtlSvrResponse updateHsmCtlSvr(@PathVariable String hsmCtlSvrId,
			@RequestBody @Validated UpdateHsmCtlSvrRequest request, Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 요청 파라미터 검증
		service.checkParams(hsmCtlSvrId, request);

		// 3. HSM 제어서버 정보 수정, 수정 이력 등록
		service.updateHsmCtlSvrInfo(hsmCtlSvrId, request);

		// 4. 수정 결과 전송
		UpdateHsmCtlSvrResponse response = new UpdateHsmCtlSvrResponse(request);
		response.setHsmCtlSvrId(hsmCtlSvrId);
		return response;
	}
}
