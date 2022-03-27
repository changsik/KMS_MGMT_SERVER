package com.tmonet.kms.mgmt.manager.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.manager.mapper.ManagerMapper;
import com.tmonet.kms.mgmt.manager.vo.ManagerInfoVo;

@Service
public class SelectManagerService {

	private static final Logger logger = LoggerFactory.getLogger(SelectManagerService.class);

	@Autowired
	private ManagerMapper mapper;

	public List<ManagerInfoVo> selectManagerInfo(String managerId) {
		try {
			List<ManagerInfoVo> listMngInfo = new ArrayList<ManagerInfoVo>();
			if (managerId == null) {
				listMngInfo = mapper.selectAllManagerInfo();
			} else {
				ManagerInfoVo manager = mapper.selectManagerInfo(managerId);
				listMngInfo.add(manager);
			}

			if (listMngInfo.size() == 0) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}

			return listMngInfo;

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

}
