package com.tmonet.kms.mgmt.hsm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHistoryOperationCode;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHsmInstallType;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHsmVendor;
import com.tmonet.kms.mgmt.common.vo.HsmHistoryVo;
import com.tmonet.kms.mgmt.hsm.mapper.HsmMapper;
import com.tmonet.kms.mgmt.hsm.model.RegisterHsmRequest;
import com.tmonet.kms.mgmt.hsm.vo.HsmInfoVo;

@Service
public class RegisterHsmService {

	private static final Logger logger = LoggerFactory.getLogger(RegisterHsmService.class);

	@Autowired
	private HsmMapper mapper;

	public void checkParams(RegisterHsmRequest request) {
		try {
			boolean vendorIs = false;
			for (EnumHsmVendor vendor : EnumHsmVendor.values()) {
				if (vendor.getKey().equals(request.getHsmVendor())) {
					vendorIs = true;
					break;
				}
			}
			if (!vendorIs) {
				throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
			}

			boolean installTypeIs = false;
			for (EnumHsmInstallType installType : EnumHsmInstallType.values()) {
				if (installType.getKey().equals(request.getHsmInstallType())) {
					installTypeIs = true;
					break;
				}
			}
			if (!installTypeIs) {
				throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
			}

			// TODO:제어서버를 통해 사용가능한 HSM인지 검증
			boolean emptyData = true;
			if (!emptyData) {
				throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
			}

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	@Transactional(rollbackFor = { KMSException.class })
	public String registerHsmInfo(RegisterHsmRequest request) {
		try {
			// HSM 등록
			HsmInfoVo hsm = new HsmInfoVo();
			hsm.setGROUP_ID(request.getHsmGroupId());
			hsm.setVENDOR(request.getHsmVendor());
			hsm.setSERIAL(request.getHsmSerialNumber());
			hsm.setINSTALL_TYPE(request.getHsmInstallType());
			hsm.setIP_ADDR(request.getHsmIpAddress());
			hsm.setREG_USER("ADMIN");

			if (mapper.insertHsmInfo(hsm) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
			}

			// HSM 관리 이력 등록
			HsmHistoryVo hsmHistory = new HsmHistoryVo();
			HsmInfoVo regHsm = mapper.selectRegisteredHsmInfo();
			if (regHsm == null) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}

			hsmHistory.setGROUP_ID(regHsm.getGROUP_ID());
			hsmHistory.setHSM_ID(regHsm.getHSM_ID());
			hsmHistory.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_CREATE_HSM.getKey());
			hsmHistory.setHMAC("");
			hsmHistory.setREG_USER("ADMIN");
			if (mapper.insertHsmHistory(hsmHistory) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
			}

			return regHsm.getHSM_ID();

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

}
