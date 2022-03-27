package com.tmonet.kmsp.common.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmonet.kms.mgmt.keyprofile.model.RegisterKeyProfileRequest;
import com.tmonet.kms.mgmt.keyprofile.model.RegisterKeyProfileResponse;
import com.tmonet.kms.mgmt.keyprofile.service.RegisterKeyProfileService;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyAttribute;
import com.tmonet.kmsp.common.Attributes;
import com.tmonet.kmsp.common.ObjectType;
import com.tmonet.kms.mgmt.partition.service.SelectPartitionService;
import com.tmonet.kms.mgmt.partition.vo.PartitionInfoVo;

@Service
public class CommonService {
	
	@Autowired
	private RegisterKeyProfileService service;
	
	@Autowired
	private SelectPartitionService service2;

	/**
	 * Make RegisterKeyProfileRequest Param.
	 * 
	 * @param algorithm		
	 * @param keyLength
	 * @param objectType	
	 * @param usageMask
	 * @param extractable	(없으면 공백)
	 * @return
	 */
	public RegisterKeyProfileRequest makeRegisterKeyProfileParams(String algorithm, String keyLength,
			String objectType, String usageMask, String extractable) {
		
			RegisterKeyProfileRequest keyProfile = new RegisterKeyProfileRequest();
			
			keyProfile.setKeyProfileName(algorithm);
			
			KeyAttribute keyAttribute1 = new KeyAttribute();
			KeyAttribute keyAttribute2 = new KeyAttribute();
			KeyAttribute keyAttribute3 = new KeyAttribute();
			KeyAttribute keyAttribute4 = new KeyAttribute();
			KeyAttribute keyAttribute5 = new KeyAttribute();
			KeyAttribute keyAttribute6 = new KeyAttribute(); // ???
			
			keyAttribute1.setKeyAttributeId("cryptographicAlgorithm");
			keyAttribute1.setKeyAttributeValue(algorithm);
			
			keyAttribute2.setKeyAttributeId("keyLength");
			keyAttribute2.setKeyAttributeValue(keyLength);
			
			keyAttribute3.setKeyAttributeId("keyObjectType");
			keyAttribute3.setKeyAttributeValue(ObjectType.getKMSKeyObjectType(objectType));
			
			keyAttribute4.setKeyAttributeId("keyUsageMask");
			keyAttribute4.setKeyAttributeValue(Attributes.getKMSKeyUsageMaskValues(usageMask));
			
			keyAttribute5.setKeyAttributeId("canExportKey");
			keyAttribute5.setKeyAttributeValue(Attributes.getKMSCanExportValues(extractable));
			
			keyAttribute6.setKeyAttributeId("publicExponent"); // ???
			keyAttribute6.setKeyAttributeValue("010001"); // ???
			
			List<KeyAttribute> listKeyAttribute = Arrays.asList(keyAttribute1, keyAttribute2, keyAttribute3, keyAttribute4, keyAttribute5, keyAttribute6); // ???
			keyProfile.setListKeyAttribute(listKeyAttribute);
			
			return keyProfile;
		}
	
	/**
	 * Register keyProfile and return keyProfileId.
	 * 
	 * @param request
	 * @return
	 */
	public RegisterKeyProfileResponse registerKeyProfile(RegisterKeyProfileRequest request) {

		// 1. 요청 파라미터 검증
		service.checkParams(request);

		// 2. 키 프로파일 등록
		// 키 프로파일 속성 목록 등록
		// 키 프로파일 관리 이력 등록
		String regProfileId = service.registerKeyprofileInfo(request);

		// 3. 등록 결과 반환
		RegisterKeyProfileResponse response = new RegisterKeyProfileResponse(request);
		response.setKeyProfileId(regProfileId);
		
		return response;
	}
	
	/**
	 * Get serviceId for "GRP00001" & "Authrium01".
	 * 
	 * @return serviceID
	 */
	public String getServiceId() {
			List<PartitionInfoVo> listPartitionInfo = service2.selectPartitionInfo("GRP00001", "Authrium01");
			String serviceId = listPartitionInfo.get(0).getSERVICE_ID();
			
			return serviceId;
	}
}
