package com.tmonet.kms.mgmt.hsmctl.service;

import java.util.ArrayList;
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
import com.tmonet.kms.mgmt.hsmctl.model.DeleteHsmCtlListRequest;
import com.tmonet.kms.mgmt.hsmctl.model.DeleteHsmModuleRequest;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlListVo;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlSvrHistoryVo;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlSvrInfoVo;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmModule;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmModuleInfo;

@Service
public class DeleteHsmCtlListService {

	private static final Logger logger = LoggerFactory.getLogger(DeleteHsmCtlListService.class);

	@Autowired
	private HsmCtlMapper mapper;

	public void checkParams(DeleteHsmCtlListRequest request) {
		try {
			for (HsmModule hsmModule : request.getDeleteHsmModuleList()) {
				HsmCtlListVo hsmCtl = new HsmCtlListVo();
				hsmCtl.setCTLSVR_ID(request.getHsmCtlSvrId());
				hsmCtl.setHSM_ID(hsmModule.getHsmId());
				HsmCtlListVo selectedHsmCtl = mapper.selectHsmCtlList(hsmCtl);
				if (selectedHsmCtl == null) {
					throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
				}
				if (selectedHsmCtl.getCTL_ORDER() != hsmModule.getHsmControlOrder()) {
					throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
				}
			}

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	@Transactional(rollbackFor = { KMSException.class })
	public void deleteHsmCtlList(DeleteHsmCtlListRequest request) {
		try {
			String hsmGroupId = selectHsmGroupId(request.getDeleteHsmModuleList());

			for (HsmModule hsmModule : request.getDeleteHsmModuleList()) {
				// 관리대상 HSM 삭제
				HsmCtlListVo hsmCtl = new HsmCtlListVo();
				hsmCtl.setCTLSVR_ID(request.getHsmCtlSvrId());
				hsmCtl.setHSM_ID(hsmModule.getHsmId());
				if (mapper.deleteHsmCtlList(hsmCtl) < 1) {
					throw new KMSException(KMSErrorCode.DATABASE_DELETE_FAILURE);
				}

				HsmCtlSvrHistoryVo history = new HsmCtlSvrHistoryVo();
				history.setCTLSVR_ID(request.getHsmCtlSvrId());
				history.setGROUP_ID(hsmGroupId);
				history.setHSM_ID(hsmModule.getHsmId());
				history.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_DELETE_HSM_CTL_LIST.getKey());
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

	public String selectHsmGroupId(List<HsmModule> hsmModuleList) {
		try {
			HsmInfoVo hsm = mapper.selectHsmInfo(hsmModuleList.get(0).getHsmId());
			if (hsm == null) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}

			return hsm.getGROUP_ID();

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	public DeleteHsmModuleRequest makeHsmCtlListDeleteRequestData(DeleteHsmCtlListRequest request) {
		try {
			DeleteHsmModuleRequest reqData = new DeleteHsmModuleRequest();
			ApiInfo apiInfo = new ApiInfo();
			apiInfo.setApiId("HSM.ADMIN.05");
			apiInfo.setApiVersion(new Version(1, 0, 0));
			reqData.setApiInfo(apiInfo);
			List<HsmModuleInfo> deleteHsmModuleList = new ArrayList<HsmModuleInfo>();
			for (HsmModule hsmModule : request.getDeleteHsmModuleList()) {
				HsmModuleInfo newHsmModule = new HsmModuleInfo();
				newHsmModule.setHsmId(hsmModule.getHsmId());
				newHsmModule.setHsmSerialNumber(hsmModule.getHsmSerialNumber());
				newHsmModule.setHsmControlOrder(hsmModule.getHsmControlOrder());
				for (EnumHsmControlMode ctlMode : EnumHsmControlMode.values()) {
					if (ctlMode.getKey().equals(hsmModule.getHsmControlMode())) {
						newHsmModule.setHsmControlMode(ctlMode);
					}
				}
				deleteHsmModuleList.add(newHsmModule);
			}
			reqData.setDeleteHsmModuleList(deleteHsmModuleList);

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
