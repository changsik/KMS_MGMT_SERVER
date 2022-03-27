package com.tmonet.kmsp.getattribute.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.vo.KeyListVo;
import com.tmonet.kms.mgmt.key.mapper.KeyManagementMapper;

@Service
public class GetAttributeService {

//	private static final Logger logger = LoggerFactory.getLogger(UpdateAsymmetricKeyService.class);

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
}