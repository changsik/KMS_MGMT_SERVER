package com.tmonet.kms.mgmt.hsmctl.controller;

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
import com.tmonet.kms.mgmt.common.kmsenum.EnumHistoryOperationCode;
import com.tmonet.kms.mgmt.common.kmsenum.EnumHsmControlMode;
import com.tmonet.kms.mgmt.hsmctl.model.SelectHsmCtlListRequest;
import com.tmonet.kms.mgmt.hsmctl.model.SelectHsmCtlListResponse;
import com.tmonet.kms.mgmt.hsmctl.service.SelectHsmCtlListService;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlList;
import com.tmonet.kms.mgmt.hsmctl.vo.HsmCtlListVo;

@RestController
@RequestMapping("/kms")
public class SelectHsmCtlListController {

	private static final Logger logger = LoggerFactory.getLogger(SelectHsmCtlListController.class);

	@Autowired
	private SelectHsmCtlListService service;

	@GetMapping("/hsmctl/list/{hsmCtlSvrId}")
	public @ResponseBody SelectHsmCtlListResponse selectHsmCtlList(@PathVariable String hsmCtlSvrId,
			@RequestBody @Validated SelectHsmCtlListRequest request, Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		//2. 관리대상 HSM 목록 조회
		List<HsmCtlListVo> listVo = service.selectHsmCtlList(hsmCtlSvrId);
		
		//3. 조회 결과 전송
		SelectHsmCtlListResponse response = new SelectHsmCtlListResponse(request);
		List<HsmCtlList> listHsmCtl = new ArrayList<HsmCtlList>();
		for(HsmCtlListVo vo : listVo) {
			HsmCtlList hsmCtl = new HsmCtlList();
			hsmCtl.setHsmCtlSvrId(hsmCtlSvrId);
			hsmCtl.setHsmGroupId(vo.getGROUP_ID());
			hsmCtl.setHsmId(vo.getHSM_ID());
			hsmCtl.setHsmCtlOrder(vo.getCTL_ORDER());
			for(EnumHsmControlMode ctlMode : EnumHsmControlMode.values()) {
				if(ctlMode.getKey().equals(vo.getMODE())) {
					Code code = new Code();
					code.setCode(ctlMode.getKey());
					code.setName(ctlMode.getValue());
					hsmCtl.setHsmCtlMode(code);
					break;
				}
			}
			hsmCtl.setRegDatetime(vo.getREG_DTTM().getTime());
			if(vo.getUPT_DTTM() != null) {
				hsmCtl.setLastUpdateDatetime(vo.getUPT_DTTM().getTime());
			}
			
			listHsmCtl.add(hsmCtl);
			
		}
		
		response.setListHsmCtlList(listHsmCtl);
		return response;

	}

}
