package com.tmonet.kms.mgmt.manager.controller;

import java.util.ArrayList;
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
import com.tmonet.kms.mgmt.manager.model.MngAuthResponse;
import com.tmonet.kms.mgmt.manager.service.SelectMngAuthService;
import com.tmonet.kms.mgmt.manager.vo.MngAuth;
import com.tmonet.kms.mgmt.manager.vo.MngAuthInfoVo;

@RestController
@RequestMapping("/kms")
public class SelectMngAuthController {

	private static final Logger logger = LoggerFactory.getLogger(SelectMngAuthController.class);

	@Autowired
	private SelectMngAuthService service;

	@GetMapping(value = { "/manager/auth/{managerId}/", "/manager/auth/{managerId}/{serviceId}" })
	public @ResponseBody MngAuthResponse selectMngAuth(@PathVariable String managerId,
			@PathVariable(required = false) String serviceId, @RequestBody @Validated BaseRequest request,
			Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 관리자 접근권한정보 조회
		List<MngAuthInfoVo> listMngAuthInfo = service.selectMngAuth(managerId, serviceId);

		// 3. 조회 결과 전송
		MngAuthResponse response = new MngAuthResponse(request);
		List<MngAuth> listMngAuth = new ArrayList<MngAuth>();
		for (MngAuthInfoVo mngAuth : listMngAuthInfo) {
			MngAuth ma = new MngAuth();
			ma.setManagerId(mngAuth.getID());
			ma.setServiceId(mngAuth.getSERVICE_ID());
			ma.setCreateYn(mngAuth.getCREATE_YN());
			ma.setReadYn(mngAuth.getREAD_YN());
			ma.setUpdateYn(mngAuth.getUPDATE_YN());
			ma.setDeleteYn(mngAuth.getDELETE_YN());
			ma.setRegDatetime(mngAuth.getREG_DTTM().getTime());
			if (mngAuth.getUPT_DTTM() != null) {
				ma.setLastUpdateDatetime(mngAuth.getUPT_DTTM().getTime());
			}
			listMngAuth.add(ma);
		}

		response.setListMngAuth(listMngAuth);
		return response;

	}

}
