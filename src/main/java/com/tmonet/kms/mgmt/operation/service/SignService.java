package com.tmonet.kms.mgmt.operation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmonet.common.object.Version;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumKeyLifecycleState;
import com.tmonet.kms.mgmt.common.kmsenum.EnumOperationCode;
import com.tmonet.kms.mgmt.common.object.ApiInfo;
import com.tmonet.kms.mgmt.common.vo.HsmServiceInfoVo;
import com.tmonet.kms.mgmt.common.vo.KeyAttrListVo;
import com.tmonet.kms.mgmt.common.vo.KeyListVo;
import com.tmonet.kms.mgmt.key.vo.KeyHistoryVo;
import com.tmonet.kms.mgmt.operation.mapper.OperationMapper;
import com.tmonet.kms.mgmt.operation.model.SignRequest;
import com.tmonet.kms.mgmt.operation.model.hsm.HsmSignRequest;

@Service
public class SignService {

//	private static final Logger logger = LoggerFactory.getLogger(SignService.class);

	@Autowired
	private OperationMapper mapper;

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

	public void checkKeyStatus(KeyListVo keyList, List<KeyAttrListVo> keyAttrList, HsmServiceInfoVo hsmServiceInfo) {
		if (!keyList.getGROUP_ID().equals(hsmServiceInfo.getGROUP_ID()) ||
				!keyList.getPARTITION_ID().equals(hsmServiceInfo.getPARTITION_ID())) {
			throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
		}
		
		if (!keyList.getKEY_STATUS().equals(EnumKeyLifecycleState.KEY_LC_ACTIVE.getKey())) {
			throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
		}
		for (KeyAttrListVo vo : keyAttrList) {
			if (vo.getATTR_ID().equals("keyUsageMask")) {
				// 0000000F
				// F : 1(Decrypt) 1(Encrypt) 1(Verify) 1(Sign)
				int i = Integer.parseInt(vo.getVALUE(), 16);
				if ((i & 1) != 1) {
					throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
				}
			}
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

	public HsmSignRequest makeHsmSignRequestData(SignRequest request, String partitionId) {
		try {
			HsmSignRequest reqData = new HsmSignRequest();
			ApiInfo apiInfo = new ApiInfo();
			apiInfo.setApiId("HSM.OP.SIGN.01");
			apiInfo.setApiVersion(new Version(1, 0, 0));
			reqData.setApiInfo(apiInfo);
			reqData.setPartitionId(partitionId);

			reqData.setKeyId(request.getKeyId());
			reqData.setPlaintext(request.getPlaintext());
			reqData.setCryptographicParameter(request.getCryptographicParameter());
			return reqData;
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
			keyHistory.setTYPE(EnumOperationCode.OP_CODE_SIGN.getKey());
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
