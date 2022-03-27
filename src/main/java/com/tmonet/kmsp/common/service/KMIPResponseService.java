package com.tmonet.kmsp.common.service;

import org.springframework.stereotype.Service;

import com.tmonet.kms.mgmt.common.object.ApiResult;
import com.tmonet.kmsp.common.Constants;
import com.tmonet.kmsp.common.vo.BatchItemResponseVo;
import com.tmonet.kmsp.common.vo.KMIPResponseVo;
import com.tmonet.kmsp.common.vo.ProtocolVersionVo;
import com.tmonet.kmsp.common.vo.ResponseHeaderVo;
import com.tmonet.kmsp.common.vo.TypeValueVo;

@Service
public class KMIPResponseService {
	
	public KMIPResponseVo getResponseMessage(String operation, Object responsePayload, ApiResult apiResult) {
		
		String respTimestamp = String.valueOf(System.currentTimeMillis());
		
		if(apiResult == null) {
			apiResult = new ApiResult();
			apiResult.setErrorCode("00000000");
			apiResult.setResultStatus("00000000");
			apiResult.setErrorMessage("Success");
		}
		
		// header
		ResponseHeaderVo header = new ResponseHeaderVo();
		ProtocolVersionVo version = new ProtocolVersionVo();
		TypeValueVo timeStamp = new TypeValueVo(Constants.KMIP_TYPE_DATE, respTimestamp); 
		TypeValueVo batchCount = new TypeValueVo(Constants.KMIP_TYPE_INT, "1");
		header.setProtocolVersion(version);
		header.setTimeStamp(timeStamp);
		header.setBatchCount(batchCount);
		
		TypeValueVo majorVer = new TypeValueVo(Constants.KMIP_TYPE_INT, "2");
		TypeValueVo minorVer = new TypeValueVo(Constants.KMIP_TYPE_INT, "0");
		version.setProtocolVersionMajor(majorVer);
		version.setProtocolVersionMinor(minorVer);
		
		// BatchItem
		TypeValueVo operationVo = new TypeValueVo(Constants.KMIP_TYPE_ENUM, operation);
		TypeValueVo resultStatusVo = new TypeValueVo(Constants.KMIP_TYPE_ENUM, apiResult.getErrorMessage());
		
		BatchItemResponseVo batchItem = new BatchItemResponseVo();
		batchItem.setOperation(operationVo);
		batchItem.setResultStatus(resultStatusVo);
		batchItem.setResponsePayload(responsePayload);
		
		// 결과저장
		KMIPResponseVo response = new KMIPResponseVo();
		response.setResult(apiResult);
		response.setResponseHeader(header);
		response.setBatchItem(batchItem);
		
		return response;
	}
}
