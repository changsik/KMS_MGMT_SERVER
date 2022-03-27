package com.tmonet.kms.mgmt.hsmctl.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.hsmctl.model.DeleteHsmModuleRequest;
import com.tmonet.kms.mgmt.hsmctl.model.DeleteHsmModuleResponse;
import com.tmonet.kms.mgmt.hsmctl.model.RegisterHsmModuleResponse;
import com.tmonet.kms.mgmt.hsmctl.model.UpdateHsmCtlListRequest;
import com.tmonet.kms.mgmt.hsmctl.model.UpdateHsmCtlListResponse;
import com.tmonet.kms.mgmt.hsmctl.model.UpdateHsmModuleRequest;
import com.tmonet.kms.mgmt.hsmctl.model.UpdateHsmModuleResponse;
import com.tmonet.kms.mgmt.hsmctl.service.UpdateHsmCtlListService;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlSvrInfoVo;

@RestController
@RequestMapping("/kms")
public class UpdateHsmCtlListController {

	private static final Logger logger = LoggerFactory.getLogger(UpdateHsmCtlListController.class);

	@Autowired
	private UpdateHsmCtlListService service;

	@PatchMapping("/hsmctl/list")
	public @ResponseBody UpdateHsmCtlListResponse updateHsmCtlList(
			@RequestBody @Validated UpdateHsmCtlListRequest request, Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 요청 파라미터 검증
		service.checkParams(request);

		// 3. HSM 제어서버에 수정 요청 할 HSM Module 데이터 생성
		UpdateHsmModuleRequest hsmModuleRequest = service.makeHsmCtlListUpdateRequestData(request);
		HsmCtlSvrInfoVo ctlSvr = service.selectHsmCtlSvrInfo(request.getHsmCtlSvrId());

		// 4. HSM 제어서버에 HSM Module 정보 수정 요청
		UpdateHsmModuleResponse hsmModuleResponse = new UpdateHsmModuleResponse();
		try {
			String hsmServerUrl = "http://" + ctlSvr.getIP_ADDR() + ":" + ctlSvr.getPORT()
					+ "/hsm/admin/datastore/hsmmodule";
			RestTemplate rt = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
			HttpEntity<UpdateHsmModuleRequest> reqEntity = new HttpEntity<UpdateHsmModuleRequest>(hsmModuleRequest);
			ResponseEntity<UpdateHsmModuleResponse> resEntity = rt.exchange(hsmServerUrl, HttpMethod.PATCH, reqEntity,
					UpdateHsmModuleResponse.class);
			hsmModuleResponse = resEntity.getBody();
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.HSM_SERVER_ERROR);
		}

		// 5. 관리대상 HSM 목록 수정, 수정 이력 등록
		service.updateHsmCtlList(request);

		// 6. 수정 결과 전송
		UpdateHsmCtlListResponse response = new UpdateHsmCtlListResponse(request);
		response.setHsmCtlSvrId(request.getHsmCtlSvrId());

		return response;

	}

}
