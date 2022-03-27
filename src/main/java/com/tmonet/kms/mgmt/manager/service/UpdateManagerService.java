package com.tmonet.kms.mgmt.manager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHistoryOperationCode;
import com.tmonet.kms.mgmt.common.kmsenum.EnumManagerType;
import com.tmonet.kms.mgmt.common.kmsenum.EnumUsageStatus;
import com.tmonet.kms.mgmt.manager.mapper.ManagerMapper;
import com.tmonet.kms.mgmt.manager.model.UpdateManagerRequest;
import com.tmonet.kms.mgmt.manager.vo.ManagerHistoryVo;
import com.tmonet.kms.mgmt.manager.vo.ManagerInfoVo;

@Service
public class UpdateManagerService {

	private static final Logger logger = LoggerFactory.getLogger(UpdateManagerService.class);

	@Autowired
	private ManagerMapper mapper;

	public void checkParams(String managerId, UpdateManagerRequest request) {
		try {
			ManagerInfoVo manager = mapper.selectManagerInfo(managerId);

			if (manager == null) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}

			if (request.getUsageStatus() != null) {
				boolean statusIs = false;
				for (EnumUsageStatus status : EnumUsageStatus.values()) {
					if (status.getKey().equals(request.getUsageStatus())) {
						statusIs = true;
						break;
					}
				}
				if (!statusIs) {
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
	public void updateManagerInfo(String managerId, UpdateManagerRequest request) {
		try {
			// 변경 값 유무 확인
			ManagerInfoVo mngBeforeUpdate = mapper.selectManagerInfo(managerId);
			boolean dataIsChanged = false;

			String updatedData = "";
			if (request.getManagerPassword() != null) {
				if (!request.getManagerPassword().equals(mngBeforeUpdate.getPASSWORD())) {
					updatedData += "PASSWORD:" + request.getManagerPassword() + " ";
					dataIsChanged = true;
				}
			}
			if (request.getManagerEmail() != null) {
				if (!request.getManagerEmail().equals(mngBeforeUpdate.getEMAIL())) {
					updatedData += "EMAIL:" + request.getManagerEmail() + " ";
					dataIsChanged = true;
				}
			}
			if (request.getManagerName() != null) {
				if (!request.getManagerName().equals(mngBeforeUpdate.getNAME())) {
					updatedData += "NAME:" + request.getManagerName() + " ";
					dataIsChanged = true;
				}
			}
			if (request.getDescription() != null) {
				if (!request.getDescription().equals(mngBeforeUpdate.getDESCRIPTION())) {
					updatedData += "DESCRIPTION:" + request.getDescription() + " ";
					dataIsChanged = true;
				}
			}
			if (request.getUsageStatus() != null) {
				if (!request.getUsageStatus().equals(mngBeforeUpdate.getSTATUS())) {
					updatedData += "STATUS:" + request.getUsageStatus() + " ";
					dataIsChanged = true;
				}
			}

			if (!dataIsChanged) {
				throw new KMSException(KMSErrorCode.DATABASE_UPDATE_FAILURE);
			}

			// 관리자 정보 수정
			ManagerInfoVo manager = new ManagerInfoVo();
			manager.setID(managerId);
			manager.setPASSWORD(request.getManagerPassword());
			manager.setEMAIL(request.getManagerEmail());
			manager.setNAME(request.getManagerName());
			manager.setDESCRIPTION(request.getDescription());
			manager.setSTATUS(request.getUsageStatus());
			manager.setUPT_USER("ADMIN");

			if (mapper.updateManagerInfo(manager) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_UPDATE_FAILURE);
			}

			// 관리자 정보 수정 이력 등록
			ManagerHistoryVo history = new ManagerHistoryVo();
			history.setID(managerId);
			history.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_UPDATE_MANAGER.getKey());
			history.setPARAM(updatedData);
			history.setHMAC("");
			history.setREG_USER("ADMIN");

			if (mapper.insertManagerHistory(history) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
			}

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

}
