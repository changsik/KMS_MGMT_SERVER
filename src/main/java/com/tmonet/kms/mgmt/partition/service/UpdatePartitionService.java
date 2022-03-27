package com.tmonet.kms.mgmt.partition.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHistoryOperationCode;
import com.tmonet.kms.mgmt.common.kmsenum.EnumPartitionType;
import com.tmonet.kms.mgmt.common.vo.HsmHistoryVo;
import com.tmonet.kms.mgmt.partition.mapper.PartitionMapper;
import com.tmonet.kms.mgmt.partition.model.UpdatePartitionRequest;
import com.tmonet.kms.mgmt.partition.vo.PartitionInfoVo;

@Service
public class UpdatePartitionService {

	private static final Logger logger = LoggerFactory.getLogger(UpdatePartitionService.class);

	@Autowired
	private PartitionMapper mapper;

	public void checkParams(String hsmGroupId, String partitionId, UpdatePartitionRequest request) {
		try {
			PartitionInfoVo partition = new PartitionInfoVo();
			partition.setPARTITION_ID(partitionId);
			partition.setGROUP_ID(hsmGroupId);
			partition = mapper.selectPartitionInfo(partition);
			if (partition == null) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}

			if (request.getPartitionType() != null) {
				boolean patTypeData = false;
				for (EnumPartitionType patType : EnumPartitionType.values()) {
					if (patType.getKey().equals(request.getPartitionType())) {
						patTypeData = true;
					}
				}

				if (!patTypeData) {
					throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
				}
			}

			// TODO:HSM 제어서버를 통해 사용가능한 파티션 여부 확인

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	public void updatePartitionInfo(String hsmGroupId, String partitionId, UpdatePartitionRequest request) {
		try {
			// 변경 값 유무 확인
			PartitionInfoVo partition = new PartitionInfoVo();
			partition.setPARTITION_ID(partitionId);
			partition.setGROUP_ID(hsmGroupId);
			PartitionInfoVo patBeforeUpdate = mapper.selectPartitionInfo(partition);

			// 파티션 정보 수정
			partition = new PartitionInfoVo();
			partition.setPARTITION_ID(partitionId);
			partition.setGROUP_ID(hsmGroupId);
			partition.setPROTECT_MODE(request.getPartitionType());
			partition.setPASSWORD(request.getPartitionPassphrase());
			partition.setIDENT(request.getPartitionIdent());
			partition.setDESCRIPTION(request.getDescription());
			partition.setUPT_USER("ADMIN");

			if (mapper.updatePartitionInfo(partition) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_UPDATE_FAILURE);
			}

			// 파티션 정보 수정 이력 등록
			PartitionInfoVo updatedPat = mapper.selectPartitionInfo(partition);
			boolean dataIsChanged = false;
			if (updatedPat == null) {
				throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
			}

			// 수정 전 정보와 다르면 PARAM 컬럼에 주입
			String updatedData = "";
			if (patBeforeUpdate.getPASSWORD() != null) {
				if (!patBeforeUpdate.getPASSWORD().equals(updatedPat.getPASSWORD())) {
					updatedData += "PASSWORD : " + updatedPat.getPASSWORD() + " ";
					dataIsChanged = true;
				}
			}
			if (patBeforeUpdate.getPROTECT_MODE() != null) {
				if (!patBeforeUpdate.getPROTECT_MODE().equals(updatedPat.getPROTECT_MODE())) {
					updatedData += "PROTECT_MODE : " + updatedPat.getPROTECT_MODE() + " ";
					dataIsChanged = true;
				}
			}
			if (patBeforeUpdate.getIDENT() != null) {
				if (!patBeforeUpdate.getIDENT().equals(updatedPat.getIDENT())) {
					updatedData += "IDENT : " + updatedPat.getIDENT() + " ";
					dataIsChanged = true;
				}
			}
			if (patBeforeUpdate.getDESCRIPTION() != null) {
				if (!patBeforeUpdate.getDESCRIPTION().equals(updatedPat.getDESCRIPTION())) {
					updatedData += "DESCRIPTION : " + updatedPat.getDESCRIPTION() + " ";
					dataIsChanged = true;
				}
			}

			if (!dataIsChanged) {
				throw new KMSException(KMSErrorCode.DATABASE_UPDATE_FAILURE);
			}

			HsmHistoryVo hsmHistory = new HsmHistoryVo();
			hsmHistory.setPARTITION_ID(partitionId);
			hsmHistory.setGROUP_ID(hsmGroupId);
			hsmHistory.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_UPDATE_PARTITION.getKey());
			hsmHistory.setPARAM(updatedData);
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
