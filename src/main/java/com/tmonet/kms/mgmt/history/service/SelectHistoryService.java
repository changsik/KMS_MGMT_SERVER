package com.tmonet.kms.mgmt.history.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumResourceCode;
import com.tmonet.kms.mgmt.history.mapper.HistoryMapper;
import com.tmonet.kms.mgmt.history.model.History;
import com.tmonet.kms.mgmt.history.model.SelectHistoryRequest;
import com.tmonet.kms.mgmt.history.vo.HistoryVo;

@Service
public class SelectHistoryService {

//	private static final Logger logger = LoggerFactory.getLogger(SelectHistoryService.class);

	@Autowired
	private HistoryMapper mapper;

	public List<HistoryVo> selectHistory(SelectHistoryRequest request) {

		String resource = request.getResource();
		List<HistoryVo> historyListVo = new ArrayList<HistoryVo>();
		if (resource.equals(EnumResourceCode.RESOURCE_CODE_HSM.getKey())) {
			historyListVo = mapper.selectHsmHistory(request.getId());
		} else if (resource.equals(EnumResourceCode.RESOURCE_CODE_HSM_CONTROL_SERVER.getKey())) {
			historyListVo = mapper.selectHsmCtrlSvrHistory(request.getId());
		} else if (resource.equals(EnumResourceCode.RESOURCE_CODE_KEY.getKey())) {
			historyListVo = mapper.selectKeyHistory(request.getId());
		} else if (resource.equals(EnumResourceCode.RESOURCE_CODE_KEY_PROFILE.getKey())) {
			historyListVo = mapper.selectKeyProfileHistory(request.getId());
		} else if (resource.equals(EnumResourceCode.RESOURCE_CODE_CLIENT.getKey())) {
			historyListVo = mapper.selectClientHistory(request.getId());
		} else if (resource.equals(EnumResourceCode.RESOURCE_CODE_MANAGER.getKey())) {
			historyListVo = mapper.selectManagerHistory(request.getId());
		} else if (resource.equals(EnumResourceCode.RESOURCE_CODE_CLIENT_AUTH.getKey())) {
			historyListVo = mapper.selectClientAuthHistory(request.getId());
		} else if (resource.equals(EnumResourceCode.RESOURCE_CODE_MANAGER_AUTH.getKey())) {
			historyListVo = mapper.selectManagerAuthHistory(request.getId());
		} else {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}
		return historyListVo;
	}

	public List<History> makeSelectHistoryResponseData(List<HistoryVo> historyListVo) {
		List<History> historyList = new ArrayList<History>();
		for (HistoryVo historyVo : historyListVo) {
			History history = new History();
			history.setHistNo(historyVo.getHIST_NO());
			history.setHsmId(historyVo.getHSM_ID());
			history.setCtlsvrId(historyVo.getCTLSVR_ID());
			history.setKeyId(historyVo.getKEY_ID());
			history.setProfileId(historyVo.getPROFILE_ID());
			history.setId(historyVo.getID());
			history.setIpAddr(historyVo.getIP_ADDR());
			history.setPartitionId(historyVo.getPARTITION_ID());
			history.setGroupId(historyVo.getGROUP_ID());
			history.setAttrId(historyVo.getATTR_ID());
			history.setType(historyVo.getTYPE());
			history.setParam(historyVo.getPARAM());
			history.setHmac(historyVo.getHMAC());
			history.setRegDttm(historyVo.getREG_DTTM());
			history.setRegUser(historyVo.getREG_USER());
			historyList.add(history);
		}
		return historyList;
	}
}
