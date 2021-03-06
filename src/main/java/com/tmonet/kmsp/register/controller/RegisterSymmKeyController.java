package com.tmonet.kmsp.register.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumKeyAttributeId;
import com.tmonet.kms.mgmt.common.object.ApiResult;
import com.tmonet.kms.mgmt.common.vo.HsmServiceInfoVo;
import com.tmonet.kms.mgmt.common.vo.KeyAttrListVo;
import com.tmonet.kms.mgmt.key.model.ImportSymmetricKeyRequest;
import com.tmonet.kms.mgmt.key.model.ImportSymmetricKeyResponse;
import com.tmonet.kms.mgmt.key.model.hsm.HsmImportSymmetricKeyRequest;
import com.tmonet.kms.mgmt.key.model.hsm.HsmImportSymmetricKeyResponse;
import com.tmonet.kms.mgmt.key.service.ImportSymmetricKeyService;
import com.tmonet.kms.mgmt.key.vo.KeyProfileAttrListVo;
import com.tmonet.kms.mgmt.keyprofile.model.RegisterKeyProfileRequest;
import com.tmonet.kms.mgmt.keyprofile.model.RegisterKeyProfileResponse;
import com.tmonet.kmsp.common.Constants;
import com.tmonet.kmsp.common.service.CommonService;
import com.tmonet.kmsp.common.service.KMIPResponseService;
import com.tmonet.kmsp.common.vo.KMIPResponseVo;
import com.tmonet.kmsp.common.vo.TypeValueVo;
import com.tmonet.kmsp.register.vo.RegisterSymmKeyResponsePayloadVo;
import com.tmonet.kmsp.register.model.RegisterSymmKeyRequest;

@RestController("register.symmetricKey")
@RequestMapping("/kmsp")
public class RegisterSymmKeyController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterSymmKeyController.class);
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private KMIPResponseService responseService;
	
	@Autowired
	private ImportSymmetricKeyService importSymmetricKeyService;
	
	/**
	 * Register Symmetric Key
	 * 
	 * @param request
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/register/symmetrickey")
	public @ResponseBody KMIPResponseVo registerSymmetricKey(@RequestBody RegisterSymmKeyRequest request) throws Exception {
		
		String objectType = request.getRequestPayload().getObjectType().getValue();
		String usageMask = request.getRequestPayload().getAttributes().getCryptographicUsageMask().getValue();
		String algorithm = request.getRequestPayload().getAttributes().getCryptographicAlgorithm().getValue();
		String keyLength = request.getRequestPayload().getAttributes().getCryptographicLength().getValue();
		String activateDate = request.getRequestPayload().getAttributes().getActivationDate().getValue();
		String keyValue = request.getRequestPayload().getSymmetricKey().getKeyBlock().getKeyValue().getKeyMaterial().getValue();
		String extractable = "";
		
		// 1. Make keyProfile params.
		RegisterKeyProfileRequest keyProfile;
		keyProfile = commonService.makeRegisterKeyProfileParams(algorithm, keyLength, objectType, usageMask, extractable);
		
		// 2. Register keyProfile.
		RegisterKeyProfileResponse keyProfileResponse = commonService.registerKeyProfile(keyProfile);
		String keyProfileId = keyProfileResponse.getKeyProfileId();
		
		// 3. Get serviceId for "GRP00001" & "Authrium01".
		String serviceId = commonService.getServiceId();
		
		logger.info("keyProfileId : "+keyProfileId);
		logger.info("serviceId : "+serviceId);

		ImportSymmetricKeyRequest importSymmetricKey = new ImportSymmetricKeyRequest();
		
		importSymmetricKey.setKeyProfileId(keyProfileId);
		importSymmetricKey.setServiceId(serviceId);
		importSymmetricKey.setKeyLabel("symmetricKey");
		importSymmetricKey.setSymmetricKeyData(keyValue);
		
		// 4. Register SymmetricKey to HSM.
		ImportSymmetricKeyResponse symetricKeyResponse = importSymmetricKey(importSymmetricKey);
		
		// 5. responsePayload ??????
		RegisterSymmKeyResponsePayloadVo responsePayload = new RegisterSymmKeyResponsePayloadVo();
		TypeValueVo uniqueIdentifier = new TypeValueVo(Constants.KMIP_TYPE_TEXT, symetricKeyResponse.getKeyId());
		responsePayload.setUniqueIdentifier(uniqueIdentifier);
		
		// 6. ?????? ????????? ??????
		String operation = request.getOperation().getValue();
		ApiResult apiResult = symetricKeyResponse.getResult(); 
		
		return responseService.getResponseMessage(operation, responsePayload, apiResult);
	}

	public ImportSymmetricKeyResponse importSymmetricKey(ImportSymmetricKeyRequest request) throws Exception {
		// 1. ?????? ???????????? validation ??????
		//if (errors.hasErrors()) {
		//	throw new KMSException(KMSErrorCode.BAD_REQUEST);
		//}

		// 2. ??? ???????????? ????????? ??????
		importSymmetricKeyService.checkKeyProfileStatus(request.getKeyProfileId());
		
		// 3. HSM ?????? ?????? ?????? ??????
		HsmServiceInfoVo hsmServiceInfo = importSymmetricKeyService.getHsmServiceInfo(request.getServiceId());

		// 4. ??? ???????????? ?????? ????????? ?????? ??????
		List<KeyProfileAttrListVo> keyProfileAttrList = importSymmetricKeyService.selectKeyProfileAttrList(request.getKeyProfileId());
		
		// 5. HSM ?????? ?????? ????????? ?????? ?????? ????????? ??????
		HsmImportSymmetricKeyRequest hsmRequest = importSymmetricKeyService.makeHsmImportSymmetricKeyRequestData(request, hsmServiceInfo.getPARTITION_ID(), keyProfileAttrList);
		
		// 6. HSM ?????? ????????? ?????? ??? ?????? ??????
		HsmImportSymmetricKeyResponse hsmResponse = new HsmImportSymmetricKeyResponse(hsmRequest);
		try {
			String hsmServerUrl = "http://" + hsmServiceInfo.getSLB_IP_ADDR() + ":" + hsmServiceInfo.getSLB_PORT() + "/hsm/symmetrickey/import";
			RestTemplate rt = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
			hsmResponse = rt.postForObject(hsmServerUrl, hsmRequest, HsmImportSymmetricKeyResponse.class);
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.HSM_SERVER_ERROR);
		}
		
		// 7. ??? ???????????? ???????????? ??? ??????????????? ?????? ??? ?????? ????????? ??????
		List<KeyAttrListVo> keyAttrList = importSymmetricKeyService.setKeyProfileAttrListToKeyAttrList(keyProfileAttrList);
		importSymmetricKeyService.setKeyAttr(keyAttrList, EnumKeyAttributeId.KEY_ATTR_ID_KEY_LABEL.getKey(), request.getKeyLabel());
		
		// 8. ?????? ????????? ?????? ??? ?????? ??? ???????????? DB ??????
		importSymmetricKeyService.storeSymmetricKeyInfo(request.getKeyProfileId(), hsmResponse.getKeyId(), hsmServiceInfo, keyAttrList);
		
		// 9. ?????? ????????? ??????
		ImportSymmetricKeyResponse response = new ImportSymmetricKeyResponse(request);
		response.setResult(hsmResponse.getResult());
		response.setKeyId(hsmResponse.getKeyId()); 
		return response;
	}

}