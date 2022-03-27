package com.tmonet.kms.mgmt.hsmctl.service;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.tmonet.kms.mgmt.hsmctl.model.UpdateHsmCtlListRequest;
import com.tmonet.kms.mgmt.hsmctl.model.UpdateHsmModuleRequest;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlListVo;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlSvrHistoryVo;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlSvrInfoVo;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmModule;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmModuleInfo;

@Service
public class UpdateHsmCtlListService {

	private static final Logger logger = LoggerFactory.getLogger(UpdateHsmCtlListService.class);

	@Autowired
	private HsmCtlMapper mapper;

	public void checkParams(UpdateHsmCtlListRequest request) {
		try {
			for (HsmModule hsmModule : request.getUpdateHsmModuleList()) {
				HsmCtlListVo hsmCtl = new HsmCtlListVo();
				hsmCtl.setCTLSVR_ID(request.getHsmCtlSvrId());
				hsmCtl.setHSM_ID(hsmModule.getHsmId());
				if (mapper.selectHsmCtlList(hsmCtl) == null) {
					throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
				}

				if (hsmModule.getHsmControlMode() != null) {
					boolean ctlModeIs = false;
					for (EnumHsmControlMode ctlMode : EnumHsmControlMode.values()) {
						if (ctlMode.getKey().equals(hsmModule.getHsmControlMode())) {
							ctlModeIs = true;
							break;
						}
					}
					if (!ctlModeIs) {
						throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
					}
				}
			}

			// 수정 후의 order가 서로 겹치지 않는지 검사
			// <String, Integer> = <hsmId, order>
			HashMap<String, Integer> listHsmModule = new HashMap<String, Integer>();
			List<HsmCtlListVo> listHsmCtl = mapper.selectAllHsmCtlList(request.getHsmCtlSvrId());
			for (HsmCtlListVo hsmCtl : listHsmCtl) {
				listHsmModule.put(hsmCtl.getHSM_ID(), hsmCtl.getCTL_ORDER());
			}
			for (HsmModule hsmModule : request.getUpdateHsmModuleList()) {
				listHsmModule.replace(hsmModule.getHsmId(), hsmModule.getHsmControlOrder());
			}

			// key, listHsmModule.get(key) = 키, 키값
			for (String key : listHsmModule.keySet()) {
				int orderData = listHsmModule.get(key);
				for (String checkData : listHsmModule.keySet()) {
					if (!key.equals(checkData)) {
						if (orderData == listHsmModule.get(checkData)) {
							throw new KMSException(KMSErrorCode.UNAVAILABLE_STATUS);
						}
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
	public void updateHsmCtlList(UpdateHsmCtlListRequest request) {
		try {
			// 관리대상 HSM 목록 수정
			String hsmGroupId = selectHsmGroupId(request.getUpdateHsmModuleList());
			for (HsmModule hsmModule : request.getUpdateHsmModuleList()) {
				HsmCtlListVo hsmCtl = new HsmCtlListVo();
				hsmCtl.setCTLSVR_ID(request.getHsmCtlSvrId());
				hsmCtl.setHSM_ID(hsmModule.getHsmId());
				hsmCtl.setCTL_ORDER(hsmModule.getHsmControlOrder());
				hsmCtl.setMODE(hsmModule.getHsmControlMode());
				hsmCtl.setUPT_USER("ADMIN");

				HsmCtlListVo hsmCtlBeforeUpdate = mapper.selectHsmCtlList(hsmCtl);
				if (hsmCtlBeforeUpdate == null) {
					throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
				}

				if (mapper.updateHsmCtlList(hsmCtl) < 1) {
					throw new KMSException(KMSErrorCode.DATABASE_UPDATE_FAILURE);
				}

				// 수정 이력 등록
				String updatedData = "";
				boolean dataIsChanged = false;
				if (hsmModule.getHsmControlOrder() != 0) {
					if (hsmModule.getHsmControlOrder() != hsmCtlBeforeUpdate.getCTL_ORDER()) {
						updatedData += "CTL_ORDER:" + hsmModule.getHsmControlOrder() + " ";
						dataIsChanged = true;
					}
				}
				if (hsmModule.getHsmControlMode() != null) {
					if (!hsmModule.getHsmControlMode().equals(hsmCtlBeforeUpdate.getMODE())) {
						updatedData += "MODE:" + hsmModule.getHsmControlMode() + " ";
						dataIsChanged = true;
					}
				}

				if (!dataIsChanged) {
					throw new KMSException(KMSErrorCode.DATABASE_UPDATE_FAILURE);
				}

				HsmCtlSvrHistoryVo history = new HsmCtlSvrHistoryVo();
				history.setCTLSVR_ID(request.getHsmCtlSvrId());
				history.setGROUP_ID(hsmGroupId);
				history.setHSM_ID(hsmModule.getHsmId());
				history.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_UPDATE_HSM_CTL_LIST.getKey());
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

	public UpdateHsmModuleRequest makeHsmCtlListUpdateRequestData(UpdateHsmCtlListRequest request) {
		try {
			UpdateHsmModuleRequest reqData = new UpdateHsmModuleRequest();
			ApiInfo apiInfo = new ApiInfo();
			apiInfo.setApiId("HSM.ADMIN.04");
			apiInfo.setApiVersion(new Version(1, 0, 0));
			reqData.setApiInfo(apiInfo);
			List<HsmModuleInfo> updateHsmModuleList = new ArrayList<HsmModuleInfo>();
			for(HsmModule hsmModule : request.getUpdateHsmModuleList()) {
				HsmModuleInfo newHsmModule = new HsmModuleInfo();
				newHsmModule.setHsmId(hsmModule.getHsmId());
				newHsmModule.setHsmSerialNumber(hsmModule.getHsmSerialNumber());
				newHsmModule.setHsmControlOrder(hsmModule.getHsmControlOrder());
				for(EnumHsmControlMode ctlMode : EnumHsmControlMode.values()) {
					if(ctlMode.getKey().equals(hsmModule.getHsmControlMode())) {
						newHsmModule.setHsmControlMode(ctlMode);				
					}
				}
				updateHsmModuleList.add(newHsmModule);
			}
			reqData.setUpdateHsmModuleList(updateHsmModuleList);
			
			return reqData;

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

}
