package com.tmonet.kms.mgmt.manager.controller;

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
import com.tmonet.kms.mgmt.manager.model.ManagerResponse;
import com.tmonet.kms.mgmt.manager.service.DeleteManagerService;

@RestController
@RequestMapping("/kms")
public class DeleteManagerController {

	private static final Logger logger = LoggerFactory.getLogger(DeleteManagerController.class);

	@Autowired
	private DeleteManagerService service;

	@DeleteMapping("/manager/{managerId}")
	public @ResponseBody ManagerResponse deleteManager(@PathVariable String managerId,
			@RequestBody @Validated BaseRequest request, Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 요청 파라미터 검증
		service.checkParams(managerId);

		// 3. 관리자 정보 삭제, 관리 이력 등록
		service.deleteManager(managerId);

		// 4. 삭제 결과 전송
		ManagerResponse response = new ManagerResponse(request);
		response.setManagerId(managerId);
		return response;
	}

}
