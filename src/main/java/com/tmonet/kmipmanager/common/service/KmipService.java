package com.tmonet.kmipmanager.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.tmonet.common.object.Version;
import com.tmonet.common.util.ConfigLoadData;
import com.tmonet.common.util.Converter;
import com.tmonet.common.util.RequestUtil;
import com.tmonet.kms.hmac.sha256.HmacSha256;
import com.tmonet.kms.mgmt.client.model.CliAuthResponse;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;
import com.tmonet.kms.mgmt.common.model.BaseRequest;
import com.tmonet.kms.mgmt.common.object.ApiInfo;
import com.tmonet.kms.mgmt.manager.mapper.ManagerMapper;

@Service
public class KmipService {

	private static final Logger logger = LoggerFactory.getLogger(KmipService.class);
	
	@Autowired
	private ManagerMapper managerMapper;
	
	
	public HttpHeaders createHeaders(HttpMethod httpMethod, String kmsReqUrl) {
		
		HttpHeaders headers = new HttpHeaders();
		
		String kmsManagerId = ConfigLoadData.getKmsManagerId();
		String key = managerMapper.selectManagerPw(kmsManagerId);
		
		String timestamp = String.valueOf(System.currentTimeMillis());
		String data = httpMethod + kmsReqUrl + timestamp;
		String userAccessToken = Converter.byteArrayToHexString(HmacSha256.hmac(key, data));
		
		headers.set("id", kmsManagerId);
		headers.set("requestTimestamp", timestamp);
		headers.set("userAccessToken", userAccessToken);
		
		return headers;
		
	}
	
	// 클라이언트 키 권한 조회
	public CliAuthResponse selectClientAuth(String clientIp, String serviceId) {
		
		ApiInfo apiInfo = new ApiInfo();
		String apiId = "KMS.CLI.AUTH.GET.01";
		Version version = new Version(1, 0, 0);
		apiInfo.setApiId(apiId);
		apiInfo.setApiVersion(version);
		
		BaseRequest baseRequest = new BaseRequest(apiInfo);
		baseRequest.setApiInfo(apiInfo);
		
		String selectClientAuthUrl = ConfigLoadData.getKmsServerUrl() + "/kms/client/auth/get/" + clientIp + "/" + serviceId;
		
		HttpHeaders headers = createHeaders(HttpMethod.POST, selectClientAuthUrl);
		
		CliAuthResponse cliAuthResponse = new CliAuthResponse(baseRequest);
		
		try {
			cliAuthResponse = RequestUtil.restTemplateExchange(selectClientAuthUrl, baseRequest, CliAuthResponse.class, HttpMethod.POST, headers);
			logger.debug("SelectCliAuthResponse : {}", cliAuthResponse.toString());
		} catch (Exception e) {
			logger.error("selectClientAuth 에러", e);
			throw new KMSException("ER1000", KMSErrorCode.INTERNAL_SERVER_ERROR);
		}
		
		return cliAuthResponse;
		
	}
	
}
