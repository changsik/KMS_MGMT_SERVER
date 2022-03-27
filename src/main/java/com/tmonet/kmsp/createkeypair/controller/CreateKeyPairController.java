package com.tmonet.kmsp.createkeypair.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumKeyAttributeId;
import com.tmonet.kms.mgmt.common.object.ApiResult;
import com.tmonet.kms.mgmt.common.vo.HsmServiceInfoVo;
import com.tmonet.kms.mgmt.common.vo.KeyAttrListVo;
import com.tmonet.kms.mgmt.key.model.CreateAsymmetricKeyRequest;
import com.tmonet.kms.mgmt.key.model.CreateAsymmetricKeyResponse;
import com.tmonet.kms.mgmt.key.model.hsm.HsmCreateAsymmetricKeyRequest;
import com.tmonet.kms.mgmt.key.model.hsm.HsmCreateAsymmetricKeyResponse;
import com.tmonet.kms.mgmt.key.service.CreateAsymmetricKeyService;
import com.tmonet.kms.mgmt.key.vo.KeyProfileAttrListVo;
import com.tmonet.kms.mgmt.keyprofile.model.RegisterKeyProfileRequest;
import com.tmonet.kms.mgmt.keyprofile.model.RegisterKeyProfileResponse;
import com.tmonet.kmsp.common.Constants;
import com.tmonet.kmsp.common.service.CommonService;
import com.tmonet.kmsp.common.service.KMIPResponseService;
import com.tmonet.kmsp.common.vo.KMIPResponseVo;
import com.tmonet.kmsp.common.vo.TypeValueVo;
import com.tmonet.kmsp.createkeypair.model.CreateKeyPairRequest;
import com.tmonet.kmsp.createkeypair.vo.CreateKeyPairResponsePayloadVo;

@RestController
@RequestMapping("/kmsp")
public class CreateKeyPairController {
	
	private static final Logger logger = LoggerFactory.getLogger(CreateKeyPairController.class);
	
	@Autowired
	private CreateAsymmetricKeyService createAsymmetricKeyService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private KMIPResponseService responseService;

	/**
	 * 
	 * 
	 * @param request
	 * @param errors
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/createkeypair/asymmetrickey")
	public @ResponseBody KMIPResponseVo createKeyPairController(@RequestBody CreateKeyPairRequest request, Errors errors) throws Exception {
		
		// Get Values
		String privObjectType = Constants.KEY_PRIVATE;
		String pubObjectType = Constants.KEY_PUBLIC;
		String privUsageMask = request.getRequestPayload().getPrivateKeyAttributes().getCryptographicUsageMask().getValue();
		String pubUsageMask = request.getRequestPayload().getPublicKeyAttributes().getCryptographicUsageMask().getValue();
		String algorithm = request.getRequestPayload().getCommonAttributes().getCryptographicAlgorithm().getValue();
		String keyLength = request.getRequestPayload().getCommonAttributes().getCryptographicDomainParameters().getQlength().getValue();
		String activateDate = request.getRequestPayload().getCommonAttributes().getActivationDate().getValue();
		String extractable = "";
		
		// 1. Make keyProfile Params.
		RegisterKeyProfileRequest keyProfile;
		keyProfile = commonService.makeRegisterKeyProfileParams(algorithm, keyLength, privObjectType, privUsageMask, extractable);
		
		// 2. Register keyProfile.
		RegisterKeyProfileResponse keyProfileResponse = commonService.registerKeyProfile(keyProfile);
		String privKeyProfileId = keyProfileResponse.getKeyProfileId();
		
		// 3. Get serviceId for "GRP00001" & "Authrium01".
		String serviceId = commonService.getServiceId();
		
		// 4. Make keyProfile Params.
		keyProfile = commonService.makeRegisterKeyProfileParams(algorithm, keyLength, pubObjectType, pubUsageMask, extractable);
				
		// 5. Register keyProfile.
		keyProfileResponse = commonService.registerKeyProfile(keyProfile);
		String pubKeyProfileId = keyProfileResponse.getKeyProfileId();
		
		CreateAsymmetricKeyRequest createKey = new CreateAsymmetricKeyRequest();
		createKey.setKeyLabel("AsymmetricKeys");
		createKey.setPrivateKeyProfileId(privKeyProfileId);
		createKey.setPublicKeyProfileId(pubKeyProfileId);
		createKey.setServiceId(serviceId);
		
		CreateAsymmetricKeyResponse createKeyResponse = createAsymmetricKey(createKey);
		
		// 6. responsePayload 생성
		CreateKeyPairResponsePayloadVo responsePayload = new CreateKeyPairResponsePayloadVo();
		TypeValueVo PrivateKeyuniqueIdentifier = new TypeValueVo(Constants.KMIP_TYPE_TEXT, createKeyResponse.getPrivateKeyId());
		TypeValueVo PublicKeyuniqueIdentifier = new TypeValueVo(Constants.KMIP_TYPE_TEXT, createKeyResponse.getPublicKeyId());
		responsePayload.setPrivateKeyUniqueIdentifier(PrivateKeyuniqueIdentifier);
		responsePayload.setPublicKeyUniqueIdentifier(PublicKeyuniqueIdentifier);
		
		// 7. 결과 데이터 응답	
		String operation = request.getOperation().getValue();
		ApiResult apiResult = createKeyResponse.getResult();
		return responseService.getResponseMessage(operation, responsePayload, apiResult);
	}
	
	public CreateAsymmetricKeyResponse createAsymmetricKey(CreateAsymmetricKeyRequest request) throws Exception {
		// 1. 요청 파라미터 validation 체크
		//if (errors.hasErrors()) {
		//	throw new KMSException(KMSErrorCode.BAD_REQUEST);
		//}

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
