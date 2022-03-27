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
import com.tmonet.kms.mgmt.key.model.ImportSymmetricKeyRequest;
import com.tmonet.kms.mgmt.key.model.ImportSymmetricKeyResponse;
import com.tmonet.kms.mgmt.key.model.hsm.HsmImportSymmetricKeyRequest;
import com.tmonet.kms.mgmt.key.model.hsm.HsmImportSymmetricKeyResponse;
import com.tmonet.kms.mgmt.key.service.ImportSymmetricKeyService;
import com.tmonet.kms.mgmt.key.vo.KeyProfileAttrListVo;

@RestController
@RequestMapping("/kms")
public class ImportSymmetricKeyController {

//	private static final Logger logger = LoggerFactory.getLogger(ImportSymmetricKeyController.class);

	@Autowired
	private ImportSymmetricKeyService importSymmetricKeyService;

	@PostMapping("/symmetrickey/import")
	public @ResponseBody ImportSymmetricKeyResponse importSymmetricKey(
			@RequestBody @Validated ImportSymmetricKeyRequest request, Errors errors) {
		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 키 프로파일 유효성 체크
		importSymmetricKeyService.checkKeyProfileStatus(request.getKeyProfileId());
		
		// 3. HSM 제어 서버 정보 획득
		HsmServiceInfoVo hsmServiceInfo = importSymmetricKeyService.getHsmServiceInfo(request.getServiceId());

		// 4. 키 프로파일 속성 리스트 정보 획득
		List<KeyProfileAttrListVo> keyProfileAttrList = importSymmetricKeyService.selectKeyProfileAttrList(request.getKeyProfileId());
		
		// 5. HSM 제어 서버 대칭키 생성 요청 데이터 생성
		HsmImportSymmetricKeyRequest hsmRequest = importSymmetricKeyService.makeHsmImportSymmetricKeyRequestData(request, hsmServiceInfo.getPARTITION_ID(), keyProfileAttrList);
		
		// 6. HSM 제어 서버에 대칭 키 생성 요청
		HsmImportSymmetricKeyResponse hsmResponse = new HsmImportSymmetricKeyResponse(hsmRequest);
		try {
			String hsmServerUrl = "http://" + hsmServiceInfo.getSLB_IP_ADDR() + ":" + hsmServiceInfo.getSLB_PORT() + "/hsm/symmetrickey/import";
			RestTemplate rt = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
			hsmResponse = rt.postForObject(hsmServerUrl, hsmRequest, HsmImportSymmetricKeyResponse.class);
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.HSM_SERVER_ERROR);
		}
		
		// 7. 키 프로파일 속성값을 키 속성값으로 설정 후 추가 속성값 추가
		List<KeyAttrListVo> keyAttrList = importSymmetricKeyService.setKeyProfileAttrListToKeyAttrList(keyProfileAttrList);
		importSymmetricKeyService.setKeyAttr(keyAttrList, EnumKeyAttributeId.KEY_ATTR_ID_KEY_LABEL.getKey(), request.getKeyLabel());
		
		// 8. 응답 값으로 받은 키 정보 및 이력정보 DB 저장
		importSymmetricKeyService.storeSymmetricKeyInfo(request.getKeyProfileId(), hsmResponse.getKeyId(), hsmServiceInfo, keyAttrList);
		
		// 9. 결과 데이터 응답
		ImportSymmetricKeyResponse response = new ImportSymmetricKeyResponse(request);
		response.setResult(hsmResponse.getResult());
		response.setKeyId(hsmResponse.getKeyId()); 
		return response;
	}
}
