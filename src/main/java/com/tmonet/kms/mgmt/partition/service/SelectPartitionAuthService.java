package com.tmonet.kms.mgmt.partition.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmonet.common.object.Code;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumPartitionType;
import com.tmonet.kms.mgmt.partition.mapper.PartitionMapper;
import com.tmonet.kms.mgmt.partition.vo.PartitionAuth;
import com.tmonet.kms.mgmt.partition.vo.PartitionInfoVo;

@Service
public class SelectPartitionAuthService {

	private static final Logger logger = LoggerFactory.getLogger(SelectPartitionAuthService.class);

	@Autowired
	private PartitionMapper mapper;

	public List<PartitionAuth> selectPartitionAuth(String hsmGroupId, String partitionId) {
		try {
			List<PartitionInfoVo> listPatInfo = new ArrayList<PartitionInfoVo>();
			if (hsmGroupId != null && partitionId != null) {
				PartitionInfoVo vo = new PartitionInfoVo();
				vo.setGROUP_ID(hsmGroupId);
				vo.setPARTITION_ID(partitionId);
				PartitionInfoVo partition = mapper.selectPartitionInfo(vo);
				listPatInfo.add(partition);
			} else if (hsmGroupId != null) {
				listPatInfo = mapper.selectAllPatOfGroup(hsmGroupId);
			} else {
				listPatInfo = mapper.selectAllPartitionInfo();
			}

			if (listPatInfo.size() == 0) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}

			List<PartitionAuth> listAuth = new ArrayList<PartitionAuth>();
			for (PartitionInfoVo vo : listPatInfo) {
				PartitionAuth auth = new PartitionAuth();
				Code code = new Code();
				for (EnumPartitionType partitionType : EnumPartitionType.values()) {
					if (partitionType.getKey().equals(vo.getPROTECT_MODE())) {
						code.setCode(partitionType.getKey());
						code.setName(partitionType.getValue());
					}
				}

				auth.setPartitionId(vo.getPARTITION_ID());
				auth.setHsmGroupId(vo.getGROUP_ID());
				auth.setPartitionPassphrase(vo.getPASSWORD());
				auth.setPartitionType(code);
				listAuth.add(auth);
			}

			return listAuth;

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

}
