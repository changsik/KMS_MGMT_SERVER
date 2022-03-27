package com.tmonet.kms.mgmt.hsmctl.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.hsmctl.model.ReportHsmServerRequest;
import com.tmonet.kms.mgmt.hsmctl.model.ReportHsmServerResponse;
import com.tmonet.kms.mgmt.hsmctl.service.RegisterHsmCtlListService;
import com.tmonet.kms.mgmt.hsmctl.service.ReportHsmServerService;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlSvrInfoVo;

@RestController
@RequestMapping("/kms")
public class ReportHsmServerController {

	private static final Logger logger = LoggerFactory.getLogger(ReportHsmServerController.class);
	
	@Autowired
	private ReportHsmServerService service;
	
	@GetMapping("/hsm/report/server")
	public @ResponseBody ReportHsmServerResponse reportHsmServer(@RequestBody @Validated ReportHsmServerRequest request, Errors errors) {
		
		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}
		
		// 2. HSM 제어 서버에 정보 조회 요청
		HsmCtlSvrInfoVo ctlSvr = service.selectHsmCtlSvrInfo(request.getHsmCtlSvrId());
		ReportHsmServerResponse response = new ReportHsmServerResponse(request);
		try {
			String hsmServerUrl = "http://" + ctlSvr.getIP_ADDR() + ":" + ctlSvr.getPORT() + "/hsm/admin/report/server";
			RestTemplate rt = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
			response = rt.postForObject(hsmServerUrl, request, ReportHsmServerResponse.class);
		}catch(Exception e) {
			throw new KMSException(KMSErrorCode.HSM_SERVER_ERROR);
		}
		
		// 3. 조회 결과 전송
		return response;
	}
	
}
