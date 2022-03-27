package com.tmonet.kms.mgmt.hsm.controller;

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

import com.tmonet.common.object.Code;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHsmVendor;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHsmInstallType;
import com.tmonet.kms.mgmt.hsm.model.HsmResponse;
import com.tmonet.kms.mgmt.hsm.service.SelectHsmService;
import com.tmonet.kms.mgmt.hsm.vo.Hsm;
import com.tmonet.kms.mgmt.hsm.vo.HsmInfoVo;

@RestController
@RequestMapping("/kms")
public class SelectHsmController {

	private static final Logger logger = LoggerFactory.getLogger(SelectHsmController.class);

	@Autowired
	private SelectHsmService service;

	@GetMapping(value = { "/hsm/{hsmGroupId}/{hsmId}", "/hsm/{hsmGroupId}/", "/hsm//" })
	public @ResponseBody HsmResponse selectHsm(@PathVariable(required = false) String hsmGroupId,
			@PathVariable(required = false) String hsmId, @RequestBody @Validated BaseRequest request, Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. HSM 조회
		List<HsmInfoVo> listHsmVo = service.selectHsmInfo(hsmGroupId, hsmId);

		// 3. 조회 결과 전송
		List<Hsm> listHsm = new ArrayList<Hsm>();
		for (HsmInfoVo hsmInfo : listHsmVo) {
			Hsm hsm = new Hsm();

			Code hsmVendorCode = new Code();
			for (EnumHsmVendor hsmVendor : EnumHsmVendor.values()) {
				if (hsmInfo.getVENDOR().equals(hsmVendor.getKey())) {
					hsmVendorCode.setCode(hsmVendor.getKey());
					hsmVendorCode.setName(hsmVendor.getValue());
				}
			}

			Code installTypeCode = new Code();
			for (EnumHsmInstallType installType : EnumHsmInstallType.values()) {
				if (hsmInfo.getINSTALL_TYPE().equals(installType.getKey())) {
					installTypeCode.setCode(installType.getKey());
					installTypeCode.setName(installType.getValue());
				}
			}

			hsm.setHsmId(hsmInfo.getHSM_ID());
			hsm.setHsmInstallType(installTypeCode);
			hsm.setHsmIpAddress(hsmInfo.getIP_ADDR());
			hsm.setHsmSerialNumber(hsmInfo.getSERIAL());
			hsm.setHsmVendor(hsmVendorCode);
			if (hsmInfo.getUPT_DTTM() != null) {
				hsm.setLastUpdateDatetime(hsmInfo.getUPT_DTTM().getTime());
			}
			hsm.setRegDatetime(hsmInfo.getREG_DTTM().getTime());

			listHsm.add(hsm);
		}

		HsmResponse response = new HsmResponse(request);
		response.setListHsm(listHsm);

		return response;
	}

}
