package com.tmonet.kms.mgmt.keyprofile.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumUsageStatus;
import com.tmonet.kms.mgmt.keyprofile.mapper.KeyProfileMapper;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyAttribute;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyProfileInfoVo;

@Service
public class SelectKeyProfileService {

	private static final Logger logger = LoggerFactory.getLogger(SelectKeyProfileService.class);

	@Autowired
	private KeyProfileMapper mapper;

	public List<KeyProfileInfoVo> selectKeyProfileInfo(String keyProfileId) {
		try {
			// 키 프로파일 정보 조회
			List<KeyProfileInfoVo> listProfile = new ArrayList<KeyProfileInfoVo>();
			if (keyProfileId == null) {
				listProfile = mapper.selectAllKeyProfileInfo();
			} else {
				KeyProfileInfoVo profile = mapper.selectKeyProfileInfo(keyProfileId);
				if (profile == null) {
					throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
				}
				listProfile.add(profile);
			}

			if (listProfile.size() == 0) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}

			return listProfile;

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	public List<KeyAttribute> selectAllKeyProfileAttrList(String keyProfileId) {
		try {
			List<KeyAttribute> listKeyAttr = mapper.selectAllKeyProfileAttrList(keyProfileId);
			if (listKeyAttr.size() == 0) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}
			return listKeyAttr;
		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

}
