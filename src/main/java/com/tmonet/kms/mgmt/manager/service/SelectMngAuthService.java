package com.tmonet.kms.mgmt.manager.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.manager.mapper.ManagerMapper;
import com.tmonet.kms.mgmt.manager.vo.MngAuthInfoVo;

@Service
public class SelectMngAuthService {

	private static final Logger logger = LoggerFactory.getLogger(SelectMngAuthService.class);

	@Autowired
	private ManagerMapper mapper;

	public List<MngAuthInfoVo> selectMngAuth(String managerId, String serviceId) {
		try {
			List<MngAuthInfoVo> listMngAuth = new ArrayList<MngAuthInfoVo>();
			if (serviceId == null) {
				listMngAuth = mapper.selectAllMngAuthInfo(managerId);
			} else {
				MngAuthInfoVo mngAuth = new MngAuthInfoVo();
				mngAuth.setID(managerId);
				mngAuth.setSERVICE_ID(serviceId);
				mngAuth = mapper.selectMngAuthInfo(mngAuth);
				if (mngAuth != null) {
					listMngAuth.add(mngAuth);
				}
			}
			if (listMngAuth.size() == 0) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}

			return listMngAuth;

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

}
