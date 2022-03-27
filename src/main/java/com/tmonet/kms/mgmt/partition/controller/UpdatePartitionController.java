package com.tmonet.kms.mgmt.partition.controller;

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
import com.tmonet.kms.mgmt.partition.model.PartitionResponse;
import com.tmonet.kms.mgmt.partition.model.UpdatePartitionRequest;
import com.tmonet.kms.mgmt.partition.service.UpdatePartitionService;

@RestController
@RequestMapping("/kms")
public class UpdatePartitionController {

	private static final Logger logger = LoggerFactory.getLogger(UpdatePartitionController.class);

	@Autowired
	private UpdatePartitionService service;

	@PatchMapping("/partition/{hsmGroupId}/{partitionId}")
	public @ResponseBody PartitionResponse upatePartition(@PathVariable String hsmGroupId,
			@PathVariable String partitionId, @RequestBody @Validated UpdatePartitionRequest request, Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}
		
		// 2. 요청 파라미터 검증
		service.checkParams(hsmGroupId, partitionId, request);

		// 3. 파티션 수정, 수정 이력 등록
		service.updatePartitionInfo(hsmGroupId, partitionId, request);

		// 4. 수정 결과 전송
		PartitionResponse response = new PartitionResponse(request);
		response.setHsmGroupId(hsmGroupId);
		response.setPartitionId(partitionId);
		return response;

	}

}
