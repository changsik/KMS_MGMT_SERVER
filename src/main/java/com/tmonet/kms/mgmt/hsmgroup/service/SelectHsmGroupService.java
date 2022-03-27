package com.tmonet.kms.mgmt.hsmgroup.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.hsmgroup.mapper.HsmGroupMapper;
import com.tmonet.kms.mgmt.hsmgroup.vo.HsmGroupInfoVo;

@Service
public class SelectHsmGroupService {

//	private static final Logger logger = LoggerFactory.getLogger(SelectHsmGroupService.class);

	@Autowired
	private HsmGroupMapper mapper;

	public List<HsmGroupInfoVo> selectHsmGroupInfo(String groupId) {
		try {
			List<HsmGroupInfoVo> listHsmGroupInfo = new ArrayList<HsmGroupInfoVo>();
			if (groupId == null) {
				listHsmGroupInfo = mapper.selectAllHsmGroupInfo();
			} else {
				HsmGroupInfoVo hsmGroupInfo = mapper.selectHsmGroupInfo(groupId);
				if (hsmGroupInfo == null) {
					throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
				}
				listHsmGroupInfo.add(hsmGroupInfo);
			}
			return listHsmGroupInfo;
		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}
}
