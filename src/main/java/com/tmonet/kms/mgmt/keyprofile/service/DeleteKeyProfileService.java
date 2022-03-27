package com.tmonet.kms.mgmt.keyprofile.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHistoryOperationCode;
import com.tmonet.kms.mgmt.common.kmsenum.EnumUsageStatus;
import com.tmonet.kms.mgmt.keyprofile.mapper.KeyProfileMapper;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyAttribute;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyProfileHistoryVo;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyProfileInfoVo;

@Service
public class DeleteKeyProfileService {

	private static final Logger logger = LoggerFactory.getLogger(DeleteKeyProfileService.class);

	@Autowired
	private KeyProfileMapper mapper;

	public int selectKey(String keyProfileId) {
		int keyMadeOfProfile = -1;
		keyMadeOfProfile = mapper.countKeyId(keyProfileId);
		if (keyMadeOfProfile < 0) {
			throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
		}
		return keyMadeOfProfile;
	}

	@Transactional(rollbackFor = { KMSException.class })
	public int deleteKeyProfile(int keyMadeOfProfile, String keyProfileId) {
		try {
			// 해당 키 프로파일로 만든 키가 존재하면 상태만 변경
			if (keyMadeOfProfile > 0 && !mapper.selectKeyProfileInfo(keyProfileId).getSTATUS()
					.equals(EnumUsageStatus.USAGE_STATUS_DISCARD.getKey())) {
				KeyProfileInfoVo keyProfile = new KeyProfileInfoVo();
				keyProfile.setPROFILE_ID(keyProfileId);
				keyProfile.setSTATUS(EnumUsageStatus.USAGE_STATUS_DISCARD.getKey());
				keyProfile.setUPT_USER("ADMIN");
				if (mapper.updateKeyProfileInfo(keyProfile) < 1) {
					throw new KMSException(KMSErrorCode.DATABASE_UPDATE_FAILURE);
				}

				// 키 프로파일 상태 변경 이력 등록
				KeyProfileHistoryVo profileHistory = new KeyProfileHistoryVo();
				profileHistory.setPROFILE_ID(keyProfileId);
				profileHistory.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_UPDATE_KEY_PROFILE.getKey());
				profileHistory.setPARAM("PARAM:" + EnumUsageStatus.USAGE_STATUS_DISCARD.getKey());
				profileHistory.setHMAC("");
				profileHistory.setREG_USER("ADMIN");
				if (mapper.insertKeyProfileHistory(profileHistory) < 1) {
					throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
				}

				// 해당 키 프로파일로 만든 키가 존재하지 않으면 물리적으로 삭제
			} else {
				List<KeyAttribute> listKeyAttr = mapper.selectAllKeyProfileAttrList(keyProfileId);

				if (mapper.deleteAllKeyProfileAttrList(keyProfileId) < 1) {
					throw new KMSException(KMSErrorCode.DATABASE_DELETE_FAILURE);
				}
				if (mapper.deleteKeyProfileInfo(keyProfileId) < 1) {
					throw new KMSException(KMSErrorCode.DATABASE_DELETE_FAILURE);
				}

				KeyProfileHistoryVo profileHistory = new KeyProfileHistoryVo();
				profileHistory.setPROFILE_ID(keyProfileId);
				profileHistory.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_DELETE_KEY_PROFILE.getKey());
				profileHistory.setHMAC("");
				profileHistory.setREG_USER("ADMIN");
				if (mapper.insertKeyProfileHistory(profileHistory) < 1) {
					throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
				}

				for (KeyAttribute keyAttr : listKeyAttr) {
					KeyProfileHistoryVo attrHistory = new KeyProfileHistoryVo();
					attrHistory.setPROFILE_ID(keyProfileId);
					attrHistory.setATTR_ID(keyAttr.getKeyAttributeId());
					attrHistory.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_DELETE_KEY_PROFILE_ATTR.getKey());
					attrHistory.setHMAC("");
					attrHistory.setREG_USER("ADMIN");
					if (mapper.insertKeyProfileHistory(attrHistory) < 1) {
						throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
					}
				}
			}

			return 1;

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

}
