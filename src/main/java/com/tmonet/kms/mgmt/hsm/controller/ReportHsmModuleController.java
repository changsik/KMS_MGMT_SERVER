package com.tmonet.kms.mgmt.hsm.controller;

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
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.hsm.model.ReportHsmModuleRequest;
import com.tmonet.kms.mgmt.hsm.model.ReportHsmModuleResponse;
import com.tmonet.kms.mgmt.hsm.service.ReportHsmModuleService;
import com.tmonet.kms.mgmt.hsmgroup.vo.HsmGroupInfoVo;

@RestController
@RequestMapping("/kms")
public class ReportHsmModuleController {

	private static final Logger logger = LoggerFactory.getLogger(ReportHsmModuleController.class);

	@Autowired
	private ReportHsmModuleService service;

	@GetMapping(value = { "/hsm/report/module/{hsmId}", "/hsm/report/module/" })
	public @ResponseBody ReportHsmModuleResponse reportHsmModule(@PathVariable(required = false) String hsmId,
			@RequestBody @Validated ReportHsmModuleRequest request, Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}


		// 2. HSM 제어 서버에 정보 조회 요청
		ReportHsmModuleResponse response = new ReportHsmModuleResponse(request);
		try {
			String hsmServerUrl;
			if (hsmId == null) {
				hsmServerUrl = "http://" + request.getSlbIpAddress() + ":" + request.getSlbPort()
						+ "/hsm/admin/report/module";
			} else {
				HsmGroupInfoVo hsmGroupInfoVo = service.selectHsmGroupInfo(hsmId);
				hsmServerUrl = "http://" + hsmGroupInfoVo.getSLB_IP_ADDR() + ":" + hsmGroupInfoVo.getSLB_PORT()
						+ "/hsm/admin/report/module/" + hsmId;
			}
			RestTemplate rt = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
			response = rt.postForObject(hsmServerUrl, request, ReportHsmModuleResponse.class);

		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.HSM_SERVER_ERROR);
		}

		// 3. 조회 결과 전송
		return response;
	}

}
