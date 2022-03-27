package com.tmonet.kms.mgmt.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmonet.kms.mgmt.client.mapper.ClientMapper;
import com.tmonet.kms.mgmt.client.model.UpdateClientRequest;
import com.tmonet.kms.mgmt.client.vo.ClientHistoryVo;
import com.tmonet.kms.mgmt.client.vo.ClientInfoVo;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumAuthenticationType;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHistoryOperationCode;
import com.tmonet.kms.mgmt.common.kmsenum.EnumUsageStatus;

@Service
public class UpdateClientService {

	private static final Logger logger = LoggerFactory.getLogger(UpdateClientService.class);

	@Autowired
	private ClientMapper mapper;

	public void checkParams(String clientIp, UpdateClientRequest request) {
		try {
			if (mapper.selectClientInfo(clientIp) == null) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
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

			if (request.getUsageStatus() != null) {
				boolean statusIs = false;
				for (EnumUsageStatus status : EnumUsageStatus.values()) {
					if (status.getKey().equals(request.getUsageStatus())) {
						statusIs = true;
					}
				}
				if (!statusIs) {
					throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
				}
			}

			return;
		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	@Transactional(rollbackFor = { KMSException.class })
	public void updateClient(String clientIp, UpdateClientRequest request) {
		try {
			ClientInfoVo cliBeforeUpdate = mapper.selectClientInfo(clientIp);
			if (cliBeforeUpdate == null) {
				throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
			}

			// 클라이언트 정보 수정
			ClientInfoVo client = new ClientInfoVo();
			client.setIP_ADDR(clientIp);
			client.setAUTH_TYPE(request.getAuthType());
			client.setNAME(request.getClientName());
			client.setDESCRIPTION(request.getDescription());
			client.setSTATUS(request.getUsageStatus());
			client.setUPT_USER("ADMIN");

			if (mapper.updateClientInfo(client) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_UPDATE_FAILURE);
			}

			// 수정 이력 등록
			String updatedData = "";
			boolean cliIsChanged = false;

			if (cliBeforeUpdate.getAUTH_TYPE() != null) {
				if (!cliBeforeUpdate.getAUTH_TYPE().equals(request.getAuthType())) {
					updatedData += "AUTH_TYPE:" + request.getAuthType() + " ";
					cliIsChanged = true;
				}
			}
			if (cliBeforeUpdate.getNAME() != null) {
				if (!cliBeforeUpdate.getNAME().equals(request.getClientName())) {
					updatedData += "NAME:" + request.getClientName() + " ";
					cliIsChanged = true;
				}
			}
			if (cliBeforeUpdate.getDESCRIPTION() != null) {
				if (!cliBeforeUpdate.getDESCRIPTION().equals(request.getDescription())) {
					updatedData += "DESCRIPTION:" + request.getDescription() + " ";
					cliIsChanged = true;
				}
			}
			if (cliBeforeUpdate.getSTATUS() != null) {
				if (!cliBeforeUpdate.getSTATUS().equals(request.getUsageStatus())) {
					updatedData += "STATUS:" + request.getUsageStatus() + " ";
					cliIsChanged = true;
				}
			}
			if (!cliIsChanged) {
				throw new KMSException(KMSErrorCode.DATABASE_UPDATE_FAILURE);
			}

			ClientHistoryVo history = new ClientHistoryVo();
			history.setIP_ADDR(clientIp);
			history.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_UPDATE_CLIENT.getKey());
			history.setPARAM(updatedData);
			history.setHMAC("");
			history.setREG_USER("ADMIN");

			if (mapper.insertClientHistory(history) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
			}

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}

	}

}
