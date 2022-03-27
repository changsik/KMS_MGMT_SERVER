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
import com.tmonet.kms.mgmt.hsmctl.model.RegisterHsmCtlSvrRequest;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlSvrHistoryVo;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlSvrInfoVo;

@Service
public class RegisterHsmCtlSvrService {

	private static final Logger logger = LoggerFactory.getLogger(RegisterHsmCtlSvrService.class);

	@Autowired
	private HsmCtlMapper mapper;

	public void checkParams(RegisterHsmCtlSvrRequest request) {
		try {
			if (mapper.selectHsmCtlSvrInfo(request.getHsmCtlSvrId()) != null) {
				throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
			}
		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	@Transactional(rollbackFor = { KMSException.class })
	public void registerHsmCtlSvr(RegisterHsmCtlSvrRequest request) {
		try {
			// HSM 제어서버 등록
			HsmCtlSvrInfoVo ctlSvr = new HsmCtlSvrInfoVo();
			ctlSvr.setCTLSVR_ID(request.getHsmCtlSvrId());
			ctlSvr.setIP_ADDR(request.getHsmIpAddress());
			ctlSvr.setPORT(request.getHsmPort());
			ctlSvr.setDESCRIPTION(request.getDescription());
			ctlSvr.setREG_USER("ADMIN");

			if (mapper.insertHsmCtlSvrInfo(ctlSvr) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
			}

			// HSM 제어서버 관리 이력 등록
			HsmCtlSvrHistoryVo history = new HsmCtlSvrHistoryVo();
			history.setCTLSVR_ID(ctlSvr.getCTLSVR_ID());
			history.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_CREATE_HSM_CTL_SVR.getKey());
			history.setHMAC("");
			history.setREG_USER("ADMIN");

			if (mapper.insertHsmCtlSvrHistory(history) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
			}

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

}
