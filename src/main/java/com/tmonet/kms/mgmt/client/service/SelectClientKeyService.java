package com.tmonet.kms.mgmt.client.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmonet.kms.mgmt.client.mapper.ClientMapper;
import com.tmonet.kms.mgmt.client.model.SelectClientKeyListRequest;
import com.tmonet.kms.mgmt.client.vo.ClientKeyList;
import com.tmonet.kms.mgmt.client.vo.ClientKeyListVo;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumAuthenticationType;
import com.tmonet.kms.mgmt.common.kmsenum.EnumUsageStatus;

@Service
public class SelectClientKeyService {

	private static final Logger logger = LoggerFactory.getLogger(SelectClientKeyService.class);

	@Autowired
	private ClientMapper mapper;

	public void checkParams(String clientIp, String clientKeyId, SelectClientKeyListRequest request) {
		try {
			if (request.getUsageStatus() != null) {
				boolean statusIs = false;
				for (EnumUsageStatus status : EnumUsageStatus.values()) {
					if (status.getKey().equals(request.getUsageStatus())) {
						statusIs = true;
						break;
					}
				}
				if (!statusIs) {
					throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
				}
			}

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	public List<ClientKeyListVo> selectClientKey(String clientIp, String clientKeyId,
			SelectClientKeyListRequest request) {
		try {

			ClientKeyListVo cliKey = new ClientKeyListVo();
			cliKey.setKEY_ID(clientKeyId);
			cliKey.setIP_ADDR(clientIp);
			cliKey.setSTATUS(request.getUsageStatus());

			List<ClientKeyListVo> listClientKeyVo = mapper.selectAllClientKeyList(cliKey);

			if (listClientKeyVo.size() < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}

			return listClientKeyVo;

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}
}
