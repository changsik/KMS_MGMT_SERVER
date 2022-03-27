package com.tmonet.kms.mgmt.partition.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHistoryOperationCode;
import com.tmonet.kms.mgmt.common.vo.HsmHistoryVo;
import com.tmonet.kms.mgmt.partition.mapper.PartitionMapper;
import com.tmonet.kms.mgmt.partition.vo.PartitionInfoVo;

@Service
public class DeletePartitionService {

	private static final Logger logger = LoggerFactory.getLogger(DeletePartitionService.class);

	@Autowired
	private PartitionMapper mapper;

	public void checkParams(String hsmGroupId, String partitionId) {
		try {
			PartitionInfoVo partition = new PartitionInfoVo();
			partition.setGROUP_ID(hsmGroupId);
			partition.setPARTITION_ID(partitionId);
			partition = mapper.selectPartitionInfo(partition);
			if (partition == null) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}

			if (mapper.countMngAuthInfo(partition.getSERVICE_ID()) > 0) {
				throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
			} else if (mapper.countCliAuthInfo(partition.getSERVICE_ID()) > 0) {
				throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
			} else if (mapper.countKeyList(partition) > 0) {
				throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
			}

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}

	}

	@Transactional(rollbackFor = { KMSException.class })
	public void deletePartitionInfo(String hsmGroupId, String partitionId) {
		try {
			// 파티션 정보 삭제
			PartitionInfoVo partition = new PartitionInfoVo();

			partition.setPARTITION_ID(partitionId);
			partition.setGROUP_ID(hsmGroupId);
			if (mapper.deletePartitionInfo(partition) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_DELETE_FAILURE);
			}

			HsmHistoryVo hsmHistory = new HsmHistoryVo();
			hsmHistory.setPARTITION_ID(partitionId);
			hsmHistory.setGROUP_ID(hsmGroupId);
			hsmHistory.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_DELETE_PARTITION.getKey());
			hsmHistory.setHMAC("");
			hsmHistory.setREG_USER("ADMIN");

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
