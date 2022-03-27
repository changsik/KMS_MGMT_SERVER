package com.tmonet.kms.mgmt.key.controller;

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
import com.tmonet.kms.mgmt.common.kmsenum.EnumKeyAttributeId;
import com.tmonet.kms.mgmt.common.vo.HsmServiceInfoVo;
import com.tmonet.kms.mgmt.common.vo.KeyAttrListVo;
import com.tmonet.kms.mgmt.key.model.CreateAsymmetricKeyRequest;
import com.tmonet.kms.mgmt.key.model.CreateAsymmetricKeyResponse;
import com.tmonet.kms.mgmt.key.model.hsm.HsmCreateAsymmetricKeyRequest;
import com.tmonet.kms.mgmt.key.model.hsm.HsmCreateAsymmetricKeyResponse;
import com.tmonet.kms.mgmt.key.service.CreateAsymmetricKeyService;
import com.tmonet.kms.mgmt.key.vo.KeyProfileAttrListVo;

@RestController
@RequestMapping("/kms")
public class CreateAsymmetricKeyController {

//	private static final Logger logger = LoggerFactory.getLogger(CreateAsymmetricKeyController.class);

	@Autowired
	private CreateAsymmetricKeyService createAsymmetricKeyService;

	@PostMapping("/asymmetrickey/create")
	public @ResponseBody CreateAsymmetricKeyResponse createAsymmetricKey(
			@RequestBody @Validated CreateAsymmetricKeyRequest request, Errors errors) {
		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 키 프로파일 유효성 체크
		createAsymmetricKeyService.checkKeyProfileStatus(request.getPrivateKeyProfileId());
		createAsymmetricKeyService.checkKeyProfileStatus(request.getPublicKeyProfileId());

		// 3. HSM 제어 서버 정보 획득
		HsmServiceInfoVo hsmServiceInfo = createAsymmetricKeyService.getHsmServiceInfo(request.getServiceId());

		// 4. 개인키 프로파일 속성 리스트 정보 획득
		List<KeyProfileAttrListVo> priKeyProfileAttrList = createAsymmetricKeyService.selectKeyProfileAttrList(request.getPrivateKeyProfileId());
		List<KeyProfileAttrListVo> pubKeyProfileAttrList = createAsymmetricKeyService.selectKeyProfileAttrList(request.getPublicKeyProfileId());

		// 5. HSM 제어 서버 대칭키 생성 요청 데이터 생성
		HsmCreateAsymmetricKeyRequest hsmRequest = createAsymmetricKeyService.makeHsmCreateAsymmetricKeyRequestData(
				hsmServiceInfo.getPARTITION_ID(), priKeyProfileAttrList, pubKeyProfileAttrList);

		// 6. HSM 제어 서버에 대칭 키 생성 요청
		HsmCreateAsymmetricKeyResponse hsmResponse = new HsmCreateAsymmetricKeyResponse(hsmRequest);
		try {
			String hsmServerUrl = "http://" + hsmServiceInfo.getSLB_IP_ADDR() + ":" + hsmServiceInfo.getSLB_PORT()
					+ "/hsm/asymmetrickey/create";
			RestTemplate rt = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
			hsmResponse = rt.postForObject(hsmServerUrl, hsmRequest, HsmCreateAsymmetricKeyResponse.class);
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.HSM_SERVER_ERROR);
		}
		
		// 7. 키 프로파일 속성값을 키 속성값으로 설정 후 추가 속성값 추가
		// 		- 비대칭키 매칭되는 키 쌍 ID를 속성으로 추가 (개인키 -> 공개키 ID 추가, 공개키 -> 개인키 ID 추가)
		List<KeyAttrListVo> priKeyAttrList = createAsymmetricKeyService.setKeyProfileAttrListToKeyAttrList(priKeyProfileAttrList);
		createAsymmetricKeyService.setKeyAttr(priKeyAttrList, EnumKeyAttributeId.KEY_ATTR_ID_KEY_PAIR_ID.getKey(), hsmResponse.getPublicKeyId());
		createAsymmetricKeyService.setKeyAttr(priKeyAttrList, EnumKeyAttributeId.KEY_ATTR_ID_KEY_LABEL.getKey(), request.getKeyLabel());
		
		List<KeyAttrListVo> pubKeyAttrList = createAsymmetricKeyService.setKeyProfileAttrListToKeyAttrList(pubKeyProfileAttrList);
		createAsymmetricKeyService.setKeyAttr(pubKeyAttrList, EnumKeyAttributeId.KEY_ATTR_ID_KEY_PAIR_ID.getKey(), hsmResponse.getPrivateKeyId());
		createAsymmetricKeyService.setKeyAttr(pubKeyAttrList, EnumKeyAttributeId.KEY_ATTR_ID_KEY_LABEL.getKey(), request.getKeyLabel());
		
		// 8. 응답 값으로 받은 키 정보 및 이력정보 DB 저장
		createAsymmetricKeyService.storeAsymmetricKeyInfo(request.getPrivateKeyProfileId(), hsmResponse.getPrivateKeyId(), hsmServiceInfo, priKeyAttrList); // 개인 키
		createAsymmetricKeyService.storeAsymmetricKeyInfo(request.getPublicKeyProfileId(), hsmResponse.getPublicKeyId(), hsmServiceInfo, pubKeyAttrList);	  // 공개 키

		// 9. 결과 데이터 응답
		CreateAsymmetricKeyResponse response = new CreateAsymmetricKeyResponse(request);
		response.setResult(hsmResponse.getResult());
		response.setPrivateKeyId(hsmResponse.getPrivateKeyId());
		response.setPublicKeyId(hsmResponse.getPublicKeyId());
		response.setPublicKeyData(hsmResponse.getPublicKeyData());
		return response;
	}
}
