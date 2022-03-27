package com.tmonet.kms.mgmt.key.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmonet.common.object.Version;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumKeyLifecycleState;
import com.tmonet.kms.mgmt.common.kmsenum.EnumOperationCode;
import com.tmonet.kms.mgmt.common.kmsenum.EnumUsageStatus;
import com.tmonet.kms.mgmt.common.object.ApiInfo;
import com.tmonet.kms.mgmt.common.vo.HsmServiceInfoVo;
import com.tmonet.kms.mgmt.common.vo.KeyAttrListVo;
import com.tmonet.kms.mgmt.common.vo.KeyListVo;
import com.tmonet.kms.mgmt.key.mapper.KeyManagementMapper;
import com.tmonet.kms.mgmt.key.model.hsm.HsmCreateSymmetricKeyRequest;
import com.tmonet.kms.mgmt.key.vo.KeyHistoryVo;
import com.tmonet.kms.mgmt.key.vo.KeyProfileAttrListVo;
import com.tmonet.kms.mgmt.key.vo.KeyProfileInfoVo;

@Service
public class UpdateSymmetricKeyService {

//	private static final Logger logger = LoggerFactory.getLogger(UpdateSymmetricKeyService.class);

	@Autowired
	private KeyManagementMapper mapper;
	
	public KeyListVo selectKeyList(String keyId) {
		try {
			KeyListVo keyListVo = mapper.selectKeyList(keyId);
			if (keyListVo == null) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}
			return keyListVo;
		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	public List<KeyAttrListVo> selectKeyAttrList(KeyListVo vo) {
		try {
			List<KeyAttrListVo> keyAttrList = mapper.selectKeyAttrList(vo);
			if (keyAttrList.size() == 0) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}
			return keyAttrList;
		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	public void checkKeyStatus(KeyListVo keyList) {
		if (!keyList.getKEY_STATUS().equals(EnumKeyLifecycleState.KEY_LC_ACTIVE.getKey())) {
			throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
		}
	}

	public void checkKeyProfileStatus(String keyProfileId) {
		try {
			// 키 프로파일 고유 식별번호 검증
			KeyProfileInfoVo keyProfileInfoVo = mapper.selectKeyProfileInfo(keyProfileId);
			if (!keyProfileInfoVo.getSTATUS().equals(EnumUsageStatus.USAGE_STATUS_ACTIVE.getKey())) {
				throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
			}
		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	public HsmServiceInfoVo getHsmServiceInfo(String serviceId) {
		try {
			// HSM 제어 서버 정보 조회
			HsmServiceInfoVo hsmServiceInfo = mapper.selectHsmServiceInfo(serviceId);
			if (hsmServiceInfo.getSLB_IP_ADDR().equals("") || hsmServiceInfo.getPARTITION_ID().equals("")) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}
			return hsmServiceInfo;
		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	public List<KeyProfileAttrListVo> selectKeyProfileAttrList(String keyProfileId) {
		try {
			// 키 프로파일 속성 정보 획득
			List<KeyProfileAttrListVo> keyProfileAttrList = mapper.selectKeyProfileAttrList(keyProfileId);
			if (keyProfileAttrList.size() == 0) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}
			return keyProfileAttrList;
		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	public HsmCreateSymmetricKeyRequest makeHsmCreateSymmetricKeyRequestData(String partitionId,
			List<KeyProfileAttrListVo> keyProfileAttrList) {
		try {
			HsmCreateSymmetricKeyRequest reqData = new HsmCreateSymmetricKeyRequest();
			ApiInfo apiInfo = new ApiInfo();
			apiInfo.setApiId("HSM.SK.REG.01");
			apiInfo.setApiVersion(new Version(1, 0, 0));
			reqData.setApiInfo(apiInfo);
			reqData.setPartitionId(partitionId);

			for (KeyProfileAttrListVo vo : keyProfileAttrList) {
				switch (vo.getATTR_ID()) {
				case "cryptographicAlgorithm": // 알고리즘
					reqData.setCryptographicAlgorithm(vo.getATTR_DEFAULT());
					break;
				case "keyLength": // 키 길이
					reqData.setKeyLength(Integer.parseInt(vo.getATTR_DEFAULT()));
					break;
				}
			}
			return reqData;
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}
	
	public void setKeyAttr(List<KeyAttrListVo> keyAttrList, String attrId, String value) {
		KeyAttrListVo vo = new KeyAttrListVo();
		vo.setATTR_ID(attrId);
		vo.setVALUE(value);
		vo.setATTR_TYPE("S");
		keyAttrList.add(vo);
	}
	
	public List<KeyAttrListVo> setKeyProfileAttrListToKeyAttrList(List<KeyProfileAttrListVo> keyProfileAttrList) {
		List<KeyAttrListVo> keyAttrList = new ArrayList<>();

		for (KeyProfileAttrListVo keyProfileAttr : keyProfileAttrList) {
			KeyAttrListVo keyAttr = new KeyAttrListVo();
			keyAttr.setATTR_ID(keyProfileAttr.getATTR_ID());
			keyAttr.setVALUE(keyProfileAttr.getATTR_DEFAULT());
			keyAttrList.add(keyAttr);
		}

		return keyAttrList;
	}

	@Transactional(rollbackFor = { KMSException.class })
	public void updateSymmetricKeyInfo(String profileId, String oldKeyId, String newKeyId, HsmServiceInfoVo hsmGroupInfo,
			List<KeyAttrListVo> keyAttrList) {
		try {
			// oldKeyId NKM_KEY_ATTR_LIST, NKM_KEY_LIST 삭제
//			if (mapper.deleteKeyAttrList(oldKeyId) < 1) {
//				throw new KMSException(KMSErrorCode.DATABASE_DELETE_FAILURE);
//			}
//			if (mapper.deleteKeyList(oldKeyId) < 1) {
//				throw new KMSException(KMSErrorCode.DATABASE_DELETE_FAILURE);
//			}
			// 키 상태 변경 
			KeyListVo deleteKeyList = new KeyListVo();
			deleteKeyList.setKEY_ID(oldKeyId);
			deleteKeyList.setKEY_STATUS(EnumKeyLifecycleState.KEY_LC_DEACTIVATED.getKey());
			mapper.updateKeyList(deleteKeyList);
			
			// NKM_KEY_HISTORY 키 관리 이력정보 저장
			KeyHistoryVo keyHistory = new KeyHistoryVo();
			keyHistory.setKEY_ID(oldKeyId);
			keyHistory.setPARTITION_ID(hsmGroupInfo.getPARTITION_ID());
			keyHistory.setGROUP_ID(hsmGroupInfo.getGROUP_ID());
			keyHistory.setTYPE(EnumOperationCode.OP_CODE_REVOKE.getKey());
			keyHistory.setPARAM("");
			keyHistory.setHMAC("");
			keyHistory.setREG_USER("MGMT");
			if (mapper.insertKeyHistory(keyHistory) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
			}
			
			// NKM_KEY_LIST 테이블에 키 데이터 저장
			KeyListVo keyList = new KeyListVo();
			keyList.setKEY_ID(newKeyId);
			keyList.setPARTITION_ID(hsmGroupInfo.getPARTITION_ID());
			keyList.setGROUP_ID(hsmGroupInfo.getGROUP_ID());
			keyList.setPROFILE_ID(profileId);
			keyList.setKEY_STATUS(EnumKeyLifecycleState.KEY_LC_ACTIVE.getKey());
			keyList.setREG_USER("MGMT");
			if (mapper.insertKeyList(keyList) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
			}

			// NKM_KEY_ATTR_LIST 테이블에 키 속성 데이터 저장
			for (KeyAttrListVo keyAttr : keyAttrList) {
				keyAttr.setKEY_ID(newKeyId);
				keyAttr.setPARTITION_ID(hsmGroupInfo.getPARTITION_ID());
				keyAttr.setGROUP_ID(hsmGroupInfo.getGROUP_ID());
				keyAttr.setREG_USER("MGMT");
				if (mapper.insertKeyAttrList(keyAttr) < 1) {
					throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
				}
			}

			// NKM_KEY_HISTORY 테이블에 키 관리 이력정보 저장
			keyHistory = new KeyHistoryVo();
			keyHistory.setKEY_ID(newKeyId);
			keyHistory.setPARTITION_ID(hsmGroupInfo.getPARTITION_ID());
			keyHistory.setGROUP_ID(hsmGroupInfo.getGROUP_ID());
			keyHistory.setTYPE(EnumOperationCode.OP_CODE_RE_KEY.getKey());
			keyHistory.setPARAM("");
			keyHistory.setHMAC("");
			keyHistory.setREG_USER("MGMT");
			if (mapper.insertKeyHistory(keyHistory) < 1) {
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
