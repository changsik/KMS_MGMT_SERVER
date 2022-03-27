package com.tmonet.kmipmanager.operation.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.tmonet.common.object.Version;
import com.tmonet.common.util.ConfigLoadData;
import com.tmonet.common.util.RequestUtil;
import com.tmonet.kmipmanager.common.service.KmipService;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumKeyAttributeId;
import com.tmonet.kms.mgmt.common.kmsenum.EnumKeyObjectType;
import com.tmonet.kms.mgmt.common.object.ApiInfo;
import com.tmonet.kms.mgmt.key.model.CreateSymmetricKeyRequest;
import com.tmonet.kms.mgmt.key.model.CreateSymmetricKeyResponse;
import com.tmonet.kms.mgmt.keyprofile.model.RegisterKeyProfileRequest;
import com.tmonet.kms.mgmt.keyprofile.model.RegisterKeyProfileResponse;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyAttribute;

@Service
public class KmipCreateSymmetricKeyService {

	private static final Logger logger = LoggerFactory.getLogger(KmipCreateSymmetricKeyService.class);

	@Autowired
	private KmipService kmipService;

	public String registerSymmetricKeyProfile(List<KeyAttribute> listKeyAttribute) {

		ApiInfo apiInfo = new ApiInfo();
		String apiId = "KMS.KEY.PF.REG.01";
		Version version = new Version(1, 0, 0);
		apiInfo.setApiId(apiId);
		apiInfo.setApiVersion(version);

		// 속성 리스트에 추가 세팅
		KeyAttribute objType = new KeyAttribute();
		objType.setKeyAttributeId(EnumKeyAttributeId.KEY_ATTR_ID_KEY_OBJ_TYPE.getKey());
		objType.setKeyAttributeValue(EnumKeyObjectType.KEY_OBJ_TYPE_SYMMETRIC_KEY.getKey());
		listKeyAttribute.add(objType);

		KeyAttribute canExportKey = new KeyAttribute();
		canExportKey.setKeyAttributeId(EnumKeyAttributeId.KEY_ATTR_ID_EXPORT_KEY.getKey());
		canExportKey.setKeyAttributeValue("1");
		listKeyAttribute.add(canExportKey);

		RegisterKeyProfileRequest regKeyProfileRequest = new RegisterKeyProfileRequest();
		regKeyProfileRequest.setApiInfo(apiInfo);
		regKeyProfileRequest.setKeyProfileName("AES");
		regKeyProfileRequest.setListKeyAttribute(listKeyAttribute);

		String serverUrl = ConfigLoadData.getKmsServerUrl();
		String regKeyProfileUrl = serverUrl + "/kms/key/profile";

		HttpHeaders headers = kmipService.createHeaders(HttpMethod.POST, regKeyProfileUrl);

		RegisterKeyProfileResponse response = new RegisterKeyProfileResponse();

		try {
			response = RequestUtil.restTemplateExchange(regKeyProfileUrl, regKeyProfileRequest,
					RegisterKeyProfileResponse.class, HttpMethod.POST, headers);
			logger.debug("RegisterKeyProfileResponse : {}", response.toString());
		} catch (Exception e) {
			logger.error("registerSymmetricKeyProfile 에러", regKeyProfileRequest.toString());
			throw new KMSException("ER1000", KMSErrorCode.INTERNAL_SERVER_ERROR);
		}

		return response.getKeyProfileId();
	}

	public String createSymmetricKey(String keyProfileId, String serviceId) {

		ApiInfo apiInfo = new ApiInfo();
		String apiId = "KMS.SK.REG.01";
		Version version = new Version(1, 0, 0);
		apiInfo.setApiId(apiId);
		apiInfo.setApiVersion(version);

		CreateSymmetricKeyRequest createSymmetricKeyRequest = new CreateSymmetricKeyRequest();
		createSymmetricKeyRequest.setApiInfo(apiInfo);
		createSymmetricKeyRequest.setKeyProfileId(keyProfileId);
		createSymmetricKeyRequest.setServiceId(serviceId);

		String serverUrl = ConfigLoadData.getKmsServerUrl();
		String createSymmetricKeyUrl = serverUrl + "/kms/symmetrickey/create";

		HttpHeaders headers = new HttpHeaders();
		headers = kmipService.createHeaders(HttpMethod.POST, createSymmetricKeyUrl);

		CreateSymmetricKeyResponse response = new CreateSymmetricKeyResponse(createSymmetricKeyRequest);

		try {
			response = RequestUtil.restTemplateExchange(createSymmetricKeyUrl, createSymmetricKeyRequest,
					CreateSymmetricKeyResponse.class, HttpMethod.POST, headers);
			logger.debug("CreateSymmetricKey : {}", response.toString());
		} catch (Exception e) {
			logger.error("createSymmetricKey 에러", createSymmetricKeyRequest.toString());
			throw new KMSException("ER1000", KMSErrorCode.INTERNAL_SERVER_ERROR);
		}

		return response.getKeyId();
	}

}
