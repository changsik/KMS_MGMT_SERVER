package com.tmonet.kms.mgmt.manager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmonet.common.util.Converter;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHistoryOperationCode;
import com.tmonet.kms.mgmt.common.kmsenum.EnumManagerType;
import com.tmonet.kms.mgmt.common.kmsenum.EnumUsageStatus;
import com.tmonet.kms.mgmt.manager.mapper.ManagerMapper;
import com.tmonet.kms.mgmt.manager.model.RegisterManagerRequest;
import com.tmonet.kms.mgmt.manager.vo.ManagerHistoryVo;
import com.tmonet.kms.mgmt.manager.vo.ManagerInfoVo;

@Service
public class RegisterManagerService {

	private static final Logger logger = LoggerFactory.getLogger(RegisterManagerService.class);

	@Autowired
	private ManagerMapper mapper;

	public void checkParams(RegisterManagerRequest request) {
		try {
			if (mapper.selectManagerInfo(request.getManagerId()) != null) {
				throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
			}

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	@Transactional(rollbackFor = { KMSException.class })
	public void insertManagerInfo(RegisterManagerRequest request) {
		try {
			// 관리자 정보 등록
			ManagerInfoVo manager = new ManagerInfoVo();
			manager.setID(request.getManagerId());
			manager.setPASSWORD(request.getManagerPassword());
			manager.setEMAIL(request.getManagerEmail());
			manager.setNAME(request.getManagerName());
			manager.setTYPE(EnumManagerType.MNG_TYPE_NORMAL.getKey());
			manager.setDESCRIPTION(request.getDescription());
			manager.setSTATUS(EnumUsageStatus.USAGE_STATUS_ACTIVE.getKey());
			manager.setREG_USER("ADMIN");

			if (mapper.insertManagerInfo(manager) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
			}

			// 관리 이력 등록
			ManagerHistoryVo history = new ManagerHistoryVo();
			history.setID(request.getManagerId());
			history.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_CREATE_MANAGER.getKey());
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
