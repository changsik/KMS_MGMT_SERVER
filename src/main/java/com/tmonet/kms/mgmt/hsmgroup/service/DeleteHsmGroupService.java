package com.tmonet.kms.mgmt.hsmgroup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHistoryOperationCode;
import com.tmonet.kms.mgmt.common.vo.HsmHistoryVo;
import com.tmonet.kms.mgmt.hsmgroup.mapper.HsmGroupMapper;

@Service
public class DeleteHsmGroupService {

//	private static final Logger logger = LoggerFactory.getLogger(DeleteHsmGroupService.class);

	@Autowired
	private HsmGroupMapper mapper;

	public void deleteHsmGroupInfo(String groupId) {
		if (mapper.deleteHsmGroupInfo(groupId) < 1) {
			throw new KMSException(KMSErrorCode.DATABASE_DELETE_FAILURE);
		}
	}
	
	@Transactional(rollbackFor = { KMSException.class })
	public void storeHsmGroupInfo(String groupId) {
		try {
			HsmHistoryVo hsmHistory = new HsmHistoryVo();
			hsmHistory.setGROUP_ID(groupId);
			hsmHistory.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_DELETE_HSM_GROUP.getKey());
			hsmHistory.setREG_USER("MGMT");
			if (mapper.insertHsmHistory(hsmHistory) < 1) {
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
