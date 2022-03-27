package com.tmonet.kms.mgmt.hsm.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.hsm.mapper.HsmMapper;
import com.tmonet.kms.mgmt.hsm.vo.HsmInfoVo;

@Service
public class SelectHsmService {

	private static final Logger logger = LoggerFactory.getLogger(SelectHsmService.class);

	@Autowired
	private HsmMapper mapper;

	public List<HsmInfoVo> selectHsmInfo(String hsmGroupId, String hsmId) {
		try {
			List<HsmInfoVo> listHsm = new ArrayList<HsmInfoVo>();
			if (hsmId == null && hsmGroupId == null) {
				listHsm = mapper.selectAllHsmInfo();
			} else if (hsmId == null) {
				listHsm = mapper.selectAllHsmOfGroup(hsmGroupId);
			} else {
				HsmInfoVo hsm = new HsmInfoVo();
				hsm.setGROUP_ID(hsmGroupId);
				hsm.setHSM_ID(hsmId);
				hsm = mapper.selectHsmInfo(hsm);
				if (hsm != null) {
					listHsm.add(hsm);
				}
			}

			if (listHsm.size() == 0) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}

			return listHsm;

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}
}
