package com.tmonet.kms.mgmt.keyprofile.controller;

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
import com.tmonet.kms.mgmt.common.kmsenum.EnumUsageStatus;
import com.tmonet.kms.mgmt.keyprofile.model.SelectKeyProfileRequest;
import com.tmonet.kms.mgmt.keyprofile.model.SelectKeyProfileResponse;
import com.tmonet.kms.mgmt.keyprofile.service.SelectKeyProfileService;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyAttribute;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyProfile;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyProfileInfoVo;

@RestController
@RequestMapping("/kms")
public class SelectKeyProfileController {

	private static final Logger logger = LoggerFactory.getLogger(SelectKeyProfileController.class);

	@Autowired
	private SelectKeyProfileService service;

	@GetMapping(value = { "/key/profile/{keyProfileId}", "/key/profile/" })
	public @ResponseBody SelectKeyProfileResponse selectKeyProfile(@PathVariable(required = false) String keyProfileId,
			@RequestBody @Validated SelectKeyProfileRequest request, Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 키 프로파일 조회
		List<KeyProfileInfoVo> listProfile = service.selectKeyProfileInfo(keyProfileId);

		// 3. 조회 결과 전송
		SelectKeyProfileResponse response = new SelectKeyProfileResponse(request);

		try {
			List<KeyProfile> listKeyProfile = new ArrayList<KeyProfile>();

			for (KeyProfileInfoVo vo : listProfile) {
				KeyProfile keyProfile = new KeyProfile();
				keyProfile.setKeyProfileId(vo.getPROFILE_ID());
				keyProfile.setListKeyAttribute(service.selectAllKeyProfileAttrList(vo.getPROFILE_ID()));
				Code code = new Code();
				if (vo.getSTATUS() != null) {
					for (EnumUsageStatus status : EnumUsageStatus.values()) {
						if (status.getKey().equals(vo.getSTATUS())) {
							code.setCode(status.getKey());
							code.setName(status.getValue());
						}
					}
					keyProfile.setUsageStatus(code);
				}
				keyProfile.setRegDatetime(vo.getREG_DTTM().getTime());
				if (vo.getUPT_DTTM() != null) {
					keyProfile.setLaseUpdateDatetime(vo.getUPT_DTTM().getTime());
				}

				listKeyProfile.add(keyProfile);

			}

			response.setListKeyProfile(listKeyProfile);

			return response;

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

}
