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
import com.tmonet.kms.mgmt.hsmctl.model.DeleteHsmCtlSvrRequest;
import com.tmonet.kms.mgmt.hsmctl.model.DeleteHsmModuleRequest;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlListVo;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlSvrHistoryVo;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlSvrInfoVo;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmModule;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmModuleInfo;

@Service
public class DeleteHsmCtlSvrService {

	private static final Logger logger = LoggerFactory.getLogger(DeleteHsmCtlSvrService.class);

	@Autowired
	private HsmCtlMapper mapper;

	public void checkParams(String hsmCtlSvrId) {
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
	public void deleteHsmCtlSvr(String hsmCtlSvrId) {
		try {
			List<HsmCtlListVo> hsmCtlList = mapper.selectAllHsmCtlList(hsmCtlSvrId);

			// 관리대상 HSM 목록 삭제
			if(hsmCtlList.size() != 0) {
				if(mapper.deleteAllHsmCtlList(hsmCtlSvrId) < 1) {
					throw new KMSException(KMSErrorCode.DATABASE_DELETE_FAILURE);					
				}
			}
			
			// HSM 제어서버 삭제
			if (mapper.deleteHsmCtlSvrInfo(hsmCtlSvrId) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_DELETE_FAILURE);
			}

			// HSM 제어서버 삭제 이력 등록
			HsmCtlSvrHistoryVo history = new HsmCtlSvrHistoryVo();
			history.setCTLSVR_ID(hsmCtlSvrId);
			history.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_DELETE_HSM_CTL_SVR.getKey());
			history.setHMAC("");
			history.setREG_USER("ADMIN");
			if (mapper.insertHsmCtlSvrHistory(history) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
			}

			// 관리대상 HSM 목록 삭제 이력 등록
			history.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_DELETE_HSM_CTL_LIST.getKey());
			history.setHMAC("");
			for (HsmCtlListVo vo : hsmCtlList) {
				history.setHSM_ID(vo.getHSM_ID());
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

	public DeleteHsmModuleRequest makeHsmCtlListDeleteRequestData(String hsmCtlSvrId) {
		try {
			// HSM 제어서버가 관리하는 HSM Module의 정보 삭제 요청 데이터 생성
			DeleteHsmModuleRequest reqData = new DeleteHsmModuleRequest();
			ApiInfo apiInfo = new ApiInfo();
			apiInfo.setApiId("HSM.ADMIN.05");
			apiInfo.setApiVersion(new Version(1, 0, 0));
			reqData.setApiInfo(apiInfo);
			List<HsmModuleInfo> deleteHsmModuleList = new ArrayList<HsmModuleInfo>();
			List<HsmCtlListVo> listHsmCtl = mapper.selectAllHsmCtlList(hsmCtlSvrId);
			for (HsmCtlListVo hsmCtl : listHsmCtl) {
				HsmModuleInfo hsmModule = new HsmModuleInfo();
				HsmInfoVo hsm = mapper.selectHsmInfo(hsmCtl.getHSM_ID());
				if (hsm == null) {
					throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
				}
				hsmModule.setHsmId(hsmCtl.getHSM_ID());
				hsmModule.setHsmSerialNumber(hsm.getSERIAL());
				hsmModule.setHsmControlOrder(hsmCtl.getCTL_ORDER());
				for (EnumHsmControlMode ctlMode : EnumHsmControlMode.values()) {
					if (ctlMode.getKey().equals(hsmModule.getHsmControlMode())) {
						hsmModule.setHsmControlMode(ctlMode);
					}
				}
				deleteHsmModuleList.add(hsmModule);
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
