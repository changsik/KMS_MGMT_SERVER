package com.tmonet.kms.mgmt.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmonet.kms.mgmt.client.mapper.ClientMapper;
import com.tmonet.kms.mgmt.client.vo.CliAuthHistoryVo;
import com.tmonet.kms.mgmt.client.vo.CliAuthInfoVo;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHistoryOperationCode;

@Service
public class DeleteCliAuthService {

	private static final Logger logger = LoggerFactory.getLogger(DeleteCliAuthService.class);

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

	public void deleteCliAuthInfo(String clientIp, String serviceId) {
		try {
			// 클라이언트 접근권한정보 삭제
			CliAuthInfoVo cliAuth = new CliAuthInfoVo();
			cliAuth.setIP_ADDR(clientIp);
			cliAuth.setSERVICE_ID(serviceId);
			if (mapper.deleteCliAuthInfo(cliAuth) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_DELETE_FAILURE);
			}

			// 삭제 이력 등록
			CliAuthHistoryVo history = new CliAuthHistoryVo();
			history.setIP_ADDR(clientIp);
			history.setSERVICE_ID(serviceId);
			history.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_DELETE_CLI_AUTH.getKey());
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
