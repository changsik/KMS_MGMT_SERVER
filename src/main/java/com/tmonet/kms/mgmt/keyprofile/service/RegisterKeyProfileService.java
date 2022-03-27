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
import com.tmonet.kms.mgmt.common.kmsenum.EnumKeyAttributeId;
import com.tmonet.kms.mgmt.common.kmsenum.EnumUsageStatus;
import com.tmonet.kms.mgmt.keyprofile.mapper.KeyProfileMapper;
import com.tmonet.kms.mgmt.keyprofile.model.RegisterKeyProfileRequest;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyAttribute;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyProfileAttrListVo;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyProfileHistoryVo;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyProfileInfoVo;

@Service
public class RegisterKeyProfileService {

	private static final Logger logger = LoggerFactory.getLogger(RegisterKeyProfileService.class);

	@Autowired
	private KeyProfileMapper mapper;

	public void checkParams(RegisterKeyProfileRequest request) {
		try {
			List<KeyAttribute> listKeyAttribute = request.getListKeyAttribute();
			for (KeyAttribute keyAttribute : listKeyAttribute) {
				boolean keyAttrIdIs = false;
				for (EnumKeyAttributeId id : EnumKeyAttributeId.values()) {
					if (id.getKey().equals(keyAttribute.getKeyAttributeId())) {
						keyAttrIdIs = true;
						break;
					}
				}
				if (!keyAttrIdIs) {
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
	public String registerKeyprofileInfo(RegisterKeyProfileRequest request) {
		try {
			// 키 프로파일 등록
			KeyProfileInfoVo keyProfile = new KeyProfileInfoVo();
			keyProfile.setNAME(request.getKeyProfileName());
			keyProfile.setDESCRIPTION(request.getDescription());
			keyProfile.setSTATUS(EnumUsageStatus.USAGE_STATUS_ACTIVE.getKey());
			keyProfile.setREG_USER("ADMIN");
			if (mapper.insertKeyProfileInfo(keyProfile) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
			}

			// 키 프로파일 속성 목록 등록
			for (KeyAttribute keyAttr : request.getListKeyAttribute()) {
				KeyProfileAttrListVo attrList = new KeyProfileAttrListVo();
				attrList.setATTR_ID(keyAttr.getKeyAttributeId());
				attrList.setATTR_DEFAULT(keyAttr.getKeyAttributeValue());
				attrList.setREG_USER("ADMIN");

				if (mapper.insertKeyProfileAttr(attrList) < 1) {
					throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
				}
			}

			// 키 프로파일 관리 이력 등록
			KeyProfileHistoryVo profileHistory = new KeyProfileHistoryVo();
			KeyProfileInfoVo regProfile = mapper.selectRegisteredKeyProfile();
			if (regProfile == null) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}

			profileHistory.setPROFILE_ID(regProfile.getPROFILE_ID());
			profileHistory.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_CREATE_KEY_PROFILE.getKey());
			profileHistory.setHMAC("");
			profileHistory.setREG_USER("ADMIN");
			if (mapper.insertKeyProfileHistory(profileHistory) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
			}

			// 키 프로파일 속성 목록 관리 이력 등록
			for (KeyAttribute keyAttr : request.getListKeyAttribute()) {
				profileHistory.setATTR_ID(keyAttr.getKeyAttributeId());
				profileHistory.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_CREATE_KEY_PROFILE_ATTR.getKey());

				if (mapper.insertKeyProfileHistory(profileHistory) < 1) {
					throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
				}
			}

			return regProfile.getPROFILE_ID();

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}
}
