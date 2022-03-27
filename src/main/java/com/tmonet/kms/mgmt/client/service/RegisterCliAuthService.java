package com.tmonet.kms.mgmt.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmonet.kms.mgmt.client.mapper.ClientMapper;
import com.tmonet.kms.mgmt.client.model.RegisterCliAuthRequest;
import com.tmonet.kms.mgmt.client.vo.CliAuthHistoryVo;
import com.tmonet.kms.mgmt.client.vo.CliAuthInfoVo;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHistoryOperationCode;

@Service
public class RegisterCliAuthService {

	private static final Logger logger = LoggerFactory.getLogger(RegisterCliAuthService.class);

	@Autowired
	private ClientMapper mapper;

	public void checkParams(RegisterCliAuthRequest request) {
		try {
			if (mapper.selectPartitionInfo(request.getServiceId()) == null) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}

			CliAuthInfoVo cliAuth = new CliAuthInfoVo();
			cliAuth.setIP_ADDR(request.getClientIp());
			cliAuth.setSERVICE_ID(request.getServiceId());

			if (mapper.selectCliAuthInfo(cliAuth) != null) {
				throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
			}
		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	@Transactional(rollbackFor = { KMSException.class })
	public void insertCliAuthInfo(RegisterCliAuthRequest request) {
		try {
			// 클라이언트 접근권한정보 등록
			CliAuthInfoVo cliAuth = new CliAuthInfoVo();
			cliAuth.setIP_ADDR(request.getClientIp());
			cliAuth.setSERVICE_ID(request.getServiceId());
			cliAuth.setCREATE_YN(request.getCreateYn());
			cliAuth.setREAD_YN(request.getReadYn());
			cliAuth.setUPDATE_YN(request.getUpdateYn());
			cliAuth.setDELETE_YN(request.getDeleteYn());
			cliAuth.setREG_USER("ADMIN");

			if (mapper.insertCliAuthInfo(cliAuth) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
			}

			// 클라이언트 접근권한정보 관리 이력 등록
			CliAuthHistoryVo history = new CliAuthHistoryVo();
			history.setIP_ADDR(request.getClientIp());
			history.setSERVICE_ID(request.getServiceId());
			history.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_CREATE_CLI_AUTH.getKey());
			history.setHMAC("");
			history.setREG_USER("ADMIN");

			if (mapper.insertCliAuthHistory(history) < 1) {
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
