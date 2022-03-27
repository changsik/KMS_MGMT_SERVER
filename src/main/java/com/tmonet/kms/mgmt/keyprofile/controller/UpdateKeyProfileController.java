package com.tmonet.kms.mgmt.keyprofile.controller;

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
import com.tmonet.kms.mgmt.keyprofile.model.UpdateKeyProfileRequest;
import com.tmonet.kms.mgmt.keyprofile.model.UpdateKeyProfileResponse;
import com.tmonet.kms.mgmt.keyprofile.service.UpdateKeyProfileService;

@RestController
@RequestMapping("/kms")
public class UpdateKeyProfileController {

	private static final Logger logger = LoggerFactory.getLogger(UpdateKeyProfileController.class);

	@Autowired
	private UpdateKeyProfileService service;

	@PatchMapping("/key/profile/{keyProfileId}")
	public @ResponseBody UpdateKeyProfileResponse updateKeyProfile(@PathVariable String keyProfileId,
			@RequestBody @Validated UpdateKeyProfileRequest request, Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 요청 파라미터 검증
		service.checkParams(keyProfileId, request);

		// 3. 키 프로파일 수정
		service.updateKeyProfileInfo(keyProfileId, request);

		// 4. 수정 결과 전송
		UpdateKeyProfileResponse response = new UpdateKeyProfileResponse(request);
		response.setKeyProfileId(keyProfileId);

		return response;
	}

}
