package com.tmonet.kms.mgmt.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmonet.kms.mgmt.client.mapper.ClientMapper;
import com.tmonet.kms.mgmt.client.model.UpdateCliAuthRequest;
import com.tmonet.kms.mgmt.client.vo.CliAuthHistoryVo;
import com.tmonet.kms.mgmt.client.vo.CliAuthInfoVo;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHistoryOperationCode;

@Service
public class UpdateCliAuthService {

	private static final Logger logger = LoggerFactory.getLogger(UpdateCliAuthService.class);

	@Autowired
	private ClientMapper mapper;

	public void checkParams(String clientIp, String serviceId) {
		try {
			CliAuthInfoVo cliAuth = new CliAuthInfoVo();
			cliAuth.setIP_ADDR(clientIp);
			cliAuth.setSERVICE_ID(serviceId);
			if (mapper.selectCliAuthInfo(cliAuth) == null) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}
		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	@Transactional(rollbackFor = { KMSException.class })
	public void updateCliAuthInfo(String clientIp, String serviceId, UpdateCliAuthRequest request) {
		try {
			CliAuthInfoVo cliAuth = new CliAuthInfoVo();
			cliAuth.setIP_ADDR(clientIp);
			cliAuth.setSERVICE_ID(serviceId);
			cliAuth.setCREATE_YN(request.getCreateYn());
			cliAuth.setREAD_YN(request.getReadYn());
			cliAuth.setUPDATE_YN(request.getUpdateYn());
			cliAuth.setDELETE_YN(request.getDeleteYn());
			cliAuth.setUPT_USER("ADMIN");

			// 번경 값 유무 확인
			CliAuthInfoVo cliAuthBeforeUpdate = mapper.selectCliAuthInfo(cliAuth);
			if (cliAuthBeforeUpdate == null) {
				throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
			}
			boolean dataIsChanged = false;

			String updatedData = "";
			if (request.getCreateYn() != null) {
				if (!request.getCreateYn().equals(cliAuthBeforeUpdate.getCREATE_YN())) {
					updatedData += "CREATE_YN:" + request.getCreateYn() + " ";
					dataIsChanged = true;
				}
			}
			if (request.getReadYn() != null) {
				if (!request.getReadYn().equals(cliAuthBeforeUpdate.getREAD_YN())) {
					updatedData += "READ_YN:" + request.getReadYn() + " ";
					dataIsChanged = true;
				}
			}
			if (request.getUpdateYn() != null) {
				if (!request.getUpdateYn().equals(cliAuthBeforeUpdate.getUPDATE_YN())) {
					updatedData += "UPDATE_YN:" + request.getUpdateYn() + " ";
					dataIsChanged = true;
				}
			}
			if (request.getDeleteYn() != null) {
				if (!request.getDeleteYn().equals(cliAuthBeforeUpdate.getDELETE_YN())) {
					updatedData += "DELETE_YN:" + request.getDeleteYn() + " ";
					dataIsChanged = true;
				}
			}

			if (!dataIsChanged) {
				throw new KMSException(KMSErrorCode.DATABASE_UPDATE_FAILURE);
			}

			// 클라이언트 접근권한정보 수정
			if (mapper.updateCliAuthInfo(cliAuth) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_UPDATE_FAILURE);
			}

			// 클라리언트 접근권한정보 수정 이력 등록
			CliAuthHistoryVo history = new CliAuthHistoryVo();
			history.setIP_ADDR(clientIp);
			history.setSERVICE_ID(serviceId);
			history.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_UPDATE_CLI_AUTH.getKey());
			history.setPARAM(updatedData);
			history.setHMAC("");
			history.setREG_USER("ADMIN");

			if (mapper.insertCliAuthHistory(history) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
			}

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

}
