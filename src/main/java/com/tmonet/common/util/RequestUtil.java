package com.tmonet.common.util;

import java.net.SocketTimeoutException;

import org.apache.http.conn.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

public class RequestUtil {

	private static final Logger logger = LoggerFactory.getLogger(RequestUtil.class);
	
	/**
	 * @param <T>
	 * @param url
	 * @param request
	 * @param tClass
	 * @param method
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	
	public static <T> T restTemplateExchange(String url, Object request, Class<T> tClass, HttpMethod method, HttpHeaders headers) throws Exception {
		logger.info("request : {}\n{}\n", url, request.toString());
		
		ResponseEntity<T> response = null;
		HttpEntity<Object> entity;
		
		
		try {
			RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
			
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			entity = new HttpEntity<Object>(request, headers);
			
			response = restTemplate.exchange(url, method, entity, tClass);
			
		}catch(ResourceAccessException e) {
			if(e.getCause() instanceof ConnectTimeoutException) {
				logger.error("KMS_MGMT - ConnectTimeoutException");
			}
			if(e.getCause() instanceof SocketTimeoutException) {
				logger.error("KMS_MGMT - SocketTimeoutException");
			}
			if(e.getCause() instanceof InterruptedException) {
				logger.error("KMS_MGMT - InterruptedException");
			}

			throw new Exception("ER7001"); // 비정상 메세지
			
		} catch (Exception ex) { // 전문 연동 에러
			logger.error("[Failure to send http to {} ", url, ex);
			throw new Exception(ex.getMessage()); // 외부서비스 연계  실패
		}	
		
		return response.getBody();
		
	}
	
}
