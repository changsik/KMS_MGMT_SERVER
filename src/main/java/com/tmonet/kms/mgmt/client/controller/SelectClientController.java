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
import com.tmonet.kms.mgmt.client.model.ClientResponse;
import com.tmonet.kms.mgmt.client.service.SelectClientService;
import com.tmonet.kms.mgmt.client.vo.Client;
import com.tmonet.kms.mgmt.client.vo.ClientInfoVo;
import com.tmonet.kms.mgmt.client.vo.ClientKeyListVo;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumAuthenticationType;
import com.tmonet.kms.mgmt.common.kmsenum.EnumKeyLifecycleState;
import com.tmonet.kms.mgmt.common.kmsenum.EnumUsageStatus;
import com.tmonet.kms.mgmt.common.model.BaseRequest;

@RestController
@RequestMapping("/kms")
public class SelectClientController {

	private static final Logger logger = LoggerFactory.getLogger(SelectClientController.class);

	@Autowired
	private SelectClientService service;

	@GetMapping(value = { "/client/{clientIp}", "/client/" })
	public @ResponseBody ClientResponse selectClient(@PathVariable(required = false) String clientIp,
			@RequestBody @Validated BaseRequest request, Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}
		
		// 2. 클라이언트 정보 조회
		List<ClientInfoVo> listClientInfo = service.selectClientInfo(clientIp);

		// 3. 조회 결과 전송
		ClientResponse response = new ClientResponse(request);
		List<Client> listClient = new ArrayList<Client>();
		for (ClientInfoVo clientInfo : listClientInfo) {
			Client client = new Client();
			client.setClientIp(clientInfo.getIP_ADDR());
			for (EnumAuthenticationType authType : EnumAuthenticationType.values()) {
				if (authType.getKey().equals(clientInfo.getAUTH_TYPE())) {
					Code code = new Code();
					code.setCode(authType.getKey());
					code.setName(authType.getValue());
					client.setAuthType(code);
				}
			}
			client.setClientName(clientInfo.getNAME());
			client.setDescription(clientInfo.getDESCRIPTION());
			for (EnumUsageStatus status : EnumUsageStatus.values()) {
				if (status.getKey().equals(clientInfo.getSTATUS())) {
					Code code = new Code();
					code.setCode(status.getKey());
					code.setName(status.getValue());
					client.setUsageStatus(code);
				}
			}
			client.setRegDatetime(clientInfo.getREG_DTTM().getTime());
			if (clientInfo.getUPT_DTTM() != null) {
				client.setLastUpdateDatetime(clientInfo.getUPT_DTTM().getTime());
			}

			listClient.add(client);

		}

		response.setListClient(listClient);

		return response;
	}

}
