package com.tmonet.kms.mgmt.client.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tmonet.kms.mgmt.client.model.CliAuthResponse;
import com.tmonet.kms.mgmt.client.service.SelectCliAuthService;
import com.tmonet.kms.mgmt.client.vo.CliAuth;
import com.tmonet.kms.mgmt.client.vo.CliAuthInfoVo;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.model.BaseRequest;

@RestController
@RequestMapping("/kms")
public class SelectCliAuthController {

	private static final Logger logger = LoggerFactory.getLogger(SelectCliAuthController.class);

	@Autowired
	private SelectCliAuthService service;

	@PostMapping(value = { "/client/auth/get/{clientIp}/{serviceId}", "/client/auth/get/{clientIp}/" })
	public @ResponseBody CliAuthResponse selectCliAuth(@PathVariable String clientIp,
			@PathVariable(required = false) String serviceId, @RequestBody @Validated BaseRequest request,
			Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 관리자 접근권한정보 조회
		List<CliAuthInfoVo> listCliAuthVo = service.selectCliAuthInfo(clientIp, serviceId);

		// 3. 조회 결과 전송
		CliAuthResponse response = new CliAuthResponse(request);
		List<CliAuth> listCliAuth = new ArrayList<CliAuth>();
		for (CliAuthInfoVo cliAuthVo : listCliAuthVo) {
			CliAuth cliAuth = new CliAuth();
			cliAuth.setClientIp(cliAuthVo.getIP_ADDR());
			cliAuth.setServiceId(cliAuthVo.getSERVICE_ID());
			cliAuth.setCreateYn(cliAuthVo.getCREATE_YN());
			cliAuth.setReadYn(cliAuthVo.getREAD_YN());
			cliAuth.setUpdateYn(cliAuthVo.getUPDATE_YN());
			cliAuth.setDeleteYn(cliAuthVo.getDELETE_YN());
			cliAuth.setRegDatetime(cliAuthVo.getREG_DTTM().getTime());
			if (cliAuthVo.getUPT_DTTM() != null) {
				cliAuth.setLastUpdateDatetime(cliAuthVo.getUPT_DTTM().getTime());
			}
			listCliAuth.add(cliAuth);
		}

		response.setListCliAuth(listCliAuth);
		return response;

	}

}
