package com.tmonet.kms.mgmt.partition.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.partition.model.PartitionResponse;
import com.tmonet.kms.mgmt.partition.service.SelectPartitionAuthService;
import com.tmonet.kms.mgmt.partition.vo.PartitionAuth;

@RestController
@RequestMapping("/kms")
public class SelectPartitionAuthController {

	private static final Logger logger = LoggerFactory.getLogger(SelectPartitionAuthController.class);

	@Autowired
	private SelectPartitionAuthService service;

	@GetMapping(value = { "/partition/auth/{hsmGroupId}/{partitionId}", "/partition/auth/{hsmGroupId}/",
			"/partition/auth//" })
	public @ResponseBody PartitionResponse selectPartitionAuth(@PathVariable(required = false) String hsmGroupId,
			@PathVariable(required = false) String partitionId, @RequestBody @Validated BaseRequest request,
			Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 파티션 인증정보 조회
		List<PartitionAuth> listPartitionAuth = service.selectPartitionAuth(hsmGroupId, partitionId);

		// 3. 조회 결과 전송
		PartitionResponse response = new PartitionResponse(request);
		response.setListPartitionAuth(listPartitionAuth);

		return response;
	}

}
