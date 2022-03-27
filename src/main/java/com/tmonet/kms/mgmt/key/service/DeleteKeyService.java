package com.tmonet.kms.mgmt.key.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmonet.common.object.Version;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumKeyLifecycleState;
import com.tmonet.kms.mgmt.common.kmsenum.EnumKeyObjectType;
import com.tmonet.kms.mgmt.common.kmsenum.EnumOperationCode;
import com.tmonet.kms.mgmt.common.kmsenum.EnumUsageStatus;
import com.tmonet.kms.mgmt.common.object.ApiInfo;
import com.tmonet.kms.mgmt.common.vo.HsmServiceInfoVo;
import com.tmonet.kms.mgmt.common.vo.KeyAttrListVo;
import com.tmonet.kms.mgmt.common.vo.KeyListVo;
import com.tmonet.kms.mgmt.key.mapper.KeyManagementMapper;
import com.tmonet.kms.mgmt.key.model.hsm.HsmDeleteKeyRequest;
import com.tmonet.kms.mgmt.key.vo.KeyHistoryVo;
import com.tmonet.kms.mgmt.key.vo.KeyProfileAttrListVo;
import com.tmonet.kms.mgmt.key.vo.KeyProfileInfoVo;

@Service
public class DeleteKeyService {

//	private static final Logger logger = LoggerFactory.getLogger(DeleteKeyService.class);

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
		// 키 삭제 시 Lifecycle 상태가 폐기상태인 경우만 허용한다 (00000005)
		if (!keyList.getKEY_STATUS().equals(EnumKeyLifecycleState.KEY_LC_DESTROYED.getKey())) {
			throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
		}
	}
	
	public void checkAsymmetricKeyVerify(KeyListVo priKeyList, KeyListVo pubKeyList, List<KeyProfileAttrListVo> priKeyProfileAttrList, List<KeyProfileAttrListVo> pubKeyProfileAttrList) {
		for (KeyProfileAttrListVo vo : priKeyProfileAttrList) {
			switch (vo.getATTR_ID()) {
			case "keyObjectType":	// 키 객체 유형 확인 
				if (!vo.getATTR_DEFAULT().equals(EnumKeyObjectType.KEY_OBJ_TYPE_PRI_KEY.getKey())) {	// 개인키
					throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
				}
				break;
			case "keyPairId": // 키 쌍 ID와 공개키 ID와 같은지 확인
				if (!vo.getATTR_DEFAULT().equals(pubKeyList.getKEY_ID())) {
					throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
				}
				break;
			}
		}
		for (KeyProfileAttrListVo vo : pubKeyProfileAttrList) {
			switch (vo.getATTR_ID()) {
			case "keyObjectType":	// 키 객체 유형 확인 
				if (!vo.getATTR_DEFAULT().equals(EnumKeyObjectType.KEY_OBJ_TYPE_PUB_KEY.getKey())) {	// 공개키
					throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
				}
				break;
			case "keyPairId": // 키 쌍 ID와 개인키 ID와 같은지 확인
				if (!vo.getATTR_DEFAULT().equals(priKeyList.getKEY_ID())) {
					throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
				}
				break;
			}
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
	

	public HsmServiceInfoVo getHsmServiceInfo(String serviceId) throws KMSException {
		try {
			// HSM 제어 서버 정보 조회
			HsmServiceInfoVo hsmServicenfo = mapper.selectHsmServiceInfo(serviceId);
			if (hsmServicenfo.getSLB_IP_ADDR().equals("") || hsmServicenfo.getPARTITION_ID().equals("")) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}
			return hsmServicenfo;
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

	public HsmDeleteKeyRequest makeHsmDeleteKeyRequestData(String partitionId, List<KeyProfileAttrListVo> keyProfileAttrList) {
		try {
			HsmDeleteKeyRequest reqData = new HsmDeleteKeyRequest();
			ApiInfo apiInfo = new ApiInfo();
			apiInfo.setApiId("HSM.KEY.DEL.01");
			apiInfo.setApiVersion(new Version(1, 0, 0));
			reqData.setApiInfo(apiInfo);
			reqData.setPartitionId(partitionId);
			return reqData;
		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	@Transactional(rollbackFor = { KMSException.class })
	public void deleteKeyInfo(String keyId, HsmServiceInfoVo hsmGroupInfo) {
		try {
			// keyId NKM_KEY_ATTR_LIST, NKM_KEY_LIST 삭제
			if (mapper.deleteKeyAttrList(keyId) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_DELETE_FAILURE);
			}
			if (mapper.deleteKeyList(keyId) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_DELETE_FAILURE);
			}
			
			// 키 상태 변경 
//			KeyListVo deleteKeyList = new KeyListVo();
//			deleteKeyList.setKEY_ID(keyId);
//			deleteKeyList.setKEY_STATUS(EnumKeyLifecycleState.KEY_LC_ACTIVE.getKey());
//			mapper.updateKeyList(deleteKeyList);
			
			// NKM_KEY_HISTORY 키 관리 이력정보 저장
			KeyHistoryVo keyHistory = new KeyHistoryVo();
			keyHistory.setKEY_ID(keyId);
			keyHistory.setPARTITION_ID(hsmGroupInfo.getPARTITION_ID());
			keyHistory.setGROUP_ID(hsmGroupInfo.getGROUP_ID());
			keyHistory.setTYPE(EnumOperationCode.OP_CODE_REVOKE.getKey());
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
