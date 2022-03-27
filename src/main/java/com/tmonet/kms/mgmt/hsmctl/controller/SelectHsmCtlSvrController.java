package com.tmonet.kms.mgmt.hsmctl.controller;

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
import com.tmonet.kms.mgmt.hsmctl.model.SelectHsmCtlSvrRequest;
import com.tmonet.kms.mgmt.hsmctl.model.SelectHsmCtlSvrResponse;
import com.tmonet.kms.mgmt.hsmctl.service.SelectHsmCtlSvrService;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlSvr;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlSvrInfoVo;

@RestController
@RequestMapping("/kms")
public class SelectHsmCtlSvrController {

	private static final Logger logger = LoggerFactory.getLogger(SelectHsmCtlSvrController.class);

	@Autowired
	private SelectHsmCtlSvrService service;

	@GetMapping(value = { "/hsmctl/server/{hsmCtlSvrId}", "/hsmctl/server/" })
	public @ResponseBody SelectHsmCtlSvrResponse selectHsmCtlSvr(@PathVariable(required = false) String hsmCtlSvrId,
			@RequestBody @Validated SelectHsmCtlSvrRequest request, Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. HSM 제어서버 조회
		List<HsmCtlSvrInfoVo> listVo = service.selectHsmCtlSvr(hsmCtlSvrId);

		// 3. 조회 결과 전송
		SelectHsmCtlSvrResponse response = new SelectHsmCtlSvrResponse(request);
		List<HsmCtlSvr> listHsmCtlSvr = new ArrayList<HsmCtlSvr>();
		for (HsmCtlSvrInfoVo vo : listVo) {
			HsmCtlSvr ctlSvr = new HsmCtlSvr();
			ctlSvr.setHsmCtlSvrId(vo.getCTLSVR_ID());
			ctlSvr.setHsmIpAddress(vo.getIP_ADDR());
			ctlSvr.setHsmPort(vo.getPORT());
			ctlSvr.setDescription(vo.getDESCRIPTION());
			ctlSvr.setRegDatetime(vo.getREG_DTTM().getTime());
			if (vo.getUPT_DTTM() != null) {
				ctlSvr.setLastUpdateDatetime(vo.getUPT_DTTM().getTime());
			}
			listHsmCtlSvr.add(ctlSvr);
		}

		response.setListHsmCtlSvr(listHsmCtlSvr);

		return response;

	}

}
