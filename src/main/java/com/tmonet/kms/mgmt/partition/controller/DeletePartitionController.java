package com.tmonet.kms.mgmt.partition.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.partition.model.PartitionResponse;
import com.tmonet.kms.mgmt.partition.service.DeletePartitionService;

@RestController
@RequestMapping("/kms")
public class DeletePartitionController {

	private static final Logger logger = LoggerFactory.getLogger(DeletePartitionController.class);

	@Autowired
	private DeletePartitionService service;

	@DeleteMapping("/partition/{hsmGroupId}/{partitionId}")
	public @ResponseBody PartitionResponse deletePartition(@PathVariable String hsmGroupId,
			@PathVariable String partitionId, @RequestBody BaseRequest request, Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 파티션 ID 검증
		service.checkParams(hsmGroupId, partitionId);

		// 3. 파티션 정보 삭제, 삭제 이력 등록
		service.deletePartitionInfo(hsmGroupId, partitionId);

		// 4. 삭제 결과 전송
		PartitionResponse response = new PartitionResponse(request);
		response.setHsmGroupId(hsmGroupId);
		response.setPartitionId(partitionId);
		return response;

	}

}
