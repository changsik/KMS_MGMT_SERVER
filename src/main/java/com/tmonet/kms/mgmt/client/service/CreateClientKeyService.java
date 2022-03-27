package com.tmonet.kms.mgmt.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmonet.common.util.Converter;
import com.tmonet.kms.mgmt.client.mapper.ClientMapper;
import com.tmonet.kms.mgmt.client.vo.ClientInfoVo;
import com.tmonet.kms.mgmt.client.vo.ClientKeyListVo;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumKeyLifecycleState;
import com.tmonet.kms.mgmt.common.kmsenum.EnumOperationCode;

@Service
public class CreateClientKeyService {

	private static final Logger logger = LoggerFactory.getLogger(CreateClientKeyService.class);

	@Autowired
	private ClientMapper mapper;

	public ClientKeyListVo insertClientKeyList(String clientIp) {
		try {
			// 클라이언트 키 등록
			ClientKeyListVo cliKey = new ClientKeyListVo();
			String cliKeyId = mapper.selectCliKeyId();
			// 키 값 = 클라이언트 IP주소와 클라이언트 키ID를 더해 hash 한 값
			String keyValue = Converter.sha256Encode(clientIp + cliKeyId);

			cliKey.setKEY_ID(cliKeyId);
			cliKey.setIP_ADDR(clientIp);
			cliKey.setVALUE(keyValue);
			cliKey.setSTATUS(EnumKeyLifecycleState.KEY_LC_ACTIVE.getKey());
			cliKey.setREG_USER("ADMIN");
			if (mapper.insertClientKeyList(cliKey) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
			}

			return cliKey;

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}
}
