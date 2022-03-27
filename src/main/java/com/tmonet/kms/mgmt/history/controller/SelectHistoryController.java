package com.tmonet.kms.mgmt.history.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.history.model.History;
import com.tmonet.kms.mgmt.history.model.SelectHistoryRequest;
import com.tmonet.kms.mgmt.history.model.SelectHistoryResponse;
import com.tmonet.kms.mgmt.history.service.SelectHistoryService;
import com.tmonet.kms.mgmt.history.vo.HistoryVo;

@RestController
@RequestMapping("/kms")
public class SelectHistoryController {

//	private static final Logger logger = LoggerFactory.getLogger(SelectHistoryController.class);

	@Autowired
	private SelectHistoryService service;

	@PostMapping("/history/select")
	public @ResponseBody SelectHistoryResponse selectHistory(@RequestBody @Validated SelectHistoryRequest request,
			Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 리소스 이력정보 조회 
		List<HistoryVo> historyListVo = service.selectHistory(request);
		
		// 3. 이력정보 응답 데이터 생성 
		List<History> historyList = service.makeSelectHistoryResponseData(historyListVo);

		// 4. 결과 전송
		SelectHistoryResponse response = new SelectHistoryResponse(request);
		response.setListHistory(historyList);
		return response;
	}
}