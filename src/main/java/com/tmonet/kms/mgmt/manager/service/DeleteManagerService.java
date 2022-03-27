package com.tmonet.kms.mgmt.manager.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHistoryOperationCode;
import com.tmonet.kms.mgmt.manager.mapper.ManagerMapper;
import com.tmonet.kms.mgmt.manager.vo.ManagerHistoryVo;
import com.tmonet.kms.mgmt.manager.vo.MngAuthHistoryVo;
import com.tmonet.kms.mgmt.manager.vo.MngAuthInfoVo;

@Service
public class DeleteManagerService {

	private static final Logger logger = LoggerFactory.getLogger(DeleteManagerService.class);

	@Autowired
	private ManagerMapper mapper;

	public void checkParams(String managerId) {
		try {
			if (mapper.selectManagerInfo(managerId) == null) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}
		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	@Transactional(rollbackFor = { KMSException.class })
	public void deleteManager(String managerId) {
		try {
			List<MngAuthInfoVo> listMngAuth = mapper.selectAllMngAuthInfo(managerId);

			// 관리자 접근 권한 목록 삭제
			if (listMngAuth.size() > 0) {
				if (mapper.deleteAllMngAuthInfo(managerId) < 1) {
					throw new KMSException(KMSErrorCode.DATABASE_DELETE_FAILURE);
				}
			}

			// 관리자 삭제
			if (mapper.deleteManagerInfo(managerId) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_DELETE_FAILURE);
			}

			// 관리자 정보 삭제 이력 등록
			ManagerHistoryVo mngHistory = new ManagerHistoryVo();
			mngHistory.setID(managerId);
			mngHistory.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_DELETE_MANAGER.getKey());
			mngHistory.setHMAC("");
			mngHistory.setREG_USER("ADMIN");

			if (mapper.insertManagerHistory(mngHistory) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_DELETE_FAILURE);
			}

			// 관리자 접근권한목록 삭제 이력 등록
			for (MngAuthInfoVo mngAuth : listMngAuth) {
				MngAuthHistoryVo mngAuthHistory = new MngAuthHistoryVo();
				mngAuthHistory.setID(mngAuth.getID());
				mngAuthHistory.setSERVICE_ID(mngAuth.getSERVICE_ID());
				mngAuthHistory.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_DELETE_MNG_AUTH.getKey());
				mngAuthHistory.setHMAC("");
				mngAuthHistory.setREG_USER("ADMIN");

				if (mapper.insertMngAuthHistory(mngAuthHistory) < 1) {
					throw new KMSException(KMSErrorCode.DATABASE_DELETE_FAILURE);
				}
			}

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

}
