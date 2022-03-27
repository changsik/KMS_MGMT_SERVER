package com.tmonet.kms.mgmt.manager.controller;

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
import com.tmonet.kms.mgmt.common.kmsenum.EnumManagerType;
import com.tmonet.kms.mgmt.common.kmsenum.EnumUsageStatus;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.manager.model.ManagerResponse;
import com.tmonet.kms.mgmt.manager.service.SelectManagerService;
import com.tmonet.kms.mgmt.manager.vo.Manager;
import com.tmonet.kms.mgmt.manager.vo.ManagerInfoVo;

@RestController
@RequestMapping("/kms")
public class SelectManagerController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterManagerController.class);

	@Autowired
	private SelectManagerService service;

	@GetMapping(value = { "/manager/{managerId}", "/manager/" })
	public @ResponseBody ManagerResponse selectManager(@PathVariable(required = false) String managerId,
			@RequestBody @Validated BaseRequest request, Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2.관리자 조회
		List<ManagerInfoVo> listMngInfo = service.selectManagerInfo(managerId);

		// 3.조회 결과 전송
		ManagerResponse response = new ManagerResponse(request);
		List<Manager> listManager = new ArrayList<Manager>();

		for (ManagerInfoVo managerInfo : listMngInfo) {
			Manager manager = new Manager();
			manager.setManagerId(managerInfo.getID());
			manager.setManagerPassword(managerInfo.getPASSWORD());
			manager.setManagerEmail(managerInfo.getEMAIL());
			manager.setDescription(managerInfo.getDESCRIPTION());
			for (EnumManagerType mngType : EnumManagerType.values()) {
				if (mngType.getKey().equals(managerInfo.getTYPE())) {
					Code code = new Code();
					code.setCode(mngType.getKey());
					code.setName(mngType.getValue());
					manager.setManagerType(code);
					break;
				}
			}
			for (EnumUsageStatus status : EnumUsageStatus.values()) {
				if (status.getKey().equals(managerInfo.getSTATUS())) {
					Code code = new Code();
					code.setCode(status.getKey());
					code.setName(status.getValue());
					manager.setUsageStatus(code);
					break;
				}
			}

			manager.setRegDatetime(managerInfo.getREG_DTTM().getTime());
			if (managerInfo.getUPT_DTTM() != null) {
				manager.setLastUpdateDate(managerInfo.getUPT_DTTM().getTime());
			}

			listManager.add(manager);

		}

		response.setListManager(listManager);

		return response;

	}

}
