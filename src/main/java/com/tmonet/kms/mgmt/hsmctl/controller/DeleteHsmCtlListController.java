package com.tmonet.kms.mgmt.hsmctl.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.hsmctl.model.DeleteHsmCtlListRequest;
import com.tmonet.kms.mgmt.hsmctl.model.DeleteHsmCtlListResponse;
import com.tmonet.kms.mgmt.hsmctl.model.DeleteHsmModuleRequest;
import com.tmonet.kms.mgmt.hsmctl.model.DeleteHsmModuleResponse;
import com.tmonet.kms.mgmt.hsmctl.service.DeleteHsmCtlListService;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlSvrInfoVo;

@RestController
@RequestMapping("/kms")
public class DeleteHsmCtlListController {

	private static final Logger logger = LoggerFactory.getLogger(DeleteHsmCtlListController.class);

	@Autowired
	private DeleteHsmCtlListService service;

	@DeleteMapping("/hsmctl/list")
	public @ResponseBody DeleteHsmCtlListResponse deleteHsmCtlList(
			@RequestBody @Validated DeleteHsmCtlListRequest request, Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 요청 파라미터 검증
		service.checkParams(request);

		// 3. HSM 제어서버가 관리하는 HSM Module의 정보 삭제 요청 데이터 생성
		DeleteHsmModuleRequest hsmModuleRequest = service.makeHsmCtlListDeleteRequestData(request);
		HsmCtlSvrInfoVo ctlSvr = service.selectHsmCtlSvrInfo(request.getHsmCtlSvrId());

		// 4. HSM 제어서버에 삭제 요청
		DeleteHsmModuleResponse hsmModuleResponse = new DeleteHsmModuleResponse();
		try {
			String hsmServerUrl = "http://" + ctlSvr.getIP_ADDR() + ":" + ctlSvr.getPORT()
					+ "/hsm/admin/datastore/hsmmodule";
			RestTemplate rt = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
			HttpEntity<DeleteHsmModuleRequest> reqEntity = new HttpEntity<DeleteHsmModuleRequest>(hsmModuleRequest);
			ResponseEntity<DeleteHsmModuleResponse> resEntity = rt.exchange(hsmServerUrl, HttpMethod.DELETE, reqEntity,
					DeleteHsmModuleResponse.class);
			hsmModuleResponse = resEntity.getBody();
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.HSM_SERVER_ERROR);
		}

		// 5. 관리대상 HSM 삭제, 삭제 이력 등록
		service.deleteHsmCtlList(request);

		// 6. 삭제 결과 전송
		DeleteHsmCtlListResponse response = new DeleteHsmCtlListResponse(request);
		String hsmGroupId = service.selectHsmGroupId(request.getDeleteHsmModuleList());
		List<String> hsmIdList = new ArrayList<String>();
		for (int i = 0; i < request.getDeleteHsmModuleList().size(); i++) {
			hsmIdList.add(request.getDeleteHsmModuleList().get(i).getHsmId());
		}

		response.setHsmCtlSvrId(request.getHsmCtlSvrId());
		response.setHsmGroupId(hsmGroupId);
		response.setHsmIdList(hsmIdList);

		return response;

	}

}
