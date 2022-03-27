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
import com.tmonet.kms.mgmt.manager.model.UpdateMngAuthRequest;
import com.tmonet.kms.mgmt.manager.vo.MngAuthHistoryVo;
import com.tmonet.kms.mgmt.manager.vo.MngAuthInfoVo;

@Service
public class UpdateMngAuthService {

	private static final Logger logger = LoggerFactory.getLogger(UpdateMngAuthService.class);

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

	@Transactional(rollbackFor = { KMSException.class })
	public void updateMngAuth(String managerId, String serviceId, UpdateMngAuthRequest request) {
		try {

			// 관리자 접근권한정보 수정
			MngAuthInfoVo mngAuth = new MngAuthInfoVo();
			mngAuth.setID(managerId);
			mngAuth.setSERVICE_ID(serviceId);
			mngAuth.setCREATE_YN(request.getCreateYn());
			mngAuth.setREAD_YN(request.getReadYn());
			mngAuth.setUPDATE_YN(request.getUpdateYn());
			mngAuth.setDELETE_YN(request.getDeleteYn());
			mngAuth.setUPT_USER("ADMIN");

			// 변경 값 유무 확인
			MngAuthInfoVo mngAuthBeforeUpdate = mapper.selectMngAuthInfo(mngAuth);
			if (mngAuthBeforeUpdate == null) {
				throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
			}
			boolean dataIsChanged = false;

			String updatedData = "";
			if (request.getCreateYn() != null) {
				if (!request.getCreateYn().equals(mngAuthBeforeUpdate.getCREATE_YN())) {
					updatedData += "CREATE_YN:" + request.getCreateYn() + " ";
					dataIsChanged = true;
				}
			}
			if (request.getReadYn() != null) {
				if (!request.getReadYn().equals(mngAuthBeforeUpdate.getREAD_YN())) {
					updatedData += "READ_YN:" + request.getReadYn() + " ";
					dataIsChanged = true;
				}
			}
			if (request.getUpdateYn() != null) {
				if (!request.getUpdateYn().equals(mngAuthBeforeUpdate.getUPDATE_YN())) {
					updatedData += "UPDATE_YN:" + request.getUpdateYn() + " ";
					dataIsChanged = true;
				}
			}
			if (request.getDeleteYn() != null) {
				if (!request.getDeleteYn().equals(mngAuthBeforeUpdate.getDELETE_YN())) {
					updatedData += "DELETE_YN:" + request.getDeleteYn() + " ";
					dataIsChanged = true;
				}
			}

			if (!dataIsChanged) {
				throw new KMSException(KMSErrorCode.DATABASE_UPDATE_FAILURE);
			}

			// 관리자 접근권한정보 수정
			if (mapper.updateMngAuthInfo(mngAuth) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_UPDATE_FAILURE);
			}

			// 관리자 접근권한정보 수정 이력 등록
			MngAuthHistoryVo history = new MngAuthHistoryVo();
			history.setID(managerId);
			history.setSERVICE_ID(serviceId);
			history.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_UPDATE_MNG_AUTH.getKey());
			history.setPARAM(updatedData);
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
