package com.tmonet.kms.mgmt.manager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHistoryOperationCode;
import com.tmonet.kms.mgmt.manager.mapper.ManagerMapper;
import com.tmonet.kms.mgmt.manager.model.RegisterMngAuthRequest;
import com.tmonet.kms.mgmt.manager.vo.MngAuthHistoryVo;
import com.tmonet.kms.mgmt.manager.vo.MngAuthInfoVo;

@Service
public class RegisterMngAuthService {

	private static final Logger logger = LoggerFactory.getLogger(RegisterMngAuthService.class);

	@Autowired
	private ManagerMapper mapper;

	public void checkParams(RegisterMngAuthRequest request) {
		try {
			if (mapper.selectPartitionInfo(request.getServiceId()) == null) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}

			if(mapper.selectManagerInfo(request.getManagerId()) == null) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}
			
			MngAuthInfoVo mngAuth = new MngAuthInfoVo();
			mngAuth.setID(request.getManagerId());
			mngAuth.setSERVICE_ID(request.getServiceId());

			if (mapper.selectMngAuthInfo(mngAuth) != null) {
				throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
			}
		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	@Transactional(rollbackFor = { KMSException.class })
	public void insertMngAuthInfo(RegisterMngAuthRequest request) {
		try {
			// 관리자 접근권한정보 등록
			MngAuthInfoVo mngAuth = new MngAuthInfoVo();
			mngAuth.setID(request.getManagerId());
			mngAuth.setSERVICE_ID(request.getServiceId());
			mngAuth.setCREATE_YN(request.getCreateYn());
			mngAuth.setREAD_YN(request.getReadYn());
			mngAuth.setUPDATE_YN(request.getUpdateYn());
			mngAuth.setDELETE_YN(request.getDeleteYn());
			mngAuth.setREG_USER("ADMIN");

			if (mapper.insertMngAuthInfo(mngAuth) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
			}

			// 관리자 접근권한정보 관리 이력 등록
			MngAuthHistoryVo history = new MngAuthHistoryVo();
			history.setID(request.getManagerId());
			history.setSERVICE_ID(request.getServiceId());
			history.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_CREATE_MNG_AUTH.getKey());
			history.setHMAC("");
			history.setREG_USER("ADMIN");

			if (mapper.insertMngAuthHistory(history) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
			}

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

}
