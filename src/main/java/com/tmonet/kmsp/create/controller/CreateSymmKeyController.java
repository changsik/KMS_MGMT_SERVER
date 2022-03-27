package com.tmonet.kmsp.create.controller;

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
import com.tmonet.kms.mgmt.key.model.hsm.HsmCreateSymmetricKeyRequest;
import com.tmonet.kms.mgmt.key.model.hsm.HsmCreateSymmetricKeyResponse;
import com.tmonet.kms.mgmt.key.service.CreateSymmetricKeyService;
import com.tmonet.kms.mgmt.key.vo.KeyProfileAttrListVo;
import com.tmonet.kms.mgmt.keyprofile.model.RegisterKeyProfileRequest;
import com.tmonet.kms.mgmt.keyprofile.model.RegisterKeyProfileResponse;
import com.tmonet.kmsp.common.Constants;
import com.tmonet.kmsp.common.service.CommonService;
import com.tmonet.kmsp.common.service.KMIPResponseService;
import com.tmonet.kmsp.common.vo.KMIPResponseVo;
import com.tmonet.kmsp.common.vo.TypeValueVo;
import com.tmonet.kmsp.create.model.CreateSymmetricKeyRequest;
import com.tmonet.kmsp.create.vo.CreateSymmKeyResponsePayloadVo;

@RestController("create.symmetricKey")
@RequestMapping("/kmsp")
public class CreateSymmKeyController {
	
	private static final Logger logger = LoggerFactory.getLogger(CreateSymmKeyController.class);
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private KMIPResponseService responseService;
	
	@Autowired
	private CreateSymmetricKeyService createSymmetricKeyService;
	
	/**
	 * Create Symmetric Key
	 * 
	 * @param request
	 * @param errors
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/create/symmetrickey")
	public @ResponseBody KMIPResponseVo createSymmetricKey(
			@RequestBody CreateSymmetricKeyRequest request, Errors errors) throws Exception {
		
		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. Get KeyProfileId, Get ServiceId
		String algorithm = request.getRequestPayload().getAttributes().getCryptographicAlgorithm().getValue();
		String keyLength = request.getRequestPayload().getAttributes().getCryptographicLength().getValue();
		String objectType = request.getRequestPayload().getObjectType().getValue();
		String usageMask = "0000001F";
		String extractable = "";
		
		RegisterKeyProfileRequest regKeyProfileVO = commonService.makeRegisterKeyProfileParams(algorithm, keyLength, objectType, usageMask, extractable);
		RegisterKeyProfileResponse regKeyProfileResultVo = commonService.registerKeyProfile(regKeyProfileVO);
		
		String serviceId = commonService.getServiceId();
		String keyProfileId = regKeyProfileResultVo.getKeyProfileId();
		String keyLabel = "1234";
		
		// 3. 키 프로파일 유효성 체크		
		createSymmetricKeyService.checkKeyProfileStatus(keyProfileId);
		
		// 4. 키 프로파일 속성 리스트 정보 획득
		List<KeyProfileAttrListVo> keyProfileAttrList = createSymmetricKeyService.selectKeyProfileAttrList(keyProfileId);
						
		// 5. HSM 제어 서버 정보 획득
		HsmServiceInfoVo hsmServiceInfo = createSymmetricKeyService.getHsmServiceInfo(serviceId);
				
		// 6. HSM 제어 서버 대칭키 생성 요청 데이터 생성
		HsmCreateSymmetricKeyRequest hsmRequest = createSymmetricKeyService.makeHsmCreateSymmetricKeyRequestData(hsmServiceInfo.getPARTITION_ID(), keyProfileAttrList);
		
		// 7. HSM 제어 서버에 대칭 키 생성 요청
		HsmCreateSymmetricKeyResponse hsmResponse = new HsmCreateSymmetricKeyResponse(hsmRequest);
		try {
			String hsmServerUrl = "http://" + hsmServiceInfo.getSLB_IP_ADDR() + ":" + hsmServiceInfo.getSLB_PORT() + "/hsm/symmetrickey/create";
			RestTemplate rt = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
			hsmResponse = rt.postForObject(hsmServerUrl, hsmRequest, HsmCreateSymmetricKeyResponse.class);
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.HSM_SERVER_ERROR);
		}
		
		// 8. 키 프로파일 속성값을 키 속성값으로 설정 후 추가 속성값 추가
		List<KeyAttrListVo> keyAttrList = createSymmetricKeyService.setKeyProfileAttrListToKeyAttrList(keyProfileAttrList);
		createSymmetricKeyService.setKeyAttr(keyAttrList, EnumKeyAttributeId.KEY_ATTR_ID_KEY_LABEL.getKey(), keyLabel);
					
		// 9. 응답 값으로 받은 키 정보 및 이력정보 DB 저장
		createSymmetricKeyService.storeSymmetricKeyInfo(keyProfileId, hsmResponse.getKeyId(), hsmServiceInfo, keyAttrList);

		// 10. responsePayload 생성
		CreateSymmKeyResponsePayloadVo responsePayload = new CreateSymmKeyResponsePayloadVo();
		TypeValueVo objectTypeVo = new TypeValueVo(Constants.KMIP_TYPE_ENUM, objectType);
		TypeValueVo uniqueIdentifier = new TypeValueVo(Constants.KMIP_TYPE_TEXT, hsmResponse.getKeyId());
		responsePayload.setObjectType(objectTypeVo);
		responsePayload.setUniqueIdentifier(uniqueIdentifier);

		// 11. 결과 데이터 응답	
		String operation = request.getOperation().getValue();
		ApiResult apiResult = hsmResponse.getResult(); 
		return responseService.getResponseMessage(operation, responsePayload, apiResult);
	}
}
