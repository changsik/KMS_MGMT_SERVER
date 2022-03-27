package com.tmonet.kms.mgmt.keylifecycle.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmonet.kms.mgmt.client.vo.ClientKeyListVo;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumKeyLifecycleState;
import com.tmonet.kms.mgmt.common.vo.KeyListVo;
import com.tmonet.kms.mgmt.key.vo.KeyHistoryVo;
import com.tmonet.kms.mgmt.keylifecycle.mapper.KeyLifecycleMapper;
import com.tmonet.kms.mgmt.keylifecycle.model.KeyLifecycleRequest;

@Service
public class DestroyKeyService {

	private static final Logger logger = LoggerFactory.getLogger(DestroyKeyService.class);

	@Autowired
	private KeyLifecycleMapper mapper;

	public void checkParams(KeyLifecycleRequest request) {
		try {
			// 폐기 할 수 있는 상태(준비 상태 또는 정지 상태)인지 확인
			if (request.getClientKeyId() != null || !request.getClientKeyId().equals("")) {
				ClientKeyListVo cliKey = mapper.selectClientKeyList(request.getClientKeyId());
				if (cliKey == null) {
					throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
				}

				if (!cliKey.getSTATUS().equals(EnumKeyLifecycleState.KEY_LC_PRE_ACTIVE.getKey())
						&& !cliKey.getSTATUS().equals(EnumKeyLifecycleState.KEY_LC_DEACTIVATED.getKey())) {
					throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
				}

			} else {
				KeyListVo key = new KeyListVo();
				key.setKEY_ID(request.getKeyId());
				key.setPARTITION_ID(request.getPartitionId());
				key.setGROUP_ID(request.getHsmGroupId());
				key = mapper.selectKeyList(key);
				if (key == null) {
					throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
				}

				if (!key.getKEY_STATUS().equals(EnumKeyLifecycleState.KEY_LC_PRE_ACTIVE.getKey())
						&& !key.getKEY_STATUS().equals(EnumKeyLifecycleState.KEY_LC_DEACTIVATED.getKey())) {
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
	public String destroyKey(KeyLifecycleRequest request) {
		try {
			if (request.getClientKeyId() != null) {
				// CLIENT_KEY_LIST 테이블의 키 생명주기 변경
				ClientKeyListVo cliKey = new ClientKeyListVo();
				cliKey.setKEY_ID(request.getClientKeyId());
				cliKey.setSTATUS(EnumKeyLifecycleState.KEY_LC_DESTROYED.getKey());
				cliKey.setUPT_USER("ADMIN");
				if (mapper.changeCliKeyStatus(cliKey) < 1) {
					throw new KMSException(KMSErrorCode.DATABASE_UPDATE_FAILURE);
				}
			} else {
				// KEY_LIST 테이블의 키 생명주기 변경
				KeyListVo key = new KeyListVo();
				key.setKEY_ID(request.getKeyId());
				key.setPARTITION_ID(request.getPartitionId());
				key.setGROUP_ID(request.getHsmGroupId());
				key.setKEY_STATUS(EnumKeyLifecycleState.KEY_LC_DESTROYED.getKey());
				key.setUPT_USER("ADMIN");
				if (mapper.changeKeyStatus(key) < 1) {
					throw new KMSException(KMSErrorCode.DATABASE_UPDATE_FAILURE);
				}

				// 변경 이력 등록
				KeyHistoryVo history = new KeyHistoryVo();
				history.setKEY_ID(request.getKeyId());
				history.setPARTITION_ID(request.getPartitionId());
				history.setGROUP_ID(request.getHsmGroupId());
				history.setTYPE(EnumKeyLifecycleState.KEY_LC_DESTROYED.getKey());
				String param = "KEY_STATUS:" + EnumKeyLifecycleState.KEY_LC_DESTROYED.getKey();
				history.setPARAM(param);
				history.setREG_USER("ADMIN");
				if (mapper.insertKeyHistory(history) < 1) {
					throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
				}

			}

			return EnumKeyLifecycleState.KEY_LC_DESTROYED.getKey();

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}
}
