package com.tmonet.kms.mgmt.hsm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHistoryOperationCode;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHsmInstallType;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHsmVendor;
import com.tmonet.kms.mgmt.common.vo.HsmHistoryVo;
import com.tmonet.kms.mgmt.hsm.mapper.HsmMapper;
import com.tmonet.kms.mgmt.hsm.model.UpdateHsmRequest;
import com.tmonet.kms.mgmt.hsm.vo.HsmInfoVo;

@Service
public class UpdateHsmService {

	private static final Logger logger = LoggerFactory.getLogger(UpdateHsmService.class);

	@Autowired
	private HsmMapper mapper;

	public void checkParams(String hsmId, UpdateHsmRequest request) {
		// 제어서버를 통해 수정 가능한 HSM인지 검증
		HsmInfoVo hsm = new HsmInfoVo();
		hsm.setHSM_ID(hsmId);
		if (mapper.selectHsmInfo(hsm) == null) {
			throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
		}

		if (request.getHsmVendor() != null) {
			boolean vendorData = false;
			for (EnumHsmVendor vendor : EnumHsmVendor.values()) {
				if (vendor.getKey().equals(request.getHsmVendor())) {
					vendorData = true;
					break;
				}
			}
			if (!vendorData) {
				throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
			}
		}

		if (request.getHsmInstallType() != null) {
			boolean installTypeData = false;
			for (EnumHsmInstallType installType : EnumHsmInstallType.values()) {
				if (installType.getKey().equals(request.getHsmInstallType())) {
					installTypeData = true;
					break;
				}
			}
			if (!installTypeData) {
				throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
			}
		}

	}

	public void updateHsmInfo(String hsmId, UpdateHsmRequest request) {
		try {
			HsmInfoVo hsm = new HsmInfoVo();
			hsm.setHSM_ID(hsmId);
			hsm.setVENDOR(request.getHsmVendor());
			// 시리얼번호는 변경될 수 없음
			hsm.setSERIAL(request.getHsmSerialNumber());
			hsm.setINSTALL_TYPE(request.getHsmInstallType());
			hsm.setIP_ADDR(request.getHsmIpAddress());
			hsm.setUPT_USER("ADMIN");

			HsmInfoVo vo = new HsmInfoVo();
			vo.setHSM_ID(hsmId);
			HsmInfoVo hsmBeforeUpdate = mapper.selectHsmInfo(vo);
			if (hsmBeforeUpdate == null) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}

			// HSM 관리 이력 등록
			String updatedData = "";
			boolean hsmIsChanged = false;

			if (hsmBeforeUpdate.getIP_ADDR() != null) {
				if (!hsmBeforeUpdate.getIP_ADDR().equals(request.getHsmIpAddress())) {
					updatedData += "IP_ADDR:" + request.getHsmIpAddress() + " ";
					hsmIsChanged = true;
				}
			}
			if (hsmBeforeUpdate.getVENDOR() != null) {
				if (!hsmBeforeUpdate.getVENDOR().equals(request.getHsmVendor())) {
					updatedData += "VENDOR:" + request.getHsmVendor() + " ";
					hsmIsChanged = true;
				}
			}
			if (hsmBeforeUpdate.getSERIAL() != null) {
				if (!hsmBeforeUpdate.getSERIAL().equals(request.getHsmSerialNumber())) {
					updatedData += "SERIAL:" + request.getHsmSerialNumber() + " ";
					hsmIsChanged = true;
				}
			}
			if (hsmBeforeUpdate.getINSTALL_TYPE() != null) {
				if (!hsmBeforeUpdate.getINSTALL_TYPE().equals(request.getHsmInstallType())) {
					updatedData += "INSTALL_TYPE:" + request.getHsmInstallType() + " ";
					hsmIsChanged = true;
				}
			}
			if (hsmBeforeUpdate.getDESCRIPTION() != null) {
				if (!hsmBeforeUpdate.getDESCRIPTION().equals(request.getDescription())) {
					updatedData += "DESCRIPTION:" + request.getDescription() + " ";
					hsmIsChanged = true;
				}
			}

			if (!hsmIsChanged) {
				throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
			}

			if (mapper.updateHsmInfo(hsm) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_UPDATE_FAILURE);
			}

			HsmHistoryVo hsmHistory = new HsmHistoryVo();
			hsmHistory.setGROUP_ID(hsmBeforeUpdate.getGROUP_ID());
			hsmHistory.setHSM_ID(hsmId);
			hsmHistory.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_UPDATE_HSM.getKey());
			hsmHistory.setPARAM(updatedData);
			hsmHistory.setHMAC("");
			hsmHistory.setREG_USER("ADMIN");
			if (mapper.insertHsmHistory(hsmHistory) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
			}

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}
}
