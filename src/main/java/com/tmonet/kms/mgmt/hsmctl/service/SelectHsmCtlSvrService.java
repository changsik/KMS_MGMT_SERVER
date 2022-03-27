package com.tmonet.kms.mgmt.hsmctl.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.hsmctl.mapper.HsmCtlMapper;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlSvrInfoVo;

@Service
public class SelectHsmCtlSvrService {

	private static final Logger logger = LoggerFactory.getLogger(SelectHsmCtlSvrService.class);
	
	@Autowired
	private HsmCtlMapper mapper;

	public List<HsmCtlSvrInfoVo> selectHsmCtlSvr(String hsmCtlSvrId) {
		try {
			List<HsmCtlSvrInfoVo> listVo = new ArrayList<HsmCtlSvrInfoVo>();
			
			if(hsmCtlSvrId == null) {
				listVo = mapper.selectAllHsmCtlSvrInfo();
				if(listVo.size() == 0) {
					throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
				}
			}else {
				HsmCtlSvrInfoVo vo = mapper.selectHsmCtlSvrInfo(hsmCtlSvrId);
				if(vo == null) {
					throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
				}
				listVo.add(vo);
			}
			
			return listVo;
			
		}catch(KMSException e) {
			throw new KMSException(e.getCode());
		}catch(Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}
	
	
	
}
