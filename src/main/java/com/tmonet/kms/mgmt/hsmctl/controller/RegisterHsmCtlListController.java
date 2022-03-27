package com.tmonet.kms.mgmt.hsmctl.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHsmControlMode;
import com.tmonet.kms.mgmt.hsm.vo.HsmInfoVo;
import com.tmonet.kms.mgmt.hsmctl.model.RegisterHsmCtlListRequest;
import com.tmonet.kms.mgmt.hsmctl.model.RegisterHsmCtlListResponse;
import com.tmonet.kms.mgmt.hsmctl.model.RegisterHsmModuleResponse;
import com.tmonet.kms.mgmt.hsmctl.model.RegisterHsmModuleRequest;
import com.tmonet.kms.mgmt.hsmctl.service.RegisterHsmCtlListService;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlSvrInfoVo;

@RestController
@RequestMapping("/kms")
public class RegisterHsmCtlListController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterHsmCtlListController.class);

	@Autowired
	private RegisterHsmCtlListService service;

	@PostMapping("/hsmctl/list")
	public @ResponseBody RegisterHsmCtlListResponse registerHsmCtlList(
			@RequestBody @Validated RegisterHsmCtlListRequest request, Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 요청 파라미터 검증
		service.checkParams(request);

		// 3. HSM 제어서버에 등록할 HSM Module 정보 요청 데이터 생성
		RegisterHsmModuleRequest hsmModuleRequest = service.makeHsmCtlListRegisterRequestData(request);
		HsmCtlSvrInfoVo ctlSvr = service.selectHsmCtlSvrInfo(request.getHsmCtlSvrId());

		// 4. HSM 제어서버에 HSM Module 정보 등록 요청
		RegisterHsmModuleResponse hsmModuleResponse = new RegisterHsmModuleResponse();
		try {
			String hsmServerUrl = "http://" + ctlSvr.getIP_ADDR() + ":" + ctlSvr.getPORT()
					+ "/hsm/admin/datastore/hsmmodule";
			
			RestTemplate rt = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
			hsmModuleResponse = rt.postForObject(hsmServerUrl, hsmModuleRequest, RegisterHsmModuleResponse.class);
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.HSM_SERVER_ERROR);
		}

		// 4. 관리대상 HSM 등록, 관리 이력 등록
		service.insertHsmCtlList(request);

		// 5. 등록 결과 전송
		RegisterHsmCtlListResponse response = new RegisterHsmCtlListResponse(request);
		response.setHsmCtlSvrId(request.getHsmCtlSvrId());
		response.setHsmGroupId(request.getHsmGroupId());
		response.setHsmId(request.getHsmId());

		return response;

	}

}
