package com.tmonet.kms.mgmt.partition.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tmonet.common.object.Code;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumPartitionType;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.partition.model.PartitionResponse;
import com.tmonet.kms.mgmt.partition.service.SelectPartitionService;
import com.tmonet.kms.mgmt.partition.vo.Partition;
import com.tmonet.kms.mgmt.partition.vo.PartitionInfoVo;

@RestController
@RequestMapping("/kms")
public class SelectPartitionController {

	private static final Logger logger = LoggerFactory.getLogger(SelectPartitionController.class);

	@Autowired
	private SelectPartitionService service;

	@GetMapping(value = { "/partition/info/{hsmGroupId}/{partitionId}", "/partition/info/{hsmGroupId}/", "/partition/info//" })
	public @ResponseBody PartitionResponse selectPartition(@PathVariable(required = false) String hsmGroupId,
			@PathVariable(required = false) String partitionId, @RequestBody @Validated BaseRequest request,
			Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 파티션 정보 조회
		List<PartitionInfoVo> listPartitionInfo = service.selectPartitionInfo(hsmGroupId, partitionId);

		// 3. 조회 결과 전송
		PartitionResponse response = new PartitionResponse(request);
		List<Partition> listPartition = new ArrayList<Partition>();
		for (PartitionInfoVo vo : listPartitionInfo) {
			Partition partition = new Partition();
			Code code = new Code();
			for (EnumPartitionType pt : EnumPartitionType.values()) {
				if (pt.getKey().equals(vo.getPROTECT_MODE())) {
					code.setCode(pt.getKey());
					code.setName(pt.getValue());
					break;
				}
			}

			partition.setPartitionId(vo.getPARTITION_ID());
			partition.setHsmGroupId(vo.getGROUP_ID());
			partition.setServiceId(vo.getSERVICE_ID());
			partition.setPartitionType(code);
			partition.setPartitionIdent(vo.getPROTECT_MODE());
			partition.setDescription(vo.getDESCRIPTION());
			partition.setRegDatetime(vo.getREG_DTTM().getTime());
			if (vo.getUPT_DTTM() != null) {
				partition.setLastUpdateDatetime(vo.getUPT_DTTM().getTime());
			}

			listPartition.add(partition);
		}

		response.setListPartition(listPartition);
		return response;

	}

}
