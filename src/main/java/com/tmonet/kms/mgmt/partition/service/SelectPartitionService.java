package com.tmonet.kms.mgmt.partition.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.partition.mapper.PartitionMapper;
import com.tmonet.kms.mgmt.partition.vo.Partition;
import com.tmonet.kms.mgmt.partition.vo.PartitionInfoVo;

@Service
public class SelectPartitionService {

	private static final Logger logger = LoggerFactory.getLogger(SelectPartitionService.class);

	@Autowired
	private PartitionMapper mapper;

	public List<PartitionInfoVo> selectPartitionInfo(String hsmGroupId, String partitionId) {
		try {
			List<PartitionInfoVo> listPartition = new ArrayList<PartitionInfoVo>();
			if (hsmGroupId != null && partitionId != null) {
				PartitionInfoVo vo = new PartitionInfoVo();
				vo.setGROUP_ID(hsmGroupId);
				vo.setPARTITION_ID(partitionId);
				PartitionInfoVo partition = mapper.selectPartitionInfo(vo);
				if (partition != null) {
					listPartition.add(partition);
				}
			} else if (hsmGroupId != null) {
				listPartition = mapper.selectAllPatOfGroup(hsmGroupId);
			} else {
				listPartition = mapper.selectAllPartitionInfo();
			}

			if (listPartition.size() == 0) {
				throw new KMSException(KMSErrorCode.DATABASE_SELECT_FAILURE);
			}

			return listPartition;

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
	}

}
