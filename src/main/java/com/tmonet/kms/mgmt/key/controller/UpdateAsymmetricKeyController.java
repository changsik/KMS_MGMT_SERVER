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
import com.tmonet.kms.mgmt.common.vo.KeyListVo;
import com.tmonet.kms.mgmt.key.model.UpdateAsymmetricKeyRequest;
import com.tmonet.kms.mgmt.key.model.UpdateAsymmetricKeyResponse;
import com.tmonet.kms.mgmt.key.model.hsm.HsmCreateAsymmetricKeyRequest;
import com.tmonet.kms.mgmt.key.model.hsm.HsmCreateAsymmetricKeyResponse;
import com.tmonet.kms.mgmt.key.service.UpdateAsymmetricKeyService;
import com.tmonet.kms.mgmt.key.vo.KeyProfileAttrListVo;

@RestController
@RequestMapping("/kms")
public class UpdateAsymmetricKeyController {

//	private static final Logger logger = LoggerFactory.getLogger(UpdateAsymmetricKeyController.class);

	@Autowired
	private UpdateAsymmetricKeyService updateAsymmetricKeyService;

	@PostMapping("/asymmetrickey/update")
	public @ResponseBody UpdateAsymmetricKeyResponse updateAsymmetricKey(
			@RequestBody @Validated UpdateAsymmetricKeyRequest request, Errors errors) {
		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 키 정보 획득 및 상태, 용도 체크
		//	  - 비대칭키 일 경우 KMS에서 생성된 키만 갱신하도록 함  
		//		: 개인키/공개키 ID를 모두 받아서 서로의 키 쌍 ID를 확인하여 검증
		//	  - 갱신 키 ID를 하나만 받는 경우로 설계될 경우
		//	    : 키 속성에 keyPairId 값이 있는지 여부 확인
		//	    : KMS에서 생성된 비대칭키 임을 확인 할 수 있는 별도 키 속성 부여하여 확인 
		KeyListVo priKeyList = updateAsymmetricKeyService.selectKeyList(request.getPrivateKeyId());
		updateAsymmetricKeyService.checkKeyStatus(priKeyList);
		KeyListVo pubKeyList = updateAsymmetricKeyService.selectKeyList(request.getPublicKeyId());
		updateAsymmetricKeyService.checkKeyStatus(pubKeyList);

		// 3. 키 프로파일 유효성 체크
		updateAsymmetricKeyService.checkKeyProfileStatus(priKeyList.getPROFILE_ID());
		updateAsymmetricKeyService.checkKeyProfileStatus(pubKeyList.getPROFILE_ID());

		// 4. 개인키/공개키 프로파일 속성 리스트 정보 획득
		List<KeyProfileAttrListVo> priKeyProfileAttrList = updateAsymmetricKeyService.selectKeyProfileAttrList(priKeyList.getPROFILE_ID());
		List<KeyProfileAttrListVo> pubKeyProfileAttrList = updateAsymmetricKeyService.selectKeyProfileAttrList(pubKeyList.getPROFILE_ID());

		// 5. 비대칭키 검증
		updateAsymmetricKeyService.checkAsymmetricKeyVerify(priKeyList, pubKeyList, priKeyProfileAttrList, pubKeyProfileAttrList);

		// 6. HSM 제어 서버 정보 획득
		HsmServiceInfoVo hsmServiceInfo = updateAsymmetricKeyService.getHsmServiceInfo(request.getServiceId());

		// 7. HSM 제어 서버 비대칭키 생성 요청 데이터 생성
		HsmCreateAsymmetricKeyRequest hsmRequest = updateAsymmetricKeyService.makeHsmCreateAsymmetricKeyRequestData(hsmServiceInfo.getPARTITION_ID(), priKeyProfileAttrList, pubKeyProfileAttrList);

		// 8. HSM 제어 서버에 비대칭키 생성 요청
		HsmCreateAsymmetricKeyResponse hsmResponse = new HsmCreateAsymmetricKeyResponse(hsmRequest);
		try {
			String hsmServerUrl = "http://" + hsmServiceInfo.getSLB_IP_ADDR() + ":" + hsmServiceInfo.getSLB_PORT()
					+ "/hsm/asymmetrickey/create";
			RestTemplate rt = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
			hsmResponse = rt.postForObject(hsmServerUrl, hsmRequest, HsmCreateAsymmetricKeyResponse.class);
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.HSM_SERVER_ERROR);
		}
		
		// 9. 키 프로파일 속성값을 키 속성값으로 설정 후 추가 속성값 추가
		// 		- 비대칭키 매칭되는 키 쌍 ID를 속성으로 추가 (개인키 -> 공개키 ID 추가, 공개키 -> 개인키 ID 추가)
		List<KeyAttrListVo> priKeyAttrList = updateAsymmetricKeyService.setKeyProfileAttrListToKeyAttrList(priKeyProfileAttrList);
		updateAsymmetricKeyService.setKeyAttr(priKeyAttrList, EnumKeyAttributeId.KEY_ATTR_ID_KEY_PAIR_ID.getKey(), hsmResponse.getPublicKeyId());
		updateAsymmetricKeyService.setKeyAttr(priKeyAttrList, EnumKeyAttributeId.KEY_ATTR_ID_KEY_LABEL.getKey(), request.getKeyLabel());
		
		List<KeyAttrListVo> pubKeyAttrList = updateAsymmetricKeyService.setKeyProfileAttrListToKeyAttrList(pubKeyProfileAttrList);
		updateAsymmetricKeyService.setKeyAttr(pubKeyAttrList, EnumKeyAttributeId.KEY_ATTR_ID_KEY_PAIR_ID.getKey(), hsmResponse.getPrivateKeyId());
		updateAsymmetricKeyService.setKeyAttr(pubKeyAttrList, EnumKeyAttributeId.KEY_ATTR_ID_KEY_LABEL.getKey(), request.getKeyLabel());

		// 10. 응답 값으로 받은 키 정보 및 이력정보 DB 저장
		updateAsymmetricKeyService.updateAsymmetricKeyInfo(priKeyList.getPROFILE_ID(), request.getPrivateKeyId(),
				hsmResponse.getPrivateKeyId(), hsmServiceInfo, priKeyAttrList); // 개인 키
		updateAsymmetricKeyService.updateAsymmetricKeyInfo(pubKeyList.getPROFILE_ID(), request.getPublicKeyId(),
				hsmResponse.getPublicKeyId(), hsmServiceInfo, pubKeyAttrList); // 공개 키

		// 11. 결과 데이터 응답
		UpdateAsymmetricKeyResponse response = new UpdateAsymmetricKeyResponse(request);
		response.setResult(hsmResponse.getResult());
		response.setPrivateKeyId(hsmResponse.getPrivateKeyId());
		response.setPublicKeyId(hsmResponse.getPublicKeyId());
		response.setPublicKeyData(hsmResponse.getPublicKeyData());
		return response;
	}
}
