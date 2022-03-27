package com.tmonet.kms.mgmt.manager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHistoryOperationCode;
import com.tmonet.kms.mgmt.manager.mapper.ManagerMapper;
import com.tmonet.kms.mgmt.manager.vo.MngAuthHistoryVo;
import com.tmonet.kms.mgmt.manager.vo.MngAuthInfoVo;

@Service
public class DeleteMngAuthService {

	private static final Logger logger = LoggerFactory.getLogger(DeleteMngAuthService.class);

	@Autowired
	private ManagerMapper mapper;

	public void checkParams(String managerId, String serviceId) {
		try {
			MngAuthInfoVo mngAuth = new MngAuthInfoVo();
			mngAuth.setID(managerId);
			mngAuth.setSERVICE_ID(serviceId);
			if (mapper.selectMngAuthInfo(mngAuth) == null) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}
		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	public void deleteMngAuth(String managerId, String serviceId) {
		try {
			// 관리자 접근권한정보 삭제
			MngAuthInfoVo mngAuth = new MngAuthInfoVo();
			mngAuth.setID(managerId);
			mngAuth.setSERVICE_ID(serviceId);
			if (mapper.deleteMngAuthInfo(mngAuth) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_DELETE_FAILURE);
			}

			// 관리자 접근권한정보 삭제 이력 등록
			MngAuthHistoryVo history = new MngAuthHistoryVo();
			history.setID(managerId);
			history.setSERVICE_ID(serviceId);
			history.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_DELETE_MNG_AUTH.getKey());
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
