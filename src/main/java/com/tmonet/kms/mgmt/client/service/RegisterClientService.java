package com.tmonet.kms.mgmt.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmonet.kms.mgmt.client.mapper.ClientMapper;
import com.tmonet.kms.mgmt.client.model.RegisterClientRequest;
import com.tmonet.kms.mgmt.client.vo.ClientHistoryVo;
import com.tmonet.kms.mgmt.client.vo.ClientInfoVo;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumAuthenticationType;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHistoryOperationCode;
import com.tmonet.kms.mgmt.common.kmsenum.EnumUsageStatus;

@Service
public class RegisterClientService {

	private static final Logger logger = LoggerFactory.getLogger(RegisterClientService.class);

	@Autowired
	private ClientMapper mapper;

	public void checkParams(RegisterClientRequest request) {
		try {
			if (mapper.selectClientInfo(request.getClientIp()) != null) {
				throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
			}

			if (request.getAuthType() != null) {
				boolean authTypeIs = false;
				for (EnumAuthenticationType authType : EnumAuthenticationType.values()) {
					if (authType.getKey().equals(request.getAuthType())) {
						authTypeIs = true;
					}
				}
				if (!authTypeIs) {
					throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
				}
			}

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	@Transactional(rollbackFor = { KMSException.class })
	public void insertClientInfo(RegisterClientRequest request) {
		try {
			// 클라이언트 등록
			ClientInfoVo client = new ClientInfoVo();
			client.setIP_ADDR(request.getClientIp());
			client.setAUTH_TYPE(request.getAuthType());
			client.setNAME(request.getClientName());
			client.setDESCRIPTION(request.getDescription());
			client.setSTATUS(EnumUsageStatus.USAGE_STATUS_ACTIVE.getKey());
			client.setREG_USER("ADMIN");

			if (mapper.insertClientInfo(client) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
			}

			// 클라이언트 관리 이력 등록
			ClientHistoryVo history = new ClientHistoryVo();
			history.setIP_ADDR(request.getClientIp());
			history.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_CREATE_CLIENT.getKey());
			history.setHMAC("");
			history.setREG_USER("ADMIN");

			if (mapper.insertClientHistory(history) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
			}

			return;

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}
}
