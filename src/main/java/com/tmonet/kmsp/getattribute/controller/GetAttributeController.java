package com.tmonet.kmsp.getattribute.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tmonet.common.object.Code;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.kmsenum.EnumUsageStatus;
import com.tmonet.kms.mgmt.common.vo.KeyListVo;
import com.tmonet.kms.mgmt.keyprofile.model.SelectKeyProfileResponse;
import com.tmonet.kms.mgmt.keyprofile.service.SelectKeyProfileService;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyAttribute;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyProfile;
import com.tmonet.kms.mgmt.keyprofile.vo.KeyProfileInfoVo;
import com.tmonet.kmsp.common.Attributes;
import com.tmonet.kmsp.common.vo.KMIPResponseVo;
import com.tmonet.kmsp.common.ObjectType;
import com.tmonet.kmsp.common.service.KMIPResponseService;
import com.tmonet.kmsp.getattribute.model.GetAttributeRequest;
import com.tmonet.kmsp.getattribute.service.GetAttributeService;
import com.tmonet.kmsp.getattribute.vo.AttributeVo;
import com.tmonet.kmsp.getattribute.vo.GetAttributeResponsePayloadVo;
import com.tmonet.kmsp.common.vo.TypeValueVo;

@RestController
@RequestMapping("/kmsp")
public class GetAttributeController {

	private static final Logger logger = LoggerFactory.getLogger(GetAttributeController.class);

	@Autowired
	private SelectKeyProfileService selectKeyProfileService;
	
	@Autowired
	private GetAttributeService getAttributeService;
	
	@Autowired
	private KMIPResponseService responseService;

	/**
	 * 
	 * @param keyProfileId
	 * @param request
	 * @param errors
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = { "/get/attributes"})
	public @ResponseBody KMIPResponseVo getAttributeController(@PathVariable(required = false) String keyProfileId,
			@RequestBody GetAttributeRequest request, Errors errors) throws Exception {

		// 1. 요청 파라미터 validation 체크
		if (errors.hasErrors()) {
			throw new KMSException(KMSErrorCode.BAD_REQUEST);
		}
		
		logger.info("GetAttributeRequest : "+request);
		
		KeyListVo keyList = getAttributeService.selectKeyList(request.getRequestPayload().getUniqueIdentifier().getValue());
		
		logger.info("keyList Profile ID : "+keyList.getPROFILE_ID());

		// 2. 키 프로파일 조회
		//List<KeyProfileInfoVo> listProfile = service.selectKeyProfileInfo(keyProfileId);
		List<KeyProfileInfoVo> listProfile = selectKeyProfileService.selectKeyProfileInfo(keyList.getPROFILE_ID());
		
		// 3. 조회 결과 전송
		SelectKeyProfileResponse response = new SelectKeyProfileResponse(request);

		List<KeyProfile> listKeyProfile = new ArrayList<KeyProfile>();
		try {
			listKeyProfile = new ArrayList<KeyProfile>();

			for (KeyProfileInfoVo vo : listProfile) {
				KeyProfile keyProfile = new KeyProfile();
				keyProfile.setKeyProfileId(vo.getPROFILE_ID());
				keyProfile.setListKeyAttribute(selectKeyProfileService.selectAllKeyProfileAttrList(vo.getPROFILE_ID()));
				Code code = new Code();
				if (vo.getSTATUS() != null) {
					for (EnumUsageStatus status : EnumUsageStatus.values()) {
						if (status.getKey().equals(vo.getSTATUS())) {
							code.setCode(status.getKey());
							code.setName(status.getValue());
						}
					}
					keyProfile.setUsageStatus(code);
				}
				keyProfile.setRegDatetime(vo.getREG_DTTM().getTime());
				if (vo.getUPT_DTTM() != null) {
					keyProfile.setLaseUpdateDatetime(vo.getUPT_DTTM().getTime());
				}

				listKeyProfile.add(keyProfile);

			}

			response.setListKeyProfile(listKeyProfile);

		} catch (KMSException e) {
			throw new KMSException(e.getCode());
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.DATABASE_CONNECTION_FAILURE);
		}
		
		if(listKeyProfile.size() > 1) {
			throw new Exception("keyProfile is more than one.");
		}
		
		
		// 4. responsePayload 생성
		GetAttributeResponsePayloadVo responsePayload = new GetAttributeResponsePayloadVo();
		TypeValueVo uniqueIdentifier = new TypeValueVo("TextString", request.getRequestPayload().getUniqueIdentifier().getValue());
		AttributeVo attributes = new AttributeVo();
		
		KeyProfile keyProfile = listKeyProfile.get(0);
		List<KeyAttribute> listKeyAttribute = keyProfile.getListKeyAttribute();
		
		for (KeyAttribute vo : listKeyAttribute) {
			logger.info("keyAttribute : " + vo.getKeyAttributeId()+" "+vo.getKeyAttributeValue());
			
			String KMIPAttName = Attributes.getKMIPAttrName(vo.getKeyAttributeId());
			logger.info("KMIPAttName : "+KMIPAttName);
			
			makeKMIPAttributesTypeValue(KMIPAttName, vo.getKeyAttributeValue(), attributes);
		}
		
		responsePayload.setUniqueIdentifier(uniqueIdentifier);
		responsePayload.setAttributes(attributes);
		
		// 5. 결과 데이터 응답	
		String operation = request.getOperation().getValue();
		return responseService.getResponseMessage(operation, responsePayload, null);
		
	}
	
	public void makeKMIPAttributesTypeValue(String kmipAttName, String value, AttributeVo attributes) {
		switch(kmipAttName) {
			case "Extractable" :
				TypeValueVo attribute = new TypeValueVo(kmipAttName, Attributes.getKMIPExtratorValues(value));
				attributes.setExtractable(attribute);
				return;
			
			case "CryptographicAlgorithm" :
				attribute = new TypeValueVo(kmipAttName, value);
				attributes.setCryptographicAlgorithm(attribute);
				return;
				
			case "CryptographicLength" :
				attribute = new TypeValueVo(kmipAttName, value);
				attributes.setCryptographicLength(attribute);
				return;
				
			case "ObjectType" :
				attribute = new TypeValueVo(kmipAttName, ObjectType.getKMIPObjectType(value));
				attributes.setObjectType(attribute);
				return;
				
			case "CryptographicUsageMask" :
				String crypMasks = Attributes.getKMIPCryptographicUsageMaskValues(value);
				attribute = new TypeValueVo(kmipAttName, crypMasks);
				attributes.setCryptographicUsageMask(attribute);
				return;
		}
		return;
	}
}
