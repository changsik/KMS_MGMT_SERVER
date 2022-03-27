package com.tmonet.kms.mgmt.hsmctl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.hsmctl.mapper.HsmCtlMapper;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlSvrInfoVo;

@Service
public class ReportHsmServerService {

	private static final Logger logger = LoggerFactory.getLogger(ReportHsmServerService.class);

	@Autowired
	private HsmCtlMapper mapper;

	public HsmCtlSvrInfoVo selectHsmCtlSvrInfo(String hsmCtlSvrId) {
		try {
			HsmCtlSvrInfoVo ctlSvr = mapper.selectHsmCtlSvrInfo(hsmCtlSvrId);
			if(ctlSvr == null) {
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
