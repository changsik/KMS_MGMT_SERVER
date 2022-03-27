package com.tmonet.kms.mgmt.hsmgroup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHistoryOperationCode;
import com.tmonet.kms.mgmt.common.vo.HsmHistoryVo;
import com.tmonet.kms.mgmt.hsmgroup.mapper.HsmGroupMapper;
import com.tmonet.kms.mgmt.hsmgroup.model.HsmGroupRequest;
import com.tmonet.kms.mgmt.hsmgroup.vo.HsmGroupInfoVo;

@Service
public class RegisterHsmGroupService {

//	private static final Logger logger = LoggerFactory.getLogger(RegisterHsmGroupService.class);

	@Autowired
	private HsmGroupMapper mapper;

	public String registerHsmGroupInfo(HsmGroupRequest request) {
		HsmGroupInfoVo hsmGroupInfo = new HsmGroupInfoVo();
		hsmGroupInfo.setSLB_IP_ADDR(request.getvIpAddr());
		hsmGroupInfo.setSLB_PORT(request.getvPort());
		hsmGroupInfo.setDESCRIPTION(request.getDescription());
		hsmGroupInfo.setREG_USER("ADMIN");

		String groupId = mapper.insertHsmGroupInfo(hsmGroupInfo);

		if (groupId.isEmpty()) {
			throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
		}

		return groupId;
	}

	@Transactional(rollbackFor = { KMSException.class })
	public void storeHsmGroupInfo(String groupId) {
		try {
			HsmHistoryVo hsmHistory = new HsmHistoryVo();
			hsmHistory.setGROUP_ID(groupId);
			hsmHistory.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_CREATE_HSM_GROUP.getKey());
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
