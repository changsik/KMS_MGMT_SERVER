package com.tmonet.kms.mgmt.hsmgroup.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.hsmgroup.model.HsmGroup;
import com.tmonet.kms.mgmt.hsmgroup.model.HsmGroupResponse;
import com.tmonet.kms.mgmt.hsmgroup.service.SelectHsmGroupService;
import com.tmonet.kms.mgmt.hsmgroup.vo.HsmGroupInfoVo;

@RestController
@RequestMapping("/kms")
public class SelectHsmGroupController {

//	private static final Logger logger = LoggerFactory.getLogger(SelectHsmGroupController.class);

	@Autowired
	private SelectHsmGroupService service;
	
	@GetMapping(value = {"/hsm/group/{groupId}", "/hsm/group/"})
	public @ResponseBody HsmGroupResponse selectHsmGroup(@PathVariable(required = false) String groupId,
			@RequestBody @Validated BaseRequest request, Errors errors) {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}
		
		// 2. HSM 그룹 정보 조회
		List<HsmGroupInfoVo> listHsmGroupInfo = service.selectHsmGroupInfo(groupId);

		// 3. 결과 전송
		HsmGroupResponse response = new HsmGroupResponse(request);
		List<HsmGroup> listHsmGroup = new ArrayList<HsmGroup>();
		for (HsmGroupInfoVo hsmGroupInfo : listHsmGroupInfo) {
			HsmGroup hsmGroup = new HsmGroup();
			hsmGroup.setGroupId(hsmGroupInfo.getGROUP_ID());
			hsmGroup.setvIpAddr(hsmGroupInfo.getSLB_IP_ADDR());
			hsmGroup.setvPort(hsmGroupInfo.getSLB_PORT());
			hsmGroup.setDescription(hsmGroupInfo.getDESCRIPTION());
			hsmGroup.setRegDttm(hsmGroupInfo.getREG_DTTM());
			hsmGroup.setRegUser(hsmGroupInfo.getREG_USER());
			hsmGroup.setUptDttm(hsmGroupInfo.getUPT_DTTM());
			hsmGroup.setUptUser(hsmGroupInfo.getUPT_USER());
			listHsmGroup.add(hsmGroup);
		}
		response.setListHsmGroup(listHsmGroup);
		return response;
	}

}