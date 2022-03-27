package com.tmonet.kms.mgmt.hsmctl.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmonet.common.object.Version;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHistoryOperationCode;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHsmControlMode;
import com.tmonet.kms.mgmt.common.object.ApiInfo;
import com.tmonet.kms.mgmt.hsm.vo.HsmInfoVo;
import com.tmonet.kms.mgmt.hsmctl.mapper.HsmCtlMapper;
import com.tmonet.kms.mgmt.hsmctl.model.RegisterHsmCtlListRequest;
import com.tmonet.kms.mgmt.hsmctl.model.RegisterHsmModuleRequest;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlListVo;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlSvrHistoryVo;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlSvrInfoVo;

@Service
public class RegisterHsmCtlListService {

	private static final Logger logger = LoggerFactory.getLogger(RegisterHsmCtlListService.class);

	@Autowired
	private HsmCtlMapper mapper;

	public void checkParams(RegisterHsmCtlListRequest request) {
		try {
			HsmCtlListVo hsmCtl = new HsmCtlListVo();
			hsmCtl.setCTLSVR_ID(request.getHsmCtlSvrId());
			hsmCtl.setGROUP_ID(request.getHsmGroupId());
			hsmCtl.setHSM_ID(request.getHsmId());
			if (mapper.selectHsmCtlList(hsmCtl) != null) {
				throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
			}

			if (request.getHsmCtlMode() != null) {
				boolean ctlModeIs = false;
				for (EnumHsmControlMode ctlMode : EnumHsmControlMode.values()) {
					if (ctlMode.getKey().equals(request.getHsmCtlMode())) {
						ctlModeIs = true;
						break;
					}
				}
				if (!ctlModeIs) {
					throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
				}
			}

			if (request.getHsmCtlOrder() != 0) {
				List<HsmCtlListVo> listHsmCtl = mapper.selectAllHsmCtlList(request.getHsmCtlSvrId());
				for (HsmCtlListVo vo : listHsmCtl) {
					if (vo.getCTL_ORDER() == request.getHsmCtlOrder()) {
						throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
					}
				}
			}

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	@Transactional(rollbackFor = { KMSException.class })
	public void insertHsmCtlList(RegisterHsmCtlListRequest request) {
		try {
			// 관리대상 HSM 목록 등록
			HsmCtlListVo hsmCtl = new HsmCtlListVo();
			hsmCtl.setCTLSVR_ID(request.getHsmCtlSvrId());
			hsmCtl.setGROUP_ID(request.getHsmGroupId());
			hsmCtl.setHSM_ID(request.getHsmId());
			hsmCtl.setCTL_ORDER(request.getHsmCtlOrder());
			hsmCtl.setMODE(request.getHsmCtlMode());
			hsmCtl.setREG_USER("ADMIN");
			if (mapper.insertHsmCtlList(hsmCtl) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
			}

			// 관리대상 HSM 목록 관리 이력 등록
			HsmCtlSvrHistoryVo history = new HsmCtlSvrHistoryVo();
			history.setCTLSVR_ID(request.getHsmCtlSvrId());
			history.setGROUP_ID(request.getHsmGroupId());
			history.setHSM_ID(request.getHsmId());
			history.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_CREATE_HSM_CTL_LIST.getKey());
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

	public HsmInfoVo selectHsmInfo(String hsmId) {
		try {
			HsmInfoVo hsm = mapper.selectHsmInfo(hsmId);
			if (hsm == null) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}

			return hsm;

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	public RegisterHsmModuleRequest makeHsmCtlListRegisterRequestData(RegisterHsmCtlListRequest request) {
		try {
			RegisterHsmModuleRequest reqData = new RegisterHsmModuleRequest();

			HsmInfoVo hsm = mapper.selectHsmInfo(request.getHsmId());
			ApiInfo apiInfo = new ApiInfo();
			apiInfo.setApiId("HSM.ADMIN.03");
			apiInfo.setApiVersion(new Version(1, 0, 0));
			reqData.setApiInfo(apiInfo);
			reqData.setHsmId(request.getHsmId());
			reqData.setHsmSerialNumber(hsm.getSERIAL());
			reqData.setHsmControlOrder(request.getHsmCtlOrder());
			for (EnumHsmControlMode ctlMode : EnumHsmControlMode.values()) {
				if (ctlMode.getKey().equals(request.getHsmCtlMode())) {
					reqData.setHsmControlMode(ctlMode);
				}
			}

			return reqData;

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	public HsmCtlSvrInfoVo selectHsmCtlSvrInfo(String hsmCtlSvrId) {
		try {
			HsmCtlSvrInfoVo ctlSvr = mapper.selectHsmCtlSvrInfo(hsmCtlSvrId);
			if (ctlSvr == null) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}

			return ctlSvr;

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

}
