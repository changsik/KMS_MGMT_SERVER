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
import com.tmonet.kms.mgmt.operation.model.EncryptRequest;
import com.tmonet.kms.mgmt.operation.model.EncryptResponse;
import com.tmonet.kms.mgmt.operation.model.hsm.HsmEncryptRequest;
import com.tmonet.kms.mgmt.operation.model.hsm.HsmEncryptResponse;
import com.tmonet.kms.mgmt.operation.service.EncryptService;

@RestController
@RequestMapping("/kms")
public class EncryptController {

//	private static final Logger logger = LoggerFactory.getLogger(EncryptController.class);

	@Autowired
	private EncryptService encryptService;

	@PostMapping("/operation/encrypt")
	public @ResponseBody EncryptResponse encrypt(@RequestBody @Validated EncryptRequest request, Errors errors) {
		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 키 정보 획득
		KeyListVo keyList = encryptService.selectKeyList(request.getKeyId());
		List<KeyAttrListVo> keyAttrList = encryptService.selectKeyAttrList(keyList);
		
		// 3. HSM 제어 서버 정보 획득
		HsmServiceInfoVo hsmServiceInfo = encryptService.getHsmServiceInfo(request.getServiceId());
		
		// 4. 키 상태 및 용도 체크
		encryptService.checkKeyStatus(keyList, keyAttrList, hsmServiceInfo);
		
		// 5. HSM 제어 서버 암호화 요청 데이터 생성
		HsmEncryptRequest hsmRequest = encryptService.makeHsmEncryptRequestData(request, hsmServiceInfo.getPARTITION_ID());
		
		// 6. HSM 제어 서버에 암호화 요청
		HsmEncryptResponse hsmResponse = new HsmEncryptResponse(hsmRequest);
		try {
			String hsmServerUrl = "http://" + hsmServiceInfo.getSLB_IP_ADDR() + ":" + hsmServiceInfo.getSLB_PORT() + "/hsm/operation/encrypt";
			RestTemplate rt = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
			hsmResponse = rt.postForObject(hsmServerUrl, hsmRequest, HsmEncryptResponse.class);
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.HSM_SERVER_ERROR);
		}
		
		// 7. 키 사용내역 정보 DB 저장
		encryptService.storeKeyInfo(request.getKeyId(), hsmServiceInfo);
		
		// 8. 결과 데이터 응답
		EncryptResponse response = new EncryptResponse(request);
		response.setResult(hsmResponse.getResult());
		response.setCiphertext(hsmResponse.getCiphertext());
		return response;
	}
}
