package com.tmonet.kms.mgmt.partition.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmonet.common.util.Converter;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHistoryOperationCode;
import com.tmonet.kms.mgmt.common.kmsenum.EnumPartitionType;
import com.tmonet.kms.mgmt.common.vo.HsmHistoryVo;
import com.tmonet.kms.mgmt.partition.mapper.PartitionMapper;
import com.tmonet.kms.mgmt.partition.model.RegisterPartitionRequest;
import com.tmonet.kms.mgmt.partition.vo.PartitionInfoVo;

@Service
public class RegisterPartitionService {

	private static final Logger logger = LoggerFactory.getLogger(RegisterPartitionService.class);

	@Autowired
	private PartitionMapper mapper;

	public void checkPartitionInfo(RegisterPartitionRequest request) {
		try {
			if (mapper.selectHsmGroupInfo(request.getHsmGroupId()) == null) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}

			PartitionInfoVo partition = new PartitionInfoVo();
			partition.setPARTITION_ID(request.getPartitionId());
			partition.setGROUP_ID(request.getHsmGroupId());
			if (mapper.selectPartitionInfo(partition) != null) {
				throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
			}

			boolean patTypeIs = false;
			for (EnumPartitionType patType : EnumPartitionType.values()) {
				if (patType.getKey().equals(request.getPartitionType())) {
					patTypeIs = true;
					break;
				}
			}
			if (!patTypeIs) {
				throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
			}

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	public void insertPartitionInfo(RegisterPartitionRequest request) {
		try {
			// 파티션 정보 등록
			PartitionInfoVo partition = new PartitionInfoVo();
			partition.setPARTITION_ID(request.getPartitionId());
			partition.setGROUP_ID(request.getHsmGroupId());
			// 서비스 ID = 파티션 ID와 HSM 그룹 ID를 더해 hash 한 값
			String hashedData = Converter.sha256Encode(request.getPartitionId() + request.getHsmGroupId());
			partition.setSERVICE_ID(hashedData);
			partition.setPROTECT_MODE(request.getPartitionType());
			partition.setPASSWORD(request.getPartitionPassphrase());
			partition.setIDENT(request.getPartitionIdent());
			partition.setDESCRIPTION(request.getDescription());
			partition.setREG_USER("ADMIN");

			if (mapper.insertPartitionInfo(partition) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
			}

			// 파티션 관리 이력 등록
			HsmHistoryVo hsmHistory = new HsmHistoryVo();
			hsmHistory.setPARTITION_ID(request.getPartitionId());
			hsmHistory.setGROUP_ID(request.getHsmGroupId());
			hsmHistory.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_CREATE_PARTITION.getKey());
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
