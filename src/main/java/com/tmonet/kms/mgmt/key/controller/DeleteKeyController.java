package com.tmonet.kms.mgmt.key.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
import com.tmonet.kms.mgmt.common.vo.KeyListVo;
import com.tmonet.kms.mgmt.key.model.DeleteKeyRequest;
import com.tmonet.kms.mgmt.key.model.DeleteKeyResponse;
import com.tmonet.kms.mgmt.key.model.hsm.HsmDeleteKeyRequest;
import com.tmonet.kms.mgmt.key.model.hsm.HsmDeleteKeyResponse;
import com.tmonet.kms.mgmt.key.service.DeleteKeyService;
import com.tmonet.kms.mgmt.key.vo.KeyProfileAttrListVo;

@RestController
@RequestMapping("/kms")
public class DeleteKeyController {

//	private static final Logger logger = LoggerFactory.getLogger(DeleteKeyController.class);

	@Autowired
	private DeleteKeyService deleteKeyService;

	@PostMapping("/key/delete")
	public @ResponseBody DeleteKeyResponse deleteKey(@RequestBody @Validated DeleteKeyRequest request, Errors errors) {
		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}
		
		// 2. 키 정보 획득 및 상태, 용도 체크
		KeyListVo keyList = deleteKeyService.selectKeyList(request.getKeyId());
		deleteKeyService.checkKeyStatus(keyList);
		
		// 3. 키 프로파일 유효성 체크
		deleteKeyService.checkKeyProfileStatus(keyList.getPROFILE_ID());
		
		// 4. 개인키 프로파일 속성 리스트 정보 획득
		List<KeyProfileAttrListVo> keyProfileAttrList = deleteKeyService.selectKeyProfileAttrList(keyList.getPROFILE_ID());
		
		// 5. HSM 제어 서버 정보 획득
		HsmServiceInfoVo hsmServiceInfo = deleteKeyService.getHsmServiceInfo(request.getServiceId());
		
		// 6. HSM 제어 서버 대칭키 생성 요청 데이터 생성
		HsmDeleteKeyRequest hsmRequest = deleteKeyService.makeHsmDeleteKeyRequestData(hsmServiceInfo.getPARTITION_ID(), keyProfileAttrList);
		
		// 7. HSM 제어 서버에 대칭 키 생성 요청
		HsmDeleteKeyResponse hsmResponse = new HsmDeleteKeyResponse(hsmRequest);
		try {
			String hsmServerUrl = "http://" + hsmServiceInfo.getSLB_IP_ADDR() + ":" + hsmServiceInfo.getSLB_PORT() + "/hsm/key/" + request.getKeyId();
			RestTemplate rt = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
			HttpEntity<HsmDeleteKeyRequest> entity = new HttpEntity<HsmDeleteKeyRequest>(hsmRequest);
			ResponseEntity<HsmDeleteKeyResponse> res = rt.exchange(hsmServerUrl, HttpMethod.DELETE, entity, HsmDeleteKeyResponse.class);
			hsmResponse = res.getBody();
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.HSM_SERVER_ERROR);
		}

		// 8. 이력정보 DB 저장
		deleteKeyService.deleteKeyInfo(request.getKeyId(), hsmServiceInfo);

		// 9. 결과 데이터 응답
		DeleteKeyResponse response = new DeleteKeyResponse(request);
		response.setResult(hsmResponse.getResult());
		return response;
	}
}
