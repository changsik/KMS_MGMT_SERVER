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
import com.tmonet.kms.mgmt.key.model.ExportKeyRequest;
import com.tmonet.kms.mgmt.key.model.hsm.HsmExportKeyRequest;
import com.tmonet.kms.mgmt.key.vo.KeyHistoryVo;

@Service
public class ExportKeyService {

//	private static final Logger logger = LoggerFactory.getLogger(ExportKeyService.class);

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

	public void checkKeyStatus(KeyListVo keyList, List<KeyAttrListVo> keyAttrList) {
		if (!keyList.getKEY_STATUS().equals(EnumKeyLifecycleState.KEY_LC_ACTIVE.getKey())) {
			throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
		}
		
		KeyAttrListVo keyObjectType = null;
		KeyAttrListVo canExportKey = null;
		for (KeyAttrListVo vo : keyAttrList) {
			if (vo.getATTR_ID().equals("keyObjectType")) {	
				keyObjectType = vo;
			} else if (vo.getATTR_ID().equals("canExportKey")) {	
				canExportKey = vo;
			}
		}
		
		if (keyObjectType == null || canExportKey == null) {
			throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
		}
		
		// 공개키, 대칭키 일 경우만 키 추출 허용
		if (!keyObjectType.getVALUE().equals(EnumKeyObjectType.KEY_OBJ_TYPE_SYMMETRIC_KEY.getKey()) && 
				!keyObjectType.getVALUE().equals(EnumKeyObjectType.KEY_OBJ_TYPE_PUB_KEY.getKey())) {
			throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
		}
		// 키 추출 가능여부 체크 
		if (!canExportKey.getVALUE().equals("1")) {
			throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
		}
	}
	
	public void checkKeyEncryptionKeyStatus(KeyListVo keyList, List<KeyAttrListVo> keyAttrList) {
		if (!keyList.getKEY_STATUS().equals(EnumUsageStatus.USAGE_STATUS_ACTIVE.getKey())) {
			throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
		}
		for (KeyAttrListVo vo : keyAttrList) {
			// Wrap Key 여부 확인 00000010
			if (vo.getATTR_ID().equals("keyUsageMask")) {
				// 1(Decrypt)		1(Encrypt) 		1(Verify) 		1(Sign)
				// 1(MAC Generate)  				1(Unwrap Key)	1(Wrap Key)
				int i = Integer.parseInt(vo.getVALUE(), 16);
				if ((i & 10) != 10) {
					throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
				}
			}
		}
	}

	public HsmServiceInfoVo getHsmServiceInfo(String serviceId) throws KMSException {
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

	public HsmExportKeyRequest makeHsmExportKeyRequestData(ExportKeyRequest request, String partitionId) {
		try {
			HsmExportKeyRequest reqData = new HsmExportKeyRequest();
			ApiInfo apiInfo = new ApiInfo();
			apiInfo.setApiId("HSM.KEY.EXP.01");
			apiInfo.setApiVersion(new Version(1, 0, 0));
			reqData.setApiInfo(apiInfo);
			reqData.setPartitionId(partitionId);
			reqData.setKeyId(request.getKeyId());
			reqData.setWrappingMethod(request.getWrappingMethod());
			reqData.setKeyEncryptionKeyId(request.getKeyEncryptionKeyId());
			reqData.setCryptographicParameter(request.getCryptographicParameter());
			return reqData;
		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	@Transactional(rollbackFor = { KMSException.class })
	public void storeKeyInfo(String keyId, HsmServiceInfoVo hsmGroupInfo) {
		try {
			// NKM_KEY_HISTORY 테이블에 키 관리 이력정보 저장
			KeyHistoryVo keyHistory = new KeyHistoryVo();
			keyHistory.setKEY_ID(keyId);
			keyHistory.setPARTITION_ID(hsmGroupInfo.getPARTITION_ID());
			keyHistory.setGROUP_ID(hsmGroupInfo.getGROUP_ID());
			keyHistory.setTYPE(EnumOperationCode.OP_CODE_EXPORT.getKey());
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
