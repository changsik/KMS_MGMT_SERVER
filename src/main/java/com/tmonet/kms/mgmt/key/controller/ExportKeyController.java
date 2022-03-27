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
import com.tmonet.kms.mgmt.common.vo.HsmServiceInfoVo;
import com.tmonet.kms.mgmt.common.vo.KeyAttrListVo;
import com.tmonet.kms.mgmt.common.vo.KeyListVo;
import com.tmonet.kms.mgmt.key.model.ExportKeyRequest;
import com.tmonet.kms.mgmt.key.model.ExportKeyResponse;
import com.tmonet.kms.mgmt.key.model.hsm.HsmExportKeyRequest;
import com.tmonet.kms.mgmt.key.model.hsm.HsmExportKeyResponse;
import com.tmonet.kms.mgmt.key.service.ExportKeyService;

@RestController
@RequestMapping("/kms")
public class ExportKeyController {

//	private static final Logger logger = LoggerFactory.getLogger(ExportKeyController.class);

	@Autowired
	private ExportKeyService exportKeyService;

	@PostMapping("/key/export")
	public @ResponseBody ExportKeyResponse exportKey(@RequestBody @Validated ExportKeyRequest request, Errors errors) {
		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}

		// 2. 암호화 될 키 정보 획득 및 상태, 용도 체크
		KeyListVo keyList = exportKeyService.selectKeyList(request.getKeyId());
		List<KeyAttrListVo> keyAttrList = exportKeyService.selectKeyAttrList(keyList);
		exportKeyService.checkKeyStatus(keyList, keyAttrList);
		
		// 3. 키 암호화용 키 정보 획득 및 상태, 용도 체크 
		KeyListVo keyEncryptionKeyList = exportKeyService.selectKeyList(request.getKeyEncryptionKeyId());
		List<KeyAttrListVo> keyEncryptionKeyAttrList = exportKeyService.selectKeyAttrList(keyEncryptionKeyList);
		exportKeyService.checkKeyEncryptionKeyStatus(keyEncryptionKeyList, keyEncryptionKeyAttrList);
		
		// 4. HSM 제어 서버 정보 획득
		HsmServiceInfoVo hsmServiceInfo = exportKeyService.getHsmServiceInfo(request.getServiceId());
		
		// 5. HSM 제어 서버 대칭키 추출 요청 데이터 생성
		HsmExportKeyRequest hsmRequest = exportKeyService.makeHsmExportKeyRequestData(request, hsmServiceInfo.getPARTITION_ID());
		
		// 6. HSM 제어 서버에 대칭키 추출 요청
		HsmExportKeyResponse hsmResponse = new HsmExportKeyResponse(hsmRequest);
		try {
			String hsmServerUrl = "http://" + hsmServiceInfo.getSLB_IP_ADDR() + ":" + hsmServiceInfo.getSLB_PORT() + "/hsm/key/export";
			RestTemplate rt = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
			hsmResponse = rt.postForObject(hsmServerUrl, hsmRequest, HsmExportKeyResponse.class);
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.HSM_SERVER_ERROR);
		}
		
		// 7. 키 사용내역 정보 DB 저장
		exportKeyService.storeKeyInfo(request.getKeyId(), hsmServiceInfo);
		exportKeyService.storeKeyInfo(request.getKeyEncryptionKeyId(), hsmServiceInfo);
		
		// 8. 결과 데이터 응답
		ExportKeyResponse response = new ExportKeyResponse(request);
		response.setResult(hsmResponse.getResult());
		response.setExportedKeyData(hsmResponse.getExportedKeyData());
		return response;
	}
}
