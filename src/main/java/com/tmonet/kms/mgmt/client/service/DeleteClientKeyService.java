package com.tmonet.kms.mgmt.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmonet.kms.mgmt.client.mapper.ClientMapper;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;

@Service
public class DeleteClientKeyService {

	private static final Logger logger = LoggerFactory.getLogger(DeleteClientKeyService.class);

	@Autowired
	private ClientMapper mapper;

	public void deleteClientKeyList(String clientKeyId) {
		try {
			// 클라이언트 키 삭제
			if (mapper.deleteClientKey(clientKeyId) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_DELETE_FAILURE);
			}

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

}
