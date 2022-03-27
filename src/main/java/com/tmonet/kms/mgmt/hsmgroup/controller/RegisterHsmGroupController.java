package com.tmonet.kms.mgmt.hsmgroup.controller;

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
import com.tmonet.kms.mgmt.hsmgroup.model.HsmGroupRequest;
import com.tmonet.kms.mgmt.hsmgroup.model.HsmGroupResponse;
import com.tmonet.kms.mgmt.hsmgroup.service.RegisterHsmGroupService;

@RestController
@RequestMapping("/kms")
public class RegisterHsmGroupController {

//	private static final Logger logger = LoggerFactory.getLogger(RegisterHsmGroupController.class);

	@Autowired
	private RegisterHsmGroupService service;

	@PostMapping("/hsm/group")
	public @ResponseBody HsmGroupResponse registerHsmGroup(@RequestBody @Validated HsmGroupRequest request,
			Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. HSM 그룹 등록
		String groupId = service.registerHsmGroupInfo(request);

		// 3. HSM 그룹 이력내용 저장
		service.storeHsmGroupInfo(groupId);

		// 4. 결과 전송
		HsmGroupResponse response = new HsmGroupResponse(request);
		response.setGroupId(groupId);
		return response;
	}
}