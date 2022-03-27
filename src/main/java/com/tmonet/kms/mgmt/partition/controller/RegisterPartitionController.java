package com.tmonet.kms.mgmt.partition.controller;

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
import com.tmonet.kms.mgmt.partition.model.PartitionResponse;
import com.tmonet.kms.mgmt.partition.model.RegisterPartitionRequest;
import com.tmonet.kms.mgmt.partition.service.RegisterPartitionService;

@RestController
@RequestMapping("/kms")
public class RegisterPartitionController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterPartitionController.class);

	@Autowired
	private RegisterPartitionService service;

	@PostMapping("/partition")
	public @ResponseBody PartitionResponse registerPartition(@RequestBody @Validated RegisterPartitionRequest request,
			Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 요청 파라미터 검증
		service.checkPartitionInfo(request);

		// 3. 파티션 등록, 파티션 관리 이력 추가
		service.insertPartitionInfo(request);

		// 4. 등록 결과 전송
		PartitionResponse response = new PartitionResponse(request);
		response.setHsmGroupId(request.getHsmGroupId());
		response.setPartitionId(request.getPartitionId());

		return response;
	}

}
