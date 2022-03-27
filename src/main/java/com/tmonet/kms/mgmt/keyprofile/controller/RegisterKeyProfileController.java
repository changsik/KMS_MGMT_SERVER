package com.tmonet.kms.mgmt.keyprofile.controller;

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
import com.tmonet.kms.mgmt.keyprofile.model.RegisterKeyProfileRequest;
import com.tmonet.kms.mgmt.keyprofile.model.RegisterKeyProfileResponse;
import com.tmonet.kms.mgmt.keyprofile.service.RegisterKeyProfileService;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyProfileInfoVo;

@RestController
@RequestMapping("/kms")
public class RegisterKeyProfileController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterKeyProfileController.class);

	@Autowired
	private RegisterKeyProfileService service;

	@PostMapping("/key/profile")
	public @ResponseBody RegisterKeyProfileResponse registerKeyProfile(
			@RequestBody @Validated RegisterKeyProfileRequest request, Errors errors) {
		
		//logger.info("RegisterKeyProfileController");

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 요청 파라미터 검증
		service.checkParams(request);

		// 3. 키 프로파일 등록
		// 키 프로파일 속성 목록 등록
		// 키 프로파일 관리 이력 등록
		String regProfileId = service.registerKeyprofileInfo(request);

		// 4. 등록 결과 전송
		RegisterKeyProfileResponse response = new RegisterKeyProfileResponse(request);
		response.setKeyProfileId(regProfileId);
		return response;
	}

}
