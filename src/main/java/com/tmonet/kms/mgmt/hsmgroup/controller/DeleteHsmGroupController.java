package com.tmonet.kms.mgmt.hsmgroup.controller;

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
import com.tmonet.kms.mgmt.hsmgroup.model.HsmGroupResponse;
import com.tmonet.kms.mgmt.hsmgroup.service.DeleteHsmGroupService;

@RestController
@RequestMapping("/kms")
public class DeleteHsmGroupController {

//	private static final Logger logger = LoggerFactory.getLogger(DeleteHsmGroupController.class);

	@Autowired
	private DeleteHsmGroupService service;

	@DeleteMapping("/hsm/group/{groupId}")
	public @ResponseBody HsmGroupResponse updateHsmGroup(@PathVariable String groupId,
			@RequestBody @Validated BaseRequest request, Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors() || groupId.equals("")) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. HSM 그룹 삭제
		service.deleteHsmGroupInfo(groupId);

		// 3. HSM 그룹 이력내용 저장
		service.storeHsmGroupInfo(groupId);

		// 4. 결과 전송
		HsmGroupResponse response = new HsmGroupResponse(request);
		return response;
	}

}
