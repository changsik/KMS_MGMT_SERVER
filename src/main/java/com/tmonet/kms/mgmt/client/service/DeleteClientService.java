package com.tmonet.kms.mgmt.client.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmonet.kms.mgmt.client.mapper.ClientMapper;
import com.tmonet.kms.mgmt.client.vo.CliAuthHistoryVo;
import com.tmonet.kms.mgmt.client.vo.CliAuthInfoVo;
import com.tmonet.kms.mgmt.client.vo.ClientHistoryVo;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHistoryOperationCode;

@Service
public class DeleteClientService {

	private static final Logger logger = LoggerFactory.getLogger(DeleteClientService.class);
	
	@Autowired
	private ClientMapper mapper;
	
	public void checkParams(String clientIp) {
		try {
			if(mapper.selectClientInfo(clientIp) == null) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}
		}catch(KMSException e) {
			throw new KMSException(e.getCode());
		}catch(Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	@Transactional(rollbackFor = {KMSException.class})
	public void deleteClientInfo(String clientIp){
		try {
			// 클라이언트 키 삭제
			if(mapper.selectAllClientKey(clientIp).size() > 0) {
				if(mapper.deleteAllClientKey(clientIp) < 1) {
					throw new KMSException(KMSErrorCode.DATABASE_DELETE_FAILURE);
				}
			}
			
			// 클라이언트 권한 정보 삭제
			CliAuthInfoVo cliAuth = new CliAuthInfoVo();
			List<CliAuthInfoVo> listCliAuth = mapper.selectAllCliAuthInfo(clientIp);
			cliAuth.setIP_ADDR(clientIp);
			if(mapper.selectAllCliAuthInfo(clientIp).size() > 0) {
				if(mapper.deleteCliAuthInfo(cliAuth) < 1) {
					throw new KMSException(KMSErrorCode.DATABASE_DELETE_FAILURE);
				}
			}
			
			// 클라이언트 정보 삭제
			if(mapper.deleteClientInfo(clientIp) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_DELETE_FAILURE);
			}
			
			// 권한 삭제 이력 등록
			for(CliAuthInfoVo vo : listCliAuth) {
				CliAuthHistoryVo authHistory = new CliAuthHistoryVo();
				authHistory.setIP_ADDR(vo.getIP_ADDR());
				authHistory.setSERVICE_ID(vo.getSERVICE_ID());
				authHistory.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_DELETE_CLI_AUTH.getKey());
				authHistory.setHMAC("");
				authHistory.setREG_USER("ADMIN");
				if(mapper.insertCliAuthHistory(authHistory) < 1) {
					throw new KMSException(KMSErrorCode.DATABASE_DELETE_FAILURE);
				}
			}
			
			// 클라이언트 정보 삭제 이력
			ClientHistoryVo cliHistory = new ClientHistoryVo();
			cliHistory.setIP_ADDR(clientIp);
			cliHistory.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_DELETE_CLIENT.getKey());
			cliHistory.setHMAC("");
			cliHistory.setREG_USER("ADMIN");
			if(mapper.insertClientHistory(cliHistory) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_DELETE_FAILURE);
			}
			
			return;
			
		}catch(KMSException e) {
			throw new KMSException(e.getCode());
		}catch(Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
		
		
	}

}
