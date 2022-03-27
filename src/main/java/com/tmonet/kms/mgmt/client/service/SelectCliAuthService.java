package com.tmonet.kms.mgmt.client.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmonet.kms.mgmt.client.mapper.ClientMapper;
import com.tmonet.kms.mgmt.client.vo.CliAuthInfoVo;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;

@Service
public class SelectCliAuthService {

	private static final Logger logger = LoggerFactory.getLogger(SelectCliAuthService.class);

	@Autowired
	private ClientMapper mapper;

	public List<CliAuthInfoVo> selectCliAuthInfo(String clientIp, String serviceId) {
		try {
			List<CliAuthInfoVo> listCliAuth = new ArrayList<CliAuthInfoVo>();
			if (serviceId == null) {
				listCliAuth = mapper.selectAllCliAuthInfo(clientIp);
			} else {
				CliAuthInfoVo cliAuth = new CliAuthInfoVo();
				cliAuth.setIP_ADDR(clientIp);
				cliAuth.setSERVICE_ID(serviceId);
				cliAuth = mapper.selectCliAuthInfo(cliAuth);
				if (cliAuth != null) {
					listCliAuth.add(cliAuth);
				}
			}
			if (listCliAuth.size() == 0) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}

			return listCliAuth;

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

}
