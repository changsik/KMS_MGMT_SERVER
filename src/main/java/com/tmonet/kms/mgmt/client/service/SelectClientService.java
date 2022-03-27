package com.tmonet.kms.mgmt.client.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmonet.kms.mgmt.client.mapper.ClientMapper;
import com.tmonet.kms.mgmt.client.vo.ClientInfoVo;
import com.tmonet.kms.mgmt.client.vo.ClientKeyListVo;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumKeyLifecycleState;

@Service
public class SelectClientService {

	private static final Logger logger = LoggerFactory.getLogger(SelectClientService.class);

	@Autowired
	private ClientMapper mapper;

	public List<ClientInfoVo> selectClientInfo(String clientIp) {
		try {
			List<ClientInfoVo> listClient = new ArrayList<ClientInfoVo>();
			if (clientIp == null) {
				listClient = mapper.selectAllClientInfo();
			} else {
				ClientInfoVo client = mapper.selectClientInfo(clientIp);
				if (client != null) {
					listClient.add(client);
				}
			}

			if (listClient.size() == 0) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}

			return listClient;

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}

	}

}
