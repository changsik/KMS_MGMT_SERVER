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
import com.tmonet.kms.mgmt.operation.model.DecryptRequest;
import com.tmonet.kms.mgmt.operation.model.DecryptResponse;
import com.tmonet.kms.mgmt.operation.model.hsm.HsmDecryptRequest;
import com.tmonet.kms.mgmt.operation.model.hsm.HsmDecryptResponse;
import com.tmonet.kms.mgmt.operation.service.DecryptService;

@RestController
@RequestMapping("/kms")
public class DecryptController {

//	private static final Logger logger = LoggerFactory.getLogger(DecryptController.class);

	@Autowired
	private DecryptService decryptService;

	@PostMapping("/operation/decrypt")
	public @ResponseBody DecryptResponse decrypt(@RequestBody @Validated DecryptRequest request, Errors errors) {
		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 키 정보 획득
		KeyListVo keyList = decryptService.selectKeyList(request.getKeyId());
		List<KeyAttrListVo> keyAttrList = decryptService.selectKeyAttrList(keyList);
		
		// 3. HSM 제어 서버 정보 획득
		HsmServiceInfoVo hsmServiceInfo = decryptService.getHsmServiceInfo(request.getServiceId());
		
		// 4. 키 상태 및 용도 체크
		decryptService.checkKeyStatus(keyList, keyAttrList, hsmServiceInfo);
		
		// 5. HSM 제어 서버 복호화 요청 데이터 생성
		HsmDecryptRequest hsmRequest = decryptService.makeHsmDecryptRequestData(request, hsmServiceInfo.getPARTITION_ID());
		
		// 6. HSM 제어 서버에 복호화 요청
		HsmDecryptResponse hsmResponse = new HsmDecryptResponse(hsmRequest);
		try {
			String hsmServerUrl = "http://" + hsmServiceInfo.getSLB_IP_ADDR() + ":" + hsmServiceInfo.getSLB_PORT() + "/hsm/operation/decrypt";
			RestTemplate rt = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
			hsmResponse = rt.postForObject(hsmServerUrl, hsmRequest, HsmDecryptResponse.class);
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.HSM_SERVER_ERROR);
		}
		
		// 7. 키 사용내역 정보 DB 저장
		decryptService.storeKeyInfo(request.getKeyId(), hsmServiceInfo);
		
		// 8. 결과 데이터 응답
		DecryptResponse response = new DecryptResponse(request);
		response.setResult(hsmResponse.getResult());
		response.setPlaintext(hsmResponse.getPlaintext());
		return response;
	}
}
