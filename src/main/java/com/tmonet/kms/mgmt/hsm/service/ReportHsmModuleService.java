package com.tmonet.kms.mgmt.hsm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.hsm.mapper.HsmMapper;
import com.tmonet.kms.mgmt.hsmgroup.vo.HsmGroupInfoVo;

@Service
public class ReportHsmModuleService {

	private static final Logger logger = LoggerFactory.getLogger(ReportHsmModuleService.class);

	@Autowired
	private HsmMapper mapper;

	public HsmGroupInfoVo selectHsmGroupInfo(String hsmId) {
		try {
			HsmGroupInfoVo groupInfo = mapper.selectHsmGroupInfo(hsmId);
			if (groupInfo == null) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}
			return groupInfo;

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

}
