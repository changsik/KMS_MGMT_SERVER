package com.tmonet.kms.mgmt.hsm.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHistoryOperationCode;
import com.tmonet.kms.mgmt.common.vo.HsmHistoryVo;
import com.tmonet.kms.mgmt.hsm.mapper.HsmMapper;
import com.tmonet.kms.mgmt.hsm.vo.HsmInfoVo;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlListVo;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlSvrHistoryVo;

@Service
public class DeleteHsmService {

	private static final Logger logger = LoggerFactory.getLogger(DeleteHsmService.class);

	@Autowired
	private HsmMapper mapper;

	public void checkParams(String hsmId) {
		try {
			HsmInfoVo hsm = new HsmInfoVo();
			hsm.setHSM_ID(hsmId);
			if (mapper.selectHsmInfo(hsm) == null) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}
		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

	@Transactional(rollbackFor = { KMSException.class })
	public void deleteHsmInfo(String hsmId) {
		try {
			HsmInfoVo hsmVo = new HsmInfoVo();
			hsmVo.setHSM_ID(hsmId);
			HsmInfoVo hsmBeforeUpdate = mapper.selectHsmInfo(hsmVo);
			if (hsmBeforeUpdate == null) {
				throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
			}
			
			// 관리대상 HSM 목록 존재 시 삭제 불가
			List<HsmCtlListVo> ctlList = mapper.selectAllHsmCtlList(hsmId);
			if(ctlList.size() != 0) {
				throw new KMSException("HSM cannot be deleted because HsmCtlList exists", KMSErrorCode.DATABASE_DELETE_FAILURE);
			}
	
			// HSM 삭제
			if (mapper.deleteHsmInfo(hsmId) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_DELETE_FAILURE);
			}
	
			// HSM 삭제 이력 등록
			HsmHistoryVo hsmHistory = new HsmHistoryVo();
			hsmHistory.setGROUP_ID(hsmBeforeUpdate.getGROUP_ID());
			hsmHistory.setHSM_ID(hsmId);
			hsmHistory.setTYPE(EnumHistoryOperationCode.HIST_OP_CODE_DELETE_HSM.getKey());
			hsmHistory.setHMAC("");
			hsmHistory.setREG_USER("ADMIN");
			if (mapper.insertHsmHistory(hsmHistory) < 1) {
				throw new KMSException(KMSErrorCode.DATABASE_INSERT_FAILURE);
			}
		} catch (KMSException e) {
			throw new KMSException(e.getMessage(), e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

}
