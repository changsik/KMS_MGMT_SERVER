package com.tmonet.kms.mgmt.keyprofile.controller;

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
import com.tmonet.kms.mgmt.keyprofile.model.DeleteKeyProfileRequest;
import com.tmonet.kms.mgmt.keyprofile.model.DeleteKeyProfileResponse;
import com.tmonet.kms.mgmt.keyprofile.service.DeleteKeyProfileService;

@RestController
@RequestMapping("/kms")
public class DeleteKeyProfileController {

	@Autowired
	private DeleteKeyProfileService service;

	@DeleteMapping("/key/profile/{keyProfileId}")
	public @ResponseBody DeleteKeyProfileResponse deleteKeyProfile(@PathVariable String keyProfileId,
			@RequestBody @Validated DeleteKeyProfileRequest request, Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 해당 키 프로파일로 만든 키 조회
		int keyMadeOfProfile = service.selectKey(keyProfileId);

		// 3. 키 프로파일 삭제 또는 사용상태 변경
		int result = service.deleteKeyProfile(keyMadeOfProfile, keyProfileId);

		// 4. 키 프로파일 변경 이력 등록

		// 4. 삭제 결과 전송
		DeleteKeyProfileResponse response = new DeleteKeyProfileResponse(request);
		response.setKeyProfileId(keyProfileId);
		return response;
	}

}
