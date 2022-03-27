package com.tmonet.kms.mgmt.hsmctl.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.hsmctl.mapper.HsmCtlMapper;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlListVo;

@Service
public class SelectHsmCtlListService {

	private static final Logger logger = LoggerFactory.getLogger(SelectHsmCtlListService.class);

	@Autowired
	private HsmCtlMapper mapper;

	public List<HsmCtlListVo> selectHsmCtlList(String hsmCtlSvrId) {
		try {
			List<HsmCtlListVo> listHsmCtl = mapper.selectAllHsmCtlList(hsmCtlSvrId);
			if (listHsmCtl.size() == 0) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}

			return listHsmCtl;

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

}
