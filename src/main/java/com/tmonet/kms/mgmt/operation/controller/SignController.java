package com.tmonet.kms.mgmt.operation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.vo.HsmServiceInfoVo;
import com.tmonet.kms.mgmt.common.vo.KeyAttrListVo;
import com.tmonet.kms.mgmt.common.vo.KeyListVo;
import com.tmonet.kms.mgmt.operation.model.SignRequest;
import com.tmonet.kms.mgmt.operation.model.SignResponse;
import com.tmonet.kms.mgmt.operation.model.hsm.HsmSignRequest;
import com.tmonet.kms.mgmt.operation.model.hsm.HsmSignResponse;
import com.tmonet.kms.mgmt.operation.service.SignService;

@RestController
@RequestMapping("/kms")
public class SignController {

//	private static final Logger logger = LoggerFactory.getLogger(SignController.class);

	@Autowired
	private SignService signService;

	@PostMapping("/operation/sign")
	public @ResponseBody SignResponse sign(@RequestBody @Validated SignRequest request, Errors errors) {
		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 키 정보 획득
		KeyListVo keyList = signService.selectKeyList(request.getKeyId());
		List<KeyAttrListVo> keyAttrList = signService.selectKeyAttrList(keyList);
		
		// 3. HSM 제어 서버 정보 획득
		HsmServiceInfoVo hsmServiceInfo = signService.getHsmServiceInfo(request.getServiceId());
		
		// 4. 키 상태 및 용도 체크
		signService.checkKeyStatus(keyList, keyAttrList, hsmServiceInfo);
		
		// 5. HSM 제어 서버 전자서명 생성 요청 데이터 생성
		HsmSignRequest hsmRequest = signService.makeHsmSignRequestData(request, hsmServiceInfo.getPARTITION_ID());
		
		// 6. HSM 제어 서버에 전자서명 생성 요청
		HsmSignResponse hsmResponse = new HsmSignResponse(hsmRequest);
		try {
			String hsmServerUrl = "http://" + hsmServiceInfo.getSLB_IP_ADDR() + ":" + hsmServiceInfo.getSLB_PORT() + "/hsm/operation/sign";
			RestTemplate rt = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
			hsmResponse = rt.postForObject(hsmServerUrl, hsmRequest, HsmSignResponse.class);
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.HSM_SERVER_ERROR);
		}
		
		// 7. 키 사용내역 정보 DB 저장
		signService.storeKeyInfo(request.getKeyId(), hsmServiceInfo);
		
		// 8. 결과 데이터 응답
		SignResponse response = new SignResponse(request);
		response.setResult(hsmResponse.getResult());
		response.setSignature(hsmResponse.getSignature());
		return response;
	}
}
