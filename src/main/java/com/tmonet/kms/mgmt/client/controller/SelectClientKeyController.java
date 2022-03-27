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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tmonet.common.object.Code;
import com.tmonet.kms.mgmt.client.model.ClientKeyListResponse;
import com.tmonet.kms.mgmt.client.model.SelectClientKeyListRequest;
import com.tmonet.kms.mgmt.client.service.SelectClientKeyService;
import com.tmonet.kms.mgmt.client.vo.ClientKeyList;
import com.tmonet.kms.mgmt.client.vo.ClientKeyListVo;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumAuthenticationType;
import com.tmonet.kms.mgmt.common.kmsenum.EnumUsageStatus;

@RestController
@RequestMapping("/kms")
public class SelectClientKeyController {

	private static final Logger logger = LoggerFactory.getLogger(SelectClientKeyController.class);

	@Autowired
	private SelectClientKeyService service;

	@GetMapping(value = { "/client/key/{clientIp}/{clientKeyName}", "/client/key/{clientIp}/" })
	public @ResponseBody ClientKeyListResponse selectClientKey(@PathVariable String clientIp,
			@PathVariable(required = false) String clientKeyId,
			@RequestBody @Validated SelectClientKeyListRequest request, Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 요청 파라미터 검증
		service.checkParams(clientIp, clientKeyId, request);

		// 3. 클라이언트 키 정보 조회
		List<ClientKeyListVo> listClientKeyVo = service.selectClientKey(clientIp, clientKeyId, request);

		// 4. 조회 결과 전송
		ClientKeyListResponse response = new ClientKeyListResponse(request);
		List<ClientKeyList> listClientKey = new ArrayList<ClientKeyList>();
		for (ClientKeyListVo vo : listClientKeyVo) {
			ClientKeyList cliKey = new ClientKeyList();
			cliKey.setClientKeyId(vo.getKEY_ID());
			cliKey.setClientIp(vo.getIP_ADDR());
			cliKey.setClientKeyValue(vo.getVALUE());
			for (EnumUsageStatus status : EnumUsageStatus.values()) {
				if (status.getKey().equals(vo.getSTATUS())) {
					Code code = new Code();
					code.setCode(status.getKey());
					code.setName(status.getValue());
					cliKey.setUsageStatus(code);
					break;
				}
			}
			cliKey.setRegDatetime(vo.getREG_DTTM().getTime());
			if (vo.getUPT_DTTM() != null) {
				cliKey.setLastUpdateDatetime(vo.getUPT_DTTM().getTime());
			}

			listClientKey.add(cliKey);

		}

		response.setListClientKey(listClientKey);
		return response;

	}

}
