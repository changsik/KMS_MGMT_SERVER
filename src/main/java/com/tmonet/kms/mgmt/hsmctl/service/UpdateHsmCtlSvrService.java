package com.tmonet.kms.mgmt.hsmctl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHistoryOperationCode;
import com.tmonet.kms.mgmt.hsmctl.mapper.HsmCtlMapper;
import com.tmonet.kms.mgmt.hsmctl.model.UpdateHsmCtlSvrRequest;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlSvrHistoryVo;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlSvrInfoVo;

@Service
public class UpdateHsmCtlSvrService {

	@Autowired
	private HsmCtlMapper mapper;

	private static final Logger logger = LoggerFactory.getLogger(UpdateHsmCtlSvrService.class);

	public void checkParams(String hsmCtlSvrId, UpdateHsmCtlSvrRequest request) {
		try {
			if (mapper.selectHsmCtlSvrInfo(hsmCtlSvrId) == null) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	@Transactional(rollbackFor = { KMSException.class })
	public void updateHsmCtlSvrInfo(String hsmCtlSvrId, UpdateHsmCtlSvrRequest request) {
		try {
			HsmCtlSvrInfoVo svrBeforeUpdate = mapper.selectHsmCtlSvrInfo(hsmCtlSvrId);
			boolean dataIsChanged = false;

			// HSM 제어서버 수정
			HsmCtlSvrInfoVo ctlSvr = new HsmCtlSvrInfoVo();
			ctlSvr.setCTLSVR_ID(hsmCtlSvrId);
			ctlSvr.setIP_ADDR(request.getHsmIpAddress());
			if (request.getHsmPort() != 0) {
				ctlSvr.setPORT(request.getHsmPort());
			}
			ctlSvr.setDESCRIPTION(request.getDescription());
			ctlSvr.setUPT_USER("ADMIN");

			if (mapper.updateHsmCtlSvrInfo(ctlSvr) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_UPDATE_FAILURE);
			}

			// HSM 제어서버 수정 이력 등록
			HsmCtlSvrHistoryVo history = new HsmCtlSvrHistoryVo();
			String updatedData = "";
			if (request.getHsmIpAddress() != null) {
				if (!request.getHsmIpAddress().equals(svrBeforeUpdate.getIP_ADDR())) {
					updatedData += "IP_ADDR:" + request.getHsmIpAddress() + " ";
					dataIsChanged = true;
				}
			}
			if (request.getHsmPort() != 0) {
				if (request.getHsmPort() != svrBeforeUpdate.getPORT()) {
					updatedData += "PORT:" + request.getHsmPort() + " ";
					dataIsChanged = true;
				}
			}
			if (request.getDescription() != null) {
				if (!request.getDescription().equals(svrBeforeUpdate.getDESCRIPTION())) {
					updatedData += "DESCRIPTION:" + request.getHsmIpAddress() + " ";
					dataIsChanged = true;
				}
			}

			if (dataIsChanged) {
				history.setCTLSVR_ID(hsmCtlSvrId);
				history.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_UPDATE_HSM_CTL_SVR.getKey());
				history.setPARAM(updatedData);
				history.setHMAC("");
				history.setREG_USER("ADMIN");

				if (mapper.insertHsmCtlSvrHistory(history) < 1) {
					throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
				}
			}
		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

}
